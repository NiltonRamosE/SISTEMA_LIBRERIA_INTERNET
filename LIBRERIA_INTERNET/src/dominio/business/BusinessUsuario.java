package dominio.business;

import datos.AbstractDAOFactory;
import datos.DAO;
import datos.FactoryType;
import dominio.TipoUsuario;
import dominio.Usuario;
import java.util.ArrayList;
import java.util.List;

public class BusinessUsuario {
    DAO<Usuario> usuarioDAO = AbstractDAOFactory.getFactory(FactoryType.MySQL_DAO_FACTORY).getUsuarioDAO();
    
    public Usuario login(String username, String clave){
        for(Usuario u: usuarioDAO.listado()){
            if(username.equals(u.getUsername()) && clave.equals(u.getClave())){
                return u;
            }
        }
        return null;
    }
    
    public List<Usuario> getListEmpleado(){
        List<Usuario> listEmpleados = new ArrayList<>();
        for(Usuario u: usuarioDAO.listado()){
            if(u.getTypeUser().equals(TipoUsuario.EMPLEADO) || u.getTypeUser().equals(TipoUsuario.ADMINISTRADOR)){
                listEmpleados.add(u);
            }
        }
        return listEmpleados;
    }
    
    public Usuario crear(Usuario obj) {
        return usuarioDAO.crear(obj);
    }

    public Usuario eliminar(Usuario obj) {
        return usuarioDAO.eliminar(obj);
    }

    public Usuario actualizar(Usuario obj) {
        return usuarioDAO.actualizar(obj);
    }

    public Usuario buscar(int id) {
        return usuarioDAO.buscar(id);
    }
}
