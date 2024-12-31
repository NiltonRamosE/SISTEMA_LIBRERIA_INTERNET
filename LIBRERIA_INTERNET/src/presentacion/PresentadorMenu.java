package presentacion;

import dominio.Usuario;
import dominio.business.BusinessCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import presentacion.vista.Menu;

public class PresentadorMenu implements MouseListener, ActionListener{

    private IMenu imenu;
    private PresentadorEmpleado pempleado;
    private PresentadorCliente pcliente;
    private PresentadorVenta pventa;
    private PresentadorCompra pcompra;
    private PresentadorPago ppago;
    private PresentadorAdmin padmin;
    private PresentadorTarjeta ptarjeta;
    private PresentadorProducto pproducto;
    private Usuario userSession;
    private BusinessCliente clienteinformation;
    public PresentadorMenu( Usuario userSession) {
        this.userSession = userSession;
        clienteinformation = new BusinessCliente();
        imenu = new Menu();
    }

    public void initMenu(PresentadorMenu pmenu){
        this.resetVisibleLabels();
        switch(this.userSession.getTypeUser()){
            case ADMINISTRADOR:
                imenu.getMclienteLabel().setVisible(false);
                imenu.getVentaLabel().setVisible(false);
                imenu.getTarjetaLabel().setVisible(false);
                break;
            case CLIENTE:
                imenu.getClienteLabel().setVisible(false);
                imenu.getCompraLabel().setVisible(false);
                imenu.getEmpleadoLabel().setVisible(false);
                imenu.getMadminLabel().setVisible(false);
                imenu.getProductosLabel().setVisible(false);
                break;
            case EMPLEADO:
                imenu.getMclienteLabel().setVisible(false);
                imenu.getVentaLabel().setVisible(false);
                imenu.getEmpleadoLabel().setVisible(false);
                imenu.getTarjetaLabel().setVisible(false);
                break;
        }
        imenu.getUsuarioField().setText(this.userSession.getNombre() + " " + this.userSession.getApellido());
        imenu.getRolField().setText(this.userSession.getTypeUser().name());
        imenu.setPresentador(pmenu);
        imenu.iniciar();
    }
    
    private void resetVisibleLabels(){
        imenu.getClienteLabel().setVisible(true);
        imenu.getCompraLabel().setVisible(true);
        imenu.getEmpleadoLabel().setVisible(true);
        imenu.getMadminLabel().setVisible(true);
        imenu.getMclienteLabel().setVisible(true);
        imenu.getVentaLabel().setVisible(true);
        imenu.getTarjetaLabel().setVisible(true);
        imenu.getProductosLabel().setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        imenu.cerrar();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel labelClickeada = (JLabel) e.getSource();
        if (labelClickeada == imenu.getEmpleadoLabel()) {
            pempleado = new PresentadorEmpleado();
            pempleado.initVistaEmpleado(pempleado, imenu.getContentPanel());
        } else if (labelClickeada == imenu.getClienteLabel()) {
            pcliente = new PresentadorCliente();
            pcliente.initVistaCliente(pcliente, imenu.getContentPanel());
        } else if (labelClickeada == imenu.getCompraLabel()) {
            pcompra = new PresentadorCompra(userSession);
            pcompra.initVistaCompra(pcompra, imenu.getContentPanel());
        } else if (labelClickeada == imenu.getMadminLabel()) {
            padmin = new PresentadorAdmin();
            padmin.initVistaAdmin(padmin, imenu.getContentPanel());
        }else if (labelClickeada == imenu.getVentaLabel()) {
            pventa = new PresentadorVenta(clienteinformation.buscar(userSession.getId()));
            pventa.initVistaVenta(pventa, imenu.getContentPanel());
        }else if (labelClickeada == imenu.getMclienteLabel()) {
            ppago = new PresentadorPago(clienteinformation.buscar(userSession.getId()));
            ppago.initVistaPago(ppago, imenu.getContentPanel());
        }else if (labelClickeada == imenu.getProductosLabel()) {
            pproducto = new PresentadorProducto();
            pproducto.initVistaProducto(pproducto, imenu.getContentPanel());
        }else if (labelClickeada == imenu.getTarjetaLabel()) {
            ptarjeta = new PresentadorTarjeta(clienteinformation.buscar(userSession.getId()));
            ptarjeta.initVistaTarjeta(ptarjeta, imenu.getContentPanel());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
