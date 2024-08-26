package ar.edu.utn.frbb.tup.presentation.modelDto;

import java.util.List;


public class MateriaDto {
    private String nombre;
    private Long profesorid;
    private long anio;
    private long cuatrimestre;
    private List<Long> correlatividades;


    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   
    public long getProfesorid() {
        return profesorid;
    }
    public void setProfesorid(long profesorid) {
        this.profesorid = profesorid;
    }

    public long getAnio() {
        return anio;
    }

    public void setAnio(long anio) {
        this.anio = anio;
    }

    public long getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(long cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
 
    }

    public List<Long> getCorrelatividades() {
        return correlatividades;
    }

    public void setCorrelatividades(List<Long> correlatividades) {
        this.correlatividades = correlatividades;
    }
}
