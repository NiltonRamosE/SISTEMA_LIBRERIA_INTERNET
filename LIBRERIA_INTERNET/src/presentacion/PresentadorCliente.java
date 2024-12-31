package presentacion;

import dominio.Cliente;
import dominio.TipoUsuario;
import dominio.business.BusinessCliente;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import presentacion.vista.ModalCliente;
import presentacion.vista.ModuloCliente;

public class PresentadorCliente implements ActionListener{

    private ICliente icliente;
    private IModalCliente imodalCliente;
    private BusinessCliente clienteInformation;
    public PresentadorCliente() {
        clienteInformation = new BusinessCliente();
    }

    public void initVistaCliente(PresentadorCliente pcliente, JPanel contentPanel){
        icliente = new ModuloCliente();
        llenarTablaCliente();
        
        contentPanel.removeAll();
        icliente.setDimension();
        contentPanel.add(icliente.getPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        icliente.setPresentador(pcliente);
    }
    
    private void llenarTablaCliente(){
        DefaultTableModel dt = icliente.getDt();
        dt.setRowCount(0);
        String[] titulos = {"ID", "Cliente", "Codigo postal", "Direccion", "Pais", "Localidad"};
        dt.setColumnIdentifiers(titulos);
        Object fila[] = new Object[6];

        for (Cliente c : clienteInformation.listado()) {
            fila[0] = c.getId();
            fila[1] = c.getNombre() + " " + c.getApellido();
            fila[2] = c.getCodPostal();
            fila[3] = c.getDireccion();
            fila[4] = c.getPais();
            fila[5] = c.getLocalidad();
            dt.addRow(fila);
        }
        icliente.getRegistrosTable().setModel(dt);
        icliente.getRegistrosTable().getColumnModel().getColumn(0).setPreferredWidth(10);
        icliente.getRegistrosTable().setDefaultEditor(Object.class, null);
        icliente.getRegistrosTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String id = null;
        if(icliente != null){
            int filaSelect = icliente.getRegistrosTable().getSelectedRow();
            if(filaSelect >= 0){
                id = icliente.getRegistrosTable().getValueAt(filaSelect, 0).toString();
            }
        }
        if(e.getActionCommand().equals(icliente.AGREGAR)){
            this.initModalAgregar();
            System.out.println("AGREGAR CLIENTE");
        }else if(e.getActionCommand().equals(icliente.ELIMINAR)){
            this.eliminar(id);
            this.llenarTablaCliente();
            System.out.println("ELIMINAR CLIENTE");
        }else if(e.getActionCommand().equals(icliente.MODIFICAR)){
            this.initModalModificar(id);
            System.out.println("MODIFICAR CLIENTE");
        }else if(e.getActionCommand().equals(icliente.CONSULTAR)){
            this.consultar(id);
            System.out.println("CONSULTAR CLIENTE");
        }else if(e.getActionCommand().equals(imodalCliente.ADDCLIENTE)){
            if(imodalCliente.getIdField().isVisible()){
                this.modificar();
            }else{
                this.agregar();
            }
            if(icliente != null){
                this.llenarTablaCliente();
            }
            System.out.println("AÑADIR EMPLEADO");
        }else if(e.getActionCommand().equals(imodalCliente.CLOSE)){
            imodalCliente.cerrar();
            System.out.println("CERRAR MODAL EMPLEADO");
        }
    }
    
    private void initModalAgregar(){
        imodalCliente = new ModalCliente();
        imodalCliente.getTextEmpleadoLabel().setText("AGREGAR CLIENTE");
        imodalCliente.getIdField().setVisible(false);
        imodalCliente.getIdLabel().setVisible(false);
        imodalCliente.getCodPostalField().setText("02711");
        imodalCliente.getPaisField().setText("PERÚ");
        imodalCliente.setPresentador(this);
        imodalCliente.iniciar();
    }
    
    private void initModalModificar(String id){
        this.initModalAgregar();
        this.setEnableFields(true);
        imodalCliente.getCodPostalField().setEnabled(false);
        imodalCliente.getPaisField().setEnabled(false);
        imodalCliente.getTextEmpleadoLabel().setText("MODIFICAR CLIENTE");
        imodalCliente.getIdField().setVisible(true);
        imodalCliente.getIdLabel().setVisible(true);
        imodalCliente.getIdField().setEnabled(false);
        if(id != null && !id.isEmpty()){
            int usuario_id = Integer.parseInt(id);
            Cliente c = clienteInformation.buscar(usuario_id);
            try {
                imodalCliente.getIdField().setText(String.valueOf(c.getId()));
                imodalCliente.getCorreoField().setText(c.getUsername());
                imodalCliente.getClaveField().setText(c.getClave());
                imodalCliente.getRolBox().setSelectedItem(c.getTypeUser());
                imodalCliente.getNombreField().setText(c.getNombre());
                imodalCliente.getApellidoField().setText(c.getApellido());
                imodalCliente.getDniField().setText(c.getDni());
                imodalCliente.getDireccionField().setText(c.getDireccion());
                imodalCliente.getLocalidadField().setText(c.getLocalidad());
                imodalCliente.getCodPostalField().setText(c.getCodPostal());
                imodalCliente.getPaisField().setText(c.getPais());
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ID de usuario no válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void agregar(){
        String correo = imodalCliente.getCorreoField().getText();
        String clave = imodalCliente.getClaveField().getText();
        TipoUsuario type = TipoUsuario.valueOf(imodalCliente.getRolBox().getSelectedItem().toString());
        String nombres = imodalCliente.getNombreField().getText();
        String apellidos = imodalCliente.getApellidoField().getText();
        String dni = imodalCliente.getDniField().getText();
        String direccion = imodalCliente.getDireccionField().getText();
        String localidad = imodalCliente.getLocalidadField().getText();
        String codPostal = imodalCliente.getCodPostalField().getText();
        String pais = imodalCliente.getPaisField().getText();
        if (correo.isEmpty() || clave.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || dni.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Cliente c = new Cliente(direccion, localidad, codPostal, pais, correo, clave, dni, nombres, apellidos, type);
            clienteInformation.crear(c);
            imodalCliente.cerrar();
        }
    }
    
    private void eliminar(String id){
        if(id != null && !id.isEmpty()){
            int usuario_id = Integer.parseInt(id);
            Cliente c = clienteInformation.buscar(usuario_id);
            try {
                clienteInformation.eliminar(c);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ID de usuario no válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificar(){
        int id = Integer.parseInt(imodalCliente.getIdField().getText());
        String correo = imodalCliente.getCorreoField().getText();
        String clave = imodalCliente.getClaveField().getText();
        TipoUsuario type = TipoUsuario.valueOf(imodalCliente.getRolBox().getSelectedItem().toString());
        String nombres = imodalCliente.getNombreField().getText();
        String apellidos = imodalCliente.getApellidoField().getText();
        String dni = imodalCliente.getDniField().getText();
        String direccion = imodalCliente.getDireccionField().getText();
        String localidad = imodalCliente.getLocalidadField().getText();
        String codPostal = imodalCliente.getCodPostalField().getText();
        String pais = imodalCliente.getPaisField().getText();
        if (correo.isEmpty() || clave.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || dni.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Cliente c = new Cliente(direccion, localidad, codPostal, pais, correo, clave, dni, nombres, apellidos, type);
            c.setId(id);
            clienteInformation.actualizar(c);
            imodalCliente.cerrar();
        }
    }
    
    private void consultar(String id){
        this.initModalAgregar();
        imodalCliente.getTextEmpleadoLabel().setText("CONSULTAR CLIENTE");
        imodalCliente.getIdField().setVisible(true);
        imodalCliente.getIdLabel().setVisible(true);
        if(id != null && !id.isEmpty()){
            int usuario_id = Integer.parseInt(id);
            Cliente c = clienteInformation.buscar(usuario_id);
            try {
                this.setEnableFields(false);
                imodalCliente.getIdField().setText(String.valueOf(c.getId()));
                imodalCliente.getCorreoField().setText(c.getUsername());
                imodalCliente.getClaveField().setText(c.getClave());
                imodalCliente.getRolBox().setSelectedItem(c.getTypeUser());
                imodalCliente.getNombreField().setText(c.getNombre());
                imodalCliente.getApellidoField().setText(c.getApellido());
                imodalCliente.getDniField().setText(c.getDni());
                imodalCliente.getDireccionField().setText(c.getDireccion());
                imodalCliente.getLocalidadField().setText(c.getLocalidad());
                imodalCliente.getCodPostalField().setText(c.getCodPostal());
                imodalCliente.getPaisField().setText(c.getPais());
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ID de usuario no válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setEnableFields(boolean enable){
        imodalCliente.getIdField().setEnabled(enable);
        imodalCliente.getIdField().setEnabled(enable);
        imodalCliente.getCorreoField().setEnabled(enable);
        imodalCliente.getClaveField().setEnabled(enable);
        imodalCliente.getRolBox().setEnabled(enable);
        imodalCliente.getNombreField().setEnabled(enable);
        imodalCliente.getApellidoField().setEnabled(enable);
        imodalCliente.getDniField().setEnabled(enable);
        imodalCliente.getDireccionField().setEnabled(enable);
        imodalCliente.getLocalidadField().setEnabled(enable);
        imodalCliente.getCodPostalField().setEnabled(enable);
        imodalCliente.getPaisField().setEnabled(enable);
    }

    public void setImodalCliente(IModalCliente imodalCliente) {
        this.imodalCliente = imodalCliente;
    }
}
