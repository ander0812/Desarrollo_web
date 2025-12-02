package ProyectoFinal.ProyectoFinal_Ander.controller.rest;

import ProyectoFinal.ProyectoFinal_Ander.dto.ApiResponse;
import ProyectoFinal.ProyectoFinal_Ander.model.ProgramaEntrenamiento;
import ProyectoFinal.ProyectoFinal_Ander.service.ProgramaEntrenamientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/programas")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ProgramaEntrenamientoRestController {

    @Autowired
    private ProgramaEntrenamientoService programaService;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<ProgramaEntrenamiento>>> listar(
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) String instructor,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            @RequestParam(required = false) String costoMin,
            @RequestParam(required = false) String costoMax) {
        
        try {
            List<ProgramaEntrenamiento> programas;
            
            if (busqueda != null && !busqueda.isEmpty()) {
                programas = programaService.buscarPorTexto(busqueda);
            } else {
                LocalDate inicio = null;
                LocalDate fin = null;
                BigDecimal min = null;
                BigDecimal max = null;
                
                if (fechaInicio != null && !fechaInicio.isEmpty()) {
                    try {
                        inicio = LocalDate.parse(fechaInicio);
                    } catch (Exception e) {
                    }
                }
                
                if (fechaFin != null && !fechaFin.isEmpty()) {
                    try {
                        fin = LocalDate.parse(fechaFin);
                    } catch (Exception e) {
                    }
                }
                
                if (costoMin != null && !costoMin.isEmpty()) {
                    try {
                        min = new BigDecimal(costoMin);
                    } catch (NumberFormatException e) {
                    }
                }
                
                if (costoMax != null && !costoMax.isEmpty()) {
                    try {
                        max = new BigDecimal(costoMax);
                    } catch (NumberFormatException e) {
                    }
                }
                
                programas = programaService.buscarConFiltros(instructor, inicio, fin, min, max);
            }
            
            return ResponseEntity.ok(ApiResponse.success("Programas obtenidos exitosamente", programas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener programas: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<ProgramaEntrenamiento>> obtenerPorId(@PathVariable Long id) {
        try {
            Optional<ProgramaEntrenamiento> programaOpt = programaService.buscarPorId(id);
            
            if (programaOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Programa no encontrado"));
            }
            
            return ResponseEntity.ok(ApiResponse.success("Programa obtenido exitosamente", programaOpt.get()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener programa: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProgramaEntrenamiento>> crear(
            @Valid @RequestBody ProgramaEntrenamiento programa,
            BindingResult result) {
        
        try {
            if (result.hasErrors()) {
                StringBuilder errores = new StringBuilder();
                result.getFieldErrors().forEach(error ->
                    errores.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ")
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Errores de validación: " + errores.toString()));
            }
            
            ProgramaEntrenamiento programaGuardado = programaService.guardar(programa);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Programa creado exitosamente", programaGuardado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al crear programa: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProgramaEntrenamiento>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProgramaEntrenamiento programa,
            BindingResult result) {
        
        try {
            if (result.hasErrors()) {
                StringBuilder errores = new StringBuilder();
                result.getFieldErrors().forEach(error ->
                    errores.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ")
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Errores de validación: " + errores.toString()));
            }
            
            Optional<ProgramaEntrenamiento> programaExistente = programaService.buscarPorId(id);
            if (programaExistente.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Programa no encontrado"));
            }
            
            programa.setId(id);
            ProgramaEntrenamiento programaActualizado = programaService.guardar(programa);
            return ResponseEntity.ok(ApiResponse.success("Programa actualizado exitosamente", programaActualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al actualizar programa: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            Optional<ProgramaEntrenamiento> programaOpt = programaService.buscarPorId(id);
            if (programaOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Programa no encontrado"));
            }
            
            programaService.eliminar(id);
            return ResponseEntity.ok(ApiResponse.success("Programa eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al eliminar programa: " + e.getMessage()));
        }
    }
}

