package presentacion;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public interface IBoleta {
    
    void iniciar();
    
    public JLabel getClienteLabel();

    public JLabel getIdLabel();

    public JTextArea getLineavTextArea();

    public JLabel getOptionELabel();

    public JLabel getPlacaLabel();

    public JLabel getPlacaLabelText();

    public JPanel getPlacaPanel();

    public JLabel getPreferLabel();
}
