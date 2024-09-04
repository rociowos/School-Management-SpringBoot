package ar.edu.utn.frbb.tup.presentation.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import ar.edu.utn.frbb.tup.presentation.modelDto.ProfesorDto;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfesorValidatorTest {

    ProfesorValidator profesorValidator;

    @BeforeEach
    public void setUp() {
        profesorValidator = new ProfesorValidator();
    }

    @Test
    public void testProfesorValidatorSuccess(){
        ProfesorDto profesordto = new ProfesorDto();
        profesordto.setNombre("Juan");
        profesordto.setApellido("Perez");
        profesordto.setTitulo("Matematico");
        profesordto.setnombreMateria("Matematica");
        assertDoesNotThrow(() -> profesorValidator.validarProfesor(profesordto));

    }

    @Test
    public void testProfesorValidatorSinNombre(){
        ProfesorDto profesordto = new ProfesorDto();
        profesordto.setNombre("");
        profesordto.setApellido("Perez");
        profesordto.setTitulo("Matematico");
        profesordto.setnombreMateria("Matematica");
        assertThrows(IllegalArgumentException.class,(() -> profesorValidator.validarProfesor(profesordto)));

    }

    @Test
    public void testProfesorValidatorSinApellido(){
        ProfesorDto profesordto = new ProfesorDto();
        profesordto.setNombre("Juan");
        profesordto.setApellido("");
        profesordto.setTitulo("Matematico");
        profesordto.setnombreMateria("Matematica");
        assertThrows(IllegalArgumentException.class,(() -> profesorValidator.validarProfesor(profesordto)));

    }

    @Test
    public void testProfesorValidatorSinTitulo(){
        ProfesorDto profesordto = new ProfesorDto();
        profesordto.setNombre("Juan");
        profesordto.setApellido("Perez");
        profesordto.setTitulo("");
        profesordto.setnombreMateria("Matematica");
        assertThrows(IllegalArgumentException.class,(() -> profesorValidator.validarProfesor(profesordto)));

    }

    @Test
    public void testProfesorValidatorSinMateria(){
        ProfesorDto profesordto = new ProfesorDto();
        profesordto.setNombre("Juan");
        profesordto.setApellido("Perez");
        profesordto.setTitulo("Matematico");
        profesordto.setnombreMateria("");
        assertThrows(IllegalArgumentException.class,(() -> profesorValidator.validarProfesor(profesordto)));

    }




}
