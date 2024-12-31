package datos.MySQL;

import datos.DAO;
import dominio.Cliente;
import dominio.LineaVenta;
import dominio.OpcionEmpaquetado;
import dominio.PreferenciaEnvio;
import dominio.StateVenta;
import dominio.Venta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VentaDAOMySQL extends DAO<Venta> {

    private static final String TABLE_NAME = "venta";
    private static final String COLUMN_ID = "idVenta";
    private static final String COLUMN_REGISTRO_VENTA_ID = "idRegistroVenta";
    private static final String COLUMN_CLIENTE_ID = "idCliente";
    private static final String COLUMN_PREFERENCIA_ENVIO = "preferenciaEnvio";
    private static final String COLUMN_OPCION_EMPAQUETADO = "opcionEmpaquetado";
    private static final String COLUMN_ESTADO = "estado_venta";
    private static final String COLUMN_TRANSPORTE = "transporte_id";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_REGISTRO_VENTA_ID + ", " + COLUMN_CLIENTE_ID + ""
            + ",  " + COLUMN_PREFERENCIA_ENVIO + ",  " + COLUMN_OPCION_EMPAQUETADO+ ",  "+ COLUMN_ESTADO + ") VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + COLUMN_REGISTRO_VENTA_ID + " = ?, " + COLUMN_CLIENTE_ID + " = ?,"
            + "" + COLUMN_PREFERENCIA_ENVIO + " = ?," + COLUMN_OPCION_EMPAQUETADO+ " = ?,"+ COLUMN_ESTADO + " = ?,"+COLUMN_TRANSPORTE + " = ? WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_SELECT_ALL_WITH_LINES
            = "SELECT v.*, lv.idLineaVenta AS lineaVenta_id, lv.idVenta AS lv_venta_id, "
            + "lv.cantidad AS lv_cantidad, lv.idProducto AS lv_producto_id, "
            + "lv.precioUnitario AS lv_precio_unitario "
            + "FROM venta v "
            + "LEFT JOIN lineaventa lv ON v.idVenta = lv.idVenta";

    @Override
    public Venta crear(Venta obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, obj.getId_registroVenta());
            preparedStatement.setInt(2, obj.getCliente().getId());
            preparedStatement.setString(3, obj.getPenvio().name());
            preparedStatement.setString(4, obj.getOempaquetado().name());
            preparedStatement.setString(5, obj.getEstado().name());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating venta failed, no rows affected.");
            }

            try ( ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating venta failed, no ID obtained.");
                }
            }

            // Llamar al DAO de LineaCompra y realizar la inserción
            LineaVentaDAOMySQL lineaVentaDao = new LineaVentaDAOMySQL();
            for (LineaVenta lineaVenta : obj.getLineaVentas()) {
                lineaVenta.setVenta_id(obj.getId());
                lineaVentaDao.crear(lineaVenta);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return obj;
    }

    @Override
    public Venta eliminar(Venta obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, obj.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public Venta actualizar(Venta obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_UPDATE)) {

            preparedStatement.setInt(1, obj.getId_registroVenta());
            preparedStatement.setInt(2, obj.getCliente().getId());
            preparedStatement.setString(3, obj.getPenvio().name());
            preparedStatement.setString(4, obj.getOempaquetado().name());
            preparedStatement.setString(5, obj.getEstado().name());
            if(obj.getTransporte() != null){
                preparedStatement.setInt(6, obj.getTransporte().getTransporte_id());
            }else{
                preparedStatement.setString(6, null);
            }
            
            preparedStatement.setInt(7, obj.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public Venta buscar(int id) {
        Venta venta = null;

        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);

            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    venta = mapResultSetToVenta(resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return venta;
    }

    @Override
    public List<Venta> listado() {
        List<Venta> listaVentas = new ArrayList<>();
        List<LineaVenta> listaLineasVentas = new ArrayList<>();

        try ( Statement statement = connect.createStatement();  ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_WITH_LINES)) {

            while (resultSet.next()) {
                Venta venta = mapResultSetToVenta(resultSet);
                LineaVenta lineaVenta = mapResultSetToLineaVenta(resultSet);

                
                boolean VentaExiste = false;
                for (Venta v : listaVentas) {
                    if (v.getId() == venta.getId()) {
                        VentaExiste = true;
                        break;
                    }
                }
                
                if (!VentaExiste) {
                    // Si la venta no está en la lista, agrégala
                    listaVentas.add(venta);
                }
                // Agrega la línea de venta a la lista
                listaLineasVentas.add(lineaVenta);
            }

            // Asigna las líneas de venta a las ventas correspondientes
            for (Venta venta : listaVentas) {
                List<LineaVenta> lineasVentas = new ArrayList<>();
                for (LineaVenta lineaVenta : listaLineasVentas) {
                    if (lineaVenta.getVenta_id() == venta.getId()) {
                        lineasVentas.add(lineaVenta);
                    }
                }
                venta.setLineaVentas((ArrayList<LineaVenta>) lineasVentas);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaVentas;
    }

    private LineaVenta mapResultSetToLineaVenta(ResultSet resultSet) throws SQLException {
        LineaVenta lineaVenta = new LineaVenta();
        lineaVenta.setId(resultSet.getInt("lineaVenta_id"));
        lineaVenta.setVenta_id(resultSet.getInt("lv_venta_id"));
        // Configurar atributos de LineaVenta según sea necesario
        lineaVenta.setCantidad(resultSet.getInt("lv_cantidad"));
        int idProducto = resultSet.getInt("lv_producto_id");
        ProductoDAOMySQL productoDao = new ProductoDAOMySQL();
        lineaVenta.setProducto(productoDao.buscar(idProducto));
        lineaVenta.setPrecioUnitario(resultSet.getDouble("lv_precio_unitario"));

        return lineaVenta;
    }

    private Venta mapResultSetToVenta(ResultSet resultSet) throws SQLException {
        Venta venta = new Venta();
        // Configurar atributos de Venta según sea necesario
        venta.setId(resultSet.getInt(COLUMN_ID));
        venta.setId_registroVenta(resultSet.getInt(COLUMN_REGISTRO_VENTA_ID));

        // Obtener el ID del cliente desde el ResultSet
        int idCliente = resultSet.getInt(COLUMN_CLIENTE_ID);

        // Obtener el cliente a través de su ID
        ClienteDAOMySQL clienteDao = new ClienteDAOMySQL();
        Cliente cliente = clienteDao.buscar(idCliente);

        // Establecer el cliente en la venta
        venta.setCliente(cliente);
        venta.setPenvio(PreferenciaEnvio.valueOf(resultSet.getString(COLUMN_PREFERENCIA_ENVIO)));
        venta.setOempaquetado(OpcionEmpaquetado.valueOf(resultSet.getString(COLUMN_OPCION_EMPAQUETADO)));
        venta.setEstado(StateVenta.valueOf(resultSet.getString(COLUMN_ESTADO)));
        
        
        TransporteDAOMySQL transporteDao = new TransporteDAOMySQL();
        
        venta.setTransporte(transporteDao.buscar(resultSet.getInt(COLUMN_TRANSPORTE)));
        return venta;
    }

}
