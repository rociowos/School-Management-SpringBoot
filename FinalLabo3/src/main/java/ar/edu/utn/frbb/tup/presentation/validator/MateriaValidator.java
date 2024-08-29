package ar.edu.utn.frbb.tup.presentation.validator;

import org.springframework.stereotype.Component;

import ar.edu.utn.frbb.tup.presentation.modelDto.MateriaDto;

@Component
public class MateriaValidator {
    public void validarMateria(MateriaDto materiadto) {
        validarAnio(materiadto.getAnio());
        validarCuatrimestre(materiadto.getCuatrimestre());

        if (materiadto.getNombre().isEmpty() || materiadto.getNombre() == null) {
            throw new IllegalArgumentException("El nombre de la materia es obligatorio");
        }

        //profesor id

        if (materiadto.getAnio() == null || Integer.parseInt(materiadto.getAnio()) == 0) {
            throw new IllegalArgumentException("El año no puede ser nulo");
            
        }

        if (materiadto.getCuatrimestre() == null || Integer.parseInt(materiadto.getCuatrimestre()) == 0) {
            throw new IllegalArgumentException("El cuatrimestre no puede ser nulo");
            
        }

        //correlatividades

    }

    private void validarAnio(String anio) {
        try {
            Long.parseLong(anio);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El año no tiene el formato correcto");
        }
    }

    private void validarCuatrimestre(String cuatrimestre) {
        try {
            Long.parseLong(cuatrimestre);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El cuatrimestre no tiene el formato correcto");
        }
    }
}
