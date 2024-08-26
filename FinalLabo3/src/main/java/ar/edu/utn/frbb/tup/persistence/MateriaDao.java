package ar.edu.utn.frbb.tup.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import ar.edu.utn.frbb.tup.exceptions.MateriaNoEncontradaException;
import ar.edu.utn.frbb.tup.model.Materia;


@Repository
public class MateriaDao {

    private static final String MATERIATXT = "FinalLabo3\\src\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\materia.txt";


    public void crearMateria(Materia materia) {
        boolean archivoNuevo = !(new File(MATERIATXT).exists());
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(MATERIATXT, true))) {
            if (archivoNuevo) {
                escritor.write("idmateria,nombre,profesorid,anio,cuatrimestre,correlatividades ");
                escritor.newLine();
            }
            escritor.write(materiaToTxt(materia));
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String materiaToTxt(Materia materia) {
        String correlatividadesStr = String.join("-", materia.getCorrelatividades().stream()
                                           .map(String::valueOf)
                                           .toArray(String[]::new));
        return materia.getIdmateria() + "," +
               materia.getNombre() + "," +
               materia.getProfesorid() + "," +
               materia.getAnio() + "," +
               materia.getCuatrimestre() + "," +
               correlatividadesStr;
    }



    public Materia findById(long idmateria) {
        try (BufferedReader lector = new BufferedReader(new FileReader(MATERIATXT))) {
            String linea;
            lector.readLine(); 
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (Long.parseLong(datos[0]) == idmateria) {
                    return parseDatosToObjet(datos);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return null;
    }
    
    public Materia parseDatosToObjet(String[] datos) {
        Materia materia = new Materia();
        materia.setIdmateria(Long.parseLong(datos[0]));
        materia.setNombre(datos[1]);
        materia.setProfesorid(Long.parseLong(datos[2])); 
        materia.setAnio(Long.parseLong(datos[3]));
        materia.setCuatrimestre(Long.parseLong(datos[4]));
        // Conversión de correlatividades
        if (datos.length > 5) {
            List<Long> correlatividades = Arrays.stream(datos[5].split("-"))
                                                .map(Long::parseLong)
                                                .collect(Collectors.toList());
            materia.setCorrelatividades(correlatividades);
        }
        return materia;
    }
    



    public Materia borrarMateria(long idmateria) {
        List<Materia>materias = new ArrayList<>();
        List<String> materiasStr = new ArrayList<>();
        Materia materia = null;
        try (BufferedReader lector = new BufferedReader(new FileReader(MATERIATXT))) {
            String linea;
            linea = lector.readLine();
            materiasStr.add(linea);
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[0]) != idmateria) {
                    materias.add(parseDatosToObjet(campos));
                    materiasStr.add(linea);
                } else {
                    materia = parseDatosToObjet(campos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo acceder a la base de datos");
        }

        if (materia != null) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(MATERIATXT))) {
                for (String materiaStr : materiasStr) {
                    escritor.write(materiaStr);
                    escritor.newLine();
                }
                return materia;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("No se pudo escribir en el archivo");
            }
        } else {
            return materia;
        }
    }

    public void modificarMateria(Materia materia) throws MateriaNoEncontradaException {
        List<String> nuevosDatos = new ArrayList<>();
        boolean materiaEncontrado = false;
    
        try (BufferedReader lector = new BufferedReader(new FileReader( MATERIATXT))) {
            String linea = lector.readLine(); 
            nuevosDatos.add(linea);         
            while ((linea = lector.readLine()) != null) {               
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[0]) == materia.getIdmateria()) {
                   materiaEncontrado = true;
                    campos[1] = materia.getNombre();
                    campos[2] = String.valueOf(materia.getProfesorid());
                    campos[3] = String.valueOf(materia.getAnio());
                    campos[4] = String.valueOf(materia.getCuatrimestre());
                    campos[5] = String.valueOf(materia.getCorrelatividades()); //pasa string hay que pasar lista corregir
                    nuevosDatos.add(String.join(",", campos));
                } else {
                    nuevosDatos.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        if (!materiaEncontrado) {
            throw new MateriaNoEncontradaException("Materia no encontrado con ID: " + materia.getIdmateria());
        }
    
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(MATERIATXT))) {
            for (String datos : nuevosDatos) {
                escritor.write(datos);
                escritor.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    


    public Materia mostrarMateria(long idmateria) {
        try (BufferedReader lector = new BufferedReader(new FileReader(MATERIATXT))) {
            String linea;
            linea = lector.readLine();
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[0]) == idmateria) {
                   Materia materia = new Materia();
                    materia.setIdmateria(Long.parseLong(campos[0]));
                    materia.setNombre(campos[1]);
                    materia.setProfesorid(Long.parseLong(campos[2]));
                    materia.setAnio(Long.parseLong(campos[3]));
                    materia.setCuatrimestre(Long.parseLong(campos[4]));

                // Conversión de correlatividades de String a List<Long>
                    List<Long> correlatividades = Arrays.stream(campos[5].split("-"))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
                    materia.setCorrelatividades(correlatividades);

                return materia;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Materia> mostrarTodasLasMaterias() {
        List<Materia> materias = new ArrayList<>();
        MateriaDao materiaDao = new MateriaDao();

        try (BufferedReader lector = new BufferedReader(new FileReader(MATERIATXT))) {
            String linea = lector.readLine();
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                materias.add(materiaDao.parseDatosToObjet(campos));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return materias;
    }

//obtener todas las materias que dicta un profesor en orden alfabetico*/
    public List<Materia> mostrarMateriasPorProfesor(long profesorid) {
        List<Materia> materias = new ArrayList<>();
        MateriaDao materiaDao = new MateriaDao();
    
        try (BufferedReader lector = new BufferedReader(new FileReader(MATERIATXT))) {
            String linea = lector.readLine();
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                Materia materia = materiaDao.parseDatosToObjet(campos);
                if (materia.getProfesorid() == profesorid) {
                    materias.add(materia);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Ordenar la lista de materias alfabéticamente por el nombre
        materias.sort(Comparator.comparing(Materia::getNombre, String.CASE_INSENSITIVE_ORDER));
    
        return materias;
    }
    
}
    


