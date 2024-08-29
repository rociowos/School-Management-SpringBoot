package ar.edu.utn.frbb.tup.presentation.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frbb.tup.exceptions.CarreraAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.CarreraNoEncontradaException;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.presentation.modelDto.CarreraDto;
import ar.edu.utn.frbb.tup.service.CarreraService;




@RestController
@RequestMapping("/carrera")
public class CarreraController {
    

    @Autowired
    private CarreraService carreraService;

   
    @PostMapping
    public ResponseEntity<Carrera> darDeAltaCarrera(@RequestBody CarreraDto carreradto) throws CarreraAlreadyExistsException { 
        return new ResponseEntity<>(carreraService.crearCarrera(carreradto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Carrera> borrarCarrera(@PathVariable String nombre) throws CarreraNoEncontradaException{   
        return new ResponseEntity<>(carreraService.borrarCarrera(nombre),HttpStatus.OK);
    }


    @GetMapping("/{nombre}")
    public ResponseEntity<Carrera> mostrarCarrera(@PathVariable String nombre) throws CarreraNoEncontradaException { 
        return new ResponseEntity<>(carreraService.mostrarCarrera(nombre), HttpStatus.OK);
    }

   @GetMapping
    public ResponseEntity<List<Carrera>> getTodasLasCarreras() throws CarreraNoEncontradaException, FileNotFoundException, IOException { 
        return new ResponseEntity<>(carreraService.mostrarTodasLasCarreras(), HttpStatus.OK);   
    }
        
    
  
}

