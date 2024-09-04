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

import ar.edu.utn.frbb.tup.exceptions.AlumnoAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.AlumnoNoEncontradoException;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.presentation.modelDto.AlumnoDto;
import ar.edu.utn.frbb.tup.presentation.validator.AlumnoValidator;
import ar.edu.utn.frbb.tup.service.AlumnoService;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlumnoControllerTest {

    @Mock
    AlumnoService alumnoService;

    @Mock
    AlumnoValidator alumnoValidator;

    @InjectMocks
    AlumnoController alumnoController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testMostrarTodosLosAlumnosSuccess () throws AlumnoNoEncontradoException, FileNotFoundException, IOException{
        List<Alumno> alumnos = new ArrayList<>();

        when(alumnoService.mostrarTodosLosAlumnos()).thenReturn(alumnos);

        ResponseEntity<List<Alumno>> mostrados = alumnoController.getTodosLosAlumnos();

        verify(alumnoService, times(1)).mostrarTodosLosAlumnos();

        assertEquals(200, mostrados.getStatusCodeValue());
    }

    @Test
    public void testMostrarTodosLosAlumnosError () throws AlumnoNoEncontradoException, FileNotFoundException, IOException{

        doThrow(new AlumnoNoEncontradoException("No se encontraron alumnos")).when(alumnoService).mostrarTodosLosAlumnos();

        assertThrows(AlumnoNoEncontradoException.class, () -> alumnoController.getTodosLosAlumnos());

        verify(alumnoService, times(1)).mostrarTodosLosAlumnos();
    }

    @Test
    public void testMostrarAlumnoSuccess() throws AlumnoNoEncontradoException{
        Alumno alumno = getAlumno();

        when(alumnoService.mostrarAlumno(alumno.getId())).thenReturn(alumno);

        ResponseEntity<Alumno> alumnomostrado = alumnoController.mostrarAlumno(alumno.getId());

        verify(alumnoService, times(1)).mostrarAlumno(alumno.getId());

        assertEquals(200, alumnomostrado.getStatusCodeValue());
        assertEquals(alumnomostrado.getBody(), alumno);
    }


    @Test
    public void testMostrarAlumnoError() throws AlumnoNoEncontradoException{

        doThrow(new AlumnoNoEncontradoException("No se encontro el alumno")).when(alumnoService).mostrarAlumno(anyLong());

        assertThrows(AlumnoNoEncontradoException.class, () -> alumnoController.mostrarAlumno(1213));

        verify(alumnoService, times(1)).mostrarAlumno(anyLong());

    }


    @Test
    public void testAlumnoModificarSuccess() throws AlumnoNoEncontradoException {

        AlumnoDto alumnoDto = getAlumnoDto();
        when(alumnoService.modificarAlumno(1213,alumnoDto)).thenReturn(new Alumno());

        ResponseEntity<Alumno> modificado = alumnoController.modificarAlumno(1213,alumnoDto);

        verify(alumnoService, times(1)).modificarAlumno(1213,alumnoDto);
        verify(alumnoValidator, times(1)).validarAlumno(alumnoDto);

        assertEquals(200, modificado.getStatusCodeValue());
    }


    @Test
    public void testAlumoModificarFail() throws AlumnoNoEncontradoException {

        AlumnoDto alumnoDto = getAlumnoDto();

        doThrow(new AlumnoNoEncontradoException("No se encontro el alumno")).when(alumnoService).modificarAlumno(1213,alumnoDto);

        assertThrows(AlumnoNoEncontradoException.class, () -> alumnoController.modificarAlumno(1213, alumnoDto));

        verify(alumnoService, times(1)).modificarAlumno(1213,alumnoDto);
        verify(alumnoValidator, times(1)).validarAlumno(alumnoDto);
    }

    @Test
    public void testBorrarAlumnoSuccess() throws AlumnoNoEncontradoException {

        when(alumnoService.borrarAlumno(12345678)).thenReturn(new Alumno());

        ResponseEntity<Alumno> borrado = alumnoController.borrarAlumno(12345678);

        verify(alumnoService, times(1)).borrarAlumno(12345678);

        assertEquals(200, borrado.getStatusCodeValue());
    }

    @Test
    public void testBorrarAlumnoFail() throws AlumnoNoEncontradoException {

        doThrow(new AlumnoNoEncontradoException("No se encontro el alumno")).when(alumnoService).borrarAlumno(anyLong());

        assertThrows(AlumnoNoEncontradoException.class, () -> alumnoController.borrarAlumno(12345678));

        verify(alumnoService, times(1)).borrarAlumno(anyLong());
    }

    @Test
    public void testDarDeAltaAlumnoSuccess() throws AlumnoAlreadyExistsException {

        AlumnoDto alumnoDto = getAlumnoDto();

        when(alumnoService.crearAlumno(alumnoDto)).thenReturn(new Alumno());

        ResponseEntity<Alumno> alta = alumnoController.darDeAltaAlumno(alumnoDto);

        verify(alumnoService, times(1)).crearAlumno(alumnoDto);
        verify(alumnoValidator, times(1)).validarAlumno(alumnoDto);

        assertEquals(201, alta.getStatusCodeValue());
    }

    @Test
    public void testDarDeAltaAlumnoFail() throws AlumnoAlreadyExistsException {

        AlumnoDto alumnoDto = getAlumnoDto();

        doThrow(new AlumnoAlreadyExistsException("El alumno ya existe")).when(alumnoService).crearAlumno(alumnoDto);

        assertThrows(AlumnoAlreadyExistsException.class, () -> alumnoController.darDeAltaAlumno(alumnoDto));

        verify(alumnoService, times(1)).crearAlumno(alumnoDto);
        verify(alumnoValidator, times(1)).validarAlumno(alumnoDto);
    }


     public Alumno getAlumno() {
        Alumno alumno = new Alumno();
        alumno.setId(1213);
        alumno.setDni(12345678);
        alumno.setNombre("Juan");
        alumno.setApellido("Perez");
        return alumno;
    }

    public AlumnoDto getAlumnoDto() {
        AlumnoDto alumnodto = new AlumnoDto();
        alumnodto.setDni("12345678");
        alumnodto.setNombre("Juan");
        alumnodto.setApellido("Perez");
        return alumnodto;
    }
}


