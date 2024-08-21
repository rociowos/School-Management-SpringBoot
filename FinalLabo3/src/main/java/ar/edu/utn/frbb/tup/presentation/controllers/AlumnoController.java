package ar.edu.utn.frbb.tup.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.edu.utn.frbb.tup.exceptions.*;
import ar.edu.utn.frbb.tup.model.Alumno;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.utn.frbb.tup.business.AlumnoService;
import ar.edu.utn.frbb.tup.presentation.modelDto.AlumnoDto;


import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

   
    @PostMapping
    public ResponseEntity<Alumno> darDeAltaAlumno(@RequestBody AlumnoDto alumnodto) throws AlumnoAlreadyExistsException {
        return new ResponseEntity<>(alumnoService.crearAlumno(alumnodto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<Alumno> borrarAlumno(@PathVariable long dni) throws AlumnoNoEncontradoException{   
        return new ResponseEntity<>(alumnoService.borrarAlumno(dni),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> modificarAlumno(@PathVariable long id, @RequestBody AlumnoDto alumnodto) throws AlumnoNoEncontradoException {//clienteValidator.validarCliente(clientedto);
        return new ResponseEntity<>(alumnoService.modificarAlumno(id, alumnodto), HttpStatus.OK);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> mostrarAlumno(@PathVariable long id) throws AlumnoNoEncontradoException {
        return new ResponseEntity<>(alumnoService.mostrarAlumno(id), HttpStatus.OK);
    }

   @GetMapping
    public ResponseEntity<List<Alumno>> getTodosLosAlumnos() throws AlumnoNoEncontradoException, FileNotFoundException, IOException {
        return new ResponseEntity<>(alumnoService.mostrarTodosLosAlumnos(), HttpStatus.OK);   
    }
        
    
  
}