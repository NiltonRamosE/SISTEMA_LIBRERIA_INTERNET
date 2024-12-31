package presentacion;

import dominio.TipoUsuario;
import dominio.Usuario;
import dominio.business.BusinessUsuario;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import presentacion.vista.ModalEmpleado;
import presentacion.vista.ModuloEmpleado;

public class PresentadorEmpleado implements ActionListener{

    private IEmpleado iempleado;
    private IModalEmpleado imodalEmpleado;
    private BusinessUsuario usuarioInformation;
    public PresentadorEmpleado() {
        usuarioInformation = new BusinessUsuario();
    }

    public void initVistaEmpleado(PresentadorEmpleado pempleado, JPanel contentPanel){
        iempleado = new ModuloEmpleado();
        llenarTablaEmpleado();
        
        contentPanel.removeAll();
        iempleado.setDimension();
        contentPanel.add(iempleado.getPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        iempleado.setPresentador(pempleado);
    }
    private void llenarTablaEmpleado(){
        DefaultTableModel dt = iempleado.getDt();
        dt.setRowCount(0);
        String[] titulos = {"ID", "Empleado", "DNI", "Rol", "Username"};
        dt.setColumnIdentifiers(titulos);
        Object fila[] = new Object[5];

        for (Usuario u : usuarioInformation.getListEmpleado()) {
            fila[0] = u.getId();
            fila[1] = u.getNombre() + " " + u.getApellido();
            fila[2] = u.getDni();
            fila[3] = u.getTypeUser();
            fila[4] = u.getUsername();
            dt.addRow(fila);
        }
        iempleado.getRegistrosTable().setModel(dt);
        iempleado.getRegistrosTable().getColumnModel().getColumn(0).setPreferredWidth(10);
        iempleado.getRegistrosTable().setDefaultEditor(Object.class, null);
        iempleado.getRegistrosTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int filaSelect = iempleado.getRegistrosTable().getSelectedRow();
        String id = null;
        if(filaSelect >= 0){
            id = iempleado.getRegistrosTable().getValueAt(filaSelect, 0).toString();
        }
        if(e.getActionCommand().equals(iempleado.AGREGAR)){
            this.initModalAgregar();
            System.out.println("AGREGAR EMPLEADO");
        }else if(e.getActionCommand().equals(iempleado.ELIMINAR)){
            this.eliminar(id);
            this.llenarTablaEmpleado();
            System.out.println("ELIMINAR EMPLEADO");
        }else if(e.getActionCommand().equals(iempleado.MODIFICAR)){
            this.initModalModificar(id);
            System.out.println("MODIFICAR EMPLEADO");
        }else if(e.getActionCommand().equals(iempleado.CONSULTAR)){
            this.consultar(id);
            System.out.println("CONSULTAR EMPLEADO");
        }else if(e.getActionCommand().equals(imodalEmpleado.ADDEMPLEADO)){
            if(imodalEmpleado.getIdField().isVisible()){
                this.modificar();
            }else{
                this.agregar();
            }
            this.llenarTablaEmpleado();
            System.out.println("AÑADIR EMPLEADO");
        }else if(e.getActionCommand().equals(imodalEmpleado.CLOSE)){
            imodalEmpleado.cerrar();
            System.out.println("CERRAR MODAL EMPLEADO");
        }
    } 
    
    private void initModalAgregar(){
        imodalEmpleado = new ModalEmpleado();
        imodalEmpleado.getTextEmpleadoLabel().setText("AGREGAR EMPLEADO");
        imodalEmpleado.getIdField().setVisible(false);
        imodalEmpleado.getIdLabel().setVisible(false);
        imodalEmpleado.setPresentador(this);
        imodalEmpleado.iniciar();
    }
    
    private void initModalModificar(String id){
        this.initModalAgregar();
        this.setEnableFields(true);
        imodalEmpleado.getTextEmpleadoLabel().setText("MODIFICAR EMPLEADO");
        imodalEmpleado.getIdField().setVisible(true);
        imodalEmpleado.getIdLabel().setVisible(true);
        imodalEmpleado.getIdField().setEnabled(false);
        if(id != null && !id.isEmpty()){
            int usuario_id = Integer.parseInt(id);
            Usuario u = usuarioInformation.buscar(usuario_id);
            try {
                imodalEmpleado.getIdField().setText(String.valueOf(u.getId()));
                imodalEmpleado.getCorreoField().setText(u.getUsername());
                imodalEmpleado.getClaveField().setText(u.getClave());
                imodalEmpleado.getRolBox().setSelectedItem(u.getTypeUser());
                imodalEmpleado.getNombreField().setText(u.getNombre());
                imodalEmpleado.getApellidoField().setText(u.getApellido());
                imodalEmpleado.getDniField().setText(u.getDni());
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ID de usuario no válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void agregar(){
        String correo = imodalEmpleado.getCorreoField().getText();
        String clave = imodalEmpleado.getClaveField().getText();
        TipoUsuario type = TipoUsuario.valueOf(imodalEmpleado.getRolBox().getSelectedItem().toString());
        String nombres = imodalEmpleado.getNombreField().getText();
        String apellidos = imodalEmpleado.getApellidoField().getText();
        String dni = imodalEmpleado.getDniField().getText();
        if (correo.isEmpty() || clave.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || dni.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Usuario u = new Usuario(correo, clave, dni, nombres, apellidos, type);
            usuarioInformation.crear(u);
            imodalEmpleado.cerrar();
        }
    }
    
    private void eliminar(String id){
        if(id != null && !id.isEmpty()){
            int usuario_id = Integer.parseInt(id);
            Usuario u = usuarioInformation.buscar(usuario_id);
            try {
                usuarioInformation.eliminar(u);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ID de usuario no válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificar(){
        int id = Integer.parseInt(imodalEmpleado.getIdField().getText());
        String correo = imodalEmpleado.getCorreoField().getText();
        String clave = imodalEmpleado.getClaveField().getText();
        TipoUsuario type = TipoUsuario.valueOf(imodalEmpleado.getRolBox().getSelectedItem().toString());
        String nombres = imodalEmpleado.getNombreField().getText();
        String apellidos = imodalEmpleado.getApellidoField().getText();
        String dni = imodalEmpleado.getDniField().getText();
        if (correo.isEmpty() || clave.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || dni.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Usuario u = new Usuario(correo, clave, dni, nombres, apellidos, type);
            u.setId(id);
            usuarioInformation.actualizar(u);
            imodalEmpleado.cerrar();
        }
    }
    
    private void consultar(String id){
        this.initModalAgregar();
        imodalEmpleado.getTextEmpleadoLabel().setText("CONSULTAR EMPLEADO");
        imodalEmpleado.getIdField().setVisible(true);
        imodalEmpleado.getIdLabel().setVisible(true);
        if(id != null && !id.isEmpty()){
            int usuario_id = Integer.parseInt(id);
            Usuario u = usuarioInformation.buscar(usuario_id);
            try {
                this.setEnableFields(false);
                imodalEmpleado.getIdField().setText(String.valueOf(u.getId()));
                imodalEmpleado.getCorreoField().setText(u.getUsername());
                imodalEmpleado.getClaveField().setText(u.getClave());
                imodalEmpleado.getRolBox().setSelectedItem(u.getTypeUser());
                imodalEmpleado.getNombreField().setText(u.getNombre());
                imodalEmpleado.getApellidoField().setText(u.getApellido());
                imodalEmpleado.getDniField().setText(u.getDni());
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Empleado no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ID de usuario no válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setEnableFields(boolean enable){
        imodalEmpleado.getIdField().setEnabled(enable);
        imodalEmpleado.getIdField().setEnabled(enable);
        imodalEmpleado.getCorreoField().setEnabled(enable);
        imodalEmpleado.getClaveField().setEnabled(enable);
        imodalEmpleado.getRolBox().setEnabled(enable);
        imodalEmpleado.getNombreField().setEnabled(enable);
        imodalEmpleado.getApellidoField().setEnabled(enable);
        imodalEmpleado.getDniField().setEnabled(enable);
    }
}
