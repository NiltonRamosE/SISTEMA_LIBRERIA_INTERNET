package datos.MySQL;

import datos.DAO;
import dominio.TipoUsuario;
import dominio.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOMySQL extends DAO<Usuario>{

    public UsuarioDAOMySQL() {
    }
    
    private static final String TABLE_NAME = "usuario";
    private static final String COLUMN_ID = "user_id";
    private static final String COLUMN_CORREO = "correo";
    private static final String COLUMN_CLAVE = "clave";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_APELLIDO = "apellido";
    private static final String COLUMN_TIPO_USUARIO = "tipo_usuario";
    private static final String COLUMN_DNI = "dni";
    
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_CORREO + ", " + COLUMN_CLAVE + ", "
            + COLUMN_NOMBRE + ", " + COLUMN_APELLIDO + ", " + COLUMN_TIPO_USUARIO +", " + COLUMN_DNI+ ") VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";

    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + COLUMN_CORREO + " = ?, " + COLUMN_CLAVE + " = ?, "
            + COLUMN_NOMBRE + " = ?, " + COLUMN_APELLIDO + " = ?, " + COLUMN_TIPO_USUARIO + " = ?, " +COLUMN_DNI +" = ? WHERE " + COLUMN_ID + " = ?";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";

    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;

    private static final String SQL_SEARCH_TYPE_USUARIO = "SELECT id_tipoUsuario FROM tipousuario WHERE descripcion = ?";
    @Override
    public Usuario crear(Usuario obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.getUsername());
            preparedStatement.setString(2, obj.getClave());
            preparedStatement.setString(3, obj.getNombre());
            preparedStatement.setString(4, obj.getApellido());
            int id_tipo_usuario = Integer.parseInt(this.getIdTipoUsuario(obj.getTypeUser().toString()));
            preparedStatement.setInt(5, id_tipo_usuario);
            preparedStatement.setString(6, obj.getDni());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating cliente failed, no rows affected.");
            }

            try ( ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating cliente failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return obj;
    }

    @Override
    public Usuario eliminar(Usuario obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, obj.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public Usuario actualizar(Usuario obj) {
        try (PreparedStatement preparedStatement = connect.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, obj.getUsername());
            preparedStatement.setString(2, obj.getClave());
            preparedStatement.setString(3, obj.getNombre());
            preparedStatement.setString(4, obj.getApellido());
            int id_tipo_usuario = Integer.parseInt(this.getIdTipoUsuario(obj.getTypeUser().toString()));
            preparedStatement.setInt(5, id_tipo_usuario);
            preparedStatement.setString(6, obj.getDni());
            preparedStatement.setInt(7, obj.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public Usuario buscar(int id) {
        Usuario usuario = null;

        try (PreparedStatement preparedStatement = connect.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    usuario = mapResultSetToCliente(resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return usuario;
    }

    @Override
    public List<Usuario> listado() {
        List<Usuario> listaUsuarios = new ArrayList<>();

        try (Statement statement = connect.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL)) {

            while (resultSet.next()) {
                Usuario usuario = mapResultSetToCliente(resultSet);
                listaUsuarios.add(usuario);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaUsuarios;
    }
    
    private Usuario mapResultSetToCliente(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(resultSet.getInt(COLUMN_ID));
        usuario.setUsername(resultSet.getString(COLUMN_CORREO));
        usuario.setClave(resultSet.getString(COLUMN_CLAVE));
        usuario.setNombre(resultSet.getString(COLUMN_NOMBRE));
        usuario.setApellido(resultSet.getString(COLUMN_APELLIDO));
        
        TipoUsuario type = null;
        switch(resultSet.getInt(COLUMN_TIPO_USUARIO)){
            case 1:
                type = TipoUsuario.CLIENTE;
                break;
            case 2:
                type = TipoUsuario.ADMINISTRADOR;
                break;
            case 3:
                type = TipoUsuario.EMPLEADO;
                break;
        }
        usuario.setTypeUser(type);
        usuario.setDni(resultSet.getString(COLUMN_DNI));
        return usuario;
    }
    
    public  Usuario getUserEspecifico(int user_id){
        for(Usuario u : this.listado()){
            if(u.getId()==user_id){
                return u;
            }
        }
        return null;
    }
    
    private String getIdTipoUsuario(String descripcion){
        try (PreparedStatement preparedStatement = connect.prepareStatement(SQL_SEARCH_TYPE_USUARIO)) {
            preparedStatement.setString(1, descripcion);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("id_tipoUsuario");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
}
