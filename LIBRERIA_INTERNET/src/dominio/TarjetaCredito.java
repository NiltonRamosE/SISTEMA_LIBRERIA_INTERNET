package dominio;

import java.util.Random;

public class TarjetaCredito {
    private int idTarjetaCredito;
    private String numeroCuenta;
    private String fechalimit;
    private double saldo;
    private String clave;
    private int token;
    private Cliente titular;

    public TarjetaCredito() {
        setToken();
    }

    public TarjetaCredito(String numeroCuenta, String fechalimit, double saldo, String clave, Cliente titular) {
        this.numeroCuenta = numeroCuenta;
        this.fechalimit = fechalimit;
        this.saldo = saldo;
        this.clave = clave;
        this.titular = titular;
    }

    
    public int getIdTarjetaCredito() {
        return idTarjetaCredito;
    }
    
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getFechalimit() {
        return fechalimit;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getClave() {
        return clave;
    }

    public int getToken() {
        return token;
    }

    public Cliente getTitular() {
        return titular;
    }
    
    private void setToken() {
        Random random = new Random();
        this.token = random.nextInt(1000);
    }

    public void setIdTarjetaCredito(int idTarjetaCredito) {
        this.idTarjetaCredito = idTarjetaCredito;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public void setFechalimit(String fechalimit) {
        this.fechalimit = fechalimit;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }
    
}
