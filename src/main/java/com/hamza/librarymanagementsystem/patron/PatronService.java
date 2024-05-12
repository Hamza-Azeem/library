package com.hamza.librarymanagementsystem.patron;

import com.hamza.librarymanagementsystem.exception.DuplicateRecordException;
import com.hamza.librarymanagementsystem.exception.RecordNotFoundException;
import com.hamza.librarymanagementsystem.exception.RecordNotModifiedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatronService {
    private final PatronDao patronDao;

    public PatronService(PatronDao patronDao) {
        this.patronDao = patronDao;
    }
    public List<Patron> findAllPatrons(){
       return patronDao.selectAllPatrons();
    }
    public Patron findPatronById(long id){
        return patronDao.selectPatronById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format("No patron was found with id=%s", id)));
    }
    public void addPatron(PatronRegistrationRequest patronRegistrationRequest){
        if(patronDao.patronExistsWithEmail(patronRegistrationRequest.email())){
            throw new DuplicateRecordException("Email is already taken.");
        }
        Patron newPatron = new Patron(
                patronRegistrationRequest.name(),
                patronRegistrationRequest.email(),
                patronRegistrationRequest.phoneNumber(),
                patronRegistrationRequest.address()
        );
        patronDao.addPatron(newPatron);
    }
    public void updatePatron(long id, PatronUpdateRequest patronUpdateRequest){
        Patron patron = findPatronById(id);
        boolean changed = false;
        if(patronUpdateRequest.name() != null && !patronUpdateRequest.name().equals(patron.getName())){
            patron.setName(patronUpdateRequest.name());
            changed = true;
        }
        if(patronUpdateRequest.email() != null && !patronUpdateRequest.email().equals(patron.getEmail())){
            if(patronDao.patronExistsWithEmail(patronUpdateRequest.email())){
                throw new DuplicateRecordException("Email is already taken.");
            }
            patron.setEmail(patronUpdateRequest.email());
            changed = true;
        }
        if(patronUpdateRequest.phoneNumber() != null && !patronUpdateRequest.phoneNumber().equals(patron.getPhoneNumber())){
            patron.setPhoneNumber(patronUpdateRequest.phoneNumber());
            changed = true;
        }
        if(patronUpdateRequest.address() != null && !patronUpdateRequest.address().equals(patron.getAddress())){
            patron.setAddress(patronUpdateRequest.address());
            changed = true;
        }
        if(!changed){
            throw new RecordNotModifiedException("No changes found!");
        }
        patronDao.updatePatron(patron);
    }
    public void deletePatronById(long id){
        Patron patron = findPatronById(id);
        patronDao.deletePatronById(id);
    }
}
