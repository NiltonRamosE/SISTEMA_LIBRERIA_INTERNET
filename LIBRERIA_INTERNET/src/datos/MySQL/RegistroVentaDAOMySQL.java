package datos.MySQL;

import datos.DAO;
import dominio.Cliente;
import dominio.OpcionEmpaquetado;
import dominio.PreferenciaEnvio;
import dominio.RegistroVenta;
import dominio.StateVenta;
import dominio.Turno;
import dominio.Venta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RegistroVentaDAOMySQL extends DAO<RegistroVenta> {

    private static final String TABLE_NAME = "registroventa";
    private static final String COLUMN_ID = "idRegistroVenta";
    private static final String COLUMN_FECHA = "fecha";
    private static final String COLUMN_LIQUIDACION = "liquidacion";
    private static final String COLUMN_TURNO_ID = "idturno";

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME
            + " (" + COLUMN_FECHA+ ", " + COLUMN_LIQUIDACION + ", " + COLUMN_TURNO_ID + ") VALUES ( ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME
            + " SET " + COLUMN_FECHA + " = ?, " + COLUMN_LIQUIDACION + " = ?, " + COLUMN_TURNO_ID + " = ? WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_SELECT_ALL_WITH_VENTAS
            = "SELECT rv.*, v.idVenta AS id_Venta, v.idRegistroVenta AS registro_venta_id, " +
               "v.idCliente AS v_cliente_id, v.preferenciaEnvio AS preferencio_envio_id, " +
               "v.opcionEmpaquetado AS opcion_empaquetado_id, v.estado_venta AS estado_venta_A, v.transporte_id AS transporte_id " +
               "FROM registroventa rv " +
               "LEFT JOIN venta v ON rv.idRegistroVenta = v.idRegistroVenta";
    
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SQL_SEARCH_TURNO = "SELECT id FROM turno WHERE descripcion = ?";
    @Override
    public RegistroVenta crear(RegistroVenta obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.getFecha());
            preparedStatement.setDouble(2, obj.getLiquidacion());
            int id_turno = Integer.parseInt(this.getIdTurno(obj.getTurno().toString()));
            preparedStatement.setInt(3, id_turno);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating registro_venta failed, no rows affected.");
            }

            try ( ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating registro_venta failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public RegistroVenta eliminar(RegistroVenta obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, obj.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public RegistroVenta actualizar(RegistroVenta obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, obj.getFecha());
            preparedStatement.setDouble(2, obj.getLiquidacion());
            int id_turno = Integer.parseInt(this.getIdTurno(obj.getTurno().toString()));
            preparedStatement.setInt(3, id_turno);
            preparedStatement.setInt(4, obj.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public RegistroVenta buscar(int id) {
        RegistroVenta registroVenta = null;

        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);

            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    registroVenta = mapResultSetToRegistroVenta(resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return registroVenta;
    }

    @Override
    public List<RegistroVenta> listado() {
        List<RegistroVenta> listaRegistroVentas = new ArrayList<>();
        List<Venta> listaVentas = new ArrayList<>();

        try ( Statement statement = connect.createStatement();  ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_WITH_VENTAS)) {

            while (resultSet.next()) {
                RegistroVenta registroVenta = mapResultSetToRegistroVenta(resultSet);
                Venta venta = mapResultSetToVenta(resultSet);
                if (!listaVentas.contains(venta)) {
                    // Si la venta no está en la lista, agrégala
                    listaVentas.add(venta);
                }
                boolean registroVentaExiste = false;
                for (RegistroVenta rv : listaRegistroVentas) {
                    if (rv.getId() == registroVenta.getId()) {
                        registroVentaExiste = true;
                        break;
                    }
                }
                if (!registroVentaExiste) {
                    listaRegistroVentas.add(registroVenta);
                }

            }

            // Asigna las ventas a los registros de venta correspondientes
            for (RegistroVenta registroVenta : listaRegistroVentas) {
                List<Venta> ventas = new ArrayList<>();
                for (Venta venta : listaVentas) {
                    if (venta.getId_registroVenta() == registroVenta.getId()) {
                        ventas.add(venta);
                    }
                }
                registroVenta.setListVentas((ArrayList<Venta>) ventas);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaRegistroVentas;
    }

    private RegistroVenta mapResultSetToRegistroVenta(ResultSet resultSet) throws SQLException {
        RegistroVenta registroVenta = new RegistroVenta();
        registroVenta.setId(resultSet.getInt(COLUMN_ID));
        registroVenta.setFecha(resultSet.getString(COLUMN_FECHA));
        registroVenta.setLiquidacion(resultSet.getDouble(COLUMN_LIQUIDACION));
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
        registroVenta.setTurno(turno);
        return registroVenta;
    }

    private Venta mapResultSetToVenta(ResultSet resultSet) throws SQLException {
        Venta venta = new Venta();
        venta.setId(resultSet.getInt("id_Venta"));
        venta.setId_registroVenta(resultSet.getInt("registro_venta_id"));
        // Obtener el ID del cliente desde el ResultSet
        int idCliente = resultSet.getInt("v_cliente_id");
        // Obtener el cliente a través de su ID
        ClienteDAOMySQL clienteDao = new ClienteDAOMySQL();
        Cliente cliente = clienteDao.buscar(idCliente);
        // Establecer el cliente en la venta
        venta.setCliente(cliente);
        String oe = resultSet.getString("opcion_empaquetado_id");
        if(oe != null){
            venta.setOempaquetado(OpcionEmpaquetado.valueOf(oe));
        }
        String penvio = resultSet.getString("preferencio_envio_id");
        if( penvio != null){
            venta.setPenvio(PreferenciaEnvio.valueOf(penvio));
        }
        String  estado = resultSet.getString("estado_venta_A");
        
        if(estado != null){
            venta.setEstado(StateVenta.valueOf(estado));
        }
        VentaDAOMySQL ventaDAO = new VentaDAOMySQL();
        for(Venta v : ventaDAO.listado()){
            if(v.getId() == venta.getId()){
                venta.setLineaVentas(v.getLineaVentas());
            }
        }
        
        TransporteDAOMySQL transporteDao = new TransporteDAOMySQL();
        
        venta.setTransporte(transporteDao.buscar(resultSet.getInt("transporte_id")));
        return venta;
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
