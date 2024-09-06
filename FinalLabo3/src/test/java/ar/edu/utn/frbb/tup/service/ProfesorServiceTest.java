package ar.edu.utn.frbb.tup.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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


import ar.edu.utn.frbb.tup.exceptions.ProfesorAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.ProfesorNoEncontradoException;

import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.AsignaturaDao;
import ar.edu.utn.frbb.tup.persistence.CarreraDao;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;

import ar.edu.utn.frbb.tup.presentation.modelDto.ProfesorDto;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfesorServiceTest {
    @Mock
    ProfesorDao profesorDao; 
    
    @Mock
    AlumnoDao  alumnoDao;

    @Mock
    AsignaturaDao asignaturaDao;

    @Mock
    CarreraDao carreraDao;

    @Mock
    MateriaDao materiaDao;

   

    @InjectMocks
    ProfesorService ProfesorService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testDarDeAltaProfesorSuccess() throws ProfesorAlreadyExistsException {
        ProfesorDto profesorDto = getProfesorDto();
        Profesor profesor = new Profesor(profesorDto);
    
    
        when(profesorDao.BuscarId(anyLong())).thenReturn(null);

        Profesor profesorCreado = ProfesorService.crearProfesor(profesorDto);

        verify(profesorDao, times(1)).BuscarId(anyLong()); 
        verify(profesorDao, times(1)).crearProfesor(any(Profesor.class));

        assertNotNull(profesorCreado);
    }

    @Test
    public void testDarDeAltaProfesorExistente() throws ProfesorAlreadyExistsException {
        ProfesorDto profesorDto = getProfesorDto();
        Profesor profesor = new Profesor(profesorDto);
        
        when(profesorDao.BuscarId(anyLong())).thenReturn(profesor);

        assertThrows(ProfesorAlreadyExistsException.class, () -> ProfesorService.crearProfesor(profesorDto));

        verify(profesorDao, times(1)).BuscarId(anyLong());
        verify(profesorDao, times(0)).crearProfesor(any(Profesor.class));
    }

    @Test
    public void testBorrarProfesorSuccess() throws ProfesorNoEncontradoException {
        ProfesorDto profesorDto = getProfesorDto();
        Profesor profesor = new Profesor(profesorDto);

        ProfesorDao profesorDaoMock = mock(ProfesorDao.class);
        when(profesorDaoMock.BuscarId(profesor.getId())).thenReturn(profesor);
    
        ProfesorService profesorService = new ProfesorService();
        ReflectionTestUtils.setField(profesorService, "profesorDao", profesorDaoMock);
    
       
        Profesor result = profesorService.borrarProfesor(profesor.getId());
    
        assertNotNull(result);
        assertEquals(profesor.getId(), result.getId());
        verify(profesorDaoMock, times(1)).borrarProfesor(profesor.getId());
    }


    @Test
    public void testBorrarProfesorNoEncontrado() {
        ProfesorDto profesorDto = getProfesorDto();
        Profesor profesor = new Profesor(profesorDto);
    
        ProfesorDao profesorDaoMock = mock(ProfesorDao.class);
        Mockito.when(profesorDaoMock.BuscarId(profesor.getId())).thenReturn(null);
    
        ProfesorService profesorService = new ProfesorService();
        ReflectionTestUtils.setField(profesorService, "profesorDao", profesorDaoMock);
    
        
        assertThrows(ProfesorNoEncontradoException.class, () -> {
            profesorService.borrarProfesor(profesor.getId());
        });
    
        Mockito.verify(profesorDaoMock, Mockito.never()).borrarProfesor(profesor.getId());
    }

    @Test
    public void testMostrarProfesorSuccess() throws ProfesorNoEncontradoException {
        ProfesorDto profesorDto = getProfesorDto();
        Profesor profesor = new Profesor(profesorDto);

        when(profesorDao.mostrarProfesor(profesor.getId())).thenReturn(profesor);

        assertNotNull(ProfesorService.mostrarProfesor(profesor.getId()));

        verify(profesorDao, times(1)).mostrarProfesor(profesor.getId());
    }


    @Test
    public void testMostrarProfesorNoEncontrado() throws ProfesorNoEncontradoException {
        ProfesorDto profesorDto = getProfesorDto();
        Profesor profesor = new Profesor(profesorDto);

        when(profesorDao.mostrarProfesor(profesor.getId())).thenReturn(null);

        assertThrows(ProfesorNoEncontradoException.class, () ->ProfesorService.mostrarProfesor(profesor.getId()));

        verify(profesorDao, times(1)).mostrarProfesor(profesor.getId());
    }


     @Test
    public void testMostrarTodosLosProfesoresSuccess() throws ProfesorNoEncontradoException, FileNotFoundException, IOException {
        List<Profesor> profesores = List.of(new Profesor (getProfesorDto()));

        when(profesorDao.mostrarTodosLosProfesores()).thenReturn(profesores);

        Profesor mostrado = ProfesorService.mostrarTodosLosProfesores().get(0);

        assertNotNull(mostrado);

        verify(profesorDao, times(2)).mostrarTodosLosProfesores();
    }


    @Test
    public void testMostrarTodosLosProfesoresNoEncontrado() throws ProfesorNoEncontradoException { 
        List<Profesor> profesoresVacio  = List.of();

        when(profesorDao.mostrarTodosLosProfesores()).thenReturn(profesoresVacio);

        assertThrows(ProfesorNoEncontradoException.class, () ->ProfesorService.mostrarTodosLosProfesores());

        verify (profesorDao, times(1)).mostrarTodosLosProfesores();
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