package ar.edu.utn.frbb.tup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frbb.tup.exceptions.AlumnoNoEncontradoException;
import ar.edu.utn.frbb.tup.exceptions.AsignaturaAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.AsignaturaNoEncontradaException;
import ar.edu.utn.frbb.tup.exceptions.MateriaNoEncontradaException;
import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.persistence.AsignaturaDao;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.AsignaturaDto;

@Service
public class AsignaturaService {
    
    @Autowired 
    private AsignaturaDao asignaturaDao;

    @Autowired
    private AlumnoDao alumnoDao;

    @Autowired
    private MateriaDao materiaDao;


    
    public Asignatura crearAsignatura(AsignaturaDto asignaturadto) throws AsignaturaAlreadyExistsException, MateriaNoEncontradaException, AlumnoNoEncontradoException{
        Asignatura asignatura= new Asignatura(asignaturadto);
        Asignatura asignaturaExistente = asignaturaDao.BuscarAsignaturaId(asignatura.getAsignaturaId());
        if (asignaturaExistente != null){
            throw new AsignaturaAlreadyExistsException("La asignatura con ese ID ya existe");
        }
        if (materiaDao.BuscarId(asignatura.getIdmateria()) == null){
            throw new MateriaNoEncontradaException("La materia con ese ID no se encontro");
        }

        if (alumnoDao.BuscarId(asignatura.getAlumnoid()) == null) {
            throw new AlumnoNoEncontradoException("No se encontro un alumno con ese ID");
        }
        asignaturaDao.crearAsignatura(asignatura);
        return asignatura; 
    }

    public Asignatura borrarAsignatura(long asignaturaId) throws AsignaturaNoEncontradaException {
        Asignatura asignaturaExistente = asignaturaDao.BuscarAsignaturaId(asignaturaId);
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


    

    

