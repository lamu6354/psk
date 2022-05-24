package com.laura.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@Table(name = "CAR")
@NamedQueries({
        @NamedQuery(name = "Car.findAll", query = "select a from Car as a")
})
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Integer id;

    @Column(name = "CAR_MODEL")
    String carModel;

    @ManyToOne
    @JoinColumn(name = "AUTOSERVICE_ID")
    private Autoservice autoservice;

    @Column(name = "LICENSE_NUMBER")
    private Integer licenseNumber;

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;

    public Car(String carModel) {
        this.carModel = carModel;
    }

    public Car(String carModel, Autoservice autoservice) {
        this(carModel);
        this.autoservice = autoservice;
    }
}
