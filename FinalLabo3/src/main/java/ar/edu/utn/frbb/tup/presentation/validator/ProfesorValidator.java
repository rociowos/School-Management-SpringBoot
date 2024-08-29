package ar.edu.utn.frbb.tup.presentation.validator;
import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.presentation.modelDto.ProfesorDto;
@Component
public class ProfesorValidator {
    public void validarProfesor(ProfesorDto profesordto) {
     

        if (profesordto.getNombre().isEmpty() || profesordto.getNombre() == null) {
            throw new IllegalArgumentException("El nombre del profesor es obligatorio");
        }
    
        if (profesordto.getApellido().isEmpty() || profesordto.getApellido() == null) {
            throw new IllegalArgumentException("El apellido del profesor es obligatorio");
        }

        if (profesordto.getTitulo().isEmpty() || profesordto.getTitulo() == null) {
            throw new IllegalArgumentException("El titulo del profesor es obligatorio");    
        }

        if (profesordto.getnombreMateria().isEmpty() || profesordto.getnombreMateria() == null) {
            throw new IllegalArgumentException("El nombre de la materia es obligatoria");     
        }

        

    }

    

}
    

