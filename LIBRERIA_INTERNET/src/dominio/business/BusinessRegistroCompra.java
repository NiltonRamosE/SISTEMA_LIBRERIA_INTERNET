package dominio.business;

import datos.AbstractDAOFactory;
import datos.DAO;
import datos.FactoryType;
import dominio.Compra;
import dominio.RegistroCompra;
import dominio.Turno;
import java.util.ArrayList;
import java.util.List;

public class BusinessRegistroCompra {
    DAO<RegistroCompra> rcompraDAO = AbstractDAOFactory.getFactory(FactoryType.MySQL_DAO_FACTORY).getRegistroCompraDAO();
    
    public RegistroCompra crear(RegistroCompra obj) {
        return rcompraDAO.crear(obj);
    }

    public RegistroCompra eliminar(RegistroCompra obj) {
        return rcompraDAO.eliminar(obj);
    }

    public RegistroCompra actualizar(RegistroCompra obj) {
        return rcompraDAO.actualizar(obj);
    }

    public RegistroCompra buscar(int id) {
        return rcompraDAO.buscar(id);
    }

    public List<RegistroCompra> listado() {
        return rcompraDAO.listado();
    }
    
    public RegistroCompra registroCompraDiario(Turno turno, String fecha) {
        for(RegistroCompra rc : this.listado()){
            if(rc.getFecha().equals(fecha) && rc.getTurno().equals(turno)){
                return rc;
            }
        }
        return null;
    }
    
    public List<Compra> listCompras() {
        List<Compra> listHistorialCompras = new ArrayList<>();
        for(RegistroCompra rc : this.listado()){
            for(Compra c : rc.getListCompras()){
                listHistorialCompras.add(c);
            }
        }
        return listHistorialCompras;
    }
}
