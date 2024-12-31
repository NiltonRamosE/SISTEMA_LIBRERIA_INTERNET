package dominio.business;

import datos.AbstractDAOFactory;
import datos.DAO;
import datos.FactoryType;
import dominio.Proveedor;
import java.util.List;

public class BusinessProveedor {
    DAO<Proveedor> proveedorDAO = AbstractDAOFactory.getFactory(FactoryType.MySQL_DAO_FACTORY).getProveedorDAO();
    
    public Proveedor crear(Proveedor obj) {
        return proveedorDAO.crear(obj);
    }

    public Proveedor eliminar(Proveedor obj) {
        return proveedorDAO.eliminar(obj);
    }

    public Proveedor actualizar(Proveedor obj) {
        return proveedorDAO.actualizar(obj);
    }

    public Proveedor buscar(int id) {
        return proveedorDAO.buscar(id);
    }

    public List<Proveedor> listado() {
        return proveedorDAO.listado();
    }
    
    public Proveedor getProveedorByRazonSocial(String razonSocial){
        for(Proveedor pv : this.listado()){
            if(pv.getRazonSocial().equals(razonSocial)){
                return pv;
            }
        }
        return null;
    }
}
