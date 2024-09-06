package ar.edu.utn.frbb.tup.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.utn.frbb.tup.exceptions.ProfesorAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.ProfesorNoEncontradoException;
import ar.edu.utn.frbb.tup.model.Profesor;
import ar.edu.utn.frbb.tup.persistence.ProfesorDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.ProfesorDto;



@Service
public class ProfesorService {

    @Autowired 
    private ProfesorDao profesorDao;


    public Profesor crearProfesor(ProfesorDto profesordto) throws ProfesorAlreadyExistsException{
        Profesor profesor = new Profesor(profesordto);
        Profesor profesorExistente = profesorDao.BuscarId(profesor.getId());
        if (profesorExistente != null){
            throw new ProfesorAlreadyExistsException("El profesor con ese ID ya existe");
        }
        profesorDao.crearProfesor(profesor);
        return profesor; 
    }

    public Profesor borrarProfesor(long id) throws ProfesorNoEncontradoException {
        Profesor profesorExistente = profesorDao.BuscarId(id);
        if (profesorExistente == null) {
            throw new ProfesorNoEncontradoException("No se encontró un profesor con el ID proporcionado");
        }
        profesorDao.borrarProfesor(profesorExistente.getId()); 
        return profesorExistente; 
    }

    public Profesor modificarProfesor(long id, ProfesorDto profesordto) throws ProfesorNoEncontradoException {
        
       Profesor profesorExistente = profesorDao.BuscarId(id);
        if (profesorExistente == null) {
            throw new ProfesorNoEncontradoException("El profesor con el ID " + id + " no fue encontrado.");
        }
    
        
        profesorExistente.setNombre(profesordto.getNombre());
        profesorExistente.setApellido(profesordto.getApellido());
        profesorExistente.setTitulo(profesordto.getTitulo());
        profesorExistente.setNombreMateria(profesordto.getnombreMateria());
    
        profesorDao.modificarProfesor(profesorExistente);
        return profesorExistente;
    }
    

    public Profesor mostrarProfesor(long id) throws ProfesorNoEncontradoException {
        Profesor profesor = profesorDao.mostrarProfesor(id);
        if (profesor == null) {
            throw new ProfesorNoEncontradoException("No se encontro el profesor con id: " + id);
        }
        return profesor;
    }


    public List<Profesor> mostrarTodosLosProfesores() throws ProfesorNoEncontradoException, FileNotFoundException, IOException {
        List<Profesor> todosLosProfesores = profesorDao.mostrarTodosLosProfesores();
        if (todosLosProfesores.isEmpty()) {
            throw new ProfesorNoEncontradoException("No se encontraron profesores");
        }
        return profesorDao.mostrarTodosLosProfesores();
    }
}


    
