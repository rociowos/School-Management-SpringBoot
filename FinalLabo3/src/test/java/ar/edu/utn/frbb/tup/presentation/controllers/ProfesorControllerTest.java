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

import ar.edu.utn.frbb.tup.exceptions.ProfesorAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.ProfesorNoEncontradoException;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.presentation.modelDto.ProfesorDto;
import ar.edu.utn.frbb.tup.presentation.validator.ProfesorValidator;
import ar.edu.utn.frbb.tup.service.ProfesorService;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfesorControllerTest {

    @Mock
    ProfesorService profesorService;

    @Mock
    ProfesorValidator profesorValidator;

    @InjectMocks
    ProfesorController profesorController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMostrarTodosLosProfesoresSuccess () throws ProfesorNoEncontradoException, FileNotFoundException, IOException{
        List<Profesor> profesores = new ArrayList<>();

        when(profesorService.mostrarTodosLosProfesores()).thenReturn(profesores);

        ResponseEntity<List<Profesor>> mostrados = profesorController.getTodosLosProfesores();

        verify(profesorService, times(1)).mostrarTodosLosProfesores();

        assertEquals(200, mostrados.getStatusCodeValue());
    }

     @Test
    public void testMostrarTodosLosProfesoresFail () throws ProfesorNoEncontradoException, FileNotFoundException, IOException{

        doThrow(new ProfesorNoEncontradoException("No se encontraron profesores")).when(profesorService).mostrarTodosLosProfesores();

        assertThrows(ProfesorNoEncontradoException.class, () -> profesorController.getTodosLosProfesores());

        verify(profesorService, times(1)).mostrarTodosLosProfesores();
    }

    @Test
    public void testMostrarProfesorSuccess() throws ProfesorNoEncontradoException{
        Profesor profesor = getProfesor();

        when(profesorService.mostrarProfesor(profesor.getId())).thenReturn(profesor);

        ResponseEntity<Profesor> profesormostrado = profesorController.mostrarProfesor(profesor.getId());

        verify(profesorService, times(1)).mostrarProfesor(profesor.getId());

        assertEquals(200, profesormostrado.getStatusCodeValue());
        assertEquals(profesormostrado.getBody(), profesor);
    }

    @Test
    public void testMostrarProfesorFail() throws ProfesorNoEncontradoException{

        doThrow(new ProfesorNoEncontradoException("No se encontro el profesor con ese ID")).when(profesorService).mostrarProfesor(1234);

        assertThrows(ProfesorNoEncontradoException.class, () -> profesorController.mostrarProfesor(1234));

        verify(profesorService, times(1)).mostrarProfesor(1234);

    }

    @Test
    public void testModificarProfesorSuccess() throws ProfesorNoEncontradoException {

        ProfesorDto profesordto = getProfesorDto();
        when(profesorService.modificarProfesor(1234,profesordto)).thenReturn(new Profesor());

        ResponseEntity<Profesor> modificado = profesorController.modificarProfesor(1234,profesordto);

        verify(profesorService, times(1)).modificarProfesor(1234,profesordto);
        verify(profesorValidator, times(1)).validarProfesor(profesordto);

        assertEquals(200, modificado.getStatusCodeValue());
    }

    @Test
    public void testModificarProfesorFail() throws ProfesorNoEncontradoException {

        ProfesorDto profesordto = getProfesorDto();

        doThrow(new ProfesorNoEncontradoException("No se encontro el profesor")).when(profesorService).modificarProfesor(1234,profesordto);

        assertThrows(ProfesorNoEncontradoException.class, () -> profesorController.modificarProfesor(1234,profesordto));

        verify(profesorService, times(1)).modificarProfesor(1234,profesordto);
        verify(profesorValidator, times(1)).validarProfesor(profesordto);

    }

    @Test
    public void testBorrarProfesorSuccess() throws ProfesorNoEncontradoException {

        when(profesorService.borrarProfesor(1234)).thenReturn(new Profesor());

        ResponseEntity<Profesor> borrado = profesorController.borrarProfesor(1234);

        verify(profesorService, times(1)).borrarProfesor(1234);

        assertEquals(200, borrado.getStatusCodeValue());
    }

    @Test
    public void testBorrarProfesorFail() throws ProfesorNoEncontradoException {

        doThrow(new ProfesorNoEncontradoException("No se encontro el profesor")).when(profesorService).borrarProfesor(1234);

        assertThrows(ProfesorNoEncontradoException.class, () -> profesorController.borrarProfesor(1234));

        verify(profesorService, times(1)).borrarProfesor(1234);
    }
    

    @Test
    public void testDarDeAltaProfesorSuccess() throws ProfesorAlreadyExistsException {

        ProfesorDto profesordto = getProfesorDto();

        when(profesorService.crearProfesor(profesordto)).thenReturn(new Profesor());

        ResponseEntity<Profesor> alta = profesorController.darDeAltaProfesor(profesordto);

        verify(profesorService, times(1)).crearProfesor(profesordto);
        verify(profesorValidator, times(1)).validarProfesor(profesordto);

        assertEquals(201, alta.getStatusCodeValue());
    }

    @Test
    public void testDarDeAltaProfesorFail() throws ProfesorAlreadyExistsException {

        ProfesorDto profesordto = getProfesorDto();

        doThrow(new ProfesorAlreadyExistsException("El profesor ya existe")).when(profesorService).crearProfesor(profesordto);

        assertThrows(ProfesorAlreadyExistsException.class, () -> profesorController.darDeAltaProfesor(profesordto));

        verify(profesorService, times(1)).crearProfesor(profesordto);
        verify(profesorValidator, times(1)).validarProfesor(profesordto);
    }



    public Profesor getProfesor() {
        Profesor profesor = new Profesor();
        profesor.setId(1234);
        profesor.setNombre("Marta");
        profesor.setApellido("Martinez");
        profesor.setTitulo("Matematico");
        profesor.setNombreMateria("Matematica");
        return profesor;
    }

    public ProfesorDto getProfesorDto() {
        ProfesorDto profesordto = new ProfesorDto();
        profesordto.setNombre("Marta");
        profesordto.setApellido("Martinez");
        profesordto.setTitulo("Matematico");
        profesordto.setnombreMateria("Matematica");
        return profesordto;
    }

}
