public class AlumnoDao {

    private static final String ALUMNOTXT = "src\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\data_base\\alumno.txt";


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

    public Alumno findById(long id) {
        try (BufferedReader lector = new BufferedReader(new FileReader(ALUMNOTXT))) {
            String linea;
            lector.readLine();
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (Long.parseLong(datos[0]) == id) {
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

    public Alumno borrarAlumno(long id) {
        List<Alumno> alumno = new ArrayList<>();
        List<String> alumnoStr = new ArrayList<>();
        Alumno alumno = null;
        try (BufferedReader lector = new BufferedReader(new FileReader(ALUMNOTXT))) {
            String linea;
            linea = lector.readLine();
            alumnoStr.add(linea);
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[0]) != id) {
                    alumno.add(parseDatosToObjet(campos));
                    alumnoStr.add(linea);
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
                for (String alumnoStr : alumnoStr) {
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
            nuevosDatos.add(linea);
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[0]) == alumno.getId()) {
                    alumnoEncontrado = true;
                    campos[1] = alumno.getDni();
                    campos[2] = alumno.getNombre();
                    campos[3] = alumno.getApellido();
                    nuevosDatos.add(String.join(",", campos));
                } else {
                    nuevosDatos.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!alumnoEncontrado) {
            throw new AlumnoNoEncontradoException("Alumno no encontrado con ID: " + alumno.getId());
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ALUMNOTXT))) {
            for (String datos : nuevosDatos) {
                escritor.write(datos);
                escritor.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    public List<Alumno> mostrarTodosLosAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(ALUMNOTXT))) {
            String linea;
            linea = lector.readLine();
            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split(",");

                try {
                    Alumno alumno = new Alumno();
                    alumno.setId(Long.parseLong(campos[0]));
                    alumno.setDni(Long.parseLong(campos[1]));
                    alumno.setNombre(campos[2]);
                    alumno.setApellido(campos[3]);
                    alumnos.add(alumno);
            }
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo: " + ex.getMessage());
        }

        return alumnos;
    }

}