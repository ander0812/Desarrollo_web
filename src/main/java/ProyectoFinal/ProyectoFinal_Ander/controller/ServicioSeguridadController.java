package ProyectoFinal.ProyectoFinal_Ander.controller;

import ProyectoFinal.ProyectoFinal_Ander.model.ServicioSeguridad;
import ProyectoFinal.ProyectoFinal_Ander.service.ServicioSeguridadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/servicios")
public class ServicioSeguridadController {
    
    @Autowired
    private ServicioSeguridadService servicioService;
    
    @GetMapping
    public String listar(Model model, 
                        @RequestParam(required = false) String busqueda,
                        @RequestParam(required = false) String tipo,
                        @RequestParam(required = false) String ubicacion,
                        @RequestParam(required = false) String duracion,
                        @RequestParam(required = false) String precioMin,
                        @RequestParam(required = false) String precioMax) {
        List<ServicioSeguridad> servicios;
        
        if (busqueda != null && !busqueda.isEmpty()) {
            servicios = servicioService.buscarPorTexto(busqueda);
        } else {
            java.math.BigDecimal min = null;
            java.math.BigDecimal max = null;
            
            if (precioMin != null && !precioMin.isEmpty()) {
                try {
                    min = new java.math.BigDecimal(precioMin);
                } catch (NumberFormatException e) {
                }
            }
            
            if (precioMax != null && !precioMax.isEmpty()) {
                try {
                    max = new java.math.BigDecimal(precioMax);
                } catch (NumberFormatException e) {
                }
            }
            
            boolean hayFiltros = (tipo != null && !tipo.isEmpty()) || 
                                (ubicacion != null && !ubicacion.isEmpty()) ||
                                (duracion != null && !duracion.isEmpty()) ||
                                (min != null) || (max != null);
            
            if (hayFiltros) {
                servicios = servicioService.buscarConFiltros(tipo, ubicacion, duracion, min, max);
            } else {
                servicios = servicioService.listarTodos();
            }
        }
        
        model.addAttribute("servicios", servicios);
        model.addAttribute("busqueda", busqueda);
        model.addAttribute("tipo", tipo);
        model.addAttribute("ubicacion", ubicacion);
        model.addAttribute("duracion", duracion);
        model.addAttribute("precioMin", precioMin);
        model.addAttribute("precioMax", precioMax);
        return "servicios/list";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("servicio", new ServicioSeguridad());
        return "servicios/form";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var servicio = servicioService.buscarPorId(id);
        if (servicio.isEmpty()) {
            return "redirect:/servicios";
        }
        model.addAttribute("servicio", servicio.get());
        return "servicios/form";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute ServicioSeguridad servicio, BindingResult result, 
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "servicios/form";
        }
        servicioService.guardar(servicio);
        redirectAttributes.addFlashAttribute("message", "Servicio guardado exitosamente");
        return "redirect:/servicios";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        servicioService.eliminar(id);
        redirectAttributes.addFlashAttribute("message", "Servicio eliminado exitosamente");
        return "redirect:/servicios";
    }
    
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        var servicio = servicioService.buscarPorId(id);
        if (servicio.isEmpty()) {
            return "redirect:/servicios";
        }
        model.addAttribute("servicio", servicio.get());
        return "servicios/detalle";
    }
}

