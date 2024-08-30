package ar.edu.utn.frbb.tup.presentation.validator;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.presentation.modelDto.AsignaturaDto;


@Component
public class AsignaturaValidator {

    public void validarAsignatura(AsignaturaDto asignaturadto) {
    
        if (asignaturadto.getEstadoAsignatura().isEmpty() || asignaturadto.getEstadoAsignatura() == null) {
            throw new IllegalArgumentException("El estado del alumno es obligatorio");
        }

        if (Integer.parseInt(asignaturadto.getNota()) < 0) { 
            throw new IllegalArgumentException("La nota del alumno no puede ser negativa");
        }

        if (Integer.parseInt(asignaturadto.getNota()) > 10) { 
            throw new IllegalArgumentException("La nota del alumno no puede ser mayor a 10");
        }
        
    }
}
