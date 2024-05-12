package com.hamza.librarymanagementsystem.patron;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private ResponseEntity<?> savePatron(@RequestBody PatronRegistrationRequest patronRegistrationRequest){
        patronService.addPatron(patronRegistrationRequest);
        return new ResponseEntity<>(Void.class, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    private ResponseEntity<?> updatePatron(@PathVariable long id, @RequestBody PatronUpdateRequest patronUpdateRequest){
        patronService.updatePatron(id, patronUpdateRequest);
        return new ResponseEntity<>(Void.class, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<?> deletePatronById(@PathVariable long id){
        patronService.deletePatronById(id);
        return new ResponseEntity<>(Void.class, HttpStatus.NO_CONTENT);
    }
}
