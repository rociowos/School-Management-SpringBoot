package ar.edu.utn.frbb.tup.model;

import java.util.List;
import java.util.Random;

import ar.edu.utn.frbb.tup.presentation.modelDto.MateriaDto;

public class Materia {

    private long idmateria;
    private String nombre;
    private long profesorid;
    private long anio;
    private long cuatrimestre;
    private List<Long> correlatividades;


    public long getIdmateria() {
        return idmateria;
    }
    public void setIdmateria(long idmateria) {
        this.idmateria = idmateria;
    }

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


    
    public Materia(){
        super();
    }

    public Materia (MateriaDto materiadto){
        this.nombre = materiadto.getNombre();
        this.profesorid = Long.parseLong(materiadto.getProfesorid());
        this.anio = Long.parseLong(materiadto.getAnio());
        this.cuatrimestre = Long.parseLong(materiadto.getCuatrimestre());
        this.correlatividades = materiadto.getCorrelatividades();
        this.idmateria = Materia.generarIdMateria();


    }

    public static long generarIdMateria() {
        Random random = new Random();
        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            idBuilder.append(random.nextInt(10)); 
        }
        return Long.parseLong(idBuilder.toString());
    }

}



