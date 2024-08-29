package ar.edu.utn.frbb.tup.model;
import ar.edu.utn.frbb.tup.presentation.modelDto.CarreraDto;

public class Carrera {
    private String nombre;
    private int cantanios;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantanios() {
        return cantanios;
    }

    public void setCantanios(int cantanios) {
        this.cantanios = cantanios;
    }

    public Carrera(){
        super();
    }

    public Carrera (CarreraDto carreraDto){
        this.nombre = carreraDto.getNombre();
        this.cantanios = Integer.parseInt(carreraDto.getCantanios());
        
    }
}
