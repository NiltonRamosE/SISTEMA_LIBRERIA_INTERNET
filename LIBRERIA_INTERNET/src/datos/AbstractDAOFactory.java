package datos;

import dominio.Cliente;
import dominio.Compra;
import dominio.LineaVenta;
import dominio.Producto;
import dominio.Proveedor;
import dominio.RegistroCompra;
import dominio.RegistroVenta;
import dominio.TarjetaCredito;
import dominio.Transporte;
import dominio.Usuario;
import dominio.Venta;

public abstract class AbstractDAOFactory {
    
    public abstract DAO<Usuario> getUsuarioDAO();
    public abstract DAO<Producto> getProductoDAO();
    public abstract DAO<Venta> getVentaDAO();
    public abstract DAO<RegistroVenta> getRegistroVentaDAO();
    public abstract DAO<RegistroCompra> getRegistroCompraDAO();
    public abstract DAO<Cliente> getClienteDAO();
    public abstract DAO<Compra> getCompraDAO();
    public abstract DAO<Proveedor> getProveedorDAO();
    public abstract DAO<Transporte> getTransporteDAO();
    public abstract DAO<TarjetaCredito> getTarjetaCreditoDAO();
    
    public static AbstractDAOFactory getFactory(FactoryType type){
 
        if(type.equals(type.ARCHIVO_TEXTO_DAO_FACTORY)){
            return new DAOFactoryAT();
        }
        if(type.equals(type.MySQL_DAO_FACTORY)){
           return new DAOFactoryMySQL();
        }
        return null;
    }
}
