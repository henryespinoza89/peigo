package com.peigo.msvcaccounts.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;

    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "DNI", length = 9, nullable = false, updatable = false, unique = true)
    private String dni;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Account> account;

    public Client (Long id, String firstName, String lastName, String dni) {
        this.idClient = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
    }
}
