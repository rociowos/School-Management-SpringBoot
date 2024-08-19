package ar.edu.utn.frbb.tup.model;

public class Profesor {
    private long Id;
    private String nombre;
    private String apellido;
    private String titulo;
    private String nombreMateria;

    public Profesor(String nombre, String apellido, String titulo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.titulo = titulo;
        this.nombreMateria = nombreMateria;
    }


    public void setId(long id) {
        Id = id;
    }

    public long getId() {
        return Id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApellido() {
        return apellido;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }
}


