package ar.edu.utn.frbb.tup.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import ar.edu.utn.frbb.tup.exceptions.CarreraAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.CarreraNoEncontradaException;

import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.AsignaturaDao;
import ar.edu.utn.frbb.tup.persistence.CarreraDao;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;

import ar.edu.utn.frbb.tup.presentation.modelDto.CarreraDto;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CarreraServiceTest {
    
    @Mock
    CarreraDao carreraDao;
    
    @Mock
    ProfesorDao profesorDao; 
    
    @Mock
    AlumnoDao  alumnoDao;

    @Mock
    AsignaturaDao asignaturaDao;

 
    @InjectMocks
    CarreraService CarreraService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testDarDeAltaCarreraSuccess() throws CarreraAlreadyExistsException{
        CarreraDto carreraDto = getCarreraDto();
        Carrera carrera = new Carrera(carreraDto);
        
        when(carreraDao.BuscarCarrera(carrera.getNombre())).thenReturn(null);

        Carrera carreraCreada =  CarreraService.crearCarrera(carreraDto);

        verify(carreraDao, times(1)).BuscarCarrera(carrera.getNombre());
        verify(carreraDao, times(1)).crearCarrera(any(Carrera.class));

        assertNotNull(carreraCreada);
    }


    @Test
    public void testDarDeAltaCarreraExistente() throws CarreraAlreadyExistsException {
        CarreraDto carreraDto = getCarreraDto();
        Carrera carrera = new Carrera(carreraDto);
        
        
        when(carreraDao.BuscarCarrera(carrera.getNombre())).thenReturn(carrera);

        assertThrows(CarreraAlreadyExistsException.class, () -> CarreraService.crearCarrera(carreraDto));

        verify(carreraDao, times(1)).BuscarCarrera(carrera.getNombre());
        verify(carreraDao, times(0)).crearCarrera(any(Carrera.class));
    }


    @Test
    public void testBorrarCarreraSuccess() throws CarreraNoEncontradaException {
        CarreraDto carreraDto = getCarreraDto();
        Carrera carrera = new Carrera(carreraDto);

        CarreraDao carreraDaoMock = mock(CarreraDao.class);
        when(carreraDaoMock.BuscarCarrera(carrera.getNombre())).thenReturn(carrera);
    
        CarreraService carreraService = new CarreraService();
        ReflectionTestUtils.setField(carreraService, "carreraDao", carreraDaoMock);
    
       
        Carrera result = carreraService.borrarCarrera(carrera.getNombre());
    
        assertNotNull(result);
        assertEquals(carrera.getNombre(), result.getNombre());
        verify(carreraDaoMock, times(1)).borrarCarrera(carrera.getNombre());
    }

    @Test
    public void testBorrarCarreraNoEncontrada() {
        CarreraDto carreraDto = getCarreraDto();
        Carrera carrera = new Carrera(carreraDto);
    
        CarreraDao carreraDaoMock = mock(CarreraDao.class);
        when(carreraDaoMock.BuscarCarrera(carrera.getNombre())).thenReturn(null);
    
        CarreraService carreraService = new CarreraService();
        ReflectionTestUtils.setField(carreraService, "carreraDao", carreraDaoMock);
    
        
        assertThrows(CarreraNoEncontradaException.class, () -> {
           carreraService.borrarCarrera(carrera.getNombre());
        });
    
        Mockito.verify(carreraDaoMock, Mockito.never()).borrarCarrera(carrera.getNombre());
    }


    @Test
    public void testMostrarCarreraSuccess() throws CarreraNoEncontradaException {
        CarreraDto carreraDto = getCarreraDto();
        Carrera carrera = new Carrera(carreraDto);

        when(carreraDao.mostrarCarrera(carrera.getNombre())).thenReturn(carrera);

        assertNotNull(CarreraService.mostrarCarrera(carrera.getNombre()));

        verify(carreraDao, times(1)).mostrarCarrera(carrera.getNombre());
    }


    @Test
    public void testMostrarCarreraNoEncontrada() throws CarreraNoEncontradaException {
        CarreraDto carreraDto = getCarreraDto();
        Carrera carrera = new Carrera(carreraDto);

        when(carreraDao.mostrarCarrera(carrera.getNombre())).thenReturn(null);

        assertThrows(CarreraNoEncontradaException.class, () ->CarreraService.mostrarCarrera(carrera.getNombre()));

        verify(carreraDao, times(1)).mostrarCarrera(carrera.getNombre());
    }

    @Test
    public void testMostrarTodasLasCarrerasSuccess() throws CarreraNoEncontradaException, FileNotFoundException, IOException {
        List<Carrera> carreras = List.of(new Carrera (getCarreraDto()));

        when(carreraDao.mostrarTodasLasCarreras()).thenReturn(carreras);

        Carrera mostrado =  CarreraService.mostrarTodasLasCarreras().get(0);

        assertNotNull(mostrado);

        verify(carreraDao, times(2)).mostrarTodasLasCarreras();
    }


    @Test
    public void testMostrarTodasLasCarrerasNoEncontrada() throws CarreraNoEncontradaException { 
        List<Carrera> carrerasVacia = List.of();

        when(carreraDao.mostrarTodasLasCarreras()).thenReturn(carrerasVacia);

        assertThrows(CarreraNoEncontradaException.class, () ->CarreraService.mostrarTodasLasCarreras());

        verify(carreraDao, times(1)).mostrarTodasLasCarreras();
    }

    public CarreraDto getCarreraDto() {
        CarreraDto carreradto = new CarreraDto();
        carreradto.setNombre("Arquitectura");
        carreradto.setCantanios("5");
        return carreradto;
    }


}