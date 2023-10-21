package apiCRUD.apiSkinVideogame.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(FileReadException.class)
    public ResponseEntity<String> handleFileReadException(FileReadException ex) {
        // You can customize the response as needed
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while reading the file: " + ex.getMessage());
    }

    @ExceptionHandler(SkinNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String skinNotFoundHandler(SkinNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(SkinNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IOException.class)
    String IOException(Exception e) {
        return e.getMessage();
    }

}




