package datos;

import java.sql.Connection;
import java.util.List;

public abstract class DAO<T> {
    public static final String PATH = "C:/libreria/";
    public Connection connect = Conexion.getInstance();
    
    public abstract T crear(T obj);
    public abstract T eliminar(T obj);
    public abstract T actualizar(T obj);
    public abstract T buscar(int id);
    public abstract List<T> listado();
}
