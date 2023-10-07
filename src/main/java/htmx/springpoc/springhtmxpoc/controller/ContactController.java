package htmx.springpoc.springhtmxpoc.controller;

import htmx.springpoc.springhtmxpoc.model.Contact;
import htmx.springpoc.springhtmxpoc.repository.ContactRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private final ContactRepository  contactRepository;

    @GetMapping
    public String contactList(Model model){
        model.addAttribute("contacts",contactRepository.findAll());
        return "contact-list";
    }


    @GetMapping("/edit-contact/{id}")
    public String editContactPage(@PathVariable(name="id") UUID id, Model model) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            System.out.println(optionalContact.get());
            Contact contact = optionalContact.get();
            model.addAttribute("contact", contact);
            return "edit-contact";
        } else {
            model.addAttribute("contactid",id);
            return "contact-not-found";
        }
    }


    @PostMapping("/update-contact")
    public String updateContact(@ModelAttribute Contact contact, Model model) {
        contactRepository.save(contact);
        model.addAttribute("contact",contact);
        System.out.println(contact);
        return "redirect:/edit-contact/"+ contact.getId();
    }


    @GetMapping("/add-contact")
    public String addContact(@ModelAttribute Contact contact, Model model) {
        model.addAttribute("contact",new Contact());
        return "/add-contact";
    }

    @PostMapping("/add-contact")
    public String saveContact(@ModelAttribute Contact contact, Model model) {
        contactRepository.save(contact);
        return "redirect:/";
    }
    @PostConstruct
    public void addTestContacts(){
        contactRepository.save(new Contact().setNume("Calin").setPrenume("Chelcea").setDataNasterii(LocalDate.now()));
        contactRepository.save(new Contact().setNume("Maria").setPrenume("Dutu").setDataNasterii(LocalDate.now()));
    }
}
