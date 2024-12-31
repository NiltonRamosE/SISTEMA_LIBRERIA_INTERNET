package presentacion;

import dominio.Usuario;
import dominio.business.BusinessUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import presentacion.vista.Login;
import presentacion.vista.ModalCliente;

public class Presentador implements ActionListener{

    private ILogin ilogin;
    private IModalCliente imodalCliente;
    private PresentadorMenu pmenu;
    private BusinessUsuario usuarioInformation;
    public Presentador() {
        ilogin = new Login();
        usuarioInformation = new BusinessUsuario();
    }

    public void initLogin(Presentador p){
        ilogin.setPresentador(p);
        ilogin.iniciar();
    }
    
    private void limpiarFields(){
        ilogin.getClaveField().setText("");
        ilogin.getUsuarioField().setText("");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(ilogin.LOGIN)){
            String user = ilogin.getUsuarioField().getText();
            String clave = String.valueOf(ilogin.getClaveField().getPassword());
            Usuario userSession = usuarioInformation.login(user, clave);
            
            if( userSession != null){
                pmenu = new PresentadorMenu(userSession);
                pmenu.initMenu(pmenu);
            }else{
                JOptionPane.showMessageDialog(null,"Credenciales incorrectas","Error",JOptionPane.ERROR_MESSAGE);
                limpiarFields();
            }
            
        }else if(e.getActionCommand().equals(ilogin.REGISTRAR)){
            this.initModalAgregar();
            System.out.println("REGISTRARSE");
        }else if(e.getActionCommand().equals(ilogin.LOGOUT)){
            System.exit(0);
        }
    }
    
    private void initModalAgregar(){
        imodalCliente = new ModalCliente();
        imodalCliente.getTextEmpleadoLabel().setText("AGREGAR CLIENTE");
        imodalCliente.getIdField().setVisible(false);
        imodalCliente.getIdLabel().setVisible(false);
        imodalCliente.getCodPostalField().setText("02711");
        imodalCliente.getPaisField().setText("PERÚ");
        PresentadorCliente pc = new PresentadorCliente();
        pc.setImodalCliente(imodalCliente);
        imodalCliente.setPresentador(pc);
        imodalCliente.iniciar();
    }
}
