package ar.edu.utn.frbb.tup.model;
import java.util.Random;

import ar.edu.utn.frbb.tup.presentation.modelDto.AlumnoDto;

public class Alumno {
    private long id;
    private String nombre;
    private String apellido;
    private long dni;


    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getNombre(){
        return nombre;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }
    public String getApellido(){
        return apellido;
    }

    public void setDni(long dni){
        this.dni = dni;
    }
    public long getDni(){
        return dni;
    }
  
    public Alumno(){
        super();
    }

    public Alumno (AlumnoDto alumnoDto){
        this.nombre = alumnoDto.getNombre();
        this.apellido = alumnoDto.getApellido();
        this.dni = Long.parseLong(alumnoDto.getDni());
        this.id = Alumno.generarId();


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

