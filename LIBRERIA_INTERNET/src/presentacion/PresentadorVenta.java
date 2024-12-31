package presentacion;

import dominio.Cliente;
import dominio.LineaVenta;
import dominio.OpcionEmpaquetado;
import dominio.PreferenciaEnvio;
import dominio.Producto;
import dominio.RegistroVenta;
import dominio.StateVenta;
import dominio.Turno;
import dominio.Venta;
import dominio.business.BusinessProducto;
import dominio.business.BusinessRegistroVenta;
import dominio.business.BusinessVenta;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import presentacion.vista.ModuloVenta;
import presentacion.vista.ViewLibro;

public class PresentadorVenta implements ActionListener, MouseListener{

    private DefaultTableModel dt;
    private IVenta iventa;
    private IModalProducto imodalp;
    private BusinessProducto productoInformation;
    private BusinessRegistroVenta rrventaInformation;
    private BusinessVenta ventaInformation;
    private RegistroVenta rventa;
    private Venta venta;
    private Cliente clienteSession;
    private Producto producto;
    public PresentadorVenta(Cliente clienteSession) {
        this.clienteSession = clienteSession;
        productoInformation = new BusinessProducto();
        rrventaInformation = new BusinessRegistroVenta();
        ventaInformation = new BusinessVenta();
        venta = new Venta();
    }

    public void initVistaVenta(PresentadorVenta pventa, JPanel contentPanel){
        iventa = new ModuloVenta();
        contentPanel.removeAll();
        iventa.setDimension();
        contentPanel.add(iventa.getPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        iventa.setPresentador(pventa);
        
        
        establecerTurnoFecha();
        //CREACION DEL REGISTRO DE VENTA DIARIO
        RegistroVenta rvTemp = null;
        Turno turno = Turno.valueOf(iventa.getTurnoField().getText());
        String fecha = iventa.getFechaField().getText();
        rvTemp = rrventaInformation.registroVentaDiario(turno, fecha);
        if(rvTemp != null){
            rventa = rvTemp;
        }else{
            rvTemp = new RegistroVenta();
            rvTemp.setFecha(fecha);
            rvTemp.setTurno(turno);
            rventa = rrventaInformation.crear(rvTemp);
        }
        rellenarBox();
    }
    
    private void rellenarBox(){
        iventa.getEmpaquetadoBox().removeAllItems();
        for(OpcionEmpaquetado oe: OpcionEmpaquetado.values()){
            iventa.getEmpaquetadoBox().addItem(oe.name());
        }
        
        iventa.getEnvioBox().removeAllItems();
        for(PreferenciaEnvio pe: PreferenciaEnvio.values()){
            iventa.getEnvioBox().addItem(pe.name());
        }
    }
    private void establecerTurnoFecha() {
        LocalTime horaActual = LocalTime.now();
        String turno = getTurnoByHora(horaActual);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        String fecha = fechaHoraActual.format(formatter);
        
        iventa.getTurnoField().setText(turno);
        iventa.getFechaField().setText(fecha);
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
    
    private void setCamposModalProducto(Producto p){
        imodalp = new ViewLibro();
        imodalp.setPresentador(this);
        
        imodalp.getBuscadorField().setVisible(false);
        imodalp.getBuscarButton().setVisible(false);
        imodalp.getBuscarLabel().setVisible(false);
        this.consultar(p);
        imodalp.iniciar();
    }
    
    private void llenarTablaLineaVenta(){
        dt = iventa.getDt();
        dt.setRowCount(0);
        String[] titulos = {"ID","Cliente", "Producto", "Cantidad", "P. Venta", "Sub Total"};
        dt.setColumnIdentifiers(titulos);
        Object fila[] = new Object[6];
        fila[0] = venta.getId();
        fila[1] = venta.getCliente().getNombre() + " " + venta.getCliente().getApellido();
        
        for (LineaVenta lv : venta.getLineaVentas()) {
            fila[2] = lv.getProducto().getTitulo();
            fila[3] = lv.getCantidad();
            fila[4] = lv.getPrecioUnitario();
            fila[5] = lv.subtotal();
            dt.addRow(fila);
        }
        iventa.getRegistrosTable().setModel(dt);
        iventa.getRegistrosTable().getColumnModel().getColumn(0).setPreferredWidth(10);
        iventa.getRegistrosTable().setDefaultEditor(Object.class, null);
        iventa.getRegistrosTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int filaSelect = iventa.getRegistrosTable().getSelectedRow();
        if(e.getActionCommand().equals(imodalp.AGREGAR)){
            this.agregar();
            this.llenarTablaLineaVenta();
            System.out.println("PRODUCTO AGREGADO");
        }else if(e.getActionCommand().equals(iventa.MODIFICAR)){
            this.modificar(filaSelect);
            this.llenarTablaLineaVenta();
            System.out.println("PRODUCTO MODIFICADO");
        }else if(e.getActionCommand().equals(iventa.ELIMINAR)){
            this.eliminar(filaSelect);
            this.llenarTablaLineaVenta();
            System.out.println("PRODUCTO ELIMINADO");
        }else if(e.getActionCommand().equals(iventa.CONSULTAR)){
            this.openBuscador();
            System.out.println("PRODUCTO BUSCAR");
        }else if(e.getActionCommand().equals(iventa.GUARDARCARRITO)){
            this.enviar();
            System.out.println("PRODUCTO CONFIRMADO");
        }else if(e.getActionCommand().equals(imodalp.BUSCAR)){
            Producto p = productoInformation.getProductoByBuscador(imodalp.getBuscadorField().getText());
            this.consultar(p);
            System.out.println("PRODUCTO ENCONTRADO");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel labelClickeada = (JLabel) e.getSource();
        if (labelClickeada == iventa.getP001()) {
            producto = productoInformation.buscarCodigo(iventa.getP001().getName());
            System.out.println("LIBRO 1");
        } else if (labelClickeada == iventa.getP002()) {
            producto = productoInformation.buscarCodigo(iventa.getP002().getName());
            System.out.println("LIBRO 2");
        } else if (labelClickeada == iventa.getP003()) {
            producto = productoInformation.buscarCodigo(iventa.getP003().getName());
            System.out.println("LIBRO 3");
        } else if (labelClickeada == iventa.getP004()) {
            producto = productoInformation.buscarCodigo(iventa.getP004().getName());
            System.out.println("LIBRO 4");
        } else if (labelClickeada == iventa.getP005()) {
            producto = productoInformation.buscarCodigo(iventa.getP005().getName());
            System.out.println("LIBRO 5");
        } else if (labelClickeada == iventa.getP006()) {
            producto = productoInformation.buscarCodigo(iventa.getP006().getName());
            System.out.println("LIBRO 6");
        } else if (labelClickeada == iventa.getP007()) {
            producto = productoInformation.buscarCodigo(iventa.getP007().getName());
            System.out.println("LIBRO 7");
        } else if (labelClickeada == iventa.getP008()) {
            producto = productoInformation.buscarCodigo(iventa.getP008().getName());
            System.out.println("LIBRO 8");
        } else if (labelClickeada == iventa.getP009()) {
            producto = productoInformation.buscarCodigo(iventa.getP009().getName());
            System.out.println("LIBRO 9");
        } else if (labelClickeada == iventa.getP010()) {
            producto = productoInformation.buscarCodigo(iventa.getP010().getName());
            System.out.println("LIBRO 10");
        }
        this.setCamposModalProducto(producto);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    
    private void agregar(){
        Producto p = productoInformation.buscarCodigo(imodalp.getCodigoLabel().getText());
        int cantidad = Integer.parseInt(String.valueOf(imodalp.getCantidadSpinner().getValue()));
        venta.setId(1);
        venta.setCliente(clienteSession);
        venta.agregarLineaVenta(new LineaVenta(cantidad, p, p.getPrecioVenta()));
        iventa.getTotalField().setText(String.valueOf(venta.total()));
    }
    
    private void modificar(int filaSelect){
        try{
            int nuevaCantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la nueva cantidad del producto seleccionado"));
            venta.getLineaVentas().get(filaSelect).setCantidad(nuevaCantidad);
            iventa.getTotalField().setText(String.valueOf(venta.total()));
        }catch(IndexOutOfBoundsException exc){
            JOptionPane.showMessageDialog(null, "No se ha seleccionado una fila","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminar(int filaSelect){
        venta.removerLineaVenta(venta.getLineaVentas().get(filaSelect));
        iventa.getTotalField().setText(String.valueOf(venta.total()));
    }
    
    private void enviar(){
        //En este punto recien guardamos en la BD o AT
        if(!venta.getLineaVentas().isEmpty()){
            rventa.setLiquidacion(rventa.getLiquidacion() + venta.total());
            rrventaInformation.actualizar(rventa);
            venta.setId_registroVenta(rventa.getId());
            venta.setOempaquetado(OpcionEmpaquetado.valueOf(iventa.getEmpaquetadoBox().getSelectedItem().toString()));
            venta.setPenvio(PreferenciaEnvio.valueOf(iventa.getEnvioBox().getSelectedItem().toString()));
            venta.setEstado(StateVenta.PROCESO);
            ventaInformation.crear(venta);
            venta = new Venta();
        }else{
            JOptionPane.showMessageDialog(null, "No se ha añadido ningun producto","Error",JOptionPane.ERROR_MESSAGE);
        }
        iventa.getTotalField().setText("");
        dt.setRowCount(0);
    }
    
    private void openBuscador(){
        imodalp = new ViewLibro();
        imodalp.setPresentador(this);
        imodalp.getBuscadorField().setVisible(true);
        imodalp.getBuscarButton().setVisible(true);
        imodalp.getBuscarLabel().setVisible(true);
        imodalp.iniciar();
    }
    private void consultar(Producto p){
        
        imodalp.getStockLabel().setText(String.valueOf(p.getStock()));
        imodalp.getNombreLabel().setText(p.getTitulo());
        imodalp.getPrecioLabel().setText(String.valueOf(p.getPrecioVenta()));
        imodalp.getAutorLabel().setText(p.getAutor());
        imodalp.getISBNLabel().setText(p.getISBN());
        imodalp.getCodigoLabel().setText(p.getCodigo());
        imodalp.iniciar();
    }
}
