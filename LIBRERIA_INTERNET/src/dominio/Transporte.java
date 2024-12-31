package dominio;

public class Transporte {
    private int transporte_id;
    private String placa;
    private String modelo;
    private String marca;
    private StateTransporte estado;

    public int getTransporte_id() {
        return transporte_id;
    }
    
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public StateTransporte getEstado() {
        return estado;
    }

    public void setEstado(StateTransporte estado) {
        this.estado = estado;
    }

    public void setTransporte_id(int transporte_id) {
        this.transporte_id = transporte_id;
    }
    
}

