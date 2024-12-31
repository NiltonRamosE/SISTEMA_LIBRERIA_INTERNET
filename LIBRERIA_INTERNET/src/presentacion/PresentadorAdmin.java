package presentacion;

import dominio.Compra;
import dominio.LineaVenta;
import dominio.StateVenta;
import dominio.Transporte;
import dominio.Venta;
import dominio.business.BusinessRegistroCompra;
import dominio.business.BusinessRegistroVenta;
import dominio.business.BusinessTransporte;
import dominio.business.BusinessVenta;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import presentacion.vista.Boleta;
import presentacion.vista.ModuloAdministrador;

public class PresentadorAdmin implements ActionListener{
    
    private IAdmin iadmin;
    private BusinessRegistroCompra rrcomprainformation;
    private BusinessRegistroVenta rrventainformation;
    private BusinessVenta ventainformation;
    private BusinessTransporte transporteinformation;
    private IBoleta iboleta;
    public PresentadorAdmin() {
        rrcomprainformation = new BusinessRegistroCompra();
        rrventainformation = new BusinessRegistroVenta();
        transporteinformation = new BusinessTransporte();
        ventainformation = new BusinessVenta();
    }

    
    public void initVistaAdmin(PresentadorAdmin padmin, JPanel contentPanel){
        iadmin = new ModuloAdministrador();
        contentPanel.removeAll();
        iadmin.setDimension();
        contentPanel.add(iadmin.getPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        iadmin.setPresentador(padmin);
        
        this.llenarTablaCompras();
        this.llenarTablaVentas();
        this.cargarPedidoBox();
        this.cargarTransporteBox();
    }
    
    private void llenarTablaCompras(){
        DefaultTableModel dt = iadmin.getDt();
        dt.setRowCount(0);
        String[] titulos = {"ID","Proveedor", "Empleado", "Total de Compra"};
        dt.setColumnIdentifiers(titulos);
        Object fila[] = new Object[4];
        for (Compra c : rrcomprainformation.listCompras()) {
            fila[0] = c.getId();
            fila[1] = c.getProveedor().getRazonSocial();
            fila[2] = c.getEmpleado().getNombre() + " " + c.getEmpleado().getApellido();
            fila[3] = c.total();
            dt.addRow(fila);
        }
        iadmin.getRegistrosCTable().setModel(dt);
        iadmin.getRegistrosCTable().getColumnModel().getColumn(0).setPreferredWidth(10);
        iadmin.getRegistrosCTable().setDefaultEditor(Object.class, null);
        iadmin.getRegistrosCTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void llenarTablaVentas(){
        DefaultTableModel dt = iadmin.getDt();
        dt.setRowCount(0);
        String[] titulos = {"ID","Preferencia de envio", "Cliente", "Empaquetado", "Total de Venta", "Estado", "Transporte"};
        dt.setColumnIdentifiers(titulos);
        Object fila[] = new Object[7];
        for (Venta v : rrventainformation.listVentas()) {
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
        iadmin.getRegistrosTable().setModel(dt);
        iadmin.getRegistrosTable().getColumnModel().getColumn(0).setPreferredWidth(10);
        iadmin.getRegistrosTable().setDefaultEditor(Object.class, null);
        iadmin.getRegistrosTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void cargarPedidoBox(){
        iadmin.getOrdenVBox().removeAllItems();
        for (Venta orden : rrventainformation.listVentasByPagado()) {
            iadmin.getOrdenVBox().addItem(String.valueOf(orden.getId()));
        }
    }
    
    private void cargarTransporteBox(){
        iadmin.getTransporteBox().removeAllItems();
        for (Transporte transporte : transporteinformation.listTransporteActivo()) {
            iadmin.getTransporteBox().addItem(transporte.getPlaca());
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String id = null;
        if(iadmin != null){
            int filaSelect = iadmin.getRegistrosTable().getSelectedRow();
            if(filaSelect >= 0){
                id = iadmin.getRegistrosTable().getValueAt(filaSelect, 0).toString();
            }
        }
        if(e.getActionCommand().equals(iadmin.ASIGNAR)){
            Transporte t = transporteinformation.buscarByPlaca(iadmin.getTransporteBox().getSelectedItem().toString());
            Venta v = ventainformation.buscar(Integer.parseInt(iadmin.getOrdenVBox().getSelectedItem().toString()));
            v.setTransporte(t);
            v.setEstado(StateVenta.ASIGNADO);
            ventainformation.actualizar(v);
            this.llenarTablaVentas();
            this.cargarPedidoBox();
        } else if(e.getActionCommand().equals(iadmin.BOLETA)){
            try{
                Venta v = ventainformation.getVentaByID(Integer.parseInt(id));
                iboleta = new Boleta();
                boolean visible = true;
                
                if(v.getTransporte() != null){
                    iboleta.getPlacaLabel().setText(v.getTransporte().getPlaca());
                }else{
                    visible = false;
                }
                iboleta.getPlacaLabel().setVisible(visible);
                iboleta.getPlacaLabelText().setVisible(visible);
                iboleta.getPlacaLabel().setVisible(visible);

                iboleta.getIdLabel().setText(String.valueOf(v.getId()));
                iboleta.getClienteLabel().setText(v.getCliente().getNombre() + " " + v.getCliente().getApellido());
                iboleta.getPreferLabel().setText(v.getPenvio().name());
                iboleta.getOptionELabel().setText(v.getOempaquetado().name());
                System.out.println(""+v.getLineaVentas().get(0).getProducto().getTitulo());
                StringBuilder stringBuilder = new StringBuilder();

                for(LineaVenta lv : v.getLineaVentas()) {
                    stringBuilder.append("Producto: ").append(lv.getProducto().getTitulo()).append(", ");
                    stringBuilder.append("Precio: ").append(lv.getPrecioUnitario()).append(", ");
                    stringBuilder.append("Cantidad: ").append(lv.getCantidad()).append("\n");
                }
                iboleta.getLineavTextArea().setText(stringBuilder.toString());

                iboleta.iniciar();
            }catch(NullPointerException a){
                JOptionPane.showMessageDialog(null, "No se selecciono una venta");
            }
            System.out.println("BOLETA GENERADA");
        }
    }  
}
