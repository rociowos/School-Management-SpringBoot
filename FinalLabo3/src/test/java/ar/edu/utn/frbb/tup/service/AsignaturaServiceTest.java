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

import ar.edu.utn.frbb.tup.exceptions.AlumnoNoEncontradoException;
import ar.edu.utn.frbb.tup.exceptions.AsignaturaAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.AsignaturaNoEncontradaException;
import ar.edu.utn.frbb.tup.exceptions.MateriaAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.MateriaNoEncontradaException;
import ar.edu.utn.frbb.tup.exceptions.ProfesorNoEncontradoException;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.AsignaturaDao;
import ar.edu.utn.frbb.tup.persistence.CarreraDao;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.AlumnoDto;
import ar.edu.utn.frbb.tup.presentation.modelDto.AsignaturaDto;
import ar.edu.utn.frbb.tup.presentation.modelDto.MateriaDto;
import ar.edu.utn.frbb.tup.presentation.modelDto.ProfesorDto;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AsignaturaServiceTest {
    
    @Mock
    AsignaturaDao asignaturaDao;

    @Mock
    AlumnoDao  alumnoDao;

    @Mock
    CarreraDao carreraDao;

    @Mock
    MateriaDao materiaDao;

    @Mock
    ProfesorDao profesorDao;

    @InjectMocks
    AsignaturaService asignaturaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testDarDeAltaMateriaSuccess() throws MateriaAlreadyExistsException, ProfesorNoEncontradoException, AsignaturaAlreadyExistsException, MateriaNoEncontradaException, AlumnoNoEncontradoException{
        AsignaturaDto asignaturaDto = getAsignaturaDto();
        Asignatura asignatura = new Asignatura(asignaturaDto);
        
        MateriaDto materiaDto = getMateriaDto();
        Materia materia = new Materia(materiaDto);
        AlumnoDto alumnoDto = getAlumnoDto();
        Alumno alumno = new Alumno(alumnoDto);
        
        
        when(asignaturaDao.findByAsignaturaId(anyLong())).thenReturn(null);
        when (materiaDao.findById(anyLong())).thenReturn(materia);
        when (alumnoDao.findById(anyLong())).thenReturn(alumno);

        Asignatura asignaturaCreada =  asignaturaService.crearAsignatura(asignaturaDto);
        

        verify(asignaturaDao, times(1)).findByAsignaturaId(anyLong());
        verify(asignaturaDao, times(1)).crearAsignatura(any(Asignatura.class));

        assertNotNull(asignaturaCreada);
    }

   


    @Test
    public void testDarDeAltaAsignaturaExistente() throws AsignaturaAlreadyExistsException {
        AsignaturaDto asignaturaDto = getAsignaturaDto();
        Asignatura asignatura= new Asignatura(asignaturaDto);
        
        when(asignaturaDao.findByAsignaturaId(anyLong())).thenReturn(asignatura);

        assertThrows(AsignaturaAlreadyExistsException.class, () -> asignaturaService.crearAsignatura(asignaturaDto));

        verify(asignaturaDao, times(1)).findByAsignaturaId(anyLong());
        verify(asignaturaDao, times(0)).crearAsignatura(any(Asignatura.class));
    }



    @Test
    public void testBorrarAsignaturaSuccess() throws AsignaturaNoEncontradaException {
        AsignaturaDto asignaturaDto = getAsignaturaDto();
        Asignatura asignatura= new Asignatura(asignaturaDto);

        AsignaturaDao asignaturaDaoMock = mock(AsignaturaDao.class);
        when(asignaturaDaoMock.findByAsignaturaId(asignatura.getAsignaturaId())).thenReturn(asignatura);
    
        AsignaturaService asignaturaService = new AsignaturaService();
        ReflectionTestUtils.setField(asignaturaService, "asignaturaDao", asignaturaDaoMock);
    
       
        Asignatura result = asignaturaService.borrarAsignatura(asignatura.getAsignaturaId());
    
        assertNotNull(result);
        assertEquals(asignatura.getAsignaturaId(), result.getAsignaturaId());
        verify(asignaturaDaoMock, times(1)).borrarAsignatura(asignatura.getAsignaturaId());
    }
    
    @Test
    public void testBorrarAsignaturaNoEncontrado() {
        AsignaturaDto asignaturaDto = getAsignaturaDto();
        Asignatura asignatura= new Asignatura(asignaturaDto);

        AsignaturaDao asignaturaDaoMock = mock(AsignaturaDao.class);
        Mockito.when(asignaturaDaoMock.findByAsignaturaId(asignatura.getAsignaturaId())).thenReturn(null);
    
        AsignaturaService asignaturaService = new AsignaturaService();
        ReflectionTestUtils.setField(asignaturaService, "asignaturaDao", asignaturaDaoMock);
    
        
        assertThrows(AsignaturaNoEncontradaException.class, () -> {
            asignaturaService.borrarAsignatura(asignatura.getAsignaturaId());
        });
    
        Mockito.verify(asignaturaDaoMock, Mockito.never()).borrarAsignatura(Asignatura.generarIdAsignatura());
    }

    
       
    @Test
    public void TestMostrarAsignaturaExistente() throws AsignaturaNoEncontradaException {
            
        long asignaturaId = 1L;
        long alumnoId = 1L;
        Asignatura expectedAsignatura = new Asignatura();
        expectedAsignatura.setAsignaturaId(asignaturaId);
        expectedAsignatura.setAlumnoid(alumnoId);
        
        AsignaturaDao asignaturaDaoMock = mock(AsignaturaDao.class);
        when(asignaturaDaoMock.mostrarAsignatura(asignaturaId, alumnoId)).thenReturn(expectedAsignatura);
        
        AsignaturaService asignaturaService = new AsignaturaService();
        ReflectionTestUtils.setField(asignaturaService, "asignaturaDao", asignaturaDaoMock);
        
        Asignatura result = asignaturaService.mostrarAsignatura(asignaturaId, alumnoId);
        
            
        assertNotNull(result);
        assertEquals(asignaturaId, result.getAsignaturaId());
        assertEquals(alumnoId, result.getAlumnoid());
    }

       
    @Test
    public void TestMostrarAsignaturaNoEncontrada() {
           
        long asignaturaId = 1L;
        long alumnoId = 1L;
        
        AsignaturaDao asignaturaDaoMock = Mockito.mock(AsignaturaDao.class);
        Mockito.when(asignaturaDaoMock.mostrarAsignatura(asignaturaId, alumnoId)).thenReturn(null);
        
        AsignaturaService asignaturaService = new AsignaturaService();
        ReflectionTestUtils.setField(asignaturaService, "asignaturaDao", asignaturaDaoMock);
        
        
        assertThrows(AsignaturaNoEncontradaException.class, () -> {
            asignaturaService.mostrarAsignatura(asignaturaId, alumnoId);
        });
    }
    
    

    public AsignaturaDto getAsignaturaDto() {
        AsignaturaDto asignaturadto = new AsignaturaDto();
        asignaturadto.setIdmateria("1111");
        asignaturadto.setEstadoAsignatura("APROBADA");
        asignaturadto.setNota("7");
        asignaturadto.setAlumnoid("1234");
        return asignaturadto;
    }

    public MateriaDto getMateriaDto() {
        MateriaDto materiadto = new MateriaDto();
        materiadto.setNombre("Matematica 2");
        materiadto.setProfesorid("5555");
        materiadto.setAnio("2");
        materiadto.setCuatrimestre("1");
        materiadto.setCorrelatividades(null);
        return materiadto;
    }

    public AlumnoDto getAlumnoDto() {
        AlumnoDto alumnodto = new AlumnoDto();
        alumnodto.setDni("12345678");
        alumnodto.setNombre("Jorge");
        alumnodto.setApellido("Lopez");
        return alumnodto;
    }
}