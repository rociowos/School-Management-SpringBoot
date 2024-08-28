package ar.edu.utn.frbb.tup.service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frbb.tup.exceptions.MateriaAlreadyExistsException;
import ar.edu.utn.frbb.tup.exceptions.MateriaNoEncontradaException;
import ar.edu.utn.frbb.tup.model.Materia;
import ar.edu.utn.frbb.tup.persistence.MateriaDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.MateriaDto;



@Service
public class MateriaService {

    @Autowired 
    private MateriaDao materiaDao;


    public Materia crearMateria(MateriaDto materiadto) throws MateriaAlreadyExistsException{
        Materia materia = new Materia(materiadto);
        Materia materiaExistente = materiaDao.findById(materia.getIdmateria());
        if (materiaExistente != null){
            throw new MateriaAlreadyExistsException("La materia con ese ID ya existe");
        }
        materiaDao.crearMateria(materia);
        return materia; 
    }

    public Materia borrarMateria(long idmateria) throws MateriaNoEncontradaException {
        Materia materiaExistente = materiaDao.findById(idmateria);
        if (materiaExistente == null) {
            throw new MateriaNoEncontradaException("No se encontró una materia con el ID proporcionado");
        }
        materiaDao.borrarMateria(materiaExistente.getIdmateria()); 
        return materiaExistente; 
    }

    public Materia modificarMateria(MateriaDto materiadto) throws MateriaNoEncontradaException {
        Materia materia = new Materia(materiadto);
        materiaDao.modificarMateria(materia);

        return materia;
    }
 
    public Materia mostrarMateria(long idmateria) throws MateriaNoEncontradaException {
        Materia materia = materiaDao.mostrarMateria(idmateria);
        if (materia == null) {
            throw new MateriaNoEncontradaException("No se encontro la materia con id: " + idmateria);
        }
        return materia;
    }

   
 
    public List<Materia> mostrarTodasLasMaterias() throws MateriaNoEncontradaException, FileNotFoundException, IOException {
        List<Materia> todasLasMaterias = materiaDao.mostrarTodasLasMaterias();
        if (todasLasMaterias.isEmpty()) {
            throw new MateriaNoEncontradaException("No se encontraron materias");
        }
        return materiaDao.mostrarTodasLasMaterias();
    }


    public List<Materia> mostrarMateriasPorProfesor(long profesorid) throws MateriaNoEncontradaException, FileNotFoundException, IOException {
    List<Materia> todasLasMaterias = materiaDao.mostrarTodasLasMaterias(); // Obtenemos todas las materias

    // Filtramos las materias por el ID del profesor
    List<Materia> materiasFiltradas = todasLasMaterias.stream()
            .filter(materia -> materia.getProfesorid() == profesorid)
            .sorted(Comparator.comparing(Materia::getNombre, String.CASE_INSENSITIVE_ORDER)) // Ordenamos alfabéticamente por nombre
            .collect(Collectors.toList());

    if (materiasFiltradas.isEmpty()) {
        throw new MateriaNoEncontradaException("No se encontraron materias para el profesor con ID: " + profesorid);
    }

    return materiasFiltradas; // Retornamos la lista filtrada y ordenada
}

}


    