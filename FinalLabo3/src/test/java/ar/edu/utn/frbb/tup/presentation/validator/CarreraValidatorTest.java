package ar.edu.utn.frbb.tup.presentation.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import ar.edu.utn.frbb.tup.presentation.modelDto.CarreraDto;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CarreraValidatorTest {

    CarreraValidator carreraValidator;

    @BeforeEach
    public void setUp() {
        carreraValidator = new CarreraValidator();
    }


    @Test
    public void testCarreraValidatorSuccess(){
        CarreraDto carreradto = new CarreraDto();
        carreradto.setNombre("Arquitectura");
        carreradto.setCantanios("5");

        assertDoesNotThrow(() ->carreraValidator.validarCarrera(carreradto));
    }


    @Test
    public void testCarreraValidatorSinNombre(){
        CarreraDto carreradto = new CarreraDto();
        carreradto.setNombre("");
        carreradto.setCantanios("5");

         assertThrows(IllegalArgumentException.class,(() ->carreraValidator.validarCarrera(carreradto)));
    }

    @Test
    public void testCarreraValidatorSinAnio(){
        CarreraDto carreradto = new CarreraDto();
        carreradto.setNombre("Arquitectura");
        carreradto.setCantanios("");

         assertThrows(IllegalArgumentException.class,(() ->carreraValidator.validarCarrera(carreradto)));
    }


    @Test
    public void testCarreraValidatorAnioNegativo(){
        CarreraDto carreradto = new CarreraDto();
        carreradto.setNombre("Arquitectura");
        carreradto.setCantanios("-5");

         assertThrows(IllegalArgumentException.class,(() ->carreraValidator.validarCarrera(carreradto)));
    }


    @Test
    public void testCarreraValidatorAnioString(){
        CarreraDto carreradto = new CarreraDto();
        carreradto.setNombre("Arquitectura");
        carreradto.setCantanios("Cinco");

         assertThrows(IllegalArgumentException.class,(() ->carreraValidator.validarCarrera(carreradto)));
    }



}
