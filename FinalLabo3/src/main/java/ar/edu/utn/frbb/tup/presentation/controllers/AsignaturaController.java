package ar.edu.utn.frbb.tup.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.edu.utn.frbb.tup.exceptions.*;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.presentation.modelDto.AsignaturaDto;
import ar.edu.utn.frbb.tup.presentation.validator.AsignaturaValidator;
import ar.edu.utn.frbb.tup.service.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/asignatura")
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    @Autowired
    private AsignaturaValidator asignaturaValidator;

   
    @PostMapping
    public ResponseEntity<Asignatura> darDeAltaAsignatura(@RequestBody AsignaturaDto asignaturadto) throws AsignaturaAlreadyExistsException{
        asignaturaValidator.validarAsignatura(asignaturadto);
        return new ResponseEntity<>(asignaturaService.crearAsignatura(asignaturadto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{asignaturaId}")
    public ResponseEntity<Asignatura> borrarAsignatura(@PathVariable long asignaturaId) throws AsignaturaNoEncontradaException{   
        return new ResponseEntity<>(asignaturaService.borrarAsignatura(asignaturaId),HttpStatus.OK);
    }

    @GetMapping("/{asignaturaId}/{alumnoid}")
    public ResponseEntity<Asignatura> mostrarAsignatura(@PathVariable long asignaturaId,@PathVariable long alumnoid) throws AsignaturaNoEncontradaException {
        return new ResponseEntity<>(asignaturaService.mostrarAsignatura(asignaturaId, alumnoid), HttpStatus.OK);
    }

    
  
}