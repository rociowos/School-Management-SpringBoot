package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exceptions.*;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.presentation.modelDto.MateriaDto;
import ar.edu.utn.frbb.tup.presentation.validator.MateriaValidator;
import ar.edu.utn.frbb.tup.service.MateriaService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/materia")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private MateriaValidator materiaValidator;

   
    @PostMapping
    public ResponseEntity<Materia> darDeAltaMateria(@RequestBody MateriaDto materiadto) throws MateriaAlreadyExistsException {
        materiaValidator.validarMateria(materiadto);
        return new ResponseEntity<>(materiaService.crearMateria(materiadto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{idmateria}")
    public ResponseEntity<Materia> borrarMateria(@PathVariable long idmateria) throws MateriaNoEncontradaException{   
        return new ResponseEntity<>(materiaService.borrarMateria(idmateria),HttpStatus.OK);
    }

    @PutMapping("/{idmateria}")
    public ResponseEntity<Materia> modificarMateria(@PathVariable long idmateria, @RequestBody MateriaDto materiadto) throws MateriaNoEncontradaException {
        materiaValidator.validarMateria(materiadto);
        return new ResponseEntity<>(materiaService.modificarMateria(materiadto), HttpStatus.OK);
    }
    

    @GetMapping("/{idmateria}")
    public ResponseEntity<Materia> mostrarMateria(@PathVariable long idmateria) throws MateriaNoEncontradaException {
        return new ResponseEntity<>(materiaService.mostrarMateria(idmateria), HttpStatus.OK);
    }

   @GetMapping
    public ResponseEntity<List<Materia>> getTodasLasMaterias() throws MateriaNoEncontradaException, FileNotFoundException, IOException {
        return new ResponseEntity<>(materiaService.mostrarTodasLasMaterias(), HttpStatus.OK);   
    }
        
    //obtener todas las materias que dicta un profesor en orden alfabetico
    @GetMapping("/profesor/{profesorid}")
    public ResponseEntity<List<Materia>> getMateriasPorProfesor(@PathVariable long profesorid) throws FileNotFoundException, MateriaNoEncontradaException, IOException {
        List<Materia> materias = materiaService.mostrarMateriasPorProfesor(profesorid);
        return ResponseEntity.ok(materias);
    }

  
}