package ar.edu.utn.frbb.tup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frbb.tup.exceptions.AsignaturaAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.AsignaturaNoEncontradaException;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.persistence.AsignaturaDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.AsignaturaDto;

@Service
public class AsignaturaService {
    
    @Autowired 
    private AsignaturaDao asignaturaDao;


    public Asignatura crearAsignatura(AsignaturaDto asignaturadto) throws AsignaturaAlreadyExistsException{
        Asignatura asignatura= new Asignatura(asignaturadto);
        Asignatura asignaturaExistente = asignaturaDao.findByAsignaturaId(asignatura.getAsignaturaId());
        if (asignaturaExistente != null){
            throw new AsignaturaAlreadyExistsException("La asignatura con ese ID ya existe");
        }
        asignaturaDao.crearAsignatura(asignatura);
        return asignatura; 
    }

    public Asignatura borrarAsignatura(long asignaturaId) throws AsignaturaNoEncontradaException {
        Asignatura asignaturaExistente = asignaturaDao.findByAsignaturaId(asignaturaId);
        if (asignaturaExistente == null) {
            throw new AsignaturaNoEncontradaException("No se encontró una asignatura con el ID proporcionado");
        }
        asignaturaDao.borrarAsignatura(asignaturaExistente.getAsignaturaId()); 
        return asignaturaExistente; 
    }
    
    //mostrar asignatura del id del alumno

    public Asignatura mostrarAsignatura(long asignaturaId, long alumnoId) throws AsignaturaNoEncontradaException {
        Asignatura asignatura = asignaturaDao.mostrarAsignatura(asignaturaId, alumnoId);
        
        if (asignatura == null || asignatura.getAlumnoid() != alumnoId) {
            throw new AsignaturaNoEncontradaException("No se encontró la asignatura con el ID: " + asignaturaId + " para el alumno con ID: " + alumnoId);
        }
        
        return asignatura;
    }
    
}


    

    

