package presentacion;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public interface IPagoCliente extends ICRUD{
    
    public static final String PAGAR = "EFECTUAR PAGO";
    public static final String TOKEN = "GENERAR TOKEN";
    public static final String BOLETA = "BOLETA";
    
    public void setPresentador(PresentadorPago ppago);
    
    public JPasswordField getClavecuentaField();

    public JTextField getCuentaField();

    public JTextField getSaldoField();

    public JTextField getTokenField();
}
