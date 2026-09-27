package org.cjvaldi.inventory.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.cjvaldi.inventory.api.dto.ErrorRespuestaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de entidad no encontrada (HTTP 404)
    @ExceptionHandler(RecursoNoEncontradoExcepcion.class)
    public ResponseEntity manejarRecursoNoEncontrado(
            RecursoNoEncontradoExcepcion ex,
            HttpServletRequest request) {

        ErrorRespuestaDTO error = new ErrorRespuestaDTO(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Manejo de validaciones fallidas de Jakarta Bean Validation (HTTP 400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity manejarValidaciones(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map erroresPorCampo = new HashMap<>();
        for (FieldError campoError : ex.getBindingResult().getFieldErrors()) {
            erroresPorCampo.put(campoError.getField(), campoError.getDefaultMessage());
        }

        ErrorRespuestaDTO error = new ErrorRespuestaDTO(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Error en las validaciones de los campos",
                request.getRequestURI(),
                erroresPorCampo
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Fallback general para errores imprevistos (HTTP 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity manejarErrorGeneral(
            Exception ex,
            HttpServletRequest request) {

        ErrorRespuestaDTO error = new ErrorRespuestaDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Ha ocurrido un error interno en el servidor",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}