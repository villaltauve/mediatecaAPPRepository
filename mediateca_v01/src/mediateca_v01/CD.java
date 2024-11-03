package mediateca_v01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CD extends Material {
    private String artista;
    private String genero;
    private int duracion;
    private int numCanciones;

    public CD(String id, String titulo, String artista, int numCanciones, int duracion, String genero, int unidadesDisponibles) {
        super(id, titulo, unidadesDisponibles);
        this.artista = artista;
        this.genero = genero;
        this.duracion = duracion;
        this.numCanciones = numCanciones;
    }

    @Override
    public String getDetalles() {
        return "CD - Título: " + titulo + ", Artista: " + artista + ", Género: " + genero + 
               ", Duración: " + duracion + " minutos, Número de Canciones: " + numCanciones;
    }
    
    public String getArtista() {
    	return artista;
    }
    
    public String getGenero() {
    	return genero;
    }
    
    public int getDuracion() {
    	return duracion;
    }
    
    public int getNumCanciones() {
    	return numCanciones;
    }
    
    public int getunidadesDisponibles() {
    	return unidadesDisponibles;
    }

    public void agregarMaterial() throws SQLException {
        String sql = "INSERT INTO CD (id, titulo, artista, numCanciones, duracion, genero, unidadesDisponibles) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, titulo);
            pstmt.setString(3, artista);
            pstmt.setInt(4, numCanciones);
            pstmt.setInt(5, duracion);
            pstmt.setString(6, genero);
            pstmt.setInt(7, unidadesDisponibles);
            pstmt.executeUpdate();
        }
    }

    public void modificarMaterial(String nuevoTitulo, String nuevoArtista, int nuevoNumCanciones, int nuevaDuracion, String nuevoGenero, int nuevasUnidades) throws SQLException {
        this.titulo = nuevoTitulo;
        this.artista = nuevoArtista;
        this.genero = nuevoGenero;
        this.duracion = nuevaDuracion;
        this.numCanciones = nuevoNumCanciones;
        this.unidadesDisponibles = nuevasUnidades;

        String sql = "UPDATE CD SET titulo = ?, artista = ?, numCanciones = ?, duracion = ?, genero = ?, unidadesDisponibles = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, titulo);
            pstmt.setString(2, artista);
            pstmt.setInt(3, numCanciones);
            pstmt.setInt(4, duracion);
            pstmt.setString(5, genero);
            pstmt.setInt(6, unidadesDisponibles);
            pstmt.setString(7, id);
            pstmt.executeUpdate();
        }
    }

    public void borrarMaterial() throws SQLException {
        String sql = "DELETE FROM CD WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    public static CD buscarMaterial(String id) throws SQLException {
        String sql = "SELECT * FROM CD WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new CD(
                    rs.getString("id"),
                    rs.getString("titulo"),
                    rs.getString("artista"),
                    rs.getInt("numCanciones"),
                    rs.getInt("duracion"),
                    rs.getString("genero"),
                    rs.getInt("unidadesDisponibles")
                );
            }
        }
        return null;
    }
    

}
