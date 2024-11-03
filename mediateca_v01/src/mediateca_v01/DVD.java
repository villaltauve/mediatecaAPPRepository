package mediateca_v01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DVD extends Material {
    private String director;
    private String genero;
    private int duracion;

    public DVD(String id, String titulo, String director, int duracion, String genero, int unidadesDisponibles) {
        super(id, titulo, unidadesDisponibles);
        this.director = director;
        this.genero = genero;
        this.duracion = duracion;
    }

    @Override
    public String getDetalles() {
        return "DVD - Título: " + titulo + ", Director: " + director + ", Género: " + genero + ", Duración: " + duracion + " minutos";
    }
    
    public String getDirector() {
    	return director;
    }
    
    public String getGenero() {
    	return genero;
    }
    
    public int getDuracion() {
    	return duracion;
    }

    public void agregarMaterial() throws SQLException {
        String sql = "INSERT INTO DVD (id, titulo, director, duracion, genero, unidadesDisponibles) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, titulo);
            pstmt.setString(3, director);
            pstmt.setInt(4, duracion);
            pstmt.setString(5, genero);
            pstmt.setInt(6, unidadesDisponibles);
            pstmt.executeUpdate();
        }
    }

    public void modificarMaterial(String nuevoTitulo, String nuevoDirector, int nuevaDuracion, String nuevoGenero, int nuevasUnidades) throws SQLException {
        this.titulo = nuevoTitulo;
        this.director = nuevoDirector;
        this.genero = nuevoGenero;
        this.duracion = nuevaDuracion;
        this.unidadesDisponibles = nuevasUnidades;

        String sql = "UPDATE DVD SET titulo = ?, director = ?, duracion = ?, genero = ?, unidadesDisponibles = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, titulo);
            pstmt.setString(2, director);
            pstmt.setInt(3, duracion);
            pstmt.setString(4, genero);
            pstmt.setInt(5, unidadesDisponibles);
            pstmt.setString(6, id);
            pstmt.executeUpdate();
        }
    }

    public void borrarMaterial() throws SQLException {
        String sql = "DELETE FROM DVD WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    public static DVD buscarMaterial(String id) throws SQLException {
        String sql = "SELECT * FROM DVD WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new DVD(
                    rs.getString("id"),
                    rs.getString("titulo"),
                    rs.getString("director"),
                    rs.getInt("duracion"),
                    rs.getString("genero"),
                    rs.getInt("unidadesDisponibles")
                );
            }
        }
        return null;
    }
    

}
