package presentacion;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public interface IMenu {
    public void setPresentador(PresentadorMenu pmenu);

    public void iniciar();
    
    public void cerrar();
    
    public JLabel getClienteLabel();

    public JLabel getCompraLabel();

    public JLabel getEmpleadoLabel();

    public JLabel getMadminLabel();

    public JLabel getMclienteLabel();

    public JLabel getVentaLabel();
    
    public JPanel getContentPanel();
    
    public JTextField getRolField();

    public JTextField getUsuarioField();
    
    public JLabel getProductosLabel();

    public JLabel getTarjetaLabel();
}
