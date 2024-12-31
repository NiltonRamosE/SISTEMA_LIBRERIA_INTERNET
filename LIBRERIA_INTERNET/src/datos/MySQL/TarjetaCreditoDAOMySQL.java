package datos.MySQL;

import datos.DAO;
import dominio.Cliente;
import dominio.TarjetaCredito;
import dominio.Transporte;
import dominio.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TarjetaCreditoDAOMySQL extends DAO<TarjetaCredito> {

    private static final String TABLE_NAME = "tarjetacredito";
    private static final String COLUMN_ID = "idTarjetaCredito";
    private static final String COLUMN_NUMERO_CUENTA = "numero_cuenta";
    private static final String COLUMN_FECHA_LIMITE = "fechalimite";
    private static final String COLUMN_SALDO = "saldo";
    private static final String COLUMN_CLAVE = "clave";
    private static final String COLUMN_TITULAR = "idUsuario";
    private static final String COLUMN_NOMBRE_TITULAR = "nombre_titular";

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NUMERO_CUENTA + ", " + COLUMN_FECHA_LIMITE + ", "
            + COLUMN_SALDO + "," + COLUMN_CLAVE + ", " + COLUMN_TITULAR + ") VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + COLUMN_NUMERO_CUENTA + " = ?, "
            + COLUMN_FECHA_LIMITE + " = ?, " + COLUMN_SALDO + " = ?, " + COLUMN_CLAVE + " = ?, " + COLUMN_TITULAR + " = ? WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_SELECT_ALL = "SELECT " + TABLE_NAME + ".* " + " FROM " + TABLE_NAME;

    @Override
    public TarjetaCredito crear(TarjetaCredito obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.getNumeroCuenta());
            preparedStatement.setString(2, obj.getFechalimit());
            preparedStatement.setDouble(3, obj.getSaldo());
            preparedStatement.setString(4, obj.getClave());
            preparedStatement.setInt(5, obj.getTitular().getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating proveedor failed, no rows affected.");
            }

            try ( ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setIdTarjetaCredito(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating proveedor failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return obj;
    }

    @Override
    public TarjetaCredito eliminar(TarjetaCredito obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, obj.getIdTarjetaCredito());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public TarjetaCredito actualizar(TarjetaCredito obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, obj.getNumeroCuenta());
            preparedStatement.setString(2, obj.getFechalimit());
            preparedStatement.setDouble(3, obj.getSaldo());
            preparedStatement.setString(4, obj.getClave());
            preparedStatement.setInt(5, obj.getTitular().getId());
            preparedStatement.setInt(6, obj.getIdTarjetaCredito());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public TarjetaCredito buscar(int id) {

        TarjetaCredito tarjetaCredito = null;

        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);

            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    tarjetaCredito = mapResultSetToTarjetaCredito(resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tarjetaCredito;
    }

    @Override
    public List<TarjetaCredito> listado() {
        List<TarjetaCredito> listaTarjetas = new ArrayList<>();

        try ( Statement statement = connect.createStatement();  ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL)) {

            while (resultSet.next()) {
                TarjetaCredito tarjetaCredito = mapResultSetToTarjetaCredito(resultSet);
                listaTarjetas.add(tarjetaCredito);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaTarjetas;
    }

    private TarjetaCredito mapResultSetToTarjetaCredito(ResultSet resultSet) throws SQLException {
        TarjetaCredito tarjetaCredito = new TarjetaCredito();
        tarjetaCredito.setIdTarjetaCredito(resultSet.getInt(COLUMN_ID));
        tarjetaCredito.setNumeroCuenta(resultSet.getString(COLUMN_NUMERO_CUENTA));
        tarjetaCredito.setFechalimit(resultSet.getString(COLUMN_FECHA_LIMITE));
        tarjetaCredito.setSaldo(resultSet.getDouble(COLUMN_SALDO));
        tarjetaCredito.setClave(resultSet.getString(COLUMN_CLAVE));
        
        ClienteDAOMySQL clientes = new ClienteDAOMySQL();
        Cliente clienteBuscado = clientes.buscar(resultSet.getInt("idUsuario"));
        if(clienteBuscado != null){
            tarjetaCredito.setTitular(clienteBuscado);
        }
        
        return tarjetaCredito;
    }

}
