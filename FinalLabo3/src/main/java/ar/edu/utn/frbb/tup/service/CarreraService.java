package ar.edu.utn.frbb.tup.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frbb.tup.exceptions.CarreraAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.CarreraNoEncontradaException;
import ar.edu.utn.frbb.tup.model.Carrera;
import ar.edu.utn.frbb.tup.persistence.CarreraDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.CarreraDto;


@Service
public class CarreraService {

    @Autowired 
    private CarreraDao carreraDao;


    public Carrera crearCarrera(CarreraDto carreradto) throws CarreraAlreadyExistsException{
        Carrera carrera = new Carrera(carreradto);
        Carrera carreraExistente = carreraDao.findByCarrera(carrera.getNombre());
        if (carreraExistente != null){
            throw new CarreraAlreadyExistsException("La carrera con ese nombre ya existe");
        }
        carreraDao.crearCarrera(carrera);
        return carrera; 
    }

    public Carrera borrarCarrera(String nombre) throws CarreraNoEncontradaException {
        Carrera carreraExistente = carreraDao.findByCarrera(nombre);
        if (carreraExistente == null) {
            throw new CarreraNoEncontradaException("No se encontró una carrera con ese nombre");
        }
        carreraDao.borrarCarrera(carreraExistente.getNombre()); 
        return carreraExistente; 
    }

    public Carrera mostrarCarrera(String nombre) throws CarreraNoEncontradaException {
        Carrera carrera = carreraDao.mostrarCarrera(nombre);
        if (carrera == null) {
            throw new CarreraNoEncontradaException("No se encontro una carrera con el nombre: " + nombre);
        }
        return carrera;
    }


    public List<Carrera> mostrarTodasLasCarreras() throws CarreraNoEncontradaException, FileNotFoundException, IOException {
        List<Carrera> todasLasCarreras = carreraDao.mostrarTodasLasCarreras();
        if (todasLasCarreras.isEmpty()) {
            throw new CarreraNoEncontradaException("No se encontraron carreras");
        }
        return carreraDao.mostrarTodasLasCarreras();
    }
}


    
    
