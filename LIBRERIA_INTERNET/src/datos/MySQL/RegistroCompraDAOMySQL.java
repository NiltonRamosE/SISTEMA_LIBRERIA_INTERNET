package datos.MySQL;

import datos.DAO;
import dominio.Compra;
import dominio.LineaCompra;
import dominio.Proveedor;
import dominio.RegistroCompra;
import dominio.Turno;
import dominio.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RegistroCompraDAOMySQL extends DAO<RegistroCompra>{

    private static final String TABLE_NAME = "registrocompra";
    private static final String COLUMN_ID = "idRegistroCompra";
    private static final String COLUMN_FECHA = "Fecha";
    private static final String COLUMN_TURNO_ID = "idturno";

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME +
            " (" + COLUMN_FECHA + ", " + COLUMN_TURNO_ID + ") VALUES (?, ?)";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME +
            " SET " + COLUMN_FECHA + " = ?, " + COLUMN_TURNO_ID + " = ? WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_SELECT_ALL_WITH_COMPRAS =
            "SELECT rc.*, c.idCompra AS id_compra, c.idProveedor AS c_proveedor_id, c.idRegistroCompra AS c_registro_compra_id"
            + ", c.idUsuario AS usuario_id " +
            "FROM registrocompra rc " +
            "LEFT JOIN compra c ON rc.idRegistroCompra = c.idRegistroCompra";
    //private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SQL_SEARCH_TURNO = "SELECT id FROM turno WHERE descripcion = ?";
    @Override
    public RegistroCompra crear(RegistroCompra obj) {
        try (PreparedStatement preparedStatement = connect.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.getFecha());
            int id_turno = Integer.parseInt(this.getIdTurno(obj.getTurno().toString()));
            preparedStatement.setInt(2, id_turno);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating registro_compra failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating registro_compra failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public RegistroCompra eliminar(RegistroCompra obj) {
        try (PreparedStatement preparedStatement = connect.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, obj.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public RegistroCompra actualizar(RegistroCompra obj) {
        try (PreparedStatement preparedStatement = connect.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, obj.getFecha());
            int id_turno = Integer.parseInt(this.getIdTurno(obj.getTurno().toString()));
            preparedStatement.setInt(2, id_turno);
            preparedStatement.setInt(3, obj.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public RegistroCompra buscar(int id) {
        RegistroCompra registroCompra = null;

        try (PreparedStatement preparedStatement = connect.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    registroCompra = mapResultSetToRegistroCompra(resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return registroCompra;
    }

    @Override
    public List<RegistroCompra> listado() {
        List<RegistroCompra> listaRegistroCompras = new ArrayList<>();
        List<Compra> listaCompras = new ArrayList<>();

        try (Statement statement = connect.createStatement(); ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_WITH_COMPRAS)) {
            while (resultSet.next()) {
                RegistroCompra registroCompra = mapResultSetToRegistroCompra(resultSet);
                Compra compra = mapResultSetToCompra(resultSet);
                
                if (!listaCompras.contains(compra)) {
                    // Si la venta no está en la lista, agrégala
                    listaCompras.add(compra);
                }
                
                boolean registroCompraExiste = false;
                for (RegistroCompra rv : listaRegistroCompras) {
                    if (rv.getId() == registroCompra.getId()) {
                        registroCompraExiste = true;
                        break;
                    }
                }
                if (!registroCompraExiste) {
                    listaRegistroCompras.add(registroCompra);
                }
            }

            // Asigna las compras a los registros de compra correspondientes
            for (RegistroCompra registroCompra : listaRegistroCompras) {
                List<Compra> compras = new ArrayList<>();
                for (Compra compra : listaCompras) {
                    if (compra.getId_registroCompra()== registroCompra.getId()) {
                        compras.add(compra);
                    }
                }
                registroCompra.setListCompras((ArrayList<Compra>) compras);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaRegistroCompras;
    }

    private RegistroCompra mapResultSetToRegistroCompra(ResultSet resultSet) throws SQLException {
        RegistroCompra registroCompra = new RegistroCompra();
        registroCompra.setId(resultSet.getInt(COLUMN_ID));
        registroCompra.setFecha(resultSet.getString(COLUMN_FECHA));
        Turno turno = null;
        switch(resultSet.getString(COLUMN_TURNO_ID)){
            case "1":
                turno = Turno.MANANA;
                break;
            case "2":
                turno = Turno.TARDE;
                break;
            case "3":
                turno = Turno.NOCHE;
                break;
        }
        registroCompra.setTurno(turno);
        return registroCompra;
    }
    
    private Compra mapResultSetToCompra(ResultSet resultSet) throws SQLException {
        Compra compra = new Compra();
        compra.setId(resultSet.getInt("id_compra"));
        compra.setId_registroCompra(resultSet.getInt("c_registro_compra_id"));
        // Obtener el ID del proveedor desde el ResultSet
        int idProveedor = resultSet.getInt("c_proveedor_id");
        // Obtener el proveedor a través de su ID
        ProveedorDAOMySQL proveedorDao = new ProveedorDAOMySQL();
        Proveedor proveedor = proveedorDao.buscar(idProveedor);
        // Establecer el proveedor en la compra
        compra.setProveedor(proveedor);

        int idEmpleado = resultSet.getInt("usuario_id");

        // Obtener el usuario(empleado) través de su ID
        UsuarioDAOMySQL usuarioDao = new UsuarioDAOMySQL();
        Usuario usuario = usuarioDao.buscar(idEmpleado);

        // Establecer el usuario (empleado)en la compra
        compra.setEmpleado(usuario);
        
        CompraDAOMySQL compraDAO = new CompraDAOMySQL();
        for(Compra c : compraDAO.listado()){
            if(c.getId() == compra.getId()){
                compra.setLineaCompras(c.getLineaCompras());
            }
        }
        return compra;
    }
    
    private String getIdTurno(String descripcion){
        try (PreparedStatement preparedStatement = connect.prepareStatement(SQL_SEARCH_TURNO)) {
            preparedStatement.setString(1, descripcion);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("id");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
}
