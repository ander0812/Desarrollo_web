package ProyectoFinal.ProyectoFinal_Ander.controller.rest;

import ProyectoFinal.ProyectoFinal_Ander.dto.ApiResponse;
import ProyectoFinal.ProyectoFinal_Ander.model.Pago;
import ProyectoFinal.ProyectoFinal_Ander.service.PagoService;
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
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class PagoRestController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<Pago>>> listar(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Long contratacionId,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String medioPago,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            @RequestParam(required = false) String montoMin,
            @RequestParam(required = false) String montoMax) {
        
        try {
            List<Pago> pagos;
            
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
            
            if (montoMin != null && !montoMin.isEmpty()) {
                try {
                    min = new BigDecimal(montoMin);
                } catch (NumberFormatException e) {
                }
            }
            
            if (montoMax != null && !montoMax.isEmpty()) {
                try {
                    max = new BigDecimal(montoMax);
                } catch (NumberFormatException e) {
                }
            }
            
            boolean hayFiltros = clienteId != null || contratacionId != null || estado != null 
                    || medioPago != null || inicio != null || fin != null || min != null || max != null;
            
            if (hayFiltros) {
                pagos = pagoService.buscarConFiltros(clienteId, contratacionId, estado, medioPago, inicio, fin, min, max);
            } else {
                pagos = pagoService.listarTodos();
            }
            
            return ResponseEntity.ok(ApiResponse.success("Pagos obtenidos exitosamente", pagos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener pagos: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<Pago>> obtenerPorId(@PathVariable Long id) {
        try {
            Optional<Pago> pagoOpt = pagoService.buscarPorId(id);
            
            if (pagoOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Pago no encontrado"));
            }
            
            return ResponseEntity.ok(ApiResponse.success("Pago obtenido exitosamente", pagoOpt.get()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener pago: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Pago>> crear(
            @Valid @RequestBody Pago pago,
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
            
            Pago pagoGuardado = pagoService.guardar(pago);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Pago creado exitosamente", pagoGuardado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al crear pago: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Pago>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Pago pago,
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
            
            Optional<Pago> pagoExistente = pagoService.buscarPorId(id);
            if (pagoExistente.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Pago no encontrado"));
            }
            
            pago.setId(id);
            Pago pagoActualizado = pagoService.guardar(pago);
            return ResponseEntity.ok(ApiResponse.success("Pago actualizado exitosamente", pagoActualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al actualizar pago: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            Optional<Pago> pagoOpt = pagoService.buscarPorId(id);
            if (pagoOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Pago no encontrado"));
            }
            
            pagoService.eliminar(id);
            return ResponseEntity.ok(ApiResponse.success("Pago eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al eliminar pago: " + e.getMessage()));
        }
    }

    @GetMapping("/reconciliacion/{contratacionId}")
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<Map<String, Object>>> reconciliar(@PathVariable Long contratacionId) {
        try {
            Map<String, Object> resultado = pagoService.reconciliarPagos(contratacionId);
            if (resultado.containsKey("error")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error((String) resultado.get("error")));
            }
            return ResponseEntity.ok(ApiResponse.success("Reconciliación realizada exitosamente", resultado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al reconciliar pagos: " + e.getMessage()));
        }
    }

    @GetMapping("/reconciliacion")
    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> reconciliarTodos() {
        try {
            List<Map<String, Object>> resultados = pagoService.reconciliarTodosLosPagos();
            return ResponseEntity.ok(ApiResponse.success("Reconciliaciones realizadas exitosamente", resultados));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al reconciliar pagos: " + e.getMessage()));
        }
    }
}



