package com.hms.service.impl;

import com.hms.entity.EmergencyContact;
import com.hms.exception.ResourceNotFoundException;
import com.hms.repository.EmergencyContactRepository;
import com.hms.service.EmergencyContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EmergencyContactServiceImpl implements EmergencyContactService {

    private final EmergencyContactRepository emergencyContactRepository;

    @Override
    public EmergencyContact createContact(EmergencyContact contact) {
        return emergencyContactRepository.save(contact);
    }

    @Override
    public EmergencyContact updateContact(UUID id, EmergencyContact contact) {
        EmergencyContact existingContact = getContactById(id);
        contact.setId(id);
        return emergencyContactRepository.save(contact);
    }

    @Override
    public void deleteContact(UUID id) {
        EmergencyContact contact = getContactById(id);
        emergencyContactRepository.delete(contact);
    }

    @Override
    @Transactional(readOnly = true)
    public EmergencyContact getContactById(UUID id) {
        return emergencyContactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emergency contact not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmergencyContact> getAllContacts() {
        return emergencyContactRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmergencyContact> getContactsByDepartment(String department) {
        return emergencyContactRepository.findByDepartment(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmergencyContact> get24x7Contacts() {
        return emergencyContactRepository.findByIsAvailable24x7(true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmergencyContact> getContactsByPriorityLevel(Integer maxPriorityLevel) {
        return emergencyContactRepository.findByPriorityLevelLessThanEqual(maxPriorityLevel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllDepartments() {
        return emergencyContactRepository.findAllDepartments();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmergencyContact> searchContacts(String keyword) {
        return emergencyContactRepository.searchContacts(keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmergencyContact> getAllContactsSortedByPriority() {
        return emergencyContactRepository.findAllOrderByPriorityAndDepartment();
    }
}