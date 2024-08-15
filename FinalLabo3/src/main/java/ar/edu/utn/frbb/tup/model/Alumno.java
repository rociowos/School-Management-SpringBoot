package ar.edu.utn.frbb.tup.model;

public class Alumno {
    private long id;
    private String nombre;
    private String apellido;
    private long dni;


    public void setId(long id){
        this.id = id;
    }
    public void getId(){
        return id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void getNombre(){
        return nombre;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }
    public void getApellido(){
        return apellido;
    }

    public void setDni(long dni){
        this.dni = dni;
    }
    public void getDni(){
        return dni;
    }



}

