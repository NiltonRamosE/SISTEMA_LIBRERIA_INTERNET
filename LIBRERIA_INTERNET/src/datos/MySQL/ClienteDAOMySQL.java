package datos.MySQL;

import datos.DAO;
import dominio.Cliente;
import dominio.TipoUsuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOMySQL extends DAO<Cliente> {

    public ClienteDAOMySQL() {
    }

    private static final String TABLE_NAME = "usuario";
    private static final String COLUMN_ID = "user_id";
    private static final String COLUMN_CORREO = "correo";
    private static final String COLUMN_CLAVE = "clave";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_APELLIDO = "apellido";
    private static final String COLUMN_TIPO_USUARIO = "tipo_usuario";
    private static final String COLUMN_DIRECCION = "direccion";
    private static final String COLUMN_LOCALIDAD = "localidad";
    private static final String COLUMN_CODPOSTAL = "codPostal";
    private static final String COLUMN_PAIS = "pais";
    private static final String COLUMN_DNI = "dni";
    private static final String COLUMN_DESCRIPCION_TIPO_USUARIO = "nombreTipoUsuario";
    
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_CORREO + ", " + COLUMN_CLAVE + ", "
            + COLUMN_NOMBRE + ", " + COLUMN_APELLIDO + ", " + COLUMN_TIPO_USUARIO + ", " + COLUMN_DIRECCION + ", " + COLUMN_LOCALIDAD + ", " + COLUMN_CODPOSTAL + ", " + COLUMN_PAIS+ ", "+ COLUMN_DNI+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";

    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + COLUMN_CORREO + " = ?, " + COLUMN_CLAVE + " = ?, "
            + COLUMN_NOMBRE + " = ?, " + COLUMN_APELLIDO + " = ?, " + COLUMN_TIPO_USUARIO + " = ?, " + COLUMN_DIRECCION + " = ?, " + COLUMN_LOCALIDAD + " = ?, " + COLUMN_CODPOSTAL+ " = ?, "+ COLUMN_PAIS + " = ?, " + COLUMN_DNI + " = ? WHERE " + COLUMN_ID + " = ?";

    private static final String SQL_SELECT_BY_ID = "SELECT " + TABLE_NAME + ".*, tipousuario.descripcion AS " + COLUMN_DESCRIPCION_TIPO_USUARIO +
                         " FROM " + TABLE_NAME +
                         " INNER JOIN tipousuario ON " + TABLE_NAME + "." + COLUMN_TIPO_USUARIO + " = tipousuario.id_tipoUsuario" +
                         " WHERE " + TABLE_NAME + "." + COLUMN_ID + " = ? AND " + TABLE_NAME + "." + COLUMN_TIPO_USUARIO + " = 1";


    private static final String SQL_SELECT_ALL = "SELECT " + TABLE_NAME + ".*, tipousuario.descripcion AS " + COLUMN_DESCRIPCION_TIPO_USUARIO +
                         " FROM " + TABLE_NAME +
                         " INNER JOIN tipousuario ON " + TABLE_NAME + "." + COLUMN_TIPO_USUARIO + " = tipousuario.id_tipoUsuario" +
                         " WHERE " +  TABLE_NAME + "." + COLUMN_TIPO_USUARIO + " = 1";
    
    private static final String SQL_SEARCH_TYPE_USUARIO = "SELECT id_tipoUsuario FROM tipousuario WHERE descripcion = ?";
    @Override
    public Cliente crear(Cliente obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.getUsername());
            preparedStatement.setString(2, obj.getClave());
            preparedStatement.setString(3, obj.getNombre());
            preparedStatement.setString(4, obj.getApellido());
            int id_tipo_usuario = Integer.parseInt(this.getIdTipoUsuario(obj.getTypeUser().toString()));
            preparedStatement.setInt(5, id_tipo_usuario);
            preparedStatement.setString(6, obj.getDireccion());
            preparedStatement.setString(7, obj.getLocalidad());
            preparedStatement.setString(8, obj.getCodPostal());
            preparedStatement.setString(9, obj.getPais());
            preparedStatement.setString(10, obj.getDni());
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
    public Cliente eliminar(Cliente obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, obj.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public Cliente actualizar(Cliente obj) {
        try (PreparedStatement preparedStatement = connect.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, obj.getUsername());
            preparedStatement.setString(2, obj.getClave());
            preparedStatement.setString(3, obj.getNombre());
            preparedStatement.setString(4, obj.getApellido());
            int id_tipo_usuario = Integer.parseInt(this.getIdTipoUsuario(obj.getTypeUser().toString()));
            preparedStatement.setInt(5, id_tipo_usuario);
            preparedStatement.setString(6, obj.getDireccion());
            preparedStatement.setString(7, obj.getLocalidad());
            preparedStatement.setString(8, obj.getCodPostal());
            preparedStatement.setString(9, obj.getPais());
            preparedStatement.setString(10, obj.getDni());
            preparedStatement.setInt(11, obj.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public Cliente buscar(int id) {
        Cliente cliente = null;

        try (PreparedStatement preparedStatement = connect.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    cliente = mapResultSetToCliente(resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return cliente;
    }

    @Override
    public List<Cliente> listado() {
        List<Cliente> listaClientes = new ArrayList<>();

        try (Statement statement = connect.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL)) {

            while (resultSet.next()) {
                Cliente cliente = mapResultSetToCliente(resultSet);
                listaClientes.add(cliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaClientes;
    }
    
    private Cliente mapResultSetToCliente(ResultSet resultSet) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(resultSet.getInt(COLUMN_ID));
        cliente.setUsername(resultSet.getString(COLUMN_CORREO));
        cliente.setClave(resultSet.getString(COLUMN_CLAVE));
        cliente.setNombre(resultSet.getString(COLUMN_NOMBRE));
        cliente.setApellido(resultSet.getString(COLUMN_APELLIDO));
        cliente.setTypeUser(TipoUsuario.valueOf(resultSet.getString(COLUMN_DESCRIPCION_TIPO_USUARIO)));
        cliente.setDireccion(resultSet.getString(COLUMN_DIRECCION));
        cliente.setLocalidad(resultSet.getString(COLUMN_LOCALIDAD));
        cliente.setCodPostal(resultSet.getString(COLUMN_CODPOSTAL));
        cliente.setPais(resultSet.getString(COLUMN_PAIS));
        cliente.setDni(resultSet.getString(COLUMN_DNI));
        return cliente;
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
