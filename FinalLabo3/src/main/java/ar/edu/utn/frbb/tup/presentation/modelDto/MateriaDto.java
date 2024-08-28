package ar.edu.utn.frbb.tup.presentation.modelDto;

import java.util.List;


public class MateriaDto {
    private String nombre;
    private String profesorid;
    private String anio;
    private String cuatrimestre;
    private List<Long> correlatividades;


    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   
    public String getProfesorid() {
        return profesorid;
    }
    public void setProfesorid(String profesorid) {
        this.profesorid = profesorid;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(String cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
 
    }

    public List<Long> getCorrelatividades() {
        return correlatividades;
    }

    public void setCorrelatividades(List<Long> correlatividades) {
        this.correlatividades = correlatividades;
    }
}
