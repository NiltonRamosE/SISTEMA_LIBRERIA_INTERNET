package datos.MySQL;

import datos.DAO;
import dominio.Transporte;
import dominio.StateTransporte;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TransporteDAOMySQL extends DAO<Transporte> {

    private static final String TABLE_NAME = "transporte";
    private static final String COLUMN_ID = "transporte_id";
    private static final String COLUMN_PLACA = "placa";
    private static final String COLUMN_MODELO = "modelo";
    private static final String COLUMN_MARCA = "marca";
    private static final String COLUMN_ESTADO_TRANSPORTE = "estadoTransporte";

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_PLACA + ", " + COLUMN_MODELO + ", "
            + COLUMN_MARCA + "," + COLUMN_ESTADO_TRANSPORTE + ") VALUES (?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + COLUMN_PLACA + " = ?, "
            + COLUMN_MODELO + " = ?, " + COLUMN_MARCA + " = ?, " + COLUMN_ESTADO_TRANSPORTE + " = ? WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;

    @Override
    public Transporte crear(Transporte obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.getPlaca());
            preparedStatement.setString(2, obj.getModelo());
            preparedStatement.setString(3, obj.getMarca());
            preparedStatement.setString(4, obj.getEstado().name());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating proveedor failed, no rows affected.");
            }

            try ( ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setTransporte_id(generatedKeys.getInt(1));
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
    public Transporte eliminar(Transporte obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, obj.getTransporte_id());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public Transporte actualizar(Transporte obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, obj.getPlaca());
            preparedStatement.setString(2, obj.getModelo());
            preparedStatement.setString(3, obj.getMarca());
            preparedStatement.setString(4, obj.getEstado().getDeclaringClass().getName());
            preparedStatement.setInt(5, obj.getTransporte_id());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public Transporte buscar(int id) {
        Transporte transporte = null;

        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);

            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    transporte = mapResultSetToTransporte(resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return transporte;
    }

    @Override
    public List<Transporte> listado() {
        List<Transporte> listaTransportes = new ArrayList<>();

        try ( Statement statement = connect.createStatement();  ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL)) {

            while (resultSet.next()) {
                Transporte transporte = mapResultSetToTransporte(resultSet);
                listaTransportes.add(transporte);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaTransportes;
    }

    private Transporte mapResultSetToTransporte(ResultSet resultSet) throws SQLException {
        Transporte transporte = new Transporte();
        transporte.setTransporte_id(resultSet.getInt(COLUMN_ID));
        transporte.setPlaca(resultSet.getString(COLUMN_PLACA));
        transporte.setModelo(resultSet.getString(COLUMN_MODELO));
        transporte.setMarca(resultSet.getString(COLUMN_MARCA));
        StateTransporte estado = null;
        switch(resultSet.getString(COLUMN_ESTADO_TRANSPORTE)){
            case "1":
                estado = StateTransporte.ACTIVO;
                break;
            case "2":
                estado = StateTransporte.MANTENIMIENTO;
                break;
            case "3":
                estado = StateTransporte.INACTIVO;
                break;
        }
        transporte.setEstado(estado);
        return transporte;
    }

}
