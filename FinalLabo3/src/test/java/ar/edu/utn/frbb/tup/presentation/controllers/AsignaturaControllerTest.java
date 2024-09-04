package ar.edu.utn.frbb.tup.presentation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import ar.edu.utn.frbb.tup.exceptions.AlumnoNoEncontradoException;
import ar.edu.utn.frbb.tup.exceptions.AsignaturaAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.AsignaturaNoEncontradaException;
import ar.edu.utn.frbb.tup.exceptions.MateriaNoEncontradaException;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;
import ar.edu.utn.frbb.tup.presentation.modelDto.AsignaturaDto;
import ar.edu.utn.frbb.tup.presentation.validator.AsignaturaValidator;
import ar.edu.utn.frbb.tup.service.AsignaturaService;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AsignaturaControllerTest {
    

    @Mock
    AsignaturaService asignaturaService;

    @Mock
    AsignaturaValidator asignaturaValidator;

    @InjectMocks
    AsignaturaController asignaturaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testCrearAsignaturaSuccess() throws AsignaturaNoEncontradaException, AsignaturaAlreadyExistsException, MateriaNoEncontradaException, AlumnoNoEncontradoException{

        AsignaturaDto asignaturaDto = getAsignaturaDto();

        when(asignaturaService.crearAsignatura(asignaturaDto)).thenReturn(new Asignatura());

        ResponseEntity<Asignatura> response = asignaturaController.darDeAltaAsignatura(asignaturaDto);

        verify(asignaturaService, times(1)).crearAsignatura(asignaturaDto);

        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void testCrearAsignaturaFail() throws AsignaturaNoEncontradaException, AsignaturaAlreadyExistsException, MateriaNoEncontradaException, AlumnoNoEncontradoException {

        AsignaturaDto asignaturaDto = getAsignaturaDto();

        doThrow(AsignaturaAlreadyExistsException.class).when(asignaturaService).crearAsignatura(asignaturaDto);

        assertThrows(AsignaturaAlreadyExistsException.class, () -> asignaturaController.darDeAltaAsignatura(asignaturaDto));

        verify(asignaturaService, times(1)).crearAsignatura(asignaturaDto);
    }

    @Test
    public void testBorrarAsignaturaSuccess() throws AsignaturaNoEncontradaException {

        when(asignaturaService.borrarAsignatura(anyLong())).thenReturn(new Asignatura());

        ResponseEntity<Asignatura> response = asignaturaController.borrarAsignatura(anyLong());

        verify(asignaturaService, times(1)).borrarAsignatura(anyLong());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testBorrarAsignaturaFail() throws AsignaturaNoEncontradaException {

        doThrow(AsignaturaNoEncontradaException.class).when(asignaturaService).borrarAsignatura(anyLong());

        assertThrows(AsignaturaNoEncontradaException.class, () -> asignaturaController.borrarAsignatura(anyLong()));

        verify(asignaturaService, times(1)).borrarAsignatura(anyLong());

    }

    @Test
    public void testMostrarAsignaturaSuccess() throws AsignaturaNoEncontradaException{
        Asignatura asignatura = getAsignatura();

        when(asignaturaService.mostrarAsignatura(asignatura.getAsignaturaId(), asignatura.getAlumnoid())).thenReturn(asignatura);

        ResponseEntity<Asignatura> asignaturamostrada = asignaturaController.mostrarAsignatura(asignatura.getAsignaturaId(), asignatura.getAlumnoid());

        verify(asignaturaService, times(1)).mostrarAsignatura(asignatura.getAsignaturaId(), asignatura.getAlumnoid());

        assertEquals(200, asignaturamostrada.getStatusCodeValue());
        assertEquals(asignaturamostrada.getBody(), asignatura);
    }

    @Test
    public void testMostrarAsignaturaFail() throws AsignaturaNoEncontradaException{

        doThrow(new AsignaturaNoEncontradaException("No se encontro la asignatura")).when(asignaturaService).mostrarAsignatura(1111,1234);

        assertThrows(AsignaturaNoEncontradaException.class, () -> asignaturaController.mostrarAsignatura(1111,1234));

        verify(asignaturaService, times(1)).mostrarAsignatura(1111,1234);

    }



    public AsignaturaDto getAsignaturaDto() {
        AsignaturaDto asignaturadto = new AsignaturaDto();
        asignaturadto.setIdmateria("1111");
        asignaturadto.setEstadoAsignatura("APROBADA");
        asignaturadto.setNota("7");
        asignaturadto.setAlumnoid("1234");
       
        return asignaturadto;
    }

    public Asignatura getAsignatura() {
        Asignatura asignatura = new Asignatura();
        asignatura.setAsignaturaId(1222);
        asignatura.setIdmateria(1111);
        asignatura.setEstadoAsignatura(EstadoAsignatura.APROBADA);
        asignatura.setNota(7);
        asignatura.setAlumnoid(1234);
        return asignatura;
    }
}