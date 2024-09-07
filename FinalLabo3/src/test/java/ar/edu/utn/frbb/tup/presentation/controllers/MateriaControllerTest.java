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

import ar.edu.utn.frbb.tup.exceptions.MateriaAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.MateriaNoEncontradaException;
import ar.edu.utn.frbb.tup.exceptions.ProfesorNoEncontradoException;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.presentation.modelDto.MateriaDto;
import ar.edu.utn.frbb.tup.presentation.validator.MateriaValidator;
import ar.edu.utn.frbb.tup.service.MateriaService;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MateriaControllerTest {

    @Mock
    MateriaService materiaService;

    @Mock
    MateriaValidator materiaValidator;

    @InjectMocks
    MateriaController materiaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    
    @Test
    public void testMostrarTodasLasMateriasSuccess () throws MateriaNoEncontradaException, FileNotFoundException, IOException{
        List<Materia> materias = new ArrayList<>();

        when(materiaService.mostrarTodasLasMaterias()).thenReturn(materias);

        ResponseEntity<List<Materia>> mostrados = materiaController.getTodasLasMaterias();

        verify(materiaService, times(1)).mostrarTodasLasMaterias();

        assertEquals(200, mostrados.getStatusCodeValue());
    }

    @Test
    public void testMostrarTodasLasMateriasFail () throws MateriaNoEncontradaException, FileNotFoundException, IOException{

        doThrow(new MateriaNoEncontradaException("No se encontraron materias")).when(materiaService).mostrarTodasLasMaterias();

        assertThrows(MateriaNoEncontradaException.class, () -> materiaController.getTodasLasMaterias());

        verify(materiaService, times(1)).mostrarTodasLasMaterias();
    }


    @Test
    public void testMostrarMateriaIdSuccess() throws MateriaNoEncontradaException{
        Materia materia = getMateria();

        when(materiaService.mostrarMateria(materia.getIdmateria())).thenReturn(materia);

        ResponseEntity<Materia> materiamostrada = materiaController.mostrarMateria(materia.getIdmateria());

        verify(materiaService, times(1)).mostrarMateria(materia.getIdmateria());

        assertEquals(200, materiamostrada.getStatusCodeValue());
        assertEquals(materiamostrada.getBody(), materia);
    }

    @Test
    public void testMostrarMateriaIdFail() throws MateriaNoEncontradaException{

        doThrow(new MateriaNoEncontradaException("No se encontro la materia con ese ID")).when(materiaService).mostrarMateria(1234);

        assertThrows(MateriaNoEncontradaException.class, () -> materiaController.mostrarMateria(1234));

        verify(materiaService, times(1)).mostrarMateria(1234);

    }


    @Test
    public void testMostrarMateriaIdProfesorSuccess() throws MateriaNoEncontradaException, FileNotFoundException, IOException {

        List<Materia> materias = new ArrayList<>();

        when(materiaService.mostrarMateriasPorProfesor(anyLong())).thenReturn(materias);

        ResponseEntity<List<Materia>> response = materiaController.getMateriasPorProfesor(5555);

        verify(materiaService, times(1)).mostrarMateriasPorProfesor(5555);

        assertEquals(200, response.getStatusCodeValue());
    }


    @Test
    public void testMostrarMateriaIdProfesorFail() throws MateriaNoEncontradaException, FileNotFoundException, IOException{

        doThrow(MateriaNoEncontradaException.class).when(materiaService).mostrarMateriasPorProfesor(5555);

        assertThrows(MateriaNoEncontradaException.class, () -> materiaController.getMateriasPorProfesor(5555));

        verify(materiaService, times(1)).mostrarMateriasPorProfesor(5555);
    }


    @Test
    public void testModificarMateriaSuccess() throws MateriaNoEncontradaException {

        MateriaDto materiaDto = getMateriaDto();
        Materia materia = getMateria();
        when(materiaService.modificarMateria(materia.getIdmateria(),materiaDto)).thenReturn(new Materia());

        ResponseEntity<Materia> modificado = materiaController.modificarMateria(1234,materiaDto);

        verify(materiaService, times(1)).modificarMateria(materia.getIdmateria(),materiaDto);
        verify(materiaValidator, times(1)).validarMateria(materiaDto);

        assertEquals(200, modificado.getStatusCodeValue());
    }


    @Test
    public void testModificarMateriaFail() throws MateriaNoEncontradaException {
        MateriaDto materiaDto = getMateriaDto();
        Materia materia = getMateria();

        doThrow(new MateriaNoEncontradaException("No se encontro materia para modificar")).when(materiaService).modificarMateria(materia.getIdmateria(),materiaDto);

        assertThrows(MateriaNoEncontradaException.class, () -> materiaController.modificarMateria(1234, materiaDto));

        verify(materiaService, times(1)).modificarMateria(materia.getIdmateria(),materiaDto);
        verify(materiaValidator, times(1)).validarMateria(materiaDto);
    }



    @Test
    public void testBorrarMateriaSuccess() throws MateriaNoEncontradaException {

        when(materiaService.borrarMateria(1234)).thenReturn(new Materia());

        ResponseEntity<Materia> borrado = materiaController.borrarMateria(1234);

        verify(materiaService, times(1)).borrarMateria(1234);

        assertEquals(200, borrado.getStatusCodeValue());
    }



    @Test
    public void testBorrarMateriaFail() throws MateriaNoEncontradaException {

        doThrow(new MateriaNoEncontradaException("No se encontro la materia")).when(materiaService).borrarMateria(1234);

        assertThrows(MateriaNoEncontradaException.class, () -> materiaController.borrarMateria(1234));

        verify(materiaService, times(1)).borrarMateria(1234);
    }



    @Test
    public void testDarDeAltaMateriaSuccess() throws MateriaAlreadyExistsException, ProfesorNoEncontradoException {

        MateriaDto materiaDto = getMateriaDto();

        when(materiaService.crearMateria(materiaDto)).thenReturn(new Materia());

        ResponseEntity<Materia> alta = materiaController.darDeAltaMateria(materiaDto);

        verify(materiaService, times(1)).crearMateria(materiaDto);
        verify(materiaValidator, times(1)).validarMateria(materiaDto);

        assertEquals(201, alta.getStatusCodeValue());
    }

    @Test
    public void testDarDeAltaMateriaFail() throws MateriaAlreadyExistsException, ProfesorNoEncontradoException {

        MateriaDto materiaDto = getMateriaDto();

        doThrow(new MateriaAlreadyExistsException("El cliente ya existe")).when(materiaService).crearMateria(materiaDto);

        assertThrows(MateriaAlreadyExistsException.class, () -> materiaController.darDeAltaMateria(materiaDto));

        verify(materiaService, times(1)).crearMateria(materiaDto);
        verify(materiaValidator, times(1)).validarMateria(materiaDto);
    }


    public Materia getMateria() {
        Materia materia = new Materia();
        materia.setIdmateria(1234);
        materia.setNombre("Matematica 2");
        materia.setProfesorid(5555);
        materia.setAnio(2);
        materia.setCuatrimestre(1);
        materia.setCorrelatividades(null);
        return materia;
        
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

}