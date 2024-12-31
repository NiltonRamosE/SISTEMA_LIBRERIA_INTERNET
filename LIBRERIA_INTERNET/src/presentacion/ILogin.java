package presentacion;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public interface ILogin {
    
    public static final String LOGIN = "INICIAR SESION";
    public static final String LOGOUT = "CERRAR SESION";
    public static final String REGISTRAR = "REGISTRAR";
    public void setPresentador(Presentador p);

    public void iniciar();
    
    public JPasswordField getClaveField();

    public JTextField getUsuarioField();
}
