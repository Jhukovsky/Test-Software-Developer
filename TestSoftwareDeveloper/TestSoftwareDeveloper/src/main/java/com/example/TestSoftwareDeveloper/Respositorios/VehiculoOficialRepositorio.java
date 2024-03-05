package com.example.TestSoftwareDeveloper.Respositorios;

import com.example.TestSoftwareDeveloper.Vehiculos.VehiculoOficial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoOficialRepositorio extends JpaRepository<VehiculoOficial,Long> {
}
