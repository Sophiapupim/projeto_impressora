import java.lang.reflect.Proxy;
import java.util.*;
public class Testes {
 public static void main(String[] args) {
  if (Impressora.validarConfiguracao(1,"i9","USB",0)!=null) throw new AssertionError("USB valido");
  if (Impressora.validarConfiguracao(1,"qualquer","USB",0)==null) throw new AssertionError("Modelo invalido");
  if (Impressora.validarConfiguracao(2,"i8","abc",9600)==null) throw new AssertionError("COM invalida");
  if (Impressora.validarConfiguracao(2,"i8","COM2",123)==null) throw new AssertionError("Velocidade invalida");
  if (Impressora.validarConfiguracao(3,"i9","999.1.2.3",9100)==null) throw new AssertionError("IP invalido");
  if (Impressora.validarConfiguracao(3,"i9","192.168.0.20",9100)!=null) throw new AssertionError("IP valido");
  if (Impressora.validarConfiguracao(3,"i9","192.168.0.20",0)==null) throw new AssertionError("Porta invalida");
  List<String> chamadas = new ArrayList<>();
  List<Object[]> parametros = new ArrayList<>();
  DispositivoImpressora falso = (DispositivoImpressora) Proxy.newProxyInstance(DispositivoImpressora.class.getClassLoader(), new Class<?>[]{DispositivoImpressora.class}, (o,m,a)->{chamadas.add(m.getName());parametros.add(a);return m.getName().equals("texto")?10:m.getName().equals("statusPapel")?5:0;});
  Impressora i = new Impressora(falso);
  Main.executarMenu(new Entrada(new Scanner("4\n5\n6\n7\n8\n9\n10\n0\n")), i);
  if(!chamadas.isEmpty()) throw new AssertionError("Bloqueio desconectado");
  i.abrirConexao(); chamadas.clear(); parametros.clear();
  Main.executarMenu(new Entrada(new Scanner("4\nOla\n5\nQR teste\n6\n7\n8\n9\n10\n0\n")), i);
  if(!chamadas.equals(List.of("texto","cortar","qrCode","cortar","codigoBarras","cortar","xmlVenda","cortar","xmlCancelamento","cortar","gaveta","bip"))) throw new AssertionError(chamadas);
  if(!parametros.get(4)[0].equals(8)) throw new AssertionError("CODE128 deve ser 8");
  if(!parametros.get(11)[1].equals(10)) throw new AssertionError("Bip curto");
  for(int j:new int[]{1,3,5,7,9}) if(!parametros.get(j)[0].equals(4)) throw new AssertionError("Corte fixo");
  chamadas.clear();
  i.imprimirXml("inexistente-teste.xml",false);
  if(!chamadas.isEmpty()) throw new AssertionError("Arquivo ausente nao imprime nem corta");
  System.out.println("OK: menu simplificado, parametros fixos, cinco cortes, bloqueio e arquivo ausente verificados.");
 }
}
