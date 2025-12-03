package ProyectoFinal.ProyectoFinal_Ander.controller.rest;

import ProyectoFinal.ProyectoFinal_Ander.dto.ApiResponse;
import ProyectoFinal.ProyectoFinal_Ander.model.Reserva;
import ProyectoFinal.ProyectoFinal_Ander.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ReservaRestController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<Reserva>>> listar(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Long programaId,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        
        try {
            List<Reserva> reservas;
            
            LocalDate inicio = null;
            LocalDate fin = null;
            
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
            
            boolean hayFiltros = estado != null || clienteId != null || programaId != null || inicio != null || fin != null;
            
            if (hayFiltros) {
                reservas = reservaService.buscarConFiltros(clienteId, programaId, estado, inicio, fin);
            } else {
                reservas = reservaService.listarTodos();
            }
            
            return ResponseEntity.ok(ApiResponse.success("Reservas obtenidas exitosamente", reservas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener reservas: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<Reserva>> obtenerPorId(@PathVariable Long id) {
        try {
            Optional<Reserva> reservaOpt = reservaService.buscarPorId(id);
            
            if (reservaOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Reserva no encontrada"));
            }
            
            return ResponseEntity.ok(ApiResponse.success("Reserva obtenida exitosamente", reservaOpt.get()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener reserva: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Reserva>> crear(
            @Valid @RequestBody Reserva reserva,
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
            
            Reserva reservaGuardada = reservaService.guardar(reserva);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Reserva creada exitosamente", reservaGuardada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al crear reserva: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Reserva>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Reserva reserva,
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
            
            Optional<Reserva> reservaExistente = reservaService.buscarPorId(id);
            if (reservaExistente.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Reserva no encontrada"));
            }
            
            reserva.setId(id);
            Reserva reservaActualizada = reservaService.guardar(reserva);
            return ResponseEntity.ok(ApiResponse.success("Reserva actualizada exitosamente", reservaActualizada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al actualizar reserva: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            Optional<Reserva> reservaOpt = reservaService.buscarPorId(id);
            if (reservaOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Reserva no encontrada"));
            }
            
            reservaService.eliminar(id);
            return ResponseEntity.ok(ApiResponse.success("Reserva eliminada exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al eliminar reserva: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<ApiResponse<Reserva>> cancelar(@PathVariable Long id) {
        try {
            Reserva reservaCancelada = reservaService.cancelar(id);
            if (reservaCancelada == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Reserva no encontrada"));
            }
            return ResponseEntity.ok(ApiResponse.success("Reserva cancelada exitosamente", reservaCancelada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al cancelar reserva: " + e.getMessage()));
        }
    }
}




