package entites;

import entites.Veiculo;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    private List <Veiculo> veiculosEstacionados; //veiculos estacionados
    private List <Veiculo> historicoSaidas; // relatorio de quem ja saiu
    private final double valor_por_hora = 10;

    public Estacionamento() {
        this.veiculosEstacionados = new ArrayList<>();
        this.historicoSaidas = new ArrayList<>();
    }

    // 1. registrar entrada
    public String registrarEntrada(String placa, String modelo) {
        if (placa.isEmpty() || modelo.isEmpty()) {
        return "Erro: Placa e modelo são obrigatorios!";
        }

        for (Veiculo v : veiculosEstacionados){
            if (v.getPlaca().equalsIgnoreCase(placa)){
                return "Erro: Veiculo com placa" + placa + "já esta no patio!";
            }
        }

        Veiculo novoVeiculo = new Veiculo(placa, modelo);
        veiculosEstacionados.add(novoVeiculo);
        return "Sucesso: Veiculo " + placa + "entrou as: " + novoVeiculo.getEntradaFormatada();
    }

    public String registrarSaida(String placa) {
        if (placa.isEmpty()) return "Erro: Informe a placa para a saida!";
        Veiculo veiculoEncontrado = null;
        for (Veiculo v : veiculosEstacionados) {
            if (v.getPlaca().equalsIgnoreCase(placa)){
                veiculoEncontrado = v;
                break;
            }
        }

        if (veiculoEncontrado == null){
            return "Erro: Veiculo placa " + placa + "não encontrado no patio!";
        }

        LocalDateTime saida = LocalDateTime.now();
        veiculoEncontrado.setDataSaida(saida);

        long minutos = Duration.between(veiculoEncontrado.getDataEntrada(), saida).toMinutes();
        long horasCobradas = (minutos <= 60) ? 1 : (minutos / 60) + ((minutos % 60 > 0) ? 1 : 0);

        double valorTotal = (horasCobradas * valor_por_hora);

        veiculoEncontrado.setValorPago(valorTotal);

        veiculosEstacionados.remove(veiculoEncontrado);
        historicoSaidas.add(veiculoEncontrado);

        StringBuilder recibo = new StringBuilder();
        recibo.append("=== RECIBO DE SAÍDA ===\n");
        recibo.append("Placa: ").append(veiculoEncontrado.getPlaca()).append("\n");
        recibo.append("Entrada: ").append(veiculoEncontrado.getEntradaFormatada()).append("\n");
        recibo.append("Saída: ").append(veiculoEncontrado.getSaidaFormatada()).append("\n");
        recibo.append("Tempo: ").append(minutos).append(" minutos (Cobrado: ").append(horasCobradas).append("h)\n");
        recibo.append("Valor a Pagar: R$ ").append(String.format("%.2f", valorTotal)).append("\n");
        recibo.append("=======================\n");

        return recibo.toString();
    }

    public String listarVeiculosAtivos() {
        if (veiculosEstacionados.isEmpty()) {
            return "🅿️ O estacionamento está vazio no momento.";
        }
        StringBuilder lista = new StringBuilder("--- VEÍCULOS NO PÁTIO ---\n");
        for (Veiculo v : veiculosEstacionados) {
            lista.append("Placa: ").append(v.getPlaca())
                    .append(" | Modelo: ").append(v.getModelo())
                    .append(" | Entrada: ").append(v.getEntradaFormatada()).append("\n");
        }
        return lista.toString();
    }

    public String gerarRelatorio() {
        if (historicoSaidas.isEmpty()) {
            return "📉 Nenhuma saída registrada no histórico.";
        }
        StringBuilder relatorio = new StringBuilder("--- RELATÓRIO DE CAIXA (HISTÓRICO) ---\n");
        double totalFaturado = 0;
        for (Veiculo v : historicoSaidas) {
            relatorio.append("[Placa: ").append(v.getPlaca()).append("] - R$ ").append(String.format("%.2f", v.getValorPago())).append("\n");
            totalFaturado += v.getValorPago();
        }
        relatorio.append("\n>>> TOTAL GERAL FATURADO: R$ ").append(String.format("%.2f", totalFaturado)).append(" <<<");
        return relatorio.toString();


    }
}
