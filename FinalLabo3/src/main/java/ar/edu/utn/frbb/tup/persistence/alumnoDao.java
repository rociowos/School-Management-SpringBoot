package ar.edu.utn.frbb.tup.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.utn.frbb.tup.exceptions.AlumnoNoEncontradoException;
import ar.edu.utn.frbb.tup.model.Alumno;

@Repository
public class AlumnoDao {

    private static final String ALUMNOTXT = "FinalLabo3\\src\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\alumno.txt";


    public void crearAlumno(Alumno alumno) {
        boolean archivoNuevo = !(new File(ALUMNOTXT).exists());
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ALUMNOTXT, true))) {
            if (archivoNuevo) {
                escritor.write("id,dni,nombre,apellido");
                escritor.newLine();
            }
            escritor.write(alumnoToTxt(alumno));
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String alumnoToTxt(Alumno alumno) {
        return  alumno.getId() + "," +
                alumno.getDni() + "," +
                alumno.getNombre() + "," +
                alumno.getApellido();

    }

    public Alumno findByDni(long dni) {
        try (BufferedReader lector = new BufferedReader(new FileReader(ALUMNOTXT))) {
            String linea;
            lector.readLine();
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (Long.parseLong(datos[1]) == dni) {
                    Alumno alumno = new Alumno();
                    alumno.setId(Long.parseLong(datos[0]));
                    alumno.setDni(Long.parseLong(datos[1]));
                    alumno.setNombre(datos[2]);
                    alumno.setApellido(datos[3]);
                    return alumno;
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo: " + ex.getMessage());
        }
        return null;
    }


    public Alumno parseDatosToObjet(String[] datos){
        Alumno alumno = new Alumno();
        alumno.setId(Long.parseLong(datos[0]));
        alumno.setDni(Long.parseLong(datos[1]));
        alumno.setNombre(datos[2]);
        alumno.setApellido(datos[3]);
        return alumno;
    }

    public Alumno borrarAlumno(long dni) {
        List<Alumno> alumnos = new ArrayList<>();
        List<String> alumnosStr = new ArrayList<>();
        Alumno alumno = null;
        try (BufferedReader lector = new BufferedReader(new FileReader(ALUMNOTXT))) {
            String linea;
            linea = lector.readLine();
            alumnosStr.add(linea);
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[1]) != dni) {
                    alumnos.add(parseDatosToObjet(campos));
                    alumnosStr.add(linea);
                } else {
                    alumno = parseDatosToObjet(campos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo acceder a la base de datos");
        }

        if (alumno != null) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ALUMNOTXT))) {
                for (String alumnoStr : alumnosStr) {
                    escritor.write(alumnoStr);
                    escritor.newLine();
                }
                return alumno;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("No se pudo escribir en el archivo");
            }
        } else {
            return alumno;
        }
    }

    public void modificarAlumno(Alumno alumno) throws AlumnoNoEncontradoException {
        List<String> nuevosDatos = new ArrayList<>();
        boolean alumnoEncontrado = false;
    
        try (BufferedReader lector = new BufferedReader(new FileReader(ALUMNOTXT))) {
            String linea = lector.readLine();
            // Agregar la cabecera (primera línea) si existe
            if (linea != null) {
                nuevosDatos.add(linea);
            }
            
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                
                // Asegúrate de que hay suficientes campos antes de intentar acceder a ellos
                if (Long.parseLong(campos[1]) == alumno.getDni()) {
                    alumnoEncontrado = true;
                    campos[1] = String.valueOf(alumno.getDni());
                    campos[2] = alumno.getNombre();
                    campos[3] = alumno.getApellido();
                    nuevosDatos.add(String.join(",", campos));
                } else {
                    nuevosDatos.add(linea);
                }
            }
            
            if (!alumnoEncontrado) {
                throw new AlumnoNoEncontradoException("El alumno con DNI " + alumno.getDni() + " no fue encontrado.");
            }
        } catch (IOException e) {
            e.printStackTrace(); // Maneja la excepción de I/O
        }
    
        // Aquí debes escribir los nuevos datos de vuelta al archivo
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ALUMNOTXT))) {
            for (String nuevoDato : nuevosDatos) {
                escritor.write(nuevoDato);
                escritor.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Maneja la excepción de I/O
        }
    }


    public Alumno mostrarAlumno(long id) {
        try (BufferedReader lector = new BufferedReader(new FileReader(ALUMNOTXT))) {
            String linea;
            linea = lector.readLine();
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[0]) == id) {
                    Alumno alumno = new Alumno();
                    alumno.setId(Long.parseLong(campos[0]));
                    alumno.setDni(Long.parseLong(campos[1]));
                    alumno.setNombre(campos[2]);
                    alumno.setApellido(campos[3]);
                    return alumno;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Alumno> mostrarTodosLosAlumnos() throws FileNotFoundException, IOException {
        List<Alumno> alumnos = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(ALUMNOTXT))) {
            String linea;
            linea = lector.readLine();
            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split(",");

                Alumno alumno = new Alumno();
                alumno.setId(Long.parseLong(datos[0]));
                alumno.setDni(Long.parseLong(datos[1]));
                alumno.setNombre(datos[2]);
                alumno.setApellido(datos[3]);
                alumnos.add(alumno);

        return alumnos;
    }


}
        return alumnos;
}
}