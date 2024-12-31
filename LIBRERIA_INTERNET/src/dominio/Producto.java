package dominio;

public class Producto {
    private int id;
    private String codigo;
    private String titulo;
    private String ISBN;
    private String autor;
    private int stock;
    private double precioVenta;
    private double precioCompra;

    public Producto() {
    }

    public Producto(String codigo, String titulo, String ISBN, String autor, double precioVenta, double precioCompra) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.ISBN = ISBN;
        this.autor = autor;
        this.precioVenta = precioVenta;
        this.precioCompra = precioCompra;
    }

    
    public Producto(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getAutor() {
        return autor;
    }

    public int getStock() {
        return stock;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    
}
