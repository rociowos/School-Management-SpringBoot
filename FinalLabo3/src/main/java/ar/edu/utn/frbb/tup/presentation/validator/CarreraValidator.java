package ar.edu.utn.frbb.tup.presentation.validator;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.presentation.modelDto.CarreraDto;


@Component
public class CarreraValidator {

    public void validarCarrera(CarreraDto carreradto) {
        validarCantanios(carreradto.getCantanios());


        if (carreradto.getNombre().isEmpty() || carreradto.getNombre() == null) {
            throw new IllegalArgumentException("El nombre de la carrera es obligatoria");
        }

        if (Integer.parseInt(carreradto.getCantanios()) == 0) {
            throw new IllegalArgumentException("La cantidad de años es obligatoria");
        }

        if (Integer.parseInt(carreradto.getCantanios()) <= 0) { 
            throw new IllegalArgumentException("La cantidad de años no puede ser negativa");
        }

        

    }  
    
    private void validarCantanios(String cantanios) {
        try {
            Integer.parseInt(cantanios);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La cantidad de años no tiene el formato correcto");
        }
    }
}
