package ar.edu.utn.frbb.tup.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.utn.frbb.tup.model.Carrera;

@Repository
public class CarreraDao {

    private static final String CARRERATXT = "FinalLabo3\\src\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\carrera.txt";


    public void crearCarrera(Carrera carrera) {
        boolean archivoNuevo = !(new File(CARRERATXT).exists());
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(CARRERATXT, true))) {
            if (archivoNuevo) {
                escritor.write("nombre,cantanios ");
                escritor.newLine();
            }
            escritor.write(carreraToTxt(carrera));
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String carreraToTxt(Carrera carrera) {
        return  carrera.getNombre() + "," +
                carrera.getCantanios();
                
    }

    public Carrera findByCarrera(String nombre) {
        try (BufferedReader lector = new BufferedReader(new FileReader(CARRERATXT))) {
            String linea;
            lector.readLine();
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos[0].equals(nombre)) { 
                    Carrera carrera = new Carrera();
                    carrera.setNombre(datos[0]);
                    carrera.setCantanios(Integer.parseInt(datos[1]));
                    return carrera;
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo: " + ex.getMessage());
        }
        return null;
    }


    public Carrera parseDatosToObjet(String[] datos){
        Carrera carrera = new Carrera();
        carrera.setNombre(datos[0]);
        carrera.setCantanios(Integer.parseInt(datos[1]));
        return carrera;
    }

    public Carrera borrarCarrera(String nombre) {
        List<Carrera> carreras = new ArrayList<>();
        List<String> carrerasStr = new ArrayList<>();
        Carrera carrera = null;
        try (BufferedReader lector = new BufferedReader(new FileReader(CARRERATXT))) {
            String linea;
            linea = lector.readLine();
            carrerasStr.add(linea);
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (!campos[0].equals(nombre)){
                    carreras.add(parseDatosToObjet(campos));
                    carrerasStr.add(linea);
                } else {
                   carrera = parseDatosToObjet(campos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo acceder a la base de datos");
        }

        if (carrera != null) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(CARRERATXT))) {
                for (String carreraStr : carrerasStr) {
                    escritor.write(carreraStr);
                    escritor.newLine();
                }
                return carrera;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("No se pudo escribir en el archivo");
            }
        } else {
            return carrera;
        }
    }


    public Carrera mostrarCarrera(String nombre) {
        try (BufferedReader lector = new BufferedReader(new FileReader(CARRERATXT))) {
            String linea;
            linea = lector.readLine();
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos[0].equals(nombre)){ 
                    Carrera carrera = new Carrera();
                    carrera.setNombre(campos[0]);
                    carrera.setCantanios(Integer.parseInt(campos[1]));
                    return carrera;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Carrera> mostrarTodasLasCarreras() {
        List<Carrera> carreras = new ArrayList<>();
    
        try (BufferedReader lector = new BufferedReader(new FileReader(CARRERATXT))) {
            String linea;
            linea = lector.readLine();
            while ((linea = lector.readLine()) != null) {
                
                String[] datos = linea.split(",");
    
                try {
                    Carrera carrera = new Carrera();
                    carrera.setNombre(datos[0]);
                    carrera.setCantanios(Integer.parseInt(datos[1]));
                    carreras.add(carrera);
    
                } catch (DateTimeParseException e) {
                    System.err.println("Error al parsear la fecha en la línea: " + linea);
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo: " + ex.getMessage());
        }
    
        return carreras;
    }
    
}
