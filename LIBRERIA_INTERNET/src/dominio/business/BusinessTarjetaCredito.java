package dominio.business;

import datos.AbstractDAOFactory;
import datos.DAO;
import datos.FactoryType;
import dominio.Cliente;
import dominio.TarjetaCredito;
import java.util.ArrayList;
import java.util.List;

public class BusinessTarjetaCredito {
    DAO<TarjetaCredito> tarjetaDAO = AbstractDAOFactory.getFactory(FactoryType.MySQL_DAO_FACTORY).getTarjetaCreditoDAO();
    
    public List<TarjetaCredito> listado(){
        return tarjetaDAO.listado();
    }
    
    public TarjetaCredito crear(TarjetaCredito obj) {
        return tarjetaDAO.crear(obj);
    }

    public TarjetaCredito eliminar(TarjetaCredito obj) {
        return tarjetaDAO.eliminar(obj);
    }

    public TarjetaCredito actualizar(TarjetaCredito obj) {
        return tarjetaDAO.actualizar(obj);
    }

    public TarjetaCredito buscar(int id) {
        return tarjetaDAO.buscar(id);
    }
    public TarjetaCredito getTarjetaClienteEspecifico(String numeroCuenta, String clave) {
        for(TarjetaCredito t: this.listado()){
            if(numeroCuenta.equals(t.getNumeroCuenta()) && clave.equals(t.getClave())){
                return t;
            }
        }
        return null;
    }
    
    public List<TarjetaCredito> listadoByCliente(int id){
        List<TarjetaCredito> list = new ArrayList<>();
        for(TarjetaCredito tj : this.listado()){
            if(tj.getTitular().getId() == id){
                list.add(tj);
            }
        }
        return list;
    }
}
