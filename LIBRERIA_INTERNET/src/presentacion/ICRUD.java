package presentacion;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public interface ICRUD {
    public static final String AGREGAR = "AGREGAR";
    public static final String ELIMINAR = "ELIMINAR";
    public static final String MODIFICAR = "MODIFICAR";
    public static final String CONSULTAR = "CONSULTAR";
    
    public JPanel getPanel();
    public void setDimension();
    public JTable getRegistrosTable();
    public DefaultTableModel getDt();
}
