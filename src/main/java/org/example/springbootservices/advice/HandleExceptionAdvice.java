package org.example.springbootservices.advice;

import org.example.springbootservices.dto.ErrorFieldDTO;
import org.example.springbootservices.dto.ErrorResponseDTO;
import org.example.springbootservices.exception.AdventurerException;
import org.example.springbootservices.exception.InvalidEnumValueException;
import org.example.springbootservices.exception.MissionException;
import org.example.springbootservices.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class HandleExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){

        Map<String, List<String>> errors = ex.getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        List<ErrorFieldDTO> errorList = errors.entrySet()
                .stream()
                .map(entry -> new ErrorFieldDTO(entry.getKey(), entry.getValue()))
                .toList();

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Invalid Request",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                errorList
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(InvalidEnumValueException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidEnumException(InvalidEnumValueException ex){
        ErrorFieldDTO fieldError = new ErrorFieldDTO(
                ex.getField(),
                List.of(ex.getMessage())
        );

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Invalid Request",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                List.of(fieldError)
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Resource Not Found",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                List.of(new ErrorFieldDTO("resource",List.of(ex.getMessage())))
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(AdventurerException.class)
    public ResponseEntity<ErrorResponseDTO> handleAdventurerException(AdventurerException ex){
        ErrorFieldDTO fieldError = new ErrorFieldDTO(
                "Adventurer",
                List.of(ex.getMessage())
        );
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Invalid Request",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                List.of(fieldError)
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MissionException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissionException(MissionException ex){
        ErrorFieldDTO fieldError = new ErrorFieldDTO(
                "Mission",
                List.of(ex.getMessage())
        );
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Invalid Request",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                List.of(fieldError)
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
