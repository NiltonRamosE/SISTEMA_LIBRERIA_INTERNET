package presentacion;

import javax.swing.JComboBox;
import javax.swing.JTextField;

public interface ICompra extends ICRUD{
    public static final String ENVIAR = "CONFIRMAR PEDIDO";
    public void setPresentador(PresentadorCompra pcompra);
    
    public JTextField getCantidadField();

    public JComboBox<String> getProductoBox();

    public JComboBox<String> getProveedorBox();
    
    public JTextField getTurnoField();
    
    public JTextField getFechaField();
    
    public JTextField getTotalField();
}
