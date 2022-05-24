package com.laura.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@Table(name = "Mechanic")
@NamedQueries({
        @NamedQuery(name = "Mechanic.findAll", query = "select a from Mechanic as a"),
        @NamedQuery(name = "Mechanic.findByName", query = "select a from Mechanic as a where lower(a.name) = lower(:name)")
})
@EqualsAndHashCode
public class Mechanic implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Integer id;

    @Column(name = "NAME")
    String name;

    @Column
    @ManyToMany
    @JoinTable(
            name = "AUTOSERVICE_WORKED_AT",
            joinColumns = @JoinColumn(name = "MECHANIC_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTOSERVICE_ID")
    )
    List<Autoservice> autoservices;


    public Mechanic(String name) {
        this.name = name;
    }
}
