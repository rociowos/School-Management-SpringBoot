package ar.edu.utn.frbb.tup.presentation.modelDto;

public class ProfesorDto {
    private String nombre;
    private String apellido;
    private String titulo;
    private String nombreMateria;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getnombreMateria() {
        return nombreMateria;
    }

    public void setnombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }
}

