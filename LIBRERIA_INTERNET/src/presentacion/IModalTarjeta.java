package presentacion;

import javax.swing.JLabel;
import javax.swing.JTextField;

public interface IModalTarjeta {
    public static final String ADDTARJETA = "AÑADIR";
    public static final String CLOSE = "CERRAR";
    
    public void setPresentador(PresentadorTarjeta ptarjeta);
    
    public void iniciar();
    
    public void cerrar();
    
    public JTextField getClaveField();

    public JTextField getCorreoCuentaField();

    public JTextField getFechaField();

    public JLabel getTextTarjetaLabel();
}
