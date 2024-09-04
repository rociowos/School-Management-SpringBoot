package ar.edu.utn.frbb.tup.presentation.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import ar.edu.utn.frbb.tup.presentation.modelDto.AsignaturaDto;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AsignaturaValidatorTest {

    AsignaturaValidator asignaturaValidator;

    @BeforeEach
    public void setUp() {
        asignaturaValidator = new AsignaturaValidator();
    }

    @Test
    public void testAsignaturaValidatorSuccess(){
        AsignaturaDto asignaturadto = new AsignaturaDto();
        asignaturadto.setIdmateria("1111");
        asignaturadto.setEstadoAsignatura("APROBADA");
        asignaturadto.setNota("7");
        asignaturadto.setAlumnoid("1234");

        assertDoesNotThrow(() -> asignaturaValidator.validarAsignatura(asignaturadto));
    }

    @Test
    public void testAsignaturaValidatorSinEstadoAsignatura(){
        AsignaturaDto asignaturadto = new AsignaturaDto();
        asignaturadto.setIdmateria("1111");
        asignaturadto.setEstadoAsignatura("");
        asignaturadto.setNota("7");
        asignaturadto.setAlumnoid("1234");

        assertThrows(IllegalArgumentException.class,(() -> asignaturaValidator.validarAsignatura(asignaturadto)));
    }

    @Test
    public void testAsignaturaValidatorNotaNegativa(){
        AsignaturaDto asignaturadto = new AsignaturaDto();
        asignaturadto.setIdmateria("1111");
        asignaturadto.setEstadoAsignatura("DESAPROBADA");
        asignaturadto.setNota("-5");
        asignaturadto.setAlumnoid("1234");

        assertThrows(IllegalArgumentException.class,(() -> asignaturaValidator.validarAsignatura(asignaturadto)));
    }

    @Test
    public void testAsignaturaValidatorNotaMayorADiez(){
        AsignaturaDto asignaturadto = new AsignaturaDto();
        asignaturadto.setIdmateria("1111");
        asignaturadto.setEstadoAsignatura("APROBADA");
        asignaturadto.setNota("12");
        asignaturadto.setAlumnoid("1234");

        assertThrows(IllegalArgumentException.class,(() -> asignaturaValidator.validarAsignatura(asignaturadto)));
    }







}
