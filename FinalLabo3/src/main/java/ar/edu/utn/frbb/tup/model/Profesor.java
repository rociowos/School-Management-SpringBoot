package ar.edu.utn.frbb.tup.model;
import java.util.Random;
import ar.edu.utn.frbb.tup.presentation.modelDto.ProfesorDto;

public class Profesor {
    private long id;
    private String nombre;
    private String apellido;
    private String titulo;
    private String nombremateria;

    public Profesor(String nombre, String apellido, String titulo, String nombremateria) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.titulo = titulo;
        this.nombremateria = nombremateria;
    }


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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

    public void setNombreMateria(String nombremateria) {
        this.nombremateria = nombremateria;
    }

    public String getNombreMateria() {
        return nombremateria;
    }


    public Profesor(){
        super();
    }

    public Profesor (ProfesorDto profesorDto){
        this.nombre = profesorDto.getNombre();
        this.apellido = profesorDto.getApellido();
        this.titulo = profesorDto.getTitulo();
        this.nombremateria = profesorDto.getnombreMateria();
        this.id = Profesor.generarId();


    }

    public static long generarId() {
        Random random = new Random();
        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            idBuilder.append(random.nextInt(10)); 
        }
        return Long.parseLong(idBuilder.toString());
    }

}







