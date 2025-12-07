# Estacionamento

Esse programa é um exemplo clássico de arquitetura em camadas (quase um MVC - Model, View, Controller). Ele separa quem guarda os dados, quem pensa/calcula e quem mostra a tela.
Aqui está a explicação detalhada de como as três partes conversam entre si:

1. A Base: Veiculo.java (O Modelo)
Essa classe é apenas uma planta (blueprint). Ela não faz contas nem mostra janelas. Ela serve apenas para definir o que É um veículo no seu sistema.
Atributos: Placa, modelo, hora de entrada, hora de saída.
Encapsulamento: Usamos private nos dados e public nos métodos get e set. Isso protege os dados do carro (ninguém pode mudar a hora de entrada na mão sem usar os métodos certos).
O que ela faz: "Eu sou um objeto que guarda informações de um carro específico."

2. O Cérebro: Estacionamento.java (A Lógica/Controle)
Aqui é onde as regras de negócio vivem. Se você quiser mudar o preço de R$ 10,00 para R$ 15,00, é aqui que você mexe.
As Listas (ArrayList):
veiculosEstacionados: É o "pátio". Guarda os objetos Veiculo que estão lá agora.
historicoSaidas: É o "arquivo morto". Quando um carro sai, ele sai da lista de cima e vem para essa, para podermos somar o faturamento depois.
Métodos Inteligentes:
registrarEntrada: Antes de adicionar, ele varre a lista (for) para ver se a placa já não existe lá dentro (evita duplicidade).
registrarSaida: É o método mais complexo.
Busca o carro pela placa.
Pega a hora de agora (LocalDateTime.now()).
Calcula a diferença (Duration.between).
Aplica a regra de cobrança (arredondamento de horas).
Gera o Recibo: Em vez de imprimir na tela, ele monta um texto gigante (String) e devolve para quem chamou.

3. A Cara: EstacionamentoGUI.java (A Visão)
Essa é a parte que o usuário vê. Ela foi construída usando a biblioteca Swing.
A Estrutura Visual (O "Sanduíche")
A interface gráfica é montada em camadas:
JFrame: A janela principal (a moldura).
JPanels: São as divisões invisíveis (Norte, Centro, Sul) que organizam os botões.
Componentes: Os botões (JButton), caixas de texto (JTextField) e rótulos (JLabel).

4.Os Eventos (ActionListener)
É aqui que a mágica acontece. O programa fica "dormindo" até você clicar em algo.
Quando você clica em "Registrar Entrada":
O Java pega o texto das caixinhas (txtPlaca.getText()).
Ele chama o "Cérebro" (estacionamentoController.registrarEntrada(...)).
O Cérebro devolve uma mensagem (ex: "Sucesso").
A GUI pega essa mensagem e joga na área preta central (areaSaida.setText()).
Resumo do Fluxo de Dados
Usuário digita "ABC-1234" e clica no botão.
GUI captura o texto e chama o método do Controlador.
Controlador cria um novo Veículo, guarda na lista e diz "OK".
GUI recebe o "OK" e mostra na tela para o Usuário.
Ficou mais claro como as peças se encaixam? Se quiser, posso explicar algum trecho específico do código (como o cálculo de horas ou a criação dos botões).
