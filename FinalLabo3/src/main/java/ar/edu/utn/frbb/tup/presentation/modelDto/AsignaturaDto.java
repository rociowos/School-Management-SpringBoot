package ar.edu.utn.frbb.tup.presentation.modelDto;



public class AsignaturaDto {
    private String idmateria; 
    private String estadoAsignatura;
    private String nota;
    private String alumnoid;

    


    public String getIdmateria() {
        return idmateria;
    }
    public void setIdmateria(String idmateria) {
        this.idmateria = idmateria;
    }

    public String getEstadoAsignatura(){
        return estadoAsignatura;
    }

    public void setEstadoAsignatura(String estadoAsignatura){
        this.estadoAsignatura = estadoAsignatura;
    }
   
    public String getNota(){
        return nota;
    }

    public void setNota(String nota){
       this.nota = nota;
    }

    public String getAlumnoid(){
        return alumnoid;

    }
    
    public void setAlumnoid(String alumnoid){
        this.alumnoid = alumnoid;
    }

}