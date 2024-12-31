package presentacion;

import dominio.Cliente;
import dominio.LineaVenta;
import dominio.StateVenta;
import dominio.TarjetaCredito;
import dominio.Venta;
import dominio.business.BusinessTarjetaCredito;
import dominio.business.BusinessVenta;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import presentacion.vista.Boleta;
import presentacion.vista.ModuloClientePago;

public class PresentadorPago implements ActionListener{
    private IPagoCliente ipcliente;
    private BusinessTarjetaCredito tarjetainformation;
    private BusinessVenta ventainformation;
    private TarjetaCredito tj;
    private Cliente clienteSession;
    private IBoleta iboleta;
    public PresentadorPago(Cliente clienteSession) {
        this.clienteSession = clienteSession;
        tarjetainformation = new BusinessTarjetaCredito();
        ventainformation = new BusinessVenta();
    }

    public void initVistaPago(PresentadorPago ppago, JPanel contentPanel){
        ipcliente = new ModuloClientePago();
        contentPanel.removeAll();
        ipcliente.setDimension();
        contentPanel.add(ipcliente.getPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        ipcliente.setPresentador(ppago);
        this.llenarTablaVentaCliente();
    }
    
    private void llenarTablaVentaCliente(){
        DefaultTableModel dt = ipcliente.getDt();
        dt.setRowCount(0);
        String[] titulos = {"ID","Preferencia de envio", "Cliente", "Empaquetado", "Total de Venta", "Estado", "Transporte"};
        dt.setColumnIdentifiers(titulos);
        Object fila[] = new Object[7];
        for (Venta v : ventainformation.getVentasByCliente(this.clienteSession.getId())) {
            fila[0] = v.getId();
            fila[1] = v.getPenvio();
            fila[2] = v.getCliente().getNombre() + " " + v.getCliente().getApellido();
            fila[3] = v.getOempaquetado();
            double total = v.total();
            String totalFormateado = String.format("%.2f", total);
            fila[4] = totalFormateado;
            fila[5] = v.getEstado();
            if(v.getTransporte() != null){
                fila[6] = v.getTransporte().getPlaca();
            }else{
                fila[6] = "SIN TRANSPORTE";
            }
            dt.addRow(fila);
        }
        ipcliente.getRegistrosTable().setModel(dt);
        ipcliente.getRegistrosTable().getColumnModel().getColumn(0).setPreferredWidth(10);
        ipcliente.getRegistrosTable().setDefaultEditor(Object.class, null);
        ipcliente.getRegistrosTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getActionCommand().equals(ipcliente.BOLETA)){
            //Generar boleta
            try{
                Venta v = ventainformation.getLastVenta(this.clienteSession.getId());
                iboleta = new Boleta();
            
                iboleta.getPlacaLabel().setVisible(false);
                iboleta.getPlacaLabelText().setVisible(false);
                iboleta.getPlacaLabel().setVisible(false);

                iboleta.getIdLabel().setText(String.valueOf(v.getId()));
                iboleta.getClienteLabel().setText(v.getCliente().getNombre() + " " + v.getCliente().getApellido());
                iboleta.getPreferLabel().setText(v.getPenvio().name());
                iboleta.getOptionELabel().setText(v.getOempaquetado().name());

                StringBuilder stringBuilder = new StringBuilder();

                for(LineaVenta lv : v.getLineaVentas()) {
                    stringBuilder.append("Producto: ").append(lv.getProducto().getTitulo()).append(", ");
                    stringBuilder.append("Precio: ").append(lv.getPrecioUnitario()).append(", ");
                    stringBuilder.append("Cantidad: ").append(lv.getCantidad()).append("\n");
                }
                iboleta.getLineavTextArea().setText(stringBuilder.toString());

                iboleta.iniciar();
            }catch(NullPointerException a){
                JOptionPane.showMessageDialog(null, "No se ha realizado ninguna venta");
            }
            System.out.println("BOLETA GENERADA");
        }else if(e.getActionCommand().equals(ipcliente.PAGAR)){
            int token = Integer.parseInt(ipcliente.getTokenField().getText());
            if(tj.getToken() == token){
                String numeroCuenta = ipcliente.getCuentaField().getText();
                String clave = String.valueOf(ipcliente.getClavecuentaField().getPassword());
                
                TarjetaCredito tarjeta = tarjetainformation.getTarjetaClienteEspecifico(numeroCuenta, clave);
                if(tarjeta != null){
                    if(tarjeta.getTitular().getId() != (this.clienteSession.getId())){
                        JOptionPane.showMessageDialog(null, "La cuenta: " + numeroCuenta+ " esta asociada a otro usuario, se le informara" );
                    }
                    Venta v = ventainformation.getLastVenta(this.clienteSession.getId());
                    double nuevoSaldo = tarjeta.getSaldo() - v.total();
                    tarjeta.setSaldo(nuevoSaldo);
                    tarjetainformation.actualizar(tarjeta);
                    ipcliente.getSaldoField().setText(String.valueOf(tarjeta.getSaldo()));
                    v.setEstado(StateVenta.PAGADO);
                    ventainformation.actualizar(v);
                    
                    this.llenarTablaVentaCliente();
                    System.out.println("PAGADO");
                }else{
                    JOptionPane.showMessageDialog(null, "La cuenta: " + numeroCuenta+" no ha sido encontrada" );
                }
            }else{
                JOptionPane.showMessageDialog(null, "El token no coincide, genere uno nuevo");
                tj = new TarjetaCredito();
            }
            
        }else if(e.getActionCommand().equals(ipcliente.TOKEN)){
            tj = new TarjetaCredito();
            JOptionPane.showMessageDialog(null, "Tu token es: " + tj.getToken());
            System.out.println("TOKEN GENERADO");
        }
    }
}
