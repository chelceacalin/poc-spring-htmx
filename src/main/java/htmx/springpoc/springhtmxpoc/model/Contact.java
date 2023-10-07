package htmx.springpoc.springhtmxpoc.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nume;
    private String prenume;
    private LocalDate dataNasterii;

    @OneToMany
    @JoinColumn(name="contact_id")
    private List<Phone> phoneNumbers;
}
