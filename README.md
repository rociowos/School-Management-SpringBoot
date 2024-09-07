Este proyecto es una implementación de un conjunto de APIs REST desarrolladas en Java utilizando Spring Boot. La aplicación sigue una arquitectura en capas y gestiona entidades relacionadas con un sistema académico, tales como **Alumno**, **Asignatura**, **Materia**, **Profesor**, y **Carrera**.

## Características Principales

- **Modelo de Entidades**: 
  - Alumno, Profesor, Materia, Asignatura, Carrera.
- **Servicios REST**:
  - Endpoints para la gestión de alumnos, profesores, materias, asignaturas, y carreras.
- **Validaciones**:
  - Validaciones para la correcta gestión de datos.
- **Persistencia de datos**:
  - Los datos son almacenados en archivos de texto para cada entidad.
- **Excepciones**:
  - Manejo de excepciones personalizadas para casos como entidades duplicadas o no encontradas.
- **Capas**:
  - Presentation (Controllers(Handlers), Dtos y Validators ), Service, Persistence (DAOs y database) y Models.
- **Testeos**:
  - Presentation(Controllers y Validator) y Service. 

## Documentacion de Postman:
**https://documenter.getpostman.com/view/24364633/2sAXjRW9bV**
