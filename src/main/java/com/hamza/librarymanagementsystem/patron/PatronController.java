package com.hamza.librarymanagementsystem.patron;

import com.hamza.librarymanagementsystem.exception.Error;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {
    private final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }
    @GetMapping
    private ResponseEntity<?> findAllPatrons(){
        return ResponseEntity.ok(patronService.findAllPatrons());
    }
    @GetMapping("/{id}")
    private ResponseEntity<?> findPatronById(@PathVariable long id){
        return ResponseEntity.ok(patronService.findPatronById(id));
    }
    @PostMapping
    private ResponseEntity<?> savePatron(@RequestBody @Valid PatronRegistrationRequest patronRegistrationRequest){
        patronService.addPatron(patronRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("{}");
    }
    @PutMapping("/{id}")
    private ResponseEntity<?> updatePatron(@PathVariable long id, @RequestBody PatronUpdateRequest patronUpdateRequest){
        patronService.updatePatron(id, patronUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body("{}");
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<?> deletePatronById(@PathVariable long id){
        patronService.deletePatronById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{}");
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Error> validationHandler(MethodArgumentNotValidException exception){
//        List<String> errors = new ArrayList<>();
//        if(exception.getErrorCount() > 0){
//            for(ObjectError error: exception.getBindingResult().getAllErrors()){
//                errors.add(error.getDefaultMessage());
//            }
//        }
//        Error error = new Error(
//                errors.get(0),
//                LocalDateTime.now(),
//                HttpStatus.BAD_REQUEST
//        );
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }
}
