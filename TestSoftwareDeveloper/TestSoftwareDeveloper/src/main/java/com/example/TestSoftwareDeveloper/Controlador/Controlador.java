package com.example.TestSoftwareDeveloper.Controlador;

import com.example.TestSoftwareDeveloper.Respositorios.VehiculoOficialRepositorio;
import com.example.TestSoftwareDeveloper.Respositorios.VehiculoRepositorio;
import com.example.TestSoftwareDeveloper.Respositorios.VehiculoResidenteRepositorio;
import com.example.TestSoftwareDeveloper.Utilidades.Utilidades;
import com.example.TestSoftwareDeveloper.Vehiculos.Vehiculo;
import com.example.TestSoftwareDeveloper.Vehiculos.VehiculoOficial;
import com.example.TestSoftwareDeveloper.Vehiculos.VehiculoResidente;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("estacionamiento")
@AllArgsConstructor
public class Controlador {

    VehiculoOficialRepositorio vehiculoOficialRepositorio;
    VehiculoResidenteRepositorio vehiculoResidenteRepositorio;
    VehiculoRepositorio vehiculoRepositorio;
    Utilidades utilidades;


    @PostMapping("/altaOficial")
    public ResponseEntity<String> altaOficial(@RequestParam String placa){
        VehiculoOficial vehiculoOficial = new VehiculoOficial();
        vehiculoOficial.setPlaca(placa);
        vehiculoOficialRepositorio.save(vehiculoOficial);

        return ResponseEntity.ok(vehiculoOficial.getPlaca());
    }

    @PostMapping("/altaResidente")
    public ResponseEntity<String> altaResidente(@RequestParam String placa){
        VehiculoResidente vehiculoResidente = new VehiculoResidente();
        vehiculoResidente.setPlaca(placa);
        vehiculoResidenteRepositorio.save(vehiculoResidente);

        return ResponseEntity.ok(vehiculoResidente.getPlaca());
    }

    @PutMapping("/registrarEntrada")
    public ResponseEntity<String> registrarEntrada(@RequestParam String placa, @RequestParam String horaEntrada){
        Vehiculo vehiculo = vehiculoRepositorio.findByPlaca(placa);
        Date date = utilidades.conversionStringDate(horaEntrada);

        if (vehiculo != null) {
            vehiculo.setHoraEntrada(date);

        } else {
            System.out.println("No se encontró un vehículo con esa placa");
            vehiculo = new Vehiculo();
            vehiculo.setPlaca(placa);
            vehiculo.setHoraEntrada(date);
        }

        vehiculoRepositorio.save(vehiculo);

        return ResponseEntity.ok("La hora se ha registrado correctamente la hora de entrada: ".concat(vehiculo.getHoraEntrada().toString()));
    }

    @PutMapping("/registrarSalida")
    public ResponseEntity<String> registrarSalida(@RequestParam String placa, @RequestParam String horaSalida){
        Vehiculo vehiculo = vehiculoRepositorio.findByPlaca(placa);
        Date date = utilidades.conversionStringDate(horaSalida);

        String mensaje = "Los datos se actualizaron correctamente";

        if (vehiculo != null) {
            if(vehiculo.getClass().equals(VehiculoOficial.class)){
                VehiculoOficial vehiculoOficial = (VehiculoOficial) vehiculo;
                vehiculoOficial.setHoraSalida(date);
                vehiculoOficialRepositorio.save(vehiculoOficial);
            }else if(vehiculo.getClass().equals(VehiculoResidente.class)){
                VehiculoResidente vehiculoResidente = (VehiculoResidente) vehiculo;
                long minutosTranscurridos = (date.getTime() - vehiculoResidente.getHoraEntrada().getTime())/60000;
                vehiculoResidente.setMinutosAcumulados(vehiculoResidente.getMinutosAcumulados()+minutosTranscurridos);
                vehiculoResidenteRepositorio.save(vehiculoResidente);
            }else{
                long minutosTranscurridos = (date.getTime() - vehiculo.getHoraEntrada().getTime())/60000;
                mensaje = String.valueOf(minutosTranscurridos*.5);
            }

        } else {
            System.out.println("No se encontró un vehículo con esa placa");
            ResponseEntity.notFound();
        }

        return ResponseEntity.ok(mensaje);
    }

    @DeleteMapping("/comienzaMes")
    public ResponseEntity<String> comienzaMes(){
        List<VehiculoOficial> vehiculoOficialList = vehiculoOficialRepositorio.findAll();
        vehiculoOficialList.forEach(vehiculoOficial -> {
            vehiculoOficial.setHoraEntrada(null);
            vehiculoOficial.setHoraSalida(null);
        });

        vehiculoOficialRepositorio.saveAll(vehiculoOficialList);

        List<VehiculoResidente> vehiculoResidenteList = vehiculoResidenteRepositorio.findAll();
        vehiculoResidenteList.forEach(vehiculoResidente -> {
            vehiculoResidente.setMinutosAcumulados(0);
            vehiculoResidente.setHoraEntrada(null);
        });

        vehiculoResidenteRepositorio.saveAll(vehiculoResidenteList);

        return ResponseEntity.ok("Los registros de han reiniciado.");
    }

    @GetMapping("/pagosResidentes")
    public ResponseEntity<String> pagosResidentes(){
        List<VehiculoResidente> vehiculoResidenteList = vehiculoResidenteRepositorio.findAll();
        StringBuilder plantilla = new StringBuilder("*Informe de vehiculos residentes*\n\n" +
                "Num. placa \t Tiempo estacionado(min.) \t Cantidad a pagar\n");
        for (VehiculoResidente vehiculoResidente : vehiculoResidenteList) {
            plantilla.append(vehiculoResidente.getPlaca()).append("\t\t\t").append(vehiculoResidente.getMinutosAcumulados()).append("\t\t\t").append(vehiculoResidente.getMinutosAcumulados() * 0.05).append("\t\t\t\t\t\t\n");
        }

        File archivo = new File("C:\\Users\\jcrp_\\Downloads\\informe_vehiculos_residentes.txt");
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write(String.valueOf(plantilla));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("El archivo a sido guardado");
    }
}
