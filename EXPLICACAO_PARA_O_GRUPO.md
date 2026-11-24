# Roteiro para entender e apresentar

Divisão sugerida; todas devem compreender o fluxo inteiro.

## Eloisa — início, login e menu
Explique Main, Usuario e Entrada. Main cria os objetos; Usuario guarda login e senha; Entrada lê e valida. O for limita o login a três tentativas, o while mantém o menu aberto e o switch escolhe a operação. Mostre uma senha incorreta e depois admin / 1234. Mostre que tentar imprimir desconectada não pede o texto.

## Maria Rita — conexão e impressão
Explique os atributos tipo, modelo, conexao, parametro e conexaoAberta em Impressora. Só retorno zero da abertura marca a conexão como aberta. Nas opções 4 e 5 o usuário informa apenas conteúdo; parâmetros técnicos ficam fixos no código. Na opção 6 o tipo é 8 (CODE128) e todo o conteúdo é fixo. Após sucesso, o programa chama Corte(4). Texto aceita retorno não negativo; outros comandos comuns exigem zero.

## Sophia — XML, gaveta, bip e erros
Explique que as opções 7 e 8 leem os arquivos fornecidos automaticamente. Cancelamento também envia a assinatura fixa. Depois de imprimir, ambas chamam o corte. Gaveta usa parâmetros fixos e bip usa duração 10, reduzida de 50. try/catch trata arquivo ausente e problemas da DLL; a consulta de papel interpreta 5, 6 e 7 como estados, não como sucesso/erro comum.

## Demonstração conjunta
1. Testar previamente na impressora da escola com papel.
2. Login; tentativa de impressão desconectada; configurar modelo e abrir conexão.
3. Texto e QR Code: digitar somente conteúdo e observar corte.
4. Código de barras: escolher 6 e observar que imprime sem perguntas, usando tipo 8.
5. Venda e cancelamento: observar impressão e corte em cada um.
6. Gaveta disponível, bip curto, status e fechamento.

## Conceitos que podem ser perguntados
Classe é o modelo; objeto é a instância criada com new. Atributos guardam estado; métodos realizam ações. private protege detalhes internos. Interface define operações e o adaptador encaminha para a DLL. JNA permite ao Java chamar a biblioteca nativa. Os testes falsos conferem a lógica, mas não comprovam o corte físico: isso depende do teste na impressora.

Os nomes no README não concedem acesso ao GitHub. Para adicionar colaboradoras de verdade, a dona do repositório deve convidar as contas corretas. Não inventem testes físicos ou contribuições que ainda não fizeram.
