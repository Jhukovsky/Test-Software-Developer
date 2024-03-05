package com.example.TestSoftwareDeveloper.Vehiculos;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehiculooficial")
@DiscriminatorValue("OFICIAL")
@EqualsAndHashCode(of = "id", callSuper = false)
public class VehiculoOficial extends Vehiculo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    private Date horaSalida;
}
