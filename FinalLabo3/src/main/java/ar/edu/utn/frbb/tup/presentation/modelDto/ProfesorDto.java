package ar.edu.utn.frbb.tup.presentation.modelDto;

public class ProfesorDto {
    private String nombre;
    private String apellido;
    private String titulo;
    private String nombremateria;

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
        return nombremateria;
    }

    public void setnombreMateria(String nombremateria) {
        this.nombremateria = nombremateria;
    }
}

