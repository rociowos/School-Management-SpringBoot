package ar.edu.utn.frbb.tup.model;

import java.util.Random;
import ar.edu.utn.frbb.tup.presentation.modelDto.AsignaturaDto;


public class Asignatura {

    private long asignaturaId;
    private long idmateria; 
    private EstadoAsignatura estadoAsignatura;
    private Integer nota;
    private long alumnoid;


    public long getAsignaturaId(){
        return asignaturaId;
    }
    
    public void setAsignaturaId( long asignaturaId){
        this.asignaturaId = asignaturaId;

    }
    
    public long getIdmateria() {
        return idmateria;
    }
    public void setIdmateria(long idmateria) {
        this.idmateria = idmateria;
    }

    public EstadoAsignatura getEstadoAsignatura(){
        return estadoAsignatura;
    }

    public void setEstadoAsignatura(EstadoAsignatura estadoAsignatura){
        this.estadoAsignatura = estadoAsignatura;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Integer getNota() {
        return nota;
    }


    public long getAlumnoid() {
        return alumnoid;
    }
    public void setAlumnoid(long alumnoid) {
        this.alumnoid = alumnoid;
    }


    
    public Asignatura(){
        super();
    }

    public Asignatura (AsignaturaDto asignaturaDto){
        this.idmateria = Long.parseLong(asignaturaDto.getIdmateria());
        this.estadoAsignatura = EstadoAsignatura.fromString(asignaturaDto.getEstadoAsignatura());
        this.nota = Integer.parseInt(asignaturaDto.getNota());
        this.alumnoid = Long.parseLong(asignaturaDto.getAlumnoid());
        this.asignaturaId = Asignatura.generarIdAsignatura();

    }

    public static long generarIdAsignatura() {
        Random random = new Random();
        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            idBuilder.append(random.nextInt(10)); 
        }
        return Long.parseLong(idBuilder.toString());
    }

}


