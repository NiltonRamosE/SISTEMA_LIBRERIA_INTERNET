package dominio.business;

import datos.AbstractDAOFactory;
import datos.DAO;
import datos.FactoryType;
import dominio.RegistroVenta;
import dominio.StateVenta;
import dominio.Turno;
import dominio.Venta;
import java.util.ArrayList;
import java.util.List;

public class BusinessRegistroVenta {
    DAO<RegistroVenta> rventaDAO = AbstractDAOFactory.getFactory(FactoryType.MySQL_DAO_FACTORY).getRegistroVentaDAO();
    
    public RegistroVenta crear(RegistroVenta obj) {
        return rventaDAO.crear(obj);
    }

    public RegistroVenta eliminar(RegistroVenta obj) {
        return rventaDAO.eliminar(obj);
    }

    public RegistroVenta actualizar(RegistroVenta obj) {
        return rventaDAO.actualizar(obj);
    }

    public RegistroVenta buscar(int id) {
        return rventaDAO.buscar(id);
    }

    public List<RegistroVenta> listado() {
        return rventaDAO.listado();
    }
    
    public RegistroVenta registroVentaDiario(Turno turno, String fecha) {
        for(RegistroVenta rc : this.listado()){
            if(rc.getFecha().equals(fecha) && rc.getTurno().equals(turno)){
                return rc;
            }
        }
        return null;
    }
    
    public List<Venta> listVentas() {
        List<Venta> listHistorialVentas = new ArrayList<>();
        for(RegistroVenta rv : this.listado()){
            for(Venta v : rv.getListVentas()){
                listHistorialVentas.add(v);
            }
        }
        return listHistorialVentas;
    }
    
    public List<Venta> listVentasByPagado() {
        List<Venta> listVentasProceso = new ArrayList<>();
        for(Venta v : this.listVentas()){
            if(v.getEstado().equals(StateVenta.PAGADO)){
                listVentasProceso.add(v);
            }
        }
        return listVentasProceso;
    }
}
