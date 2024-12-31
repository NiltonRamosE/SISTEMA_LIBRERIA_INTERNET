package datos;

import datos.MySQL.ClienteDAOMySQL;
import datos.MySQL.CompraDAOMySQL;
import datos.MySQL.ProductoDAOMySQL;
import datos.MySQL.ProveedorDAOMySQL;
import datos.MySQL.RegistroCompraDAOMySQL;
import datos.MySQL.RegistroVentaDAOMySQL;
import datos.MySQL.TarjetaCreditoDAOMySQL;
import datos.MySQL.TransporteDAOMySQL;
import datos.MySQL.UsuarioDAOMySQL;
import datos.MySQL.VentaDAOMySQL;
import dominio.Cliente;
import dominio.Compra;
import dominio.Producto;
import dominio.Proveedor;
import dominio.RegistroCompra;
import dominio.RegistroVenta;
import dominio.TarjetaCredito;
import dominio.Transporte;
import dominio.Usuario;
import dominio.Venta;


public class DAOFactoryMySQL extends AbstractDAOFactory{

    @Override
    public DAO<Usuario> getUsuarioDAO() {
        return new UsuarioDAOMySQL();
    }

    @Override
    public DAO<Producto> getProductoDAO() {
        return new ProductoDAOMySQL();
    }

    @Override
    public DAO<Venta> getVentaDAO() {
        return new VentaDAOMySQL();
    }

    @Override
    public DAO<RegistroVenta> getRegistroVentaDAO() {
        return new RegistroVentaDAOMySQL();
    }

    @Override
    public DAO<RegistroCompra> getRegistroCompraDAO() {
        return new RegistroCompraDAOMySQL();
    }

    @Override
    public DAO<Cliente> getClienteDAO() {
        return new ClienteDAOMySQL();
    }

    @Override
    public DAO<Compra> getCompraDAO() {
        return new CompraDAOMySQL();
    }

    @Override
    public DAO<Proveedor> getProveedorDAO() {
        return new ProveedorDAOMySQL();
    }

    @Override
    public DAO<Transporte> getTransporteDAO() {
        return new TransporteDAOMySQL();
    }

    @Override
    public DAO<TarjetaCredito> getTarjetaCreditoDAO() {
        return new TarjetaCreditoDAOMySQL();
    }

}
