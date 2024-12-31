package dominio.business;

import datos.AbstractDAOFactory;
import datos.DAO;
import datos.FactoryType;
import dominio.Venta;
import java.util.ArrayList;
import java.util.List;


public class BusinessVenta {
    DAO<Venta> ventaDAO = AbstractDAOFactory.getFactory(FactoryType.MySQL_DAO_FACTORY).getVentaDAO();
    
    public Venta crear(Venta obj) {
        return ventaDAO.crear(obj);
    }
    
    public Venta buscar(int id) {
        return ventaDAO.buscar(id);
    }
    
    public Venta actualizar(Venta obj) {
        return ventaDAO.actualizar(obj);
    }
    
    public List<Venta> listado() {
        return ventaDAO.listado();
    }
    
    public List<Venta> getVentasByCliente(int id) {
        List<Venta> lista = new ArrayList<>();
        for (Venta v : this.listado()) {
            if (v.getCliente().getId() == id ) {
                lista.add(v);
            }
        }
        return lista;
    }
    
    public Venta getLastVenta(int id) {
        int max_id = Integer.MIN_VALUE; 
        Venta ventaMasReciente = null;

        for (Venta v : this.listado()) {
            if (v.getCliente().getId() == id && v.getId() > max_id) {
                max_id = v.getId();
                ventaMasReciente = v;
            }
        }
        return ventaMasReciente;
    }
    
    public Venta getVentaByID(int id) {
        for (Venta v : this.listado()) {
            if (v.getId() == id ) {
                return v;
            }
        }
        return null;
    }
}
