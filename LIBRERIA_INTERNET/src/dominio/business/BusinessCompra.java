package dominio.business;

import datos.AbstractDAOFactory;
import datos.DAO;
import datos.FactoryType;
import dominio.Compra;

public class BusinessCompra {
    DAO<Compra> compraDAO = AbstractDAOFactory.getFactory(FactoryType.MySQL_DAO_FACTORY).getCompraDAO();
    
    public Compra crear(Compra obj) {
        return compraDAO.crear(obj);
    }
}
