package ar.edu.utn.frbb.tup.presentation.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import ar.edu.utn.frbb.tup.presentation.modelDto.AlumnoDto;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlumnoValidatorTest {

    AlumnoValidator alumnoValidator;

    @BeforeEach
    public void setUp() {
        alumnoValidator = new AlumnoValidator();
    }

    @Test
    public void testAlumnoValidatorSuccess(){
        AlumnoDto alumnodto = new AlumnoDto();
        alumnodto.setDni("12345678");
        alumnodto.setNombre("Jorge");
        alumnodto.setApellido("Lopez");

        assertDoesNotThrow(() -> alumnoValidator.validarAlumno(alumnodto));
    }

    @Test
    public void testAlumnoValidatorErrorDni(){
        AlumnoDto alumnodto = new AlumnoDto();
        alumnodto.setDni("J");
        alumnodto.setNombre("Jorge");
        alumnodto.setApellido("Lopez");
    
        assertThrows(IllegalArgumentException.class, () -> alumnoValidator.validarAlumno(alumnodto));
    }

    @Test
    public void testAlumnoValidatorSinDni(){
        AlumnoDto alumnodto = new AlumnoDto();
        //alumnoDto.setDni("12345678");
        alumnodto.setNombre("Jorge");
        alumnodto.setApellido("Lopez");

        assertThrows(IllegalArgumentException.class, () -> alumnoValidator.validarAlumno(alumnodto));
    }

    @Test
    public void testAlumnoValidatorDniCeroOMenos(){
        AlumnoDto alumnodto = new AlumnoDto();
        alumnodto.setDni("-12345678");
        alumnodto.setNombre("Jorge");
        alumnodto.setApellido("Lopez");

    
        assertThrows(IllegalArgumentException.class, () -> alumnoValidator.validarAlumno(alumnodto));
    }

    @Test
    public void testAlumnoValidatorSinNombre(){
        AlumnoDto alumnodto = new AlumnoDto();
        alumnodto.setDni("12345678");
        alumnodto.setNombre("");
        alumnodto.setApellido("Lopez");

        assertThrows(IllegalArgumentException.class, () -> alumnoValidator.validarAlumno(alumnodto));
    }

    @Test
    public void testAlumnoValidatorSinApellido(){
        AlumnoDto alumnodto = new AlumnoDto();
        alumnodto.setDni("12345678");
        alumnodto.setNombre("Jorge");
        alumnodto.setApellido("");
        assertThrows(IllegalArgumentException.class, () -> alumnoValidator.validarAlumno(alumnodto));
    
    }

}