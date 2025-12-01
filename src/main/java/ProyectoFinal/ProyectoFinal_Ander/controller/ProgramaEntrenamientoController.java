package ProyectoFinal.ProyectoFinal_Ander.controller;

import ProyectoFinal.ProyectoFinal_Ander.model.ProgramaEntrenamiento;
import ProyectoFinal.ProyectoFinal_Ander.service.ProgramaEntrenamientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/programas")
public class ProgramaEntrenamientoController {
    
    @Autowired
    private ProgramaEntrenamientoService programaService;
    
    @GetMapping
    public String listar(Model model, 
                        @RequestParam(required = false) String busqueda,
                        @RequestParam(required = false) String instructor,
                        @RequestParam(required = false) String fechaInicio,
                        @RequestParam(required = false) String fechaFin,
                        @RequestParam(required = false) String costoMin,
                        @RequestParam(required = false) String costoMax) {
        List<ProgramaEntrenamiento> programas;
        
        if (busqueda != null && !busqueda.isEmpty()) {
            programas = programaService.buscarPorTexto(busqueda);
        } else {
            java.time.LocalDate inicio = null;
            java.time.LocalDate fin = null;
            java.math.BigDecimal min = null;
            java.math.BigDecimal max = null;
            
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
            
            if (costoMin != null && !costoMin.isEmpty()) {
                try {
                    min = new java.math.BigDecimal(costoMin);
                } catch (NumberFormatException e) {
                }
            }
            
            if (costoMax != null && !costoMax.isEmpty()) {
                try {
                    max = new java.math.BigDecimal(costoMax);
                } catch (NumberFormatException e) {
                }
            }
            
            boolean hayFiltros = (instructor != null && !instructor.isEmpty()) ||
                                (inicio != null) || (fin != null) ||
                                (min != null) || (max != null);
            
            if (hayFiltros) {
                programas = programaService.buscarConFiltros(instructor, inicio, fin, min, max);
            } else {
                programas = programaService.listarTodos();
            }
        }
        
        model.addAttribute("programas", programas);
        model.addAttribute("busqueda", busqueda);
        model.addAttribute("instructor", instructor);
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);
        model.addAttribute("costoMin", costoMin);
        model.addAttribute("costoMax", costoMax);
        return "programas/list";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("programa", new ProgramaEntrenamiento());
        return "programas/form";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var programa = programaService.buscarPorId(id);
        if (programa.isEmpty()) {
            return "redirect:/programas";
        }
        model.addAttribute("programa", programa.get());
        return "programas/form";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute ProgramaEntrenamiento programa, BindingResult result, 
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "programas/form";
        }
        programaService.guardar(programa);
        redirectAttributes.addFlashAttribute("message", "Programa guardado exitosamente");
        return "redirect:/programas";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        programaService.eliminar(id);
        redirectAttributes.addFlashAttribute("message", "Programa eliminado exitosamente");
        return "redirect:/programas";
    }
    
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        var programa = programaService.buscarPorId(id);
        if (programa.isEmpty()) {
            return "redirect:/programas";
        }
        model.addAttribute("programa", programa.get());
        return "programas/detalle";
    }
}

