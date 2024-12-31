package presentacion;

import dominio.Compra;
import dominio.LineaCompra;
import dominio.Producto;
import dominio.Proveedor;
import dominio.RegistroCompra;
import dominio.Turno;
import dominio.Usuario;
import dominio.business.BusinessCompra;
import dominio.business.BusinessProducto;
import dominio.business.BusinessProveedor;
import dominio.business.BusinessRegistroCompra;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import presentacion.vista.ModuloCompra;

public class PresentadorCompra implements ActionListener{

    private DefaultTableModel dt;
    private ICompra icompra;
    private BusinessProveedor proveedorInformation;
    private BusinessProducto productoInformation;
    private BusinessCompra compraInformation;
    private BusinessRegistroCompra rcompraInformation;
    private RegistroCompra rcompra;
    private Compra compra;
    private Usuario userSession;
    public PresentadorCompra(Usuario userSession) {
        this.userSession = userSession;
        proveedorInformation = new BusinessProveedor();
        productoInformation = new BusinessProducto();
        compraInformation = new BusinessCompra();
        rcompraInformation = new BusinessRegistroCompra();
        compra = new Compra();
    }

    public void initVistaCompra(PresentadorCompra pcompra, JPanel contentPanel){
        icompra = new ModuloCompra();
        contentPanel.removeAll();
        icompra.setDimension();
        contentPanel.add(icompra.getPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        icompra.setPresentador(pcompra);
        
        establecerTurnoFecha();
        //CREACION DEL REGISTRO DE COMPRA DIARIO
        RegistroCompra rcTemp = null;
        Turno turno = Turno.valueOf(icompra.getTurnoField().getText());
        String fecha = icompra.getFechaField().getText();
        rcTemp = rcompraInformation.registroCompraDiario(turno, fecha);
        if(rcTemp != null){
            rcompra = rcTemp;
        }else{
            rcTemp = new RegistroCompra();
            rcTemp.setFecha(fecha);
            rcTemp.setTurno(turno);
            rcompra = rcompraInformation.crear(rcTemp);
        }
        rellenarBox();
    }
    
    private void rellenarBox(){
        icompra.getProveedorBox().removeAllItems();
        for(Proveedor pv: proveedorInformation.listado()){
            icompra.getProveedorBox().addItem(pv.getRazonSocial());
        }
        icompra.getProductoBox().removeAllItems();
        for(Producto p: productoInformation.listado()){
            icompra.getProductoBox().addItem(p.getTitulo());
        }
    }
    private void establecerTurnoFecha() {
        LocalTime horaActual = LocalTime.now();
        String turno = getTurnoByHora(horaActual);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        String fecha = fechaHoraActual.format(formatter);
        
        icompra.getTurnoField().setText(turno);
        icompra.getFechaField().setText(fecha);
    }

    private String getTurnoByHora(LocalTime hora) {
        if (hora.isAfter(LocalTime.of(6, 0)) && hora.isBefore(LocalTime.of(12, 1))) {
            return Turno.MANANA.name();
        } else if (hora.isAfter(LocalTime.of(12, 0)) && hora.isBefore(LocalTime.of(18, 1))) {
            return Turno.TARDE.name();
        } else {
            return Turno.NOCHE.name();
        }
    }
    
    private void llenarTablaLineaCompra(){
        dt = icompra.getDt();
        dt.setRowCount(0);
        String[] titulos = {"ID","Proveedor", "Producto", "Cantidad", "P. Compra", "Sub Total"};
        dt.setColumnIdentifiers(titulos);
        Object fila[] = new Object[6];
        fila[0] = compra.getId();
        fila[1] = compra.getProveedor().getRazonSocial();
        
        for (LineaCompra lc : compra.getLineaCompras()) {
            fila[2] = lc.getProducto().getTitulo();
            fila[3] = lc.getCantidad();
            fila[4] = lc.getPrecioUnitario();
            fila[5] = lc.subtotal();
            dt.addRow(fila);
        }
        icompra.getRegistrosTable().setModel(dt);
        icompra.getRegistrosTable().getColumnModel().getColumn(0).setPreferredWidth(10);
        icompra.getRegistrosTable().setDefaultEditor(Object.class, null);
        icompra.getRegistrosTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int filaSelect = icompra.getRegistrosTable().getSelectedRow();
        if(e.getActionCommand().equals(icompra.AGREGAR)){
            this.agregar();
            this.llenarTablaLineaCompra();
            System.out.println("PRODUCTO AGREGADO");
        }else if(e.getActionCommand().equals(icompra.MODIFICAR)){
            this.modificar(filaSelect);
            this.llenarTablaLineaCompra();
            System.out.println("PRODUCTO MODIFICADO");
        }else if(e.getActionCommand().equals(icompra.ELIMINAR)){
            this.eliminar(filaSelect);
            this.llenarTablaLineaCompra();
            System.out.println("PRODUCTO ELIMINADO");
        }else if(e.getActionCommand().equals(icompra.ENVIAR)){
            this.enviar();
            System.out.println("PRODUCTO CONFIRMADO");
        }
    }
    
    private void agregar(){
        String proveedor = String.valueOf(icompra.getProveedorBox().getSelectedItem());
        Proveedor pv = proveedorInformation.getProveedorByRazonSocial(proveedor);
        String producto = String.valueOf(icompra.getProductoBox().getSelectedItem());
        Producto p = productoInformation.getProductoByNombre(producto);
        int cantidad = Integer.parseInt(icompra.getCantidadField().getText());
        compra.setId(1);
        compra.setProveedor(pv);
        compra.agregarLineaCompra(new LineaCompra(cantidad, p, p.getPrecioCompra()));
        icompra.getTotalField().setText(String.valueOf(compra.total()));
    }
    
    private void modificar(int filaSelect){
        try{
            int nuevaCantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la nueva cantidad del producto seleccionado"));
            compra.getLineaCompras().get(filaSelect).setCantidad(nuevaCantidad);
            icompra.getTotalField().setText(String.valueOf(compra.total()));
        }catch(IndexOutOfBoundsException exc){
            JOptionPane.showMessageDialog(null, "No se ha seleccionado una fila","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminar(int filaSelect){
        compra.removerLineaCompra(compra.getLineaCompras().get(filaSelect));
        icompra.getTotalField().setText(String.valueOf(compra.total()));
    }
    
    private void enviar(){
        //En este punto recien guardamos en la BD o AT
        if(!compra.getLineaCompras().isEmpty()){
            compra.setId_registroCompra(rcompra.getId());
            compra.setEmpleado(userSession);
            compraInformation.crear(compra);
            compra = new Compra();
        }else{
            JOptionPane.showMessageDialog(null, "No se ha añadido ningun producto","Error",JOptionPane.ERROR_MESSAGE);
        }
        icompra.getCantidadField().setText("");
        icompra.getTotalField().setText("");
        dt.setRowCount(0);
    }
}
