package presentacion;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public interface IModalEmpleado {
    public static final String ADDEMPLEADO = "AÑADIR";
    public static final String CLOSE = "CERRAR";
    public void setPresentador(PresentadorEmpleado pempleado);
    public void iniciar();
    public void cerrar();
    
    public JTextField getApellidoField();

    public JTextField getClaveField();

    public JTextField getCorreoField();

    public JTextField getDniField();

    public JTextField getNombreField();

    public JComboBox<String> getRolBox();
    
    public JTextField getIdField();

    public JLabel getIdLabel();

    public JLabel getTextEmpleadoLabel();
}
