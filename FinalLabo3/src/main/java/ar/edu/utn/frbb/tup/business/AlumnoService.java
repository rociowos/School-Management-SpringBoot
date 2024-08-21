package ar.edu.utn.frbb.tup.business;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ar.edu.utn.frbb.tup.exceptions.AlumnoAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.AlumnoNoEncontradoException;
import ar.edu.utn.frbb.tup.model.Alumno;
import ar.edu.utn.frbb.tup.persistence.AlumnoDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.AlumnoDto;

@Service
public class AlumnoService {

    @Autowired 
    private AlumnoDao alumnoDao;

   /* public Alumno modificarAlumno(AlumnoDto alumnodto) throws AlumnoNoEncontradoException{
        Alumno alumno = new Alumno(alumnodto);
        alumnoDao.modificarAlumno(alumno);
        return alumno; 
    } */

    public Alumno crearAlumno(AlumnoDto alumnodto) throws AlumnoAlreadyExistsException{
        Alumno alumno = new Alumno(alumnodto);
        Alumno alumnoExistente = alumnoDao.findByDni(alumno.getDni());
        if (alumnoExistente != null){
            throw new AlumnoAlreadyExistsException("El alumno con ese DNI ya existe");
        }
        alumnoDao.crearAlumno(alumno);
        return alumno; 
    }

    public Alumno borrarAlumno(long dni) throws AlumnoNoEncontradoException {
        Alumno alumnoExistente = alumnoDao.findByDni(dni);
        if (alumnoExistente == null) {
            throw new AlumnoNoEncontradoException("No se encontró un alumno con el DNI proporcionado");
        }
        alumnoDao.borrarAlumno(alumnoExistente.getDni()); 
        return alumnoExistente; 
    }

    public Alumno modificarAlumno(long id, AlumnoDto alumnodto) throws AlumnoNoEncontradoException {
        
        Alumno alumnoExistente = alumnoDao.findById(id);
        if (alumnoExistente == null) {
            throw new AlumnoNoEncontradoException("El alumno con el ID " + id + " no fue encontrado.");
        }
    
        // Actualizar la información del alumno
        alumnoExistente.setDni(Long.parseLong(alumnodto.getDni()));
        alumnoExistente.setNombre(alumnodto.getNombre());
        alumnoExistente.setApellido(alumnodto.getApellido());
    
        alumnoDao.modificarAlumno(alumnoExistente);
        return alumnoExistente;
    }
    

    public Alumno mostrarAlumno(long id) throws AlumnoNoEncontradoException {
        Alumno alumno = alumnoDao.mostrarAlumno(id);
        if (alumno == null) {
            throw new AlumnoNoEncontradoException("No se encontro el alumno con id: " + id);
        }
        return alumno;
    }


    public List<Alumno> mostrarTodosLosAlumnos() throws AlumnoNoEncontradoException, FileNotFoundException, IOException {
        List<Alumno> todosLosAlumnos = alumnoDao.mostrarTodosLosAlumnos();
        if (todosLosAlumnos.isEmpty()) {
            throw new AlumnoNoEncontradoException("No se encontraron alumnos");
        }
        return alumnoDao.mostrarTodosLosAlumnos();
    }
}


    
