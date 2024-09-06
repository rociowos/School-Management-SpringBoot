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

import ar.edu.utn.frbb.tup.exceptions.AlumnoAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.AlumnoNoEncontradoException;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.AsignaturaDao;
import ar.edu.utn.frbb.tup.persistence.CarreraDao;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.AlumnoDto;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AlumnoServiceTest {
    
    @Mock
    AlumnoDao  alumnoDao;

    @Mock
    AsignaturaDao asignaturaDao;

    @Mock
    CarreraDao carreraDao;

    @Mock
    MateriaDao materiaDao;

    @Mock
    ProfesorDao profesorDao;

    @InjectMocks
    AlumnoService alumnoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testDarDeAltaAlumnoSuccess() throws AlumnoAlreadyExistsException{
        AlumnoDto alumnoDto = getAlumnoDto();
        Alumno alumno = new Alumno(alumnoDto);
        
        when(alumnoDao.BuscarDni(alumno.getDni())).thenReturn(null);

        Alumno alumnoCreado =  alumnoService.crearAlumno(alumnoDto);

        verify(alumnoDao, times(1)).BuscarDni(alumno.getDni());
        verify(alumnoDao, times(1)).crearAlumno(any(Alumno.class));

        assertNotNull(alumnoCreado);
    }

    @Test
    public void testDarDeAltaAlumnoExistente() throws AlumnoAlreadyExistsException {
        AlumnoDto alumnoDto = getAlumnoDto();
        Alumno alumno = new Alumno(alumnoDto);
        
        when(alumnoDao.BuscarDni(alumno.getDni())).thenReturn(alumno);

        assertThrows(AlumnoAlreadyExistsException.class, () -> alumnoService.crearAlumno(alumnoDto));

        verify(alumnoDao, times(1)).BuscarDni(alumno.getDni());
        verify(alumnoDao, times(0)).crearAlumno(any(Alumno.class));
    }
 

    @Test
    public void testBorrarAlumnoSuccess() throws AlumnoNoEncontradoException {
        AlumnoDto alumnoDto = getAlumnoDto();
        Alumno alumno = new Alumno(alumnoDto);

        AlumnoDao alumnoDaoMock = mock(AlumnoDao.class);
        when(alumnoDaoMock.BuscarDni(alumno.getDni())).thenReturn(alumno);
    
        AlumnoService alumnoService = new AlumnoService();
        ReflectionTestUtils.setField(alumnoService, "alumnoDao", alumnoDaoMock);
    
       
        Alumno result = alumnoService.borrarAlumno(alumno.getDni());
    
        assertNotNull(result);
        assertEquals(alumno.getDni(), result.getDni());
        verify(alumnoDaoMock, times(1)).borrarAlumno(alumno.getDni());
    }

    @Test
    public void testBorrarAlumnoNoEncontrado() {
        AlumnoDto alumnoDto = getAlumnoDto();
        Alumno alumno = new Alumno(alumnoDto);
    
        AlumnoDao alumnoDaoMock = Mockito.mock(AlumnoDao.class);
        Mockito.when(alumnoDaoMock.BuscarDni(alumno.getDni())).thenReturn(null);
    
        AlumnoService alumnoService = new AlumnoService();
        ReflectionTestUtils.setField(alumnoService, "alumnoDao", alumnoDaoMock);
    
        
        assertThrows(AlumnoNoEncontradoException.class, () -> {
            alumnoService.borrarAlumno(alumno.getDni());
        });
    
        Mockito.verify(alumnoDaoMock, Mockito.never()).borrarAlumno(alumno.getDni());
    }
    @Test
    public void testMostrarAlumnoSuccess() throws AlumnoNoEncontradoException {
        AlumnoDto alumnoDto = getAlumnoDto();
        Alumno alumno = new Alumno(alumnoDto);

        when(alumnoDao.mostrarAlumno(alumno.getId())).thenReturn(alumno);

        assertNotNull(alumnoService.mostrarAlumno(alumno.getId()));

        verify(alumnoDao, times(1)).mostrarAlumno(alumno.getId());
    }

    @Test
    public void testMostrarAlumnoNoEncontrado() throws AlumnoNoEncontradoException {
        AlumnoDto alumnoDto = getAlumnoDto();
        Alumno alumno = new Alumno(alumnoDto);

        when(alumnoDao.mostrarAlumno(alumno.getId())).thenReturn(null);

        assertThrows(AlumnoNoEncontradoException.class, () ->alumnoService.mostrarAlumno(alumno.getId()));

        verify(alumnoDao, times(1)).mostrarAlumno(alumno.getId());
    }

    @Test
    public void testMostrarTodosLosAlumnosSuccess() throws AlumnoNoEncontradoException, FileNotFoundException, IOException {
        List<Alumno> alumnos = List.of(new Alumno (getAlumnoDto()));

        when(alumnoDao.mostrarTodosLosAlumnos()).thenReturn(alumnos);

        Alumno mostrado =  alumnoService.mostrarTodosLosAlumnos().get(0);

        assertNotNull(mostrado);

        verify(alumnoDao, times(2)).mostrarTodosLosAlumnos();
    }

    @Test
    public void testMostrarTodosLosAlumnosNoEncontrado() throws AlumnoNoEncontradoException { 
        List<Alumno> alumnosVacio = List.of();

        when(alumnoDao.mostrarTodosLosAlumnos()).thenReturn(alumnosVacio);

        assertThrows(AlumnoNoEncontradoException.class, () ->alumnoService.mostrarTodosLosAlumnos());

        verify(alumnoDao, times(1)).mostrarTodosLosAlumnos();
    }



     public AlumnoDto getAlumnoDto() {
        AlumnoDto alumnodto = new AlumnoDto();
        alumnodto.setDni("12345678");
        alumnodto.setNombre("Jorge");
        alumnodto.setApellido("Lopez");
        return alumnodto;
    }


}