package kz.telephonedirectory.repository;

import kz.telephonedirectory.entity.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends MongoRepository<Contact, String> {

    Contact findContactByPhoneNumber(String phoneNumber);
}
