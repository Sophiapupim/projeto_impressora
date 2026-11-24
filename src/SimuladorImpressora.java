/** Previa textual de comandos; nao reproduz a impressao fisica ou o layout SAT. */
public class SimuladorImpressora implements DispositivoImpressora {
    private boolean aberta;
    private int mostrar(String operacao) {
        if (!aberta) return -1;
        System.out.println("[SIMULACAO] " + operacao);
        return 0;
    }
    public int abrir(int tipo, String modelo, String conexao, int parametro) {
        aberta = true;
        return mostrar("Conexao virtual aberta: " + modelo + " / " + conexao);
    }
    public int fechar() { int r = mostrar("Conexao virtual fechada"); aberta = false; return r; }
    public int texto(String dados, int posicao, int estilo, int tamanho) {
        return mostrar("TEXTO (alinhamento=" + posicao + ", estilo=" + estilo + ", tamanho=" + tamanho + ")\n" + dados);
    }
    public int qrCode(String dados, int tamanho, int correcao) {
        return mostrar("QR CODE: " + dados + " | tamanho=" + tamanho + " correcao=" + correcao + " (previa do conteudo)");
    }
    public int codigoBarras(int tipo, String dados, int altura, int largura, int hri) {
        return mostrar("BARRAS: tipo=" + tipo + " dados=" + dados + " altura=" + altura + " largura=" + largura + " HRI=" + hri);
    }
    public int xmlVenda(String xml) { return mostrar("XML VENDA (bruto, sem layout fiscal):\n" + xml); }
    public int xmlCancelamento(String xml, String assinatura) {
        return mostrar("XML CANCELAMENTO (bruto, sem layout fiscal):\n" + xml + "\nAssinatura fornecida: " + !assinatura.isBlank());
    }
    public int gaveta(int pino, int ti, int tf) { return mostrar("GAVETA: pino=" + pino + " ti=" + ti + " tf=" + tf); }
    public int bip(int quantidade, int duracao, int intervalo) {
        return mostrar("BIP: quantidade=" + quantidade + " duracao=" + duracao + " intervalo=" + intervalo);
    }
    public int avancar(int linhas) { return mostrar("AVANCO: " + linhas + " linhas"); }
    public int statusPapel() { return aberta ? 5 : -1; }
    public int cortar(int avanco) { return mostrar("AVANCO " + avanco + " + -------- CORTE --------"); }
}
