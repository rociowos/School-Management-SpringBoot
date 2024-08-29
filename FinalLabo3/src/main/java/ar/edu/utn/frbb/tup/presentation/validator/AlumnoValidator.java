package ar.edu.utn.frbb.tup.presentation.validator;
import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.presentation.modelDto.AlumnoDto;

@Component
public class AlumnoValidator {
    public void validarAlumno(AlumnoDto alumnodto) {

     validarDNI(alumnodto.getDni());

        if (Long.parseLong(alumnodto.getDni()) == 0) {
            throw new IllegalArgumentException("El dni del alumno es obligatorio");
        }

        if (Integer.parseInt(alumnodto.getDni()) <= 0) { 
            throw new IllegalArgumentException("El dni del alumno no puede ser negativo");
        }
    
        if (Long.parseLong(alumnodto.getDni().toString()) < 10000000 || Long.parseLong(alumnodto.getDni().toString()) > 99999999) {
            throw new IllegalArgumentException("El dni del alumno debe ser de 8 digitos");
        }

        if (alumnodto.getNombre().isEmpty() || alumnodto.getNombre() == null) {
            throw new IllegalArgumentException("El nombre del alumno es obligatorio");
        }
    
        if (alumnodto.getApellido().isEmpty() || alumnodto.getApellido() == null) {
            throw new IllegalArgumentException("El apellido del alumno es obligatorio");
        }


    }


    private void validarDNI(String dni) {
        try {
            Long.parseLong(dni);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El dni no tiene el formato correcto");
        }
    }

}
        

    

