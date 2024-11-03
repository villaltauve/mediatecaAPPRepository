package mediateca_v01;

public abstract class Material {
    protected String id;
    protected String titulo;
    protected int unidadesDisponibles;

    public Material(String id, String titulo, int unidadesDisponibles) {
        this.id = id;
        this.titulo = titulo;
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getUnidadesDisponibles() {
        return unidadesDisponibles;
    }

    public abstract String getDetalles();
}
