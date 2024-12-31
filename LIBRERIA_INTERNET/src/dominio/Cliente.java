package dominio;

public class Cliente extends Usuario{
    
    private String direccion;
    private String localidad;
    private String codPostal;
    private String pais;

    public Cliente() {
    }

    public Cliente(String direccion, String localidad, String codPostal, String pais, String username, String clave, String dni, String nombre, String apellido, TipoUsuario typeUser) {
        super(username, clave, dni, nombre, apellido, typeUser);
        this.direccion = direccion;
        this.localidad = localidad;
        this.codPostal = codPostal;
        this.pais = pais;
    }

    
    public String getDireccion() {
        return direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public String getPais() {
        return pais;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
