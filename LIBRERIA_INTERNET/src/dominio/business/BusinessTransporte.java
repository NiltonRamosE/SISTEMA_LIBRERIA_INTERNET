package dominio.business;

import datos.AbstractDAOFactory;
import datos.DAO;
import datos.FactoryType;
import dominio.StateTransporte;
import dominio.Transporte;
import java.util.ArrayList;
import java.util.List;

public class BusinessTransporte {
    DAO<Transporte> transporteDAO = AbstractDAOFactory.getFactory(FactoryType.MySQL_DAO_FACTORY).getTransporteDAO();
    
    public Transporte crear(Transporte obj) {
        return transporteDAO.crear(obj);
    }

    public Transporte eliminar(Transporte obj) {
        return transporteDAO.eliminar(obj);
    }

    public Transporte actualizar(Transporte obj) {
        return transporteDAO.actualizar(obj);
    }

    public Transporte buscar(int id) {
        return transporteDAO.buscar(id);
    }

    public List<Transporte> listado() {
        return transporteDAO.listado();
    }
    
    public List<Transporte> listTransporteActivo() {
        List<Transporte> lista = new ArrayList<>();
        
        for(Transporte t : this.listado()){
            if(t.getEstado().equals(StateTransporte.ACTIVO)){
                lista.add(t);
            }
        }
        return lista;
    }
    
    public Transporte buscarByPlaca(String placa) {
        
        for(Transporte t : this.listado()){
            if(t.getPlaca().equals(placa)){
                return t;
            }
        }
        return null;
    }
}
