package presentacion;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public interface IVenta extends ICRUD{
    public static final String GUARDARCARRITO = "GUARDAR CARRITO";
    public void setPresentador(PresentadorVenta pventa);
    
    public JLabel getP001();

    public JLabel getP002();

    public JLabel getP003();

    public JLabel getP004();

    public JLabel getP005();

    public JLabel getP006();

    public JLabel getP007();

    public JLabel getP008();

    public JLabel getP009();

    public JLabel getP010();
    
    public JTextField getFechaField();

    public JTextField getTurnoField();
    
    public JTextField getTotalField();
    
    public JComboBox<String> getEmpaquetadoBox();

    public JComboBox<String> getEnvioBox();
}
