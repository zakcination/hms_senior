package com.hms.service;

import com.hms.entity.EmergencyContact;

import java.util.List;
import java.util.UUID;

public interface EmergencyContactService {
    EmergencyContact createContact(EmergencyContact contact);

    EmergencyContact updateContact(UUID id, EmergencyContact contact);

    void deleteContact(UUID id);

    EmergencyContact getContactById(UUID id);

    List<EmergencyContact> getAllContacts();

    List<EmergencyContact> getContactsByDepartment(String department);

    List<EmergencyContact> get24x7Contacts();

    List<EmergencyContact> getContactsByPriorityLevel(Integer maxPriorityLevel);

    List<String> getAllDepartments();

    List<EmergencyContact> searchContacts(String keyword);

    List<EmergencyContact> getAllContactsSortedByPriority();
}