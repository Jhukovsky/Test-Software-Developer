package com.example.TestSoftwareDeveloper.Respositorios;

import com.example.TestSoftwareDeveloper.Vehiculos.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepositorio extends JpaRepository<Vehiculo,Long> {
    Vehiculo findByPlaca(String placa);
}
