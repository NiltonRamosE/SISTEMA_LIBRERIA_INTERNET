package presentacion;

import javax.swing.JComboBox;
import javax.swing.JTable;

public interface IAdmin extends ICRUD{
    
    public static final String ASIGNAR = "ASIGNAR TRANSPORTE";
    public static final String BOLETA = "BOLETA";
    public void setPresentador(PresentadorAdmin padmin);
    
    public JTable getRegistrosCTable();

    public JComboBox<String> getOrdenVBox();

    public JComboBox<String> getTransporteBox();
}
