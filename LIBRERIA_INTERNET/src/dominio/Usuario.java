package dominio;

public class Usuario {
    protected int id;
    protected String username;
    protected String clave;
    protected String dni;
    protected String nombre;
    protected String apellido;
    protected TipoUsuario typeUser;

    public Usuario() {
    }

    public Usuario(String username, String clave, String dni, String nombre, String apellido, TipoUsuario typeUser) {
        this.username = username;
        this.clave = clave;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.typeUser = typeUser;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getClave() {
        return clave;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public TipoUsuario getTypeUser() {
        return typeUser;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTypeUser(TipoUsuario typeUser) {
        this.typeUser = typeUser;
    }

    
}
