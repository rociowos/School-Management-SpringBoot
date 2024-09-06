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

import ar.edu.utn.frbb.tup.exceptions.MateriaAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.MateriaNoEncontradaException;
import ar.edu.utn.frbb.tup.exceptions.ProfesorNoEncontradoException;

import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.AsignaturaDao;
import ar.edu.utn.frbb.tup.persistence.CarreraDao;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;

import ar.edu.utn.frbb.tup.presentation.modelDto.MateriaDto;
import ar.edu.utn.frbb.tup.presentation.modelDto.ProfesorDto;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MateriaServiceTest {
    @Mock
    MateriaDao materiaDao;

    @Mock
    AlumnoDao  alumnoDao;

    @Mock
    AsignaturaDao asignaturaDao;

    @Mock
    CarreraDao carreraDao;

    @Mock
    ProfesorDao profesorDao;

    @InjectMocks
    MateriaService materiaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDarDeAltaMateriaSuccess() throws MateriaAlreadyExistsException, ProfesorNoEncontradoException{
        MateriaDto materiaDto = getMateriaDto();
        Materia materia = new Materia(materiaDto);
        ProfesorDto profesorDto = getProfesorDto();
        Profesor profesor = new Profesor(profesorDto);
        
        
        when(materiaDao.BuscarId(anyLong())).thenReturn(null);
        when (profesorDao.BuscarId(anyLong())).thenReturn(profesor);

        Materia materiaCreada =  materiaService.crearMateria(materiaDto);
        

        verify(materiaDao, times(1)).BuscarId(anyLong());
        verify(materiaDao, times(1)).crearMateria(any(Materia.class));

        assertNotNull(materiaCreada);
    }


    @Test
    public void testDarDeAltaMateriaExistente() throws MateriaAlreadyExistsException {
        MateriaDto materiaDto = getMateriaDto();
        Materia materia = new Materia(materiaDto);
        
        
        when(materiaDao.BuscarId(anyLong())).thenReturn(materia);

        assertThrows(MateriaAlreadyExistsException.class, () -> materiaService.crearMateria(materiaDto));

        verify(materiaDao, times(1)).BuscarId(anyLong());
        verify(materiaDao, times(0)).crearMateria(any(Materia.class));
    }


    @Test
    public void testBorrarMateriaSuccess() throws MateriaNoEncontradaException {
        MateriaDto materiaDto = getMateriaDto();
        Materia materia = new Materia(materiaDto);
        

        MateriaDao materiaDaoMock = mock(MateriaDao.class);
        when(materiaDaoMock.BuscarId(anyLong())).thenReturn(materia);
    
        MateriaService materiaService = new MateriaService();
        ReflectionTestUtils.setField(materiaService, "materiaDao", materiaDaoMock);
    
       
        Materia result = materiaService.borrarMateria(anyLong());
    
        assertNotNull(result);
        assertEquals(materia.getIdmateria(), result.getIdmateria());
        verify(materiaDaoMock, times(1)).borrarMateria(anyLong());
    }

    @Test
    public void testBorrarMateriaNoEncontrada() {
        MateriaDto materiaDto = getMateriaDto();
        Materia materia = new Materia(materiaDto);

        MateriaDao materiaDaoMock = mock(MateriaDao.class);
        when(materiaDaoMock.BuscarId(anyLong())).thenReturn(null);
    
        MateriaService materiaService = new MateriaService();
        ReflectionTestUtils.setField(materiaService, "materiaDao", materiaDaoMock);
    
        
        assertThrows(MateriaNoEncontradaException.class, () -> {
            materiaService.borrarMateria(anyLong());
        });
    
        Mockito.verify(materiaDaoMock, Mockito.never()).borrarMateria(anyLong());
    }


    @Test
    public void testMostrarMateriaSuccess() throws MateriaNoEncontradaException {
        MateriaDto materiaDto = getMateriaDto();
        Materia materia = new Materia(materiaDto);

        when(materiaDao.mostrarMateria(materia.getIdmateria())).thenReturn(materia);

        assertNotNull(materiaService.mostrarMateria(materia.getIdmateria()));

        verify(materiaDao, times(1)).mostrarMateria(materia.getIdmateria());
    }

    @Test
    public void testMostrarMateriaNoEncontrada() throws MateriaNoEncontradaException {
        MateriaDto materiaDto = getMateriaDto();
        Materia materia = new Materia(materiaDto);

        when(materiaDao.mostrarMateria(materia.getIdmateria())).thenReturn(null);

        assertThrows(MateriaNoEncontradaException.class, () ->materiaService.mostrarMateria(materia.getIdmateria()));

        verify(materiaDao, times(1)).mostrarMateria(anyLong());
    }
    
    @Test
    public void testMostrarTodasLasMateriasSuccess() throws MateriaNoEncontradaException, FileNotFoundException, IOException {
        List<Materia> materias = List.of(new Materia (getMateriaDto()));

        when(materiaDao.mostrarTodasLasMaterias()).thenReturn(materias);

        Materia mostrada =  materiaService.mostrarTodasLasMaterias().get(0);

        assertNotNull(mostrada);

        verify(materiaDao, times(2)).mostrarTodasLasMaterias();
    }

    @Test
    public void testMostrarTodasLasMateriasNoEncontrada() throws MateriaNoEncontradaException { 
        List<Materia> materiasVacio = List.of();

        when(materiaDao.mostrarTodasLasMaterias()).thenReturn(materiasVacio);

        assertThrows(MateriaNoEncontradaException.class, () ->materiaService.mostrarTodasLasMaterias());

        verify(materiaDao, times(1)).mostrarTodasLasMaterias();
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

    public ProfesorDto getProfesorDto() {
        ProfesorDto profesordto = new ProfesorDto();
        profesordto.setNombre("Marta");
        profesordto.setApellido("Martinez");
        profesordto.setTitulo("Matematico");
        profesordto.setnombreMateria("Matematica");
        return profesordto;
    }

}