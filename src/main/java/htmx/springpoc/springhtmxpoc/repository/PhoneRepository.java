package htmx.springpoc.springhtmxpoc.repository;

import htmx.springpoc.springhtmxpoc.model.Contact;
import htmx.springpoc.springhtmxpoc.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, UUID> {
}
