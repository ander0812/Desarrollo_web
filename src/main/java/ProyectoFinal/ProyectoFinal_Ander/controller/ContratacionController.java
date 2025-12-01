package ProyectoFinal.ProyectoFinal_Ander.controller;

import ProyectoFinal.ProyectoFinal_Ander.model.Contratacion;
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
@RequestMapping("/contrataciones")
public class    ContratacionController {
    
    @Autowired
    private ContratacionService contratacionService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ServicioSeguridadService servicioService;
    
    @GetMapping
    public String listar(Model model, 
                        @RequestParam(required = false) String estado,
                        @RequestParam(required = false) Long clienteId,
                        @RequestParam(required = false) Long servicioId,
                        @RequestParam(required = false) String fechaInicio,
                        @RequestParam(required = false) String fechaFin) {
        List<Contratacion> contrataciones;
        
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
                            (servicioId != null) ||
                            (inicio != null) || (fin != null);
        
        if (hayFiltros) {
            contrataciones = contratacionService.buscarConFiltros(clienteId, servicioId, estado, inicio, fin);
        } else {
            contrataciones = contratacionService.listarTodos();
        }
        
        model.addAttribute("contrataciones", contrataciones);
        model.addAttribute("estado", estado);
        model.addAttribute("clienteId", clienteId);
        model.addAttribute("servicioId", servicioId);
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("servicios", servicioService.listarActivos());
        return "contrataciones/list";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("contratacion", new Contratacion());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("servicios", servicioService.listarActivos());
        return "contrataciones/form";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var contratacion = contratacionService.buscarPorId(id);
        if (contratacion.isEmpty()) {
            return "redirect:/contrataciones";
        }
        model.addAttribute("contratacion", contratacion.get());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("servicios", servicioService.listarActivos());
        return "contrataciones/form";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Contratacion contratacion, BindingResult result, 
                         RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("servicios", servicioService.listarActivos());
            return "contrataciones/form";
        }
        contratacionService.guardar(contratacion);
        String mensaje = "Contratación guardada exitosamente";
        if (("ACTIVA".equals(contratacion.getEstado()) || "CONFIRMADA".equals(contratacion.getEstado()))
            && contratacion.getCliente() != null && contratacion.getCliente().getEmail() != null) {
            mensaje += ". Se ha enviado un email de confirmación al cliente.";
        }
        redirectAttributes.addFlashAttribute("message", mensaje);
        return "redirect:/contrataciones";
    }
    
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        var contratacion = contratacionService.buscarPorId(id);
        if (contratacion.isEmpty()) {
            return "redirect:/contrataciones";
        }
        model.addAttribute("contratacion", contratacion.get());
        return "contrataciones/detalle";
    }
    
    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        var contratacion = contratacionService.cancelar(id);
        if (contratacion != null) {
            redirectAttributes.addFlashAttribute("message", "Contratación cancelada exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo cancelar la contratación");
        }
        return "redirect:/contrataciones";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        contratacionService.eliminar(id);
        redirectAttributes.addFlashAttribute("message", "Contratación eliminada exitosamente");
        return "redirect:/contrataciones";
    }
}

