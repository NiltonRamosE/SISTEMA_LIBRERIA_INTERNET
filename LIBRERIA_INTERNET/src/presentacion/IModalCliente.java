package presentacion;

import javax.swing.JTextField;

public interface IModalCliente extends IModalEmpleado{
    public static final String ADDCLIENTE = "AÑADIR";
    public void setPresentador(PresentadorCliente pcliente);
    
    public JTextField getCodPostalField();

    public JTextField getDireccionField();

    public JTextField getLocalidadField();

    public JTextField getPaisField();
}
