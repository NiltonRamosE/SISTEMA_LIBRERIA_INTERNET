package presentacion;

import dominio.Cliente;
import dominio.TarjetaCredito;
import dominio.business.BusinessTarjetaCredito;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import presentacion.vista.ModalTarjeta;
import presentacion.vista.ModuloTarjeta;

public class PresentadorTarjeta implements ActionListener{

    private ITarjeta itarjeta;
    private IModalTarjeta imodaltarjeta;
    private Cliente clienteSession;
    private TarjetaCredito tarjeta;
    private BusinessTarjetaCredito tarjetaInformation;
    public PresentadorTarjeta(Cliente clienteSession) {
        this.clienteSession = clienteSession;
        tarjetaInformation = new BusinessTarjetaCredito();
    }

    public void initVistaTarjeta(PresentadorTarjeta ptarjeta, JPanel contentPanel){
        itarjeta = new ModuloTarjeta();  
        this.llenarTablaTarjeta();
        contentPanel.removeAll();
        itarjeta.setDimension();
        contentPanel.add(itarjeta.getPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        itarjeta.setPresentador(ptarjeta);
    }
    
    public void llenarTablaTarjeta(){
        DefaultTableModel dt = itarjeta.getDt();
        dt.setRowCount(0);
        String[] titulos = {"ID", "Cliente", "Numero Cuenta", "Fecha Limite", "Saldo"};
        dt.setColumnIdentifiers(titulos);
        Object fila[] = new Object[5];

        for (TarjetaCredito tc : tarjetaInformation.listadoByCliente(this.clienteSession.getId())) {
            fila[0] = tc.getIdTarjetaCredito();
            fila[1] = tc.getTitular().getNombre() + " " + tc.getTitular().getApellido();
            fila[2] = tc.getNumeroCuenta();
            fila[3] = tc.getFechalimit();
            fila[4] = tc.getSaldo();
            dt.addRow(fila);
        }
        itarjeta.getRegistrosTable().setModel(dt);
        itarjeta.getRegistrosTable().getColumnModel().getColumn(0).setPreferredWidth(10);
        itarjeta.getRegistrosTable().setDefaultEditor(Object.class, null);
        itarjeta.getRegistrosTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String id = null;
        if(itarjeta != null){
            int filaSelect = itarjeta.getRegistrosTable().getSelectedRow();
            if(filaSelect >= 0){
                id = itarjeta.getRegistrosTable().getValueAt(filaSelect, 0).toString();
            }
        }
        if(e.getActionCommand().equals(itarjeta.AGREGAR)){
            this.initModalAgregar();
            System.out.println("AGREGAR TARJETA");
        }else if(e.getActionCommand().equals(itarjeta.ELIMINAR)){
            this.eliminar(id);
            this.llenarTablaTarjeta();
            System.out.println("ELIMINAR TARJETA");
        }else if(e.getActionCommand().equals(imodaltarjeta.ADDTARJETA)){
            this.agregar();
            this.llenarTablaTarjeta();
            System.out.println("AÑADIR TARJETA");
        }else if(e.getActionCommand().equals(imodaltarjeta.CLOSE)){
            imodaltarjeta.cerrar();
            System.out.println("CERRAR MODAL TARJETA");
        }
        
    }
    
    private void initModalAgregar(){
        imodaltarjeta = new ModalTarjeta();
        imodaltarjeta.getTextTarjetaLabel().setText("AGREGAR TARJETA");
        imodaltarjeta.setPresentador(this);
        imodaltarjeta.iniciar();
    }
    
    private void agregar(){
        String numeroCuenta = imodaltarjeta.getCorreoCuentaField().getText();
        String fecha = imodaltarjeta.getFechaField().getText();
        String clave = imodaltarjeta.getClaveField().getText();
        Random random = new Random();
        double tempSaldo= random.nextInt(10000);
        double saldo = (tempSaldo > 1000) ? tempSaldo: 1000;
        if (numeroCuenta.isEmpty() || fecha.isEmpty() || clave.isEmpty() || saldo == 0) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            TarjetaCredito tj = new TarjetaCredito(numeroCuenta, fecha, saldo, clave, this.clienteSession);
            tarjetaInformation.crear(tj);
            imodaltarjeta.cerrar();
        }
    }
    
    private void eliminar(String id){
        if(id != null && !id.isEmpty()){
            TarjetaCredito tj = tarjetaInformation.buscar(Integer.parseInt(id));
            try {
                tarjetaInformation.eliminar(tj);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Tarjeta no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ID de tarjeta no válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
