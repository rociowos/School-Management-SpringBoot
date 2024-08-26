package ar.edu.utn.frbb.tup.model;

public class Asignatura {

    private long asignaturaId;
    private Materia materia; //idmateria
    private EstadoAsignatura estado;
    private Integer nota;
    //idalumno
    public Asignatura() {
    }
    public Asignatura(Materia materia, long asignaturaId) {
        this.asignaturaId = asignaturaId;
        this.materia = materia;
        this.estado = EstadoAsignatura.NO_CURSADA;
    }

    public void setAsignaturaId(long asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public long getAsignaturaId() {
        return asignaturaId;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setEstado(EstadoAsignatura estado) {
        this.estado = estado;
    }

    public EstadoAsignatura getEstado() {
        return estado;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Integer getNota() {
        return nota;
    }
}
