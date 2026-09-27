package org.cjvaldi.inventory.api.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorRespuestaDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String mensaje,
        String ruta,
        Map validaciones
) {
    // Constructor de conveniencia cuando no hay mapa de validaciones detalladas
    public ErrorRespuestaDTO(int status, String error, String mensaje, String ruta) {
        this(LocalDateTime.now(), status, error, mensaje, ruta, null);
    }

    public ErrorRespuestaDTO(int status, String error, String mensaje, String ruta, Map validaciones) {
        this(LocalDateTime.now(), status, error, mensaje, ruta, validaciones);
    }
}