package ar.edu.utn.frbb.tup.model;

public class Materia {

    private int idmateria;
    private String nombre;
    private Profesor profesor;
    private int anio;
    private int cuatrimestre;


    public int getIdmateria() {
        return idmateria;
    }
    public void setIdmateria(int idmateria) {
        this.idmateria = idmateria;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProfesor() {
        return profesor;
    }
    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(int cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public Materia(){}

    public Materia(String nombre, Profesor profesor, int anio, int cuatrimestre ) {
        this.anio = anio;
        this.profesor = profesor;
        this.cuatrimestre = cuatrimestre;
        this.nombre = nombre;

    }
}
