package dominio;

import java.util.ArrayList;

public class Venta {
    private int id;
    private int id_registroVenta;
    private Cliente cliente;
    private PreferenciaEnvio penvio;
    private OpcionEmpaquetado oempaquetado;
    private StateVenta estado;
    private Transporte transporte;
    private ArrayList<LineaVenta> lineaVentas;

    public Venta() {
        lineaVentas = new ArrayList<>();
    }
    
    public double total(){
        double total_final = 0;
        for(LineaVenta lv: this.getLineaVentas()){
            total_final = total_final + lv.subtotal();
        }
        return total_final;
    }

    public void agregarLineaVenta(LineaVenta lv){
        lineaVentas.add(lv);
    }
    
    public void removerLineaVenta(LineaVenta lv){
        lineaVentas.remove(lv);
    }
    
    public int getId() {
        return id;
    }

    public int getId_registroVenta() {
        return id_registroVenta;
    }

    public ArrayList<LineaVenta> getLineaVentas() {
        return lineaVentas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public PreferenciaEnvio getPenvio() {
        return penvio;
    }

    public OpcionEmpaquetado getOempaquetado() {
        return oempaquetado;
    }

    public StateVenta getEstado() {
        return estado;
    }

    public Transporte getTransporte() {
        return transporte;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setId_registroVenta(int id_registroVenta) {
        this.id_registroVenta = id_registroVenta;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public void setLineaVentas(ArrayList<LineaVenta> lineaVentas) {
        this.lineaVentas = lineaVentas;
    }

    public void setPenvio(PreferenciaEnvio penvio) {
        this.penvio = penvio;
    }

    public void setOempaquetado(OpcionEmpaquetado oempaquetado) {
        this.oempaquetado = oempaquetado;
    }

    public void setEstado(StateVenta estado) {
        this.estado = estado;
    }

    public void setTransporte(Transporte transporte) {
        this.transporte = transporte;
    }
}
