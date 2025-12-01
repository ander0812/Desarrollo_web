package ProyectoFinal.ProyectoFinal_Ander.controller;

import ProyectoFinal.ProyectoFinal_Ander.model.Reserva;
import ProyectoFinal.ProyectoFinal_Ander.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaController {
    
    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ProgramaEntrenamientoService programaService;
    
    @GetMapping
    public String listar(Model model, 
                        @RequestParam(required = false) String estado,
                        @RequestParam(required = false) Long clienteId,
                        @RequestParam(required = false) Long programaId,
                        @RequestParam(required = false) String fechaInicio,
                        @RequestParam(required = false) String fechaFin) {
        List<Reserva> reservas;
        
        java.time.LocalDate inicio = null;
        java.time.LocalDate fin = null;
        
        if (fechaInicio != null && !fechaInicio.isEmpty()) {
            try {
                inicio = java.time.LocalDate.parse(fechaInicio);
            } catch (Exception e) {
            }
        }
        
        if (fechaFin != null && !fechaFin.isEmpty()) {
            try {
                fin = java.time.LocalDate.parse(fechaFin);
            } catch (Exception e) {
            }
        }
        
        boolean hayFiltros = (estado != null && !estado.isEmpty()) ||
                            (clienteId != null) ||
                            (programaId != null) ||
                            (inicio != null) || (fin != null);
        
        if (hayFiltros) {
            reservas = reservaService.buscarConFiltros(clienteId, programaId, estado, inicio, fin);
        } else {
            reservas = reservaService.listarTodos();
        }
        
        model.addAttribute("reservas", reservas);
        model.addAttribute("estado", estado);
        model.addAttribute("clienteId", clienteId);
        model.addAttribute("programaId", programaId);
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("programas", programaService.listarTodos());
        return "reservas/list";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("programas", programaService.listarConCupoDisponible());
        return "reservas/form";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var reserva = reservaService.buscarPorId(id);
        if (reserva.isEmpty()) {
            return "redirect:/reservas";
        }
        model.addAttribute("reserva", reserva.get());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("programas", programaService.listarTodos());
        return "reservas/form";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Reserva reserva, BindingResult result, 
                         RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("programas", programaService.listarTodos());
            return "reservas/form";
        }
        reservaService.guardar(reserva);
        String mensaje = "Reserva guardada exitosamente";
        if (("CONFIRMADA".equals(reserva.getEstado()) || "ACTIVA".equals(reserva.getEstado())) 
            && reserva.getCliente() != null && reserva.getCliente().getEmail() != null) {
            mensaje += ". Se ha enviado un email de confirmación al cliente.";
        }
        redirectAttributes.addFlashAttribute("message", mensaje);
        return "redirect:/reservas";
    }
    
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        var reserva = reservaService.buscarPorId(id);
        if (reserva.isEmpty()) {
            return "redirect:/reservas";
        }
        model.addAttribute("reserva", reserva.get());
        return "reservas/detalle";
    }
    
    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        var reserva = reservaService.cancelar(id);
        if (reserva != null) {
            redirectAttributes.addFlashAttribute("message", "Reserva cancelada exitosamente. Se ha liberado el cupo del programa.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo cancelar la reserva");
        }
        return "redirect:/reservas";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reservaService.eliminar(id);
        redirectAttributes.addFlashAttribute("message", "Reserva eliminada exitosamente");
        return "redirect:/reservas";
    }
}

