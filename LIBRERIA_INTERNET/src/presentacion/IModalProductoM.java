package presentacion;

import javax.swing.JLabel;
import javax.swing.JTextField;

public interface IModalProductoM {
    public static final String ADDPRODUCTO = "AÑADIR";
    public static final String CLOSE = "CERRAR";
    
    public void setPresentador(PresentadorProducto pproducto);
    
    public void iniciar();
    
    public void cerrar();
    
    public JTextField getIdField();

    public JLabel getIdLabel();
    
    public JTextField getISBNField();

    public JTextField getAutorField();

    public JTextField getCodigoField();

    public JTextField getPcompraField();

    public JTextField getPventaField();

    public JTextField getTituloField();

    public JLabel getTextProductoLabel();
}
