package aplication;

import entites.Estacionamento;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public class Main extends JFrame {
    private JTextField txtPlaca;
    private JTextField txtModelo;
    private JTextArea areaSaida;

    private Estacionamento estacionamentoControle;

    public Main(){
        estacionamentoControle = new Estacionamento();

        setTitle("Sistema de Estacionamento");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelDeEntrada = new JPanel(new  GridLayout(3, 2, 10, 10));
        painelDeEntrada.setBorder(BorderFactory.createTitledBorder("Dados do veiculo"));

        painelDeEntrada.add(new JLabel("Placa:"));
        txtPlaca = new JTextField();
        painelDeEntrada.add(txtPlaca);

        painelDeEntrada.add(new JLabel("Modelo:"));
        txtModelo = new JTextField();
        painelDeEntrada.add(txtModelo);

        JButton butaoEntrada = new JButton("Entrada");
        JButton butaoSaida = new JButton("Saida");
        painelDeEntrada.add(butaoEntrada);
        painelDeEntrada.add(butaoSaida);

        add(painelDeEntrada, BorderLayout.NORTH);

        areaSaida = new JTextArea();
        areaSaida.setEditable(false);
        areaSaida.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(areaSaida);

        scroll.setBorder(BorderFactory.createTitledBorder("Painel de Informações"));

        add(scroll, BorderLayout.CENTER);

        JPanel painelBotoesSul = new JPanel(new FlowLayout());
        JButton botaoListar = new JButton("Listar Veiculos do Patio");
        JButton relatorio = new JButton("Relatorio de Faturamento");
        JButton botaoLimpar = new JButton("Limpar Tela");

        painelBotoesSul.add(botaoListar);
        painelBotoesSul.add(relatorio);
        painelBotoesSul.add(botaoLimpar);

        add(painelBotoesSul, BorderLayout.SOUTH);

        butaoEntrada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = txtPlaca.getText().trim();
                String modelo = txtModelo.getText().trim();

                String resultado = estacionamentoControle.registrarEntrada(placa,  modelo);
                areaSaida.setText(resultado);

                limparCampos();
            }
        });

        butaoSaida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = txtPlaca.getText().trim();

                String resultado = estacionamentoControle.registrarSaida(placa);
                areaSaida.setText(resultado);
                limparCampos();
            }
        });

        botaoListar.addActionListener(e -> {
            String lista = estacionamentoControle.listarVeiculosAtivos();
            areaSaida.setText(lista);
        });

        relatorio.addActionListener(e -> areaSaida.setText(estacionamentoControle.gerarRelatorio()));

        botaoLimpar.addActionListener(e -> areaSaida.setText(""));
    }

    private void limparCampos(){
        txtPlaca.setText("");
        txtModelo.setText("");
        txtPlaca.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main tela = new Main();
                tela.setVisible(true);
            }
        });
    }
}