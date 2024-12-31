package datos.MySQL;

import datos.DAO;
import dominio.Producto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOMySQL extends DAO<Producto> {

    private static final String TABLE_NAME = "producto";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CODIGO = "codigo";
    private static final String COLUMN_TITULO = "titulo";
    private static final String COLUMN_ISBN = "ISBN";
    private static final String COLUMN_AUTOR = "autor";
    private static final String COLUMN_STOCK = "stock";
    private static final String COLUMN_PRECIO_VENTA = "precioVenta";
    private static final String COLUMN_PRECIO_COMPRA = "precioCompra";

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_CODIGO + ", " + COLUMN_TITULO + ", "
            + COLUMN_ISBN + ", " + COLUMN_AUTOR + ", " + COLUMN_STOCK + ", " + COLUMN_PRECIO_VENTA + ", " + COLUMN_PRECIO_COMPRA + ")"
            + " VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + COLUMN_CODIGO + " = ?, " + COLUMN_TITULO + " = ?,"
            + COLUMN_ISBN + " = ?, " + COLUMN_AUTOR + " = ?, " + COLUMN_STOCK + " = ?, " + COLUMN_PRECIO_VENTA + " = ?, "
            + COLUMN_PRECIO_COMPRA + " = ? WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;

    @Override
    public Producto crear(Producto obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.getCodigo());
            preparedStatement.setString(2, obj.getTitulo());
            preparedStatement.setString(3, obj.getISBN());
            preparedStatement.setString(4, obj.getAutor());
            preparedStatement.setInt(5, obj.getStock());
            preparedStatement.setDouble(6, obj.getPrecioVenta());
            preparedStatement.setDouble(7, obj.getPrecioCompra());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating producto failed, no rows affected.");
            }

            try ( ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating producto failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return obj;
    }

    @Override
    public Producto eliminar(Producto obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_DELETE)) {
            preparedStatement.setInt(1, obj.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public Producto actualizar(Producto obj) {
        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, obj.getCodigo());
            preparedStatement.setString(2, obj.getTitulo());
            preparedStatement.setString(3, obj.getISBN());
            preparedStatement.setString(4, obj.getAutor());
            preparedStatement.setInt(5, obj.getStock());
            preparedStatement.setDouble(6, obj.getPrecioVenta());
            preparedStatement.setDouble(7, obj.getPrecioCompra());
            preparedStatement.setInt(8, obj.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public Producto buscar(int id) {
        Producto producto = null;

        try ( PreparedStatement preparedStatement = connect.prepareStatement(SQL_SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);

            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    producto = mapResultSetToProducto(resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return producto;
    }

    @Override
    public List<Producto> listado() {
        List<Producto> listaProductos = new ArrayList<>();

        try ( Statement statement = connect.createStatement();  ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL)) {

            while (resultSet.next()) {
                Producto producto = mapResultSetToProducto(resultSet);
                listaProductos.add(producto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaProductos;
    }

    private Producto mapResultSetToProducto(ResultSet resultSet) throws SQLException {
        Producto producto = new Producto();
        producto.setId(resultSet.getInt(COLUMN_ID));
        producto.setCodigo(resultSet.getString(COLUMN_CODIGO));
        producto.setTitulo(resultSet.getString(COLUMN_TITULO));
        producto.setISBN(resultSet.getString(COLUMN_ISBN));
        producto.setAutor(resultSet.getString(COLUMN_AUTOR));
        producto.setStock(resultSet.getInt(COLUMN_STOCK));
        producto.setPrecioVenta(resultSet.getDouble(COLUMN_PRECIO_VENTA));
        producto.setPrecioCompra(resultSet.getDouble(COLUMN_PRECIO_COMPRA));
        return producto;
    }

}
