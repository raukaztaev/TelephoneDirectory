package kz.telephonedirectory.service;

import kz.telephonedirectory.entity.Contact;
import kz.telephonedirectory.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {

    private final ContactRepository contactRepository;

    @Cacheable("contacts")
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @CacheEvict(value = "contacts", allEntries = true)
    public void createContact(Contact contact) {
        contactRepository.save(contact);
    }

    public Contact findContactByPhoneNumber(String phoneNumber) {
        return contactRepository.findContactByPhoneNumber(phoneNumber);
    }

}
