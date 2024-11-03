package mediateca_v01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Libro extends Material {
    private String autor;
    private String editorial;
    private int paginas;
    private int isbn;
    private int anio;

    public Libro(String id, String titulo, String autor, int paginas, int anio, String editorial, int isbn, int unidadesDisponibles) {
        super(id, titulo, unidadesDisponibles);
        this.autor = autor;
        this.paginas = paginas;
        this.isbn = isbn;
        this.editorial = editorial;
        this.anio = anio;
    }

    @Override
    public String getDetalles() {
        return "Libro - Título: " + titulo + ", Autor: " + autor + ", Editorial: " + editorial + ", Páginas: " + paginas;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public String getEditorial() {
        return editorial;
    }
    
    public int getPaginas() {
        return paginas;
    }
    
    public int getAnio() {
    	return anio;
    }
    
    public int getIsbn() {
    	return isbn;
    }

    public void agregarMaterial() throws SQLException {
        String sql = "INSERT INTO Libro (id, titulo, autor, paginas, anio, editorial, isbn, unidadesDisponibles) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, titulo);
            pstmt.setString(3, autor);
            pstmt.setInt(4, paginas);
            pstmt.setInt(5, anio);
            pstmt.setString(6, editorial);
            pstmt.setInt(7, isbn);
            pstmt.setInt(8, unidadesDisponibles);
            pstmt.executeUpdate();
        }
    }

    public void modificarMaterial(String nuevoTitulo, String nuevoAutor, int nuevasPaginas, int nuevoAnio, String nuevaEditorial, int nuevoIsbn, int nuevasUnidades) throws SQLException {
        this.titulo = nuevoTitulo;
        this.autor = nuevoAutor;
        this.editorial = nuevaEditorial;
        this.paginas = nuevasPaginas;
        this.unidadesDisponibles = nuevasUnidades;
        this.anio = nuevoAnio;
        this.isbn = nuevoIsbn;

        String sql = "UPDATE Libro SET titulo = ?, autor = ?, paginas = ?, anio = ?, editorial = ?, isbn = ?, unidadesDisponibles = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, titulo);
            pstmt.setString(2, autor);
            pstmt.setInt(3, paginas);
            pstmt.setInt(4, anio);
            pstmt.setString(5, editorial);
            pstmt.setInt(6, isbn);
            pstmt.setInt(7, unidadesDisponibles);
            pstmt.setString(8, id);
            pstmt.executeUpdate();
        }
    }

    public void borrarMaterial() throws SQLException {
        String sql = "DELETE FROM Libro WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    public static Libro buscarMaterial(String id) throws SQLException {
        String sql = "SELECT * FROM Libro WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Libro(
                    rs.getString("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("paginas"),
                    rs.getInt("anio"),
                    rs.getString("editorial"),
                    rs.getInt("isbn"),
                    rs.getInt("unidadesDisponibles")
                );
            }
        }
        return null;
    }

}
