package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exceptions.*;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.presentation.modelDto.ProfesorDto;
import ar.edu.utn.frbb.tup.service.ProfesorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/profesor")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

   
    @PostMapping
    public ResponseEntity<Profesor> darDeAltaProfesor(@RequestBody ProfesorDto profesordto) throws ProfesorAlreadyExistsException {
        return new ResponseEntity<>(profesorService.crearProfesor(profesordto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Profesor> borrarProfesor(@PathVariable long id) throws ProfesorNoEncontradoException{   
        return new ResponseEntity<>(profesorService.borrarProfesor(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profesor> modificarProfesor(@PathVariable long id, @RequestBody ProfesorDto profesordto) throws ProfesorNoEncontradoException {//clienteValidator.validarCliente(clientedto);
        return new ResponseEntity<>(profesorService.modificarProfesor(id, profesordto), HttpStatus.OK);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> mostrarProfesor(@PathVariable long id) throws ProfesorNoEncontradoException {
        return new ResponseEntity<>(profesorService.mostrarProfesor(id), HttpStatus.OK);
    }

   @GetMapping
    public ResponseEntity<List<Profesor>> getTodosLosProfesores() throws ProfesorNoEncontradoException, FileNotFoundException, IOException {
        return new ResponseEntity<>(profesorService.mostrarTodosLosProfesores(), HttpStatus.OK);   
    }
        
    //obtener todas las materias que dicta un profesor en orden alfabetico
  
}