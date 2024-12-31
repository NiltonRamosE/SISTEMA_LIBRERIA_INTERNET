package presentacion;

import dominio.Producto;
import dominio.business.BusinessProducto;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import presentacion.vista.ModalProducto;
import presentacion.vista.ModuloProducto;

public class PresentadorProducto implements ActionListener{

    private IProducto iproducto;
    private IModalProductoM imodalProductoM;
    private BusinessProducto productoInformation;
    public PresentadorProducto() {
        productoInformation = new BusinessProducto();
    }

    public void initVistaProducto(PresentadorProducto pproducto, JPanel contentPanel){
        iproducto = new ModuloProducto();
        this.llenarTablaProducto();
        contentPanel.removeAll();
        iproducto.setDimension();
        contentPanel.add(iproducto.getPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        iproducto.setPresentador(pproducto);
    }
    
    public void llenarTablaProducto(){
        DefaultTableModel dt = iproducto.getDt();
        dt.setRowCount(0);
        String[] titulos = {"ID", "Autor", "Titulo", "ISBN", "Stock", "P. Venta", "P. Compra"};
        dt.setColumnIdentifiers(titulos);
        Object fila[] = new Object[7];

        for (Producto p : productoInformation.listado()) {
            fila[0] = p.getId();
            fila[1] = p.getAutor();
            fila[2] = p.getTitulo();
            fila[3] = p.getISBN();
            fila[4] = p.getStock();
            fila[5] = p.getPrecioVenta();
            fila[6] = p.getPrecioCompra();
            dt.addRow(fila);
        }
        iproducto.getRegistrosTable().setModel(dt);
        iproducto.getRegistrosTable().getColumnModel().getColumn(0).setPreferredWidth(10);
        iproducto.getRegistrosTable().setDefaultEditor(Object.class, null);
        iproducto.getRegistrosTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String id = null;
        if(iproducto != null){
            int filaSelect = iproducto.getRegistrosTable().getSelectedRow();
            if(filaSelect >= 0){
                id = iproducto.getRegistrosTable().getValueAt(filaSelect, 0).toString();
            }
        }
        if(e.getActionCommand().equals(iproducto.AGREGAR)){
            this.initModalAgregar();
            System.out.println("AGREGAR PRODUCTO");
        }else if(e.getActionCommand().equals(iproducto.ELIMINAR)){
            this.eliminar(id);
            this.llenarTablaProducto();
            System.out.println("ELIMINAR PRODUCTO");
        }else if(e.getActionCommand().equals(iproducto.MODIFICAR)){
            this.initModalModificar(id);
            System.out.println("MODIFICAR PRODUCTO");
        }else if(e.getActionCommand().equals(iproducto.CONSULTAR)){
            this.consultar(id);
            System.out.println("CONSULTAR PRODUCTO");
        }else if(e.getActionCommand().equals(imodalProductoM.ADDPRODUCTO)){
            if(imodalProductoM.getIdField().isVisible()){
                this.modificar();
            }else{
                this.agregar();
            }
            this.llenarTablaProducto();
            System.out.println("AÑADIR PRODUCTO");
        }else if(e.getActionCommand().equals(imodalProductoM.CLOSE)){
            imodalProductoM.cerrar();
            System.out.println("CERRAR MODAL PRODUCTO");
        }
    }
    
    private void initModalAgregar(){
        imodalProductoM = new ModalProducto();
        imodalProductoM.getTextProductoLabel().setText("AGREGAR PRODUCTO");
        imodalProductoM.getIdField().setVisible(false);
        imodalProductoM.getIdLabel().setVisible(false);
        imodalProductoM.setPresentador(this);
        imodalProductoM.iniciar();
    }
    
    private void initModalModificar(String id){
        this.initModalAgregar();
        this.setEnableFields(true);
        imodalProductoM.getTextProductoLabel().setText("MODIFICAR PRODUCTO");
        imodalProductoM.getIdField().setVisible(true);
        imodalProductoM.getIdLabel().setVisible(true);
        imodalProductoM.getIdField().setEnabled(false);
        imodalProductoM.getCodigoField().setEnabled(false);
        if(id != null && !id.isEmpty()){
            int producto_id = Integer.parseInt(id);
            Producto p = productoInformation.buscar(producto_id);
            try {
                imodalProductoM.getIdField().setText(String.valueOf(p.getId()));
                imodalProductoM.getCodigoField().setText(p.getCodigo());
                imodalProductoM.getAutorField().setText(p.getAutor());
                imodalProductoM.getTituloField().setText(p.getTitulo());
                imodalProductoM.getISBNField().setText(p.getISBN());
                imodalProductoM.getPventaField().setText(String.valueOf(p.getPrecioVenta()));
                imodalProductoM.getPcompraField().setText(String.valueOf(p.getPrecioCompra()));
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ID de Producto no válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void agregar(){
        String codigo = imodalProductoM.getCodigoField().getText();
        String autor = imodalProductoM.getAutorField().getText();
        String titulo = imodalProductoM.getTituloField().getText();
        String ISBN = imodalProductoM.getISBNField().getText();
        double pventa = Double.parseDouble(imodalProductoM.getPventaField().getText());
        double pcompra = Double.parseDouble(imodalProductoM.getPcompraField().getText());
        if (codigo.isEmpty() || autor.isEmpty() || titulo.isEmpty() || ISBN.isEmpty() || pventa == 0 || pcompra == 0) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Producto p = new Producto(codigo, titulo, ISBN, autor, pventa, pcompra);
            p.setStock(0);
            productoInformation.crear(p);
            imodalProductoM.cerrar();
        }
    }
    
    private void eliminar(String id){
        if(id != null && !id.isEmpty()){
            int producto_id = Integer.parseInt(id);
            Producto p = productoInformation.buscar(producto_id);
            try {
                productoInformation.eliminar(p);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ID de Producto no válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificar(){
        
        int id = Integer.parseInt(imodalProductoM.getIdField().getText());
        String codigo = imodalProductoM.getCodigoField().getText();
        String autor = imodalProductoM.getAutorField().getText();
        String titulo = imodalProductoM.getTituloField().getText();
        String ISBN = imodalProductoM.getISBNField().getText();
        double pventa = Double.parseDouble(imodalProductoM.getPventaField().getText());
        double pcompra = Double.parseDouble(imodalProductoM.getPcompraField().getText());
        if (codigo.isEmpty() || autor.isEmpty() || titulo.isEmpty() || ISBN.isEmpty() || pventa == 0 || pcompra == 0) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Producto p = new Producto(codigo, titulo, ISBN, autor, pventa, pcompra);
            p.setId(id);
            productoInformation.actualizar(p);
            imodalProductoM.cerrar();
        }
    }
    
    private void consultar(String id){
        this.initModalAgregar();
        imodalProductoM.getTextProductoLabel().setText("CONSULTAR CLIENTE");
        imodalProductoM.getIdField().setVisible(true);
        imodalProductoM.getIdLabel().setVisible(true);
        if(id != null && !id.isEmpty()){
            int producto_id = Integer.parseInt(id);
            Producto p = productoInformation.buscar(producto_id);
            try {
                this.setEnableFields(false);
                imodalProductoM.getIdField().setText(String.valueOf(p.getId()));
                imodalProductoM.getCodigoField().setText(p.getCodigo());
                imodalProductoM.getAutorField().setText(p.getAutor());
                imodalProductoM.getTituloField().setText(p.getTitulo());
                imodalProductoM.getISBNField().setText(p.getISBN());
                imodalProductoM.getPventaField().setText(String.valueOf(p.getPrecioVenta()));
                imodalProductoM.getPcompraField().setText(String.valueOf(p.getPrecioCompra()));
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ID de Producto no válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setEnableFields(boolean enable){
        imodalProductoM.getIdField().setEnabled(enable);
        imodalProductoM.getCodigoField().setEnabled(enable);
        imodalProductoM.getAutorField().setEnabled(enable);
        imodalProductoM.getTituloField().setEnabled(enable);
        imodalProductoM.getISBNField().setEnabled(enable);
        imodalProductoM.getPventaField().setEnabled(enable);
        imodalProductoM.getPcompraField().setEnabled(enable);
    }
}
