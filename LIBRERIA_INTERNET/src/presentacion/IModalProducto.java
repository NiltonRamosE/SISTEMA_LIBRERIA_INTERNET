package presentacion;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public interface IModalProducto {
    public static final String AGREGAR = "Agregar";
    public static final String BUSCAR = "BUSCAR";
    public void setPresentador(PresentadorVenta pventa);

    public void iniciar();

    public JLabel getISBNLabel();

    public JLabel getAutorLabel();

    public JSpinner getCantidadSpinner();

    public JLabel getCodigoLabel();

    public JLabel getNombreLabel();

    public JLabel getPrecioLabel();

    public JLabel getStockLabel();
    
    public JTextField getBuscadorField();

    public JButton getBuscarButton();

    public JLabel getBuscarLabel();
}
