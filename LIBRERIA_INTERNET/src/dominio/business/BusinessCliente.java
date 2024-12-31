package dominio.business;

import datos.AbstractDAOFactory;
import datos.DAO;
import datos.FactoryType;
import dominio.Cliente;
import java.util.List;

public class BusinessCliente {
    DAO<Cliente> clienteDAO = AbstractDAOFactory.getFactory(FactoryType.MySQL_DAO_FACTORY).getClienteDAO();
    
    public List<Cliente> listado(){
        return clienteDAO.listado();
    }
    
    public Cliente crear(Cliente obj) {
        return clienteDAO.crear(obj);
    }

    public Cliente eliminar(Cliente obj) {
        return clienteDAO.eliminar(obj);
    }

    public Cliente actualizar(Cliente obj) {
        return clienteDAO.actualizar(obj);
    }

    public Cliente buscar(int id) {
        return clienteDAO.buscar(id);
    }
}
