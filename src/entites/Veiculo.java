package entites;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Veiculo {
    private String modelo;
    private String placa;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private double valorPago;

    public Veiculo(String placa, String modelo) {
        this.placa = placa;
        this.modelo = modelo;
        this.dataEntrada = LocalDateTime.now();
    }

    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public LocalDateTime getDataEntrada() { return dataEntrada; }

    public void setDataSaida(LocalDateTime dataSaida) { this.dataSaida = dataSaida; }
    public void setValorPago(double valorPago) { this.valorPago = valorPago; }
    public double getValorPago() { return valorPago; }

    public String getEntradaFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dataEntrada.format(formatter);
    }

    public String getSaidaFormatada() {
        if (dataSaida == null) return "Ainda no Patio";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dataSaida.format(formatter);
    }
}
