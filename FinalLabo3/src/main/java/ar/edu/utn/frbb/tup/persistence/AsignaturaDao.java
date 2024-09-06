package ar.edu.utn.frbb.tup.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.utn.frbb.tup.model.Asignatura;
import ar.edu.utn.frbb.tup.model.EstadoAsignatura;

@Repository
public class AsignaturaDao {

    private static final String ASIGNATURATXT = "FinalLabo3\\src\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\asignatura.txt";


    public void crearAsignatura(Asignatura asignatura) {
        boolean archivoNuevo = !(new File(ASIGNATURATXT).exists());
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ASIGNATURATXT, true))) {
            if (archivoNuevo) {
                escritor.write("asignaturaId,idmateria,estadoAsignatura,nota,alumnoid ");
                escritor.newLine();
            }
            escritor.write(asignaturaToTxt(asignatura));
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String asignaturaToTxt(Asignatura asignatura) {
        return  asignatura.getAsignaturaId() + "," +
                asignatura.getIdmateria() + "," +
                asignatura.getEstadoAsignatura() + "," +
                asignatura.getNota() + "," + 
                asignatura.getAlumnoid();

    }

    public Asignatura BuscarAsignaturaId(long asignaturaId) {
        try (BufferedReader lector = new BufferedReader(new FileReader(ASIGNATURATXT))) {
            String linea;
            lector.readLine(); 
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (Long.parseLong(datos[0]) == asignaturaId) {
                    return parseDatosToObjet(datos);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return null;
    }
    

    public Asignatura parseDatosToObjet(String[] datos){
        Asignatura asignatura = new Asignatura();
        asignatura.setAsignaturaId(Long.parseLong(datos[0]));
        asignatura.setIdmateria(Long.parseLong(datos[1]));
        asignatura.setEstadoAsignatura(EstadoAsignatura.valueOf(datos[2]));
        asignatura.setNota(Integer.parseInt(datos[3]));
        asignatura.setAlumnoid(Long.parseLong(datos[4]));
        return asignatura;
    }
 
    public Asignatura borrarAsignatura(long asignaturaId) {
        List<Asignatura> asignaturas = new ArrayList<>();
        List<String> asignaturasStr = new ArrayList<>();
        Asignatura asignatura = null;
        try (BufferedReader lector = new BufferedReader(new FileReader(ASIGNATURATXT))) {
            String linea;
            linea = lector.readLine();
            asignaturasStr.add(linea);
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[0]) != asignaturaId) {
                    asignaturas.add(parseDatosToObjet(campos));
                    asignaturasStr.add(linea);
                } else {
                    asignatura = parseDatosToObjet(campos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo acceder a la base de datos");
        }

        if (asignatura != null) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ASIGNATURATXT))) {
                for (String asignaturaStr : asignaturasStr) {
                    escritor.write(asignaturaStr);
                    escritor.newLine();
                }
                return asignatura;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("No se pudo escribir en el archivo");
            }
        } else {
            return asignatura;
        }
    }

    
    
//filtra con el id del alumno
    public Asignatura mostrarAsignatura(long asignaturaId, long alumnoId) {
        try (BufferedReader lector = new BufferedReader(new FileReader(ASIGNATURATXT))) {
            String linea;
            linea = lector.readLine(); 
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                
                if (Long.parseLong(campos[0]) == asignaturaId && Long.parseLong(campos[4]) == alumnoId) {
                    Asignatura asignatura = new Asignatura();
                    asignatura.setAsignaturaId(Long.parseLong(campos[0]));
                    asignatura.setIdmateria(Long.parseLong(campos[1]));
                    asignatura.setEstadoAsignatura(EstadoAsignatura.valueOf(campos[2].trim().toUpperCase())); // Convertir String a Enum
                    asignatura.setNota(Integer.parseInt(campos[3]));
                    asignatura.setAlumnoid(Long.parseLong(campos[4]));
                    return asignatura;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    


    
}

    

