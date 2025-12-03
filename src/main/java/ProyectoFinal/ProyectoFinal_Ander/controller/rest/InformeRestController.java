package ProyectoFinal.ProyectoFinal_Ander.controller.rest;

import ProyectoFinal.ProyectoFinal_Ander.dto.ApiResponse;
import ProyectoFinal.ProyectoFinal_Ander.service.InformeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/informes")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class InformeRestController {

    @Autowired
    private InformeService informeService;

    @GetMapping("/ingresos")
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<Map<String, Object>>> informeIngresos(
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        
        try {
            LocalDate inicio = fechaInicio != null && !fechaInicio.isEmpty() 
                    ? LocalDate.parse(fechaInicio) 
                    : LocalDate.now().withDayOfMonth(1);
            LocalDate fin = fechaFin != null && !fechaFin.isEmpty() 
                    ? LocalDate.parse(fechaFin) 
                    : LocalDate.now();
            
            Map<String, Object> informe = informeService.generarInformeIngresos(inicio, fin);
            return ResponseEntity.ok(ApiResponse.success("Informe de ingresos generado exitosamente", informe));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al generar informe de ingresos: " + e.getMessage()));
        }
    }

    @GetMapping("/servicios")
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<Map<String, Object>>> informeServicios() {
        try {
            Map<String, Object> informe = informeService.generarInformeServicios();
            return ResponseEntity.ok(ApiResponse.success("Informe de servicios generado exitosamente", informe));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al generar informe de servicios: " + e.getMessage()));
        }
    }

    @GetMapping("/cursos")
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<Map<String, Object>>> informeCursos() {
        try {
            Map<String, Object> informe = informeService.generarInformeCursos();
            return ResponseEntity.ok(ApiResponse.success("Informe de cursos generado exitosamente", informe));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al generar informe de cursos: " + e.getMessage()));
        }
    }

    @GetMapping("/frecuencia-contrataciones")
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<Map<String, Object>>> frecuenciaContrataciones(
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        
        try {
            LocalDate inicio = fechaInicio != null && !fechaInicio.isEmpty() 
                    ? LocalDate.parse(fechaInicio) 
                    : LocalDate.now().minusMonths(6);
            LocalDate fin = fechaFin != null && !fechaFin.isEmpty() 
                    ? LocalDate.parse(fechaFin) 
                    : LocalDate.now();
            
            Map<String, Object> informe = informeService.generarFrecuenciaContrataciones(inicio, fin);
            return ResponseEntity.ok(ApiResponse.success("Informe de frecuencia generado exitosamente", informe));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al generar informe de frecuencia: " + e.getMessage()));
        }
    }
}



