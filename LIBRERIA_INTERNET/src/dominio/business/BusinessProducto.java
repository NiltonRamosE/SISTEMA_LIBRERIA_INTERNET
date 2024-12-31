package dominio.business;

import datos.AbstractDAOFactory;
import datos.DAO;
import datos.FactoryType;
import dominio.Producto;
import java.util.List;

public class BusinessProducto {
    DAO<Producto> productoDAO = AbstractDAOFactory.getFactory(FactoryType.MySQL_DAO_FACTORY).getProductoDAO();
    
    public List<Producto> listado(){
        return productoDAO.listado();
    }
    
    public Producto crear(Producto obj){
        return productoDAO.crear(obj);
    }
    
    public Producto buscar(int id){
        return productoDAO.buscar(id);
    }
    
    public Producto buscarCodigo(String codigo){
        for(Producto p:this.listado()){
            if(p.getCodigo().equals(codigo)){
                return p;
            }
        }
        return null;
    }
    
    public Producto actualizar(Producto obj){
        return productoDAO.actualizar(obj);
    }
    
    public Producto eliminar(Producto obj){
        return productoDAO.eliminar(obj);
    }
    
    public Producto getProductoByNombre(String titulo){
        for(Producto p: this.listado()){
            if(p.getTitulo().equals(titulo)){
                return p;
            }
        }
        return null;
    }
    
    public Producto getProductoByBuscador(String buscador){
        for(Producto p: this.listado()){
            if(p.getTitulo().equals(buscador) || p.getAutor().equals(buscador) || p.getISBN().equals(buscador)){
                return p;
            }
        }
        return null;
    }
}
