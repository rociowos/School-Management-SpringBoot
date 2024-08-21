/*package ar.edu.utn.frbb.tup.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frbb.tup.exceptions.ProfesorNoEncontradoException;
import ar.edu.utn.frbb.tup.model.Profesor;

public class ProfesorDao {

    private static final String PROFESORTXT = "FinalLabo3\\src\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\profesor.txt";


    public void crearProfesor(Profesor profesor) {
        boolean archivoNuevo = !(new File(PROFESORTXT).exists());
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(PROFESORTXT, true))) {
            if (archivoNuevo) {
                escritor.write("id,nombre,apellido,titulo,nombreMateria");
                escritor.newLine();
            }
            escritor.write(profesorToTxt(profesor));
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String profesorToTxt(Profesor profesor) {
        return  profesor.getId() + "," +
                profesor.getNombre() + "," +
                profesor.getApellido() + "," +
                profesor.getTitulo() + "," +
                profesor.getnombreMateria();

    }

    public Profesor findById(long id) {
        try (BufferedReader lector = new BufferedReader(new FileReader(PROFESORTXT))) {
            String linea;
            lector.readLine();
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (Long.parseLong(datos[0]) == id) {
                    Profesor profesor = new Profesor();
                    profesor.setId(Long.parseLong(datos[0]));
                    profesor.setNombre(datos[1]);
                    profesor.setApellido(datos[2]);
                    profesor.setTitulo(datos[3]);
                    profesor.setnombreMateria(datos[4]);
                    return profesor;
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo: " + ex.getMessage());
        }
        return null;
    }


    public Profesor parseDatosToObjet(String[] datos){
        Profesor profesor = new Profesor();
        profesor.setId(Long.parseLong(datos[0]));
        profesor.setNombre(datos[1]);
        profesor.setApellido(datos[2]);
        profesor.setTitulo(datos[3]);
        profesor.setnombreMateria(datos[4]);
        return profesor;
    }

    public Profesor borrarProfesor(long id) {
        List<Profesor> profesor = new ArrayList<>();
        List<String> profesorStr = new ArrayList<>();
        Profesor profesor = null;
        try (BufferedReader lector = new BufferedReader(new FileReader(PROFESORTXT))) {
            String linea;
            linea = lector.readLine();
            profesorStr.add(linea);
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[0]) != id) {
                    profesor.add(parseDatosToObjet(campos));
                    profesorStr.add(linea);
                } else {
                    profesor = parseDatosToObjet(campos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo acceder a la base de datos");
        }

        if (profesor != null) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(PROFESORTXT))) {
                for (String profesoresStr : profesorStr) {
                    escritor.write(profesorStr);
                    escritor.newLine();
                }
                return profesor;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("No se pudo escribir en el archivo");
            }
        } else {
            return profesor;
        }
    }

    public void modificarProfesor(Profesor profesor) throws ProfesorNoEncontradoException {
        List<String> nuevosDatos = new ArrayList<>();
        boolean profesorEncontrado = false;

        try (BufferedReader lector = new BufferedReader(new FileReader(PROFESORTXT))) {
            String linea = lector.readLine();
            nuevosDatos.add(linea);
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[0]) == profesor.getId()) {
                    profesorEncontrado = true;
                    campos[1] = profesor.getNombre();
                    campos[2] = profesor.getApellido();
                    campos[3] = profesor.getTitulo();
                    campos[4] = profesor.getnombreMateria();
                    nuevosDatos.add(String.join(",", campos));
                } else {
                    nuevosDatos.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!profesorEncontrado) {
            throw new ProfesorNoEncontradoException("Profesor no encontrado con ID: " + profesor.getId());
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(PROFESORTXT))) {
            for (String datos : nuevosDatos) {
                escritor.write(datos);
                escritor.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Profesor mostrarProfesor(long id) {
        try (BufferedReader lector = new BufferedReader(new FileReader(PROFESORTXT))) {
            String linea;
            linea = lector.readLine();
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[0]) == id) {
                    Profesor profesor = new Profesor();
                    profesor.setId(Long.parseLong(campos[0]));
                    profesor.setNombre(campos[1]);
                    profesor.setApellido(campos[2]);
                    profesor.setTitulo(campos[3]);
                    profesor.setnombreMateria(campos[4]);
                    return profesor;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Profesor> mostrarTodosLosProfesores() {
        List<Profesor> profesores = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(PROFESORTXT))) {
            String linea;
            linea = lector.readLine();
            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split(",");

                try {
                   Profesor profesor = new Profesor();
                    profesor.setId(Long.parseLong(datos[0]));
                    profesor.setNombre(datos[1]);
                    profesor.setApellido(datos[2]);
                    profesor.setTitulo(datos[3]);
                    profesor.setnombreMateria(datos[4]);
                    profesores.add(profesor);
            }
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo: " + ex.getMessage());
        }

        return profesores;
    }

    
}
}*/