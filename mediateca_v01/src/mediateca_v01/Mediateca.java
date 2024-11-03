package mediateca_v01;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mediateca {
	
    public static void main(String[] args) {	
    	
    	JFrame frame = new JFrame("Mediateca");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Cambiar el color de fondo del marco
        frame.getContentPane().setBackground(Color.DARK_GRAY);

        // Crear un JPanel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBackground(Color.DARK_GRAY); // Fondo oscuro para el panel

        // Crear botones con un tamaño más grande
        JButton agregar = new JButton("Agregar Material");
        JButton modificar = new JButton("Modificar Material");
        JButton eliminar = new JButton("Eliminar Material");
        JButton listar = new JButton("Listar Materiales");
        JButton buscar = new JButton("Buscar Material");
        JButton salir = new JButton("Salir");

        // Cambiar el color de los botones y texto
        Color buttonColor = Color.LIGHT_GRAY;
        Color textColor = Color.BLACK;
        
        agregar.setBackground(buttonColor);
        modificar.setBackground(buttonColor);
        eliminar.setBackground(buttonColor);
        listar.setBackground(buttonColor);
        buscar.setBackground(buttonColor);
        salir.setBackground(buttonColor);

        agregar.setForeground(textColor);
        modificar.setForeground(textColor);
        eliminar.setForeground(textColor);
        listar.setForeground(textColor);
        buscar.setForeground(textColor);
        salir.setForeground(textColor);

        // Establecer un tamaño mínimo para los botones
        agregar.setPreferredSize(new Dimension(200, 50));
        modificar.setPreferredSize(new Dimension(200, 50));
        eliminar.setPreferredSize(new Dimension(200, 50));
        listar.setPreferredSize(new Dimension(200, 50));
        buscar.setPreferredSize(new Dimension(200, 50));
        salir.setPreferredSize(new Dimension(200, 50));

        // Agregar botones al panel
        buttonPanel.add(agregar);
        buttonPanel.add(modificar);
        buttonPanel.add(eliminar);
        buttonPanel.add(listar);
        buttonPanel.add(buscar);
        buttonPanel.add(salir);

        // Agregar el panel de botones al marco
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Mostrar el marco
        frame.setVisible(true);

        // Agregar ActionListeners a los botones
        agregar.addActionListener(e -> agregarMaterial());
        modificar.addActionListener(e -> modificarMaterial());
        eliminar.addActionListener(e -> eliminarMaterial());
        listar.addActionListener(e -> listarMateriales());
        buscar.addActionListener(e -> buscarMaterial());
        salir.addActionListener(e -> System.exit(0));
    }

    private static void agregarMaterial() {
        String[] opciones = {"Libro", "Revista", "CD", "DVD"};
        String tipo = (String) JOptionPane.showInputDialog(null, "Seleccione el tipo de material:", "Agregar Material",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (tipo != null) {
            switch (tipo) {
                case "Libro":
                    agregarLibro();
                    break;
                case "Revista":
                    agregarRevista();
                    break;
                case "CD":
                    agregarCD();
                    break;
                case "DVD":
                    agregarDVD();
                    break;
            }
        }
    }

    private static void agregarLibro() {
        String id = generarId("Libro");
        String titulo = JOptionPane.showInputDialog("Título del libro:");
        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El título no puede estar vacío.");
            return;
        }

        String autor = JOptionPane.showInputDialog("Autor del libro:");
        if (autor == null || autor.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El autor no puede estar vacío.");
            return;
        }

        int paginas = obtenerNumero("Número de páginas:");
        if (paginas == -1) return; // Si se ingresó un valor inválido

        int anio = obtenerNumero("Año:");
        if (anio == -1) return; // Si se ingresó un valor inválido

        String editorial = JOptionPane.showInputDialog("Editorial del libro:");
        if (editorial == null || editorial.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La editorial no puede estar vacía.");
            return;
        }

        int isbn = obtenerNumero("ISBN:");
        if (isbn == -1) return; // Si se ingresó un valor inválido

        int unidades = obtenerNumero("Unidades disponibles:");
        if (unidades == -1) return; // Si se ingresó un valor inválido

        Libro libro = new Libro(id, titulo, autor, paginas, anio, editorial, isbn, unidades);
        try {
            libro.agregarMaterial(); 
            JOptionPane.showMessageDialog(null, "Libro agregado con éxito!");
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    // Método para obtener un número con validación
    private static int obtenerNumero(String mensaje) {
        String input = JOptionPane.showInputDialog(mensaje);
        if (input == null || input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El valor no puede estar vacío.");
            return -1; // Valor inválido
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
            return -1; // Valor inválido
        }
    }

    private static void agregarRevista() {
        String id = generarId("Revista");
        String titulo = JOptionPane.showInputDialog("Título de la revista:");
        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El título no puede estar vacío.");
            return;
        }

        String autor = JOptionPane.showInputDialog("Autor de la revista:");
        if (autor == null || autor.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El autor no puede estar vacío.");
            return;
        }

        String periodicidad = JOptionPane.showInputDialog("Periodicidad de la revista:");
        if (periodicidad == null || periodicidad.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La periodicidad no puede estar vacía.");
            return;
        }

        int unidades = obtenerNumero("Unidades disponibles:");
        if (unidades == -1) return; // Si se ingresó un valor inválido

        int anioPublicacion = obtenerNumero("Año de publicación:");
        if (anioPublicacion == -1) return; // Si se ingresó un valor inválido

        String editorial = JOptionPane.showInputDialog("Editorial de la revista:");
        if (editorial == null || editorial.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La editorial no puede estar vacía.");
            return;
        }

        Revista revista = new Revista(id, titulo, autor, periodicidad, unidades, anioPublicacion, editorial);
        try {
            revista.agregarMaterial(); 
            JOptionPane.showMessageDialog(null, "Revista agregada con éxito!");
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    private static void agregarCD() {
        String id = generarId("CD");
        String titulo = JOptionPane.showInputDialog("Título del CD:");
        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El título no puede estar vacío.");
            return;
        }

        String artista = JOptionPane.showInputDialog("Artista del CD:");
        if (artista == null || artista.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El artista no puede estar vacío.");
            return;
        }

        int numCanciones = obtenerNumero("Número de canciones:");
        if (numCanciones == -1) return; // Si se ingresó un valor inválido

        int duracion = obtenerNumero("Duración (minutos):");
        if (duracion == -1) return; // Si se ingresó un valor inválido

        String genero = JOptionPane.showInputDialog("Género del CD:");
        if (genero == null || genero.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El género no puede estar vacío.");
            return;
        }

        int unidades = obtenerNumero("Unidades disponibles:");
        if (unidades == -1) return; // Si se ingresó un valor inválido

        CD cd = new CD(id, titulo, artista, numCanciones, duracion, genero, unidades);
        try {
            cd.agregarMaterial(); 
            JOptionPane.showMessageDialog(null, "CD agregado con éxito!");
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    private static void agregarDVD() {
        String id = generarId("DVD");
        String titulo = JOptionPane.showInputDialog("Título del DVD:");
        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El título no puede estar vacío.");
            return;
        }

        String director = JOptionPane.showInputDialog("Director del DVD:");
        if (director == null || director.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El director no puede estar vacío.");
            return;
        }

        int duracion = obtenerNumero("Duración (minutos):");
        if (duracion == -1) return; // Si se ingresó un valor inválido

        String genero = JOptionPane.showInputDialog("Género del DVD:");
        if (genero == null || genero.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El género no puede estar vacío.");
            return;
        }

        int unidades = obtenerNumero("Unidades disponibles:");
        if (unidades == -1) return; // Si se ingresó un valor inválido

        DVD dvd = new DVD(id, titulo, director, duracion, genero, unidades);
        try {
            dvd.agregarMaterial(); 
            JOptionPane.showMessageDialog(null, "DVD agregado con éxito!");
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    } 
    
    private static void modificarMaterial() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del material a modificar:");

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Verificar si es un libro
            String sqlLibro = "SELECT * FROM Libro WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sqlLibro)) {
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    // Modificar libro
                    Libro libro = new Libro(rs.getString("id"), rs.getString("titulo"), rs.getString("autor"),
                        rs.getInt("paginas"), rs.getInt("anio"),
                        rs.getString("editorial"), rs.getInt("isbn"), rs.getInt("unidadesDisponibles"));

                    String nuevoTitulo = JOptionPane.showInputDialog("Nuevo título:", libro.getTitulo());
                    String nuevoAutor = JOptionPane.showInputDialog("Nuevo autor:", libro.getAutor());
                    int nuevasPaginas = Integer.parseInt(JOptionPane.showInputDialog("Nuevas páginas:", libro.getPaginas()));
                    int nuevoAnio = Integer.parseInt(JOptionPane.showInputDialog("Nuevo Año:", libro.getAnio()));
                    String nuevaEditorial = JOptionPane.showInputDialog("Nueva editorial:", libro.getEditorial());
                    int nuevoIsbn = Integer.parseInt(JOptionPane.showInputDialog("Nuevo ISBN:", libro.getIsbn()));
                    int nuevasUnidades = Integer.parseInt(JOptionPane.showInputDialog("Nuevas unidades:", libro.getUnidadesDisponibles()));

                    libro.modificarMaterial(nuevoTitulo, nuevoAutor, nuevasPaginas, nuevoAnio, nuevaEditorial, nuevoIsbn, nuevasUnidades);
                    JOptionPane.showMessageDialog(null, "Libro modificado con éxito!");
                    return;
                }
            }

            // Verificar si es una revista
            String sqlRevista = "SELECT * FROM Revista WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sqlRevista)) {
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    // Modificar revista
                    Revista revista = new Revista(rs.getString("id"), rs.getString("titulo"), rs.getString("autor"),
                        rs.getString("periodicidad"), rs.getInt("unidadesDisponibles"),
                        rs.getInt("anioPublicacion"), rs.getString("editorial"));

                    String nuevoTitulo = JOptionPane.showInputDialog("Nuevo título:", revista.getTitulo());
                    String nuevoAutor = JOptionPane.showInputDialog("Nuevo Autor:", revista.getAutor());
                    String nuevoPeriodicidad = JOptionPane.showInputDialog("Nuevo periodicidad:", revista.getPeriodicidad());
                    int nuevasUnidades = Integer.parseInt(JOptionPane.showInputDialog("Nuevas unidades:", revista.getUnidadesDisponibles()));
                    int nuevoAnioPublicacion = Integer.parseInt(JOptionPane.showInputDialog("Nuevo Año de Publicación:", revista.getAnioPublicacion()));
                    String nuevoEditorial = JOptionPane.showInputDialog("Nueva editorial:", revista.getEditorial());

                    revista.modificarMaterial(nuevoTitulo, nuevoAutor, nuevoPeriodicidad, nuevasUnidades, nuevoAnioPublicacion, nuevoEditorial);
                    JOptionPane.showMessageDialog(null, "Revista modificada con éxito!");
                    return;
                }
            }

            // Verificar si es un CD
            String sqlCD = "SELECT * FROM CD WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sqlCD)) {
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    // Modificar CD
                    CD cd = new CD(rs.getString("id"), rs.getString("titulo"), rs.getString("artista"), 
                        rs.getInt("numCanciones"), rs.getInt("duracion"), 
                        rs.getString("genero"), rs.getInt("unidadesDisponibles"));

                    String nuevoTitulo = JOptionPane.showInputDialog("Nuevo título:", cd.getTitulo());
                    String nuevoArtista = JOptionPane.showInputDialog("Nuevo artista:", cd.getArtista());
                    int nuevoNumCanciones = Integer.parseInt(JOptionPane.showInputDialog("Nuevas canciones:", cd.getNumCanciones()));
                    int nuevaDuracion = Integer.parseInt(JOptionPane.showInputDialog("Nueva duración:", cd.getDuracion()));
                    String nuevoGenero = JOptionPane.showInputDialog("Nuevo género:", cd.getGenero());
                    int nuevasUnidades = Integer.parseInt(JOptionPane.showInputDialog("Nuevas unidades:", cd.getUnidadesDisponibles()));

                    cd.modificarMaterial(nuevoTitulo, nuevoArtista, nuevoNumCanciones, nuevaDuracion, nuevoGenero, nuevasUnidades);
                    JOptionPane.showMessageDialog(null, "CD modificado con éxito!");
                    return;
                }
            }

            // Verificar si es un DVD
            String sqlDVD = "SELECT * FROM DVD WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sqlDVD)) {
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    // Modificar DVD
                    DVD dvd = new DVD(rs.getString("id"), rs.getString("titulo"), rs.getString("director"), 
                        rs.getInt("duracion"), rs.getString("genero"), 
                        rs.getInt("unidadesDisponibles"));

                    String nuevoTitulo = JOptionPane.showInputDialog("Nuevo título:", dvd.getTitulo());
                    String nuevoDirector = JOptionPane.showInputDialog("Nuevo director:", dvd.getDirector());
                    int nuevaDuracion = Integer.parseInt(JOptionPane.showInputDialog("Nueva duración:", dvd.getDuracion()));
                    String nuevoGenero = JOptionPane.showInputDialog("Nuevo género:", dvd.getGenero());
                    int nuevasUnidades = Integer.parseInt(JOptionPane.showInputDialog("Nuevas unidades:", dvd.getUnidadesDisponibles()));

                    dvd.modificarMaterial(nuevoTitulo, nuevoDirector, nuevaDuracion, nuevoGenero, nuevasUnidades);
                    JOptionPane.showMessageDialog(null, "DVD modificado con éxito!");
                    return;
                }
            }

            // Si no se encontró el material
            JOptionPane.showMessageDialog(null, "Material no encontrado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    
    
    private static void eliminarMaterial() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del material a eliminar:");

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Verificar si es un libro
            String sqlLibro = "SELECT * FROM Libro WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sqlLibro)) {
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    // Borrar libro
                    Libro libro = new Libro(rs.getString("id"),rs.getString("titulo"), rs.getString("autor"), 
                        rs.getInt("paginas"), rs.getInt("anio"), 
                        rs.getString("editorial"), rs.getInt("isbn"), rs.getInt("unidadesDisponibles"));
                    libro.borrarMaterial();
                    JOptionPane.showMessageDialog(null, "Libro eliminado con éxito!");
                    return;
                }
            }

            // Verificar si es una revista
            String sqlRevista = "SELECT * FROM Revista WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sqlRevista)) {
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    // Borrar revista
                    Revista revista = new Revista(rs.getString("id"), rs.getString("titulo"), rs.getString("autor"),
                        rs.getString("periodicidad"), rs.getInt("unidadesDisponibles"),
                        rs.getInt("anioPublicacion"), rs.getString("editorial"));
                    revista.borrarMaterial();
                    JOptionPane.showMessageDialog(null, "Revista eliminada con éxito!");
                    return;
                }
            }

            // Verificar si es un CD
            String sqlCD = "SELECT * FROM CD WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sqlCD)) {
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    // Borrar CD
                    CD cd = new CD(rs.getString("id"), rs.getString("titulo"), rs.getString("artista"), 
                        rs.getInt("numCanciones"), rs.getInt("duracion"), 
                        rs.getString("genero"), rs.getInt("unidadesDisponibles"));
                    cd.borrarMaterial();
                    JOptionPane.showMessageDialog(null, "CD eliminado con éxito!");
                    return;
                }
            }

            // Verificar si es un DVD
            String sqlDVD = "SELECT * FROM DVD WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sqlDVD)) {
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    // Borrar DVD
                    DVD dvd = new DVD(rs.getString("id"), rs.getString("titulo"), rs.getString("director"), 
                        rs.getInt("duracion"), rs.getString("genero"), 
                        rs.getInt("unidadesDisponibles"));
                    dvd.borrarMaterial();
                    JOptionPane.showMessageDialog(null, "DVD eliminado con éxito!");
                    return;
                }
            }

            // Si no se encontró el material
            JOptionPane.showMessageDialog(null, "Material no encontrado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void listarMateriales() {
        String[] columnas = { "ID", "Título", "Tipo", "Autor/Director/Artista", "NumCanciones", "Género", "Editorial", "Duración(min)/Páginas", "Unidades Disponibles", "Año Publicacion", "ISBN", "periodicidad" };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        // Listar Libros
        try {
            String sql = "SELECT * FROM Libro";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                        rs.getString("id"),
                        rs.getString("titulo"),
                        "Libro",
                        rs.getString("autor"),
                        "N/A",
                        "N/A",
                        rs.getString("editorial"),
                        rs.getInt("paginas"),
                        rs.getInt("unidadesDisponibles"),
                        rs.getInt("anio"),
                        rs.getInt("isbn"),
                        "N/A"
                    };
                    modelo.addRow(fila);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Listar Revistas
        try {
            String sql = "SELECT * FROM Revista";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                        rs.getString("id"),
                        rs.getString("titulo"),
                        "Revista",
                        rs.getString("autor"),
                        "N/A",
                        "N/A",
                        rs.getString("editorial"),
                        "N/A",
                        rs.getInt("unidadesDisponibles"),
                        rs.getInt("anioPublicacion"),
                        "N/A",
                        rs.getString("periodicidad")
                    };
                    modelo.addRow(fila);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Listar CDs
        try {
            String sql = "SELECT * FROM CD";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                        rs.getString("id"),
                        rs.getString("titulo"),
                        "CD",
                        rs.getString("artista"),
                        rs.getInt("numCanciones"),
                        rs.getString("genero"),
                        "N/A",
                        rs.getInt("duracion"),
                        rs.getInt("unidadesDisponibles"),
                        "N/A",
                        "N/A",
                        "N/A"
                    };
                    modelo.addRow(fila);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Listar DVDs
        try {
            String sql = "SELECT * FROM DVD";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                        rs.getString("id"),
                        rs.getString("titulo"),
                        "DVD",
                        rs.getString("director"),
                        "N/A",
                        rs.getString("genero"),
                        "N/A",
                        rs.getInt("duracion"),
                        rs.getInt("unidadesDisponibles"),
                        "N/A",
                        "N/A",
                        "N/A"
                    };
                    modelo.addRow(fila);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JTable tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(1400, 600));
        JOptionPane.showMessageDialog(null, scrollPane, "Lista de Materiales", JOptionPane.INFORMATION_MESSAGE);
    }

    
    private static void buscarMaterial() {
        String[] opciones = {"Titulo", "Autor", "Genero"};
        String criterio = (String) JOptionPane.showInputDialog(
            null,
            "Seleccione el criterio de búsqueda:",
            "Buscar Material",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (criterio == null) {
            return; // El usuario canceló la operación
        }

        String valorBusqueda = JOptionPane.showInputDialog("Ingrese el valor de búsqueda:");

        if (valorBusqueda == null || valorBusqueda.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un valor para buscar.");
            return;
        }

        DefaultTableModel modelo = new DefaultTableModel(new String[]{
            "ID", "Titulo", "Tipo", "Autor/Director/Artista", "Género",
            "Editorial", "Duración(min)/Páginas", "Unidades Disponibles",
            "Año Publicación", "ISBN", "Periodicidad"
        }, 0);

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Buscar Libros
            if (criterio.equals("Titulo") || criterio.equals("Autor")) {
                String sqlLibros = "SELECT * FROM Libro WHERE " + criterio.toLowerCase() + " LIKE ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sqlLibros)) {
                    pstmt.setString(1, "%" + valorBusqueda + "%");
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        modelo.addRow(new Object[]{
                            rs.getString("id"), rs.getString("titulo"), "Libro", rs.getString("autor"),
                            "N/A", rs.getString("editorial"), rs.getInt("paginas"),
                            rs.getInt("unidadesDisponibles"), rs.getInt("anio"), rs.getInt("isbn"), "N/A"
                        });
                    }
                }
            }

            // Buscar Revistas
            if (criterio.equals("Titulo") || criterio.equals("Autor")) {
                String sqlRevistas = "SELECT * FROM Revista WHERE " + criterio.toLowerCase() + " LIKE ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sqlRevistas)) {
                    pstmt.setString(1, "%" + valorBusqueda + "%");
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        modelo.addRow(new Object[]{
                            rs.getString("id"), rs.getString("titulo"), "Revista", rs.getString("autor"),
                            "N/A", rs.getString("editorial"), "N/A", rs.getInt("unidadesDisponibles"),
                            rs.getInt("anioPublicacion"), "N/A", rs.getString("periodicidad")
                        });
                    }
                }
            }

         // Buscar CDs
            if (criterio.equals("Titulo") || criterio.equals("Genero")) {
                String sqlCDs = "SELECT * FROM CD WHERE " + (criterio.equals("Genero") ? "genero" : "titulo") + " LIKE ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sqlCDs)) {
                    pstmt.setString(1, "%" + valorBusqueda + "%");
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        modelo.addRow(new Object[]{
                            rs.getString("id"), rs.getString("titulo"), "CD", rs.getString("artista"),
                            rs.getString("genero"), "N/A", rs.getInt("duracion"), rs.getInt("unidadesDisponibles"),
                            "N/A", "N/A", "N/A"
                        });
                    }
                }
            }

            // Buscar CDs - Añadir lógica para autor
            if (criterio.equals("Autor")) {
                String sqlCDs = "SELECT * FROM CD WHERE artista LIKE ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sqlCDs)) {
                    pstmt.setString(1, "%" + valorBusqueda + "%");
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        modelo.addRow(new Object[]{
                            rs.getString("id"), rs.getString("titulo"), "CD", rs.getString("artista"),
                            rs.getString("genero"), "N/A", rs.getInt("duracion"), rs.getInt("unidadesDisponibles"),
                            "N/A", "N/A", "N/A"
                        });
                    }
                }
            }
            
         // Buscar DVDs
            if (criterio.equals("Titulo") || criterio.equals("Género")) {
                String sqlDVDs = "SELECT * FROM DVD WHERE " + (criterio.equals("Género") ? "genero" : "titulo") + " LIKE ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sqlDVDs)) {
                    pstmt.setString(1, "%" + valorBusqueda + "%");
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        modelo.addRow(new Object[]{
                            rs.getString("id"), rs.getString("titulo"), "DVD", rs.getString("director"),
                            rs.getString("genero"), "N/A", rs.getInt("duracion"), rs.getInt("unidadesDisponibles"),
                            "N/A", "N/A", "N/A"
                        });
                    }
                }
            }

            // Buscar DVDs - Añadir lógica para autor (director)
            if (criterio.equals("Autor")) {
                String sqlDVDs = "SELECT * FROM DVD WHERE director LIKE ?";
                try (PreparedStatement pstmt = connection.prepareStatement(sqlDVDs)) {
                    pstmt.setString(1, "%" + valorBusqueda + "%");
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        modelo.addRow(new Object[]{
                            rs.getString("id"), rs.getString("titulo"), "DVD", rs.getString("director"),
                            rs.getString("genero"), "N/A", rs.getInt("duracion"), rs.getInt("unidadesDisponibles"),
                            "N/A", "N/A", "N/A"
                        });
                    }
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Mostrar resultados
        JTable tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        JOptionPane.showMessageDialog(null, scrollPane, "Resultados de la Búsqueda", JOptionPane.INFORMATION_MESSAGE);
    }



    private static String generarId(String tipo) {
        String prefijo = "";
        switch (tipo) {
            case "Libro":
                prefijo = "LIB";
                break;
            case "Revista":
                prefijo = "REV";
                break;
            case "CD":
                prefijo = "CDA";
                break;
            case "DVD":
                prefijo = "DVD";
                break;
            default:
                throw new IllegalArgumentException("Tipo de material desconocido: " + tipo);
        }

        int conteo = 0;
        String sql = "SELECT COUNT(*) FROM " + tipo;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                conteo = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String newId;
        do {
            conteo++; // Incrementar para el nuevo ID
            newId = String.format("%s%05d", prefijo, conteo);

            // Verificar si el nuevo ID ya existe en la base de datos
            String checkSql = "SELECT COUNT(*) FROM " + tipo + " WHERE id = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(checkSql)) {
                pstmt.setString(1, newId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int existingCount = rs.getInt(1);
                        if (existingCount > 0) {
                            // El ID ya existe, sigue incrementando el conteo
                            continue;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Salir del bucle si el ID no existe
            break;
        } while (true);

        return newId;
    }


}
