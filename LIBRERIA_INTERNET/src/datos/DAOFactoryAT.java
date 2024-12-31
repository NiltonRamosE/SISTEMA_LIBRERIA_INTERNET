package datos;

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


public class DAOFactoryAT extends AbstractDAOFactory{

    @Override
    public DAO<Usuario> getUsuarioDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DAO<Producto> getProductoDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DAO<Venta> getVentaDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DAO<RegistroVenta> getRegistroVentaDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DAO<RegistroCompra> getRegistroCompraDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DAO<Cliente> getClienteDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DAO<Compra> getCompraDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DAO<Proveedor> getProveedorDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DAO<Transporte> getTransporteDAO() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public DAO<TarjetaCredito> getTarjetaCreditoDAO() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
