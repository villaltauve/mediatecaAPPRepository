package mediateca_v01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Revista extends Material {
    private String periodicidad;
    private int anioPublicacion;
    private String autor;
    private String editorial;

    public Revista(String id, String titulo, String autor, String periodicidad, int unidadesDisponibles, int anioPublicacion, String editorial) {
        super(id, titulo, unidadesDisponibles);
        this.autor = autor;
        this.editorial = editorial;
        this.periodicidad = periodicidad;
        this.anioPublicacion = anioPublicacion;
    }

    @Override
    public String getDetalles() {
        return "Revista - Título: " + titulo + "Autor: " + autor + "Editorial: " + editorial + "Periodicidad: " + periodicidad + ", Año de Publicacion: " + anioPublicacion;
    }
    
    public String getPeriodicidad() {
    	return periodicidad;
    }
    
    public String getEditorial() {
    	return editorial;
    }
    
    public int getAnioPublicacion() {
    	return anioPublicacion;
    }
    
    public String getAutor() {
    	return autor;
    }
    
    

    public void agregarMaterial() throws SQLException {
        String sql = "INSERT INTO Revista (id, titulo, autor, periodicidad, unidadesDisponibles, anioPublicacion, editorial) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, titulo);
            pstmt.setString(3, autor);
            pstmt.setString(4, periodicidad);
            pstmt.setInt(5, unidadesDisponibles);
            pstmt.setInt(6, anioPublicacion);
            pstmt.setString(7, editorial);
            pstmt.executeUpdate();
        }
    }

    public void modificarMaterial(String nuevoTitulo, String nuevoAutor, String nuevoPeriodicidad, int nuevasUnidades, int nuevoAnioPublicacion, String nuevoEditorial) throws SQLException {
        this.titulo = nuevoTitulo;
        this.autor = nuevoAutor;
        this.periodicidad = nuevoPeriodicidad;
        this.unidadesDisponibles = nuevasUnidades;
        this.anioPublicacion = nuevoAnioPublicacion;
        this.editorial = nuevoEditorial;

        String sql = "UPDATE Revista SET titulo = ?, autor = ?, periodicidad = ?, unidadesDisponibles = ?, anioPublicacion = ?, editorial = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, titulo);
            pstmt.setString(2, autor);
            pstmt.setString(3, periodicidad);
            pstmt.setInt(4, unidadesDisponibles);
            pstmt.setInt(5, anioPublicacion);
            pstmt.setString(6, editorial);
            pstmt.setString(7, id);
            pstmt.executeUpdate();
        }
    }

    public void borrarMaterial() throws SQLException {
        String sql = "DELETE FROM Revista WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    public static Revista buscarMaterial(String id) throws SQLException {
        String sql = "SELECT * FROM Revista WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Revista(
                    rs.getString("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("periodicidad"),
                    rs.getInt("unidadesDisponibles"),
                    rs.getInt("anioPublicacion"),
                    rs.getString("editorial")
                );
            }
        }
        
        
        return null;
    }

}
