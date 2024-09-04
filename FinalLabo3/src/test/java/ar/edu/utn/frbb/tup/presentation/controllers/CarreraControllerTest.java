package ar.edu.utn.frbb.tup.presentation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import ar.edu.utn.frbb.tup.exceptions.CarreraAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.CarreraNoEncontradaException;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.presentation.modelDto.CarreraDto;
import ar.edu.utn.frbb.tup.presentation.validator.CarreraValidator;
import ar.edu.utn.frbb.tup.service.CarreraService;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CarreraControllerTest {

    @Mock
    CarreraService carreraService;

    @Mock
    CarreraValidator carreraValidator;

    @InjectMocks
    CarreraController carreraController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testMostrarTodasLasCarrerasSuccess () throws CarreraNoEncontradaException, FileNotFoundException, IOException{
        List<Carrera> carreras = new ArrayList<>();

        when(carreraService.mostrarTodasLasCarreras()).thenReturn(carreras);

        ResponseEntity<List<Carrera>> mostrados = carreraController.getTodasLasCarreras();

        verify(carreraService, times(1)).mostrarTodasLasCarreras();

        assertEquals(200, mostrados.getStatusCodeValue());
    }

    @Test
    public void testMostrarTodasLasCarrerasError () throws CarreraNoEncontradaException, FileNotFoundException, IOException{

        doThrow(new CarreraNoEncontradaException("No se encontraron carreras")).when(carreraService).mostrarTodasLasCarreras();

        assertThrows(CarreraNoEncontradaException.class, () -> carreraController.getTodasLasCarreras());

        verify(carreraService, times(1)).mostrarTodasLasCarreras();
    }

    @Test
    public void testMostrarCarreraSuccess() throws CarreraNoEncontradaException{
        Carrera carrera = getCarrera();

        when(carreraService.mostrarCarrera(carrera.getNombre())).thenReturn(carrera);

        ResponseEntity<Carrera> carreramostrada = carreraController.mostrarCarrera(carrera.getNombre());

        verify(carreraService, times(1)).mostrarCarrera(carrera.getNombre());

        assertEquals(200, carreramostrada.getStatusCodeValue());
        assertEquals(carreramostrada.getBody(), carrera);
    }

    @Test
    public void testMostrarCarreraFail() throws CarreraNoEncontradaException{

        doThrow(new CarreraNoEncontradaException("No se encontro la carrera")).when(carreraService).mostrarCarrera("Arquitectura");

        assertThrows(CarreraNoEncontradaException.class, () -> carreraController.mostrarCarrera("Arquitectura"));

        verify(carreraService, times(1)).mostrarCarrera("Arquitectura");

    }

    @Test
    public void testBorrarCarreaSuccess() throws CarreraNoEncontradaException {

        when(carreraService.borrarCarrera("Arquitectura")).thenReturn(new Carrera());

        ResponseEntity<Carrera> borrado = carreraController.borrarCarrera("Arquitectura");

        verify(carreraService, times(1)).borrarCarrera("Arquitectura");

        assertEquals(200, borrado.getStatusCodeValue());
    }

    @Test
    public void testBorrarCarreraFail() throws CarreraNoEncontradaException {

        doThrow(new CarreraNoEncontradaException("No se encontro la carrera")).when(carreraService).borrarCarrera("Arquitectura");

        assertThrows(CarreraNoEncontradaException.class, () -> carreraController.borrarCarrera("Arquitectura"));

        verify(carreraService, times(1)).borrarCarrera("Arquitectura");
    }


    @Test
    public void testDarDeAltaCarreraSuccess() throws CarreraAlreadyExistsException {

        CarreraDto carreraDto = getCarreraDto();

        when(carreraService.crearCarrera(carreraDto)).thenReturn(new Carrera());

        ResponseEntity<Carrera> alta = carreraController.darDeAltaCarrera(carreraDto);

        verify(carreraService, times(1)).crearCarrera(carreraDto);
        verify(carreraValidator, times(1)).validarCarrera(carreraDto);

        assertEquals(201, alta.getStatusCodeValue());
    }

    @Test
    public void testDarDeAltaCarreraFail() throws CarreraAlreadyExistsException{

        CarreraDto carreraDto = getCarreraDto();

        doThrow(new CarreraAlreadyExistsException("La carrera ya existe")).when(carreraService).crearCarrera(carreraDto);

        assertThrows(CarreraAlreadyExistsException.class, () -> carreraController.darDeAltaCarrera(carreraDto));

        verify(carreraService, times(1)).crearCarrera(carreraDto);
        verify(carreraValidator, times(1)).validarCarrera(carreraDto);
    }



    public Carrera getCarrera() {
        Carrera carrera= new Carrera();
        carrera.setNombre("Arquitectura");
        carrera.setCantanios(5);
        return carrera;
    }

    public CarreraDto getCarreraDto() {
        CarreraDto carreradto = new CarreraDto();
        carreradto.setNombre("Arquitectura");
        carreradto.setCantanios("5");
        return carreradto;
    }
}
