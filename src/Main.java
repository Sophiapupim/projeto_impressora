import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Entrada entrada = new Entrada(new Scanner(System.in));
        boolean simulacao = args.length == 1 && args[0].equals("--simular");
        Impressora impressora = simulacao ? new Impressora(new SimuladorImpressora()) : new Impressora();
        System.out.println(simulacao ? "SIMULACAO: nenhum equipamento sera acionado." : "MODO REAL: impressora Elgin.");
        try {
            if (entrada.login(new Usuario("admin", "1234"))) {
                executarMenu(entrada, impressora);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Entrada encerrada. Saindo.");
        } finally {
            if (impressora.estaConectada()) impressora.fecharConexao();
        }
    }

    static void executarMenu(Entrada entrada, Impressora impressora) {
        while (true) {
            System.out.println("\n=== CONTROLE ELGIN ===\n" + impressora.configuracaoAtual());
            System.out.println("""
                    1  - Configurar conexao
                    2  - Abrir conexao
                    3  - Fechar conexao
                    4  - Imprimir texto
                    5  - Imprimir QR Code
                    6  - Imprimir codigo de barras
                    7  - Imprimir XML SAT
                    8  - Imprimir cancelamento SAT
                    9  - Abrir gaveta
                    10 - Sinal sonoro
                    11 - Avancar papel
                    12 - Consultar papel
                    0  - Sair
                    """);
            int opcao = entrada.inteiro("Opcao: ", 0, 12);
            // Bloqueia ANTES de perguntar texto, arquivo, quantidade ou outros dados.
            if (opcao >= 4 && !impressora.exigirConexao()) continue;
            switch (opcao) {
                case 0 -> { System.out.println("Ate logo!"); return; }
                case 1 -> configurar(entrada, impressora);
                case 2 -> impressora.abrirConexao();
                case 3 -> impressora.fecharConexao();
                case 4 -> impressora.imprimirTexto(entrada.obrigatorio("Texto: "), 1, 1, 0, 0);
                case 5 -> impressora.imprimirQRCode(entrada.obrigatorio("Texto do QR Code: "), 6, 2);
                case 6 -> impressora.imprimirCodigoBarras(8, "{A12345678912", 100, 2, 3);
                case 7 -> impressora.imprimirXml("XMLSAT.xml", false);
                case 8 -> impressora.imprimirXml("CANC_SAT.xml", true);
                case 9 -> impressora.abrirGaveta(1, 5, 5);
                case 10 -> impressora.sinalSonoro(1);
                case 11 -> impressora.avancarPapel(3);
                case 12 -> impressora.verificarPapel();
            }
        }
    }

    private static void configurar(Entrada entrada, Impressora impressora) {
        if (impressora.estaConectada()) {
            System.out.println("Feche a conexao antes de alterar a configuracao.");
            return;
        }
        while (true) {
            int tipo = entrada.inteiro("Conexao (1=USB, 2=cabo serial, 3=rede): ", 1, 3);
            String modelo = entrada.obrigatorio("Modelo escrito na impressora (i8 ou i9): ");
            String porta = tipo == 1 ? "USB" : entrada.obrigatorio(tipo == 2
                    ? "Porta do cabo serial (exemplo COM2): " : "IP da impressora (exemplo 192.168.0.20): ");
            int parametro = tipo == 1 ? 0 : entrada.inteiro(tipo == 2
                    ? "Velocidade configurada: 9600, 19200, 38400, 57600 ou 115200: "
                    : "Porta de rede configurada na impressora (exemplo 9100): ", 1, tipo == 3 ? 65535 : 115200);
            String erro = Impressora.validarConfiguracao(tipo, modelo, porta, parametro);
            if (erro != null) { System.out.println(erro + " Tente novamente."); continue; }
            impressora.configurar(tipo, modelo, porta, parametro);
            return;
        }
    }
}
