package com.example.TestSoftwareDeveloper.Vehiculos;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehiculoresidente")
@DiscriminatorValue("RESIDENTE")
@EqualsAndHashCode(of = "id")
public class VehiculoResidente extends Vehiculo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    private long minutosAcumulados;
}
