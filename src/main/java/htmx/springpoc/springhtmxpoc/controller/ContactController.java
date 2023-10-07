package htmx.springpoc.springhtmxpoc.controller;

import htmx.springpoc.springhtmxpoc.model.Contact;
import htmx.springpoc.springhtmxpoc.model.Phone;
import htmx.springpoc.springhtmxpoc.repository.ContactRepository;
import htmx.springpoc.springhtmxpoc.repository.PhoneRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private final ContactRepository contactRepository;
    private final PhoneRepository phoneRepository;

    @GetMapping
    public String contactList(Model model) {
        model.addAttribute("contacts", contactRepository.findAll());
        return "contact-list";
    }

    @GetMapping("/edit-contact/{id}")
    public String editContactPage(@PathVariable(name = "id") UUID id, Model model) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();
            model.addAttribute("contact", contact);
            return "edit-contact";
        } else {
            model.addAttribute("contactid", id);
            return "contact-not-found";
        }
    }


    @PostMapping("/update-contact")
    public String updateContact(HttpServletRequest request, Model model) {
        var contactID = request.getParameterMap().get("id")[0];
        var nume = request.getParameterMap().get("nume")[0];
        var prenume = request.getParameterMap().get("prenume")[0];
        var dataNasterii = request.getParameterMap().get("dataNasterii")[0];
        var phone = request.getParameterMap().get("phone")[0];
        var phoneID = request.getParameterMap().get("phone_id")[0];
        var phoneObject = phoneRepository.findById(UUID.fromString(phoneID)).get();
        phoneObject.setNumber(phone);

        phoneRepository.save(phoneObject);
        Contact contact = contactRepository.findById(UUID.fromString(contactID)).get();
        contactRepository.save(contact);

        model.addAttribute("contact", contact);
        return "redirect:/";
    }

    @GetMapping("/add-contact")
    public String addContact(@ModelAttribute Contact contact, Model model) {
        System.out.println(contact);
        model.addAttribute("contact", new Contact());
        return "/add-contact";
    }

//    @PostMapping("/add-contact")
//    public String saveContact(@ModelAttribute Contact contact, Model model) {
//        contactRepository.save(contact);
//        return "redirect:/";
//    }

    @PostMapping("/add-contact")
    public String saveContact(HttpServletRequest request, Model model) {

        var nume = request.getParameterMap().get("nume")[0];
        var prenume = request.getParameterMap().get("prenume")[0];
        var dataNasterii = request.getParameterMap().get("dataNasterii")[0];
        var phone = request.getParameterMap().get("phone")[0];
//        System.out.println(Arrays.toString(nume) +" "+ Arrays.toString(prenume) +" "+ Arrays.toString(dataNasterii));
//        System.out.println(nume[0]+" "+prenume[0]+" "+dataNasterii[0]);
        Contact contact = contactRepository.save(new Contact().setNume(nume).setPrenume(prenume).setDataNasterii(LocalDate.parse(dataNasterii)));
        phoneRepository.save(new Phone().setNumber(phone).setContact(contact));

        return "redirect:/";
    }

    @PostConstruct
    public void addTestContacts() {
        contactRepository.save(new Contact().setNume("Calin").setPrenume("Chelcea").setDataNasterii(LocalDate.now()));
        contactRepository.save(new Contact().setNume("Maria").setPrenume("Dutu").setDataNasterii(LocalDate.now()));
    }
}
