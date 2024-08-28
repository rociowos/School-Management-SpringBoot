package ar.edu.utn.frbb.tup.model;

public enum EstadoAsignatura {
    NO_CURSADA("NO_CURSADA"),
    CURSADA("CURSADA"),
    APROBADA("APROBADA");

    private final String descripcion;


    EstadoAsignatura(String descripcion) {
        this.descripcion = descripcion;
    }

    public static EstadoAsignatura fromString(String text) throws IllegalArgumentException {
        for (EstadoAsignatura tipo : EstadoAsignatura.values()) {
            if (tipo.descripcion.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No se pudo encontrar un TipoMoneda con la descripción: " + text);
    }
}