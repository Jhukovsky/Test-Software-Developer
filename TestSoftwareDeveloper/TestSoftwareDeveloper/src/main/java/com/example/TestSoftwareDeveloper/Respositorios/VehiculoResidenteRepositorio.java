package com.example.TestSoftwareDeveloper.Respositorios;

import com.example.TestSoftwareDeveloper.Vehiculos.VehiculoOficial;
import com.example.TestSoftwareDeveloper.Vehiculos.VehiculoResidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoResidenteRepositorio extends JpaRepository<VehiculoResidente,Long> {
}
