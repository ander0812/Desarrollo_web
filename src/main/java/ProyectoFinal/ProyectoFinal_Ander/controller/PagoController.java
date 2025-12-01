package ProyectoFinal.ProyectoFinal_Ander.controller;

import ProyectoFinal.ProyectoFinal_Ander.model.Pago;
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
@RequestMapping("/pagos")
public class PagoController {
    
    @Autowired
    private PagoService pagoService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ContratacionService contratacionService;
    
    @GetMapping
    public String listar(Model model, 
                        @RequestParam(required = false) String estado,
                        @RequestParam(required = false) Long clienteId,
                        @RequestParam(required = false) Long contratacionId,
                        @RequestParam(required = false) String medioPago,
                        @RequestParam(required = false) String fechaInicio,
                        @RequestParam(required = false) String fechaFin,
                        @RequestParam(required = false) String montoMin,
                        @RequestParam(required = false) String montoMax) {
        List<Pago> pagos;
        
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
        
        if (montoMin != null && !montoMin.isEmpty()) {
            try {
                min = new java.math.BigDecimal(montoMin);
            } catch (NumberFormatException e) {
            }
        }
        
        if (montoMax != null && !montoMax.isEmpty()) {
            try {
                max = new java.math.BigDecimal(montoMax);
            } catch (NumberFormatException e) {
            }
        }
        
        boolean hayFiltros = (estado != null && !estado.isEmpty()) ||
                            (clienteId != null) ||
                            (contratacionId != null) ||
                            (medioPago != null && !medioPago.isEmpty()) ||
                            (inicio != null) || (fin != null) ||
                            (min != null) || (max != null);
        
        if (hayFiltros) {
            pagos = pagoService.buscarConFiltros(clienteId, contratacionId, estado, medioPago, inicio, fin, min, max);
        } else {
            pagos = pagoService.listarTodos();
        }
        
        model.addAttribute("pagos", pagos);
        model.addAttribute("estado", estado);
        model.addAttribute("clienteId", clienteId);
        model.addAttribute("contratacionId", contratacionId);
        model.addAttribute("medioPago", medioPago);
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);
        model.addAttribute("montoMin", montoMin);
        model.addAttribute("montoMax", montoMax);
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("contrataciones", contratacionService.listarTodos());
        return "pagos/list";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model, @RequestParam(required = false) Long contratacionId) {
        Pago pago = new Pago();
        if (contratacionId != null) {
            var contratacion = contratacionService.buscarPorId(contratacionId);
            if (contratacion.isPresent()) {
                pago.setContratacion(contratacion.get());
                pago.setCliente(contratacion.get().getCliente());
            }
        }
        model.addAttribute("pago", pago);
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("contrataciones", contratacionService.listarTodos());
        return "pagos/form";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var pago = pagoService.buscarPorId(id);
        if (pago.isEmpty()) {
            return "redirect:/pagos";
        }
        model.addAttribute("pago", pago.get());
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("contrataciones", contratacionService.listarTodos());
        return "pagos/form";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Pago pago, BindingResult result, 
                         RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarTodos());
            model.addAttribute("contrataciones", contratacionService.listarTodos());
            return "pagos/form";
        }
        pagoService.guardar(pago);
        redirectAttributes.addFlashAttribute("message", "Pago guardado exitosamente");
        return "redirect:/pagos";
    }
    
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        var pago = pagoService.buscarPorId(id);
        if (pago.isEmpty()) {
            return "redirect:/pagos";
        }
        model.addAttribute("pago", pago.get());
        return "pagos/detalle";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        pagoService.eliminar(id);
        redirectAttributes.addFlashAttribute("message", "Pago eliminado exitosamente");
        return "redirect:/pagos";
    }
    
    @GetMapping("/reconciliar/{contratacionId}")
    public String reconciliar(@PathVariable Long contratacionId, Model model) {
        var reconciliacion = pagoService.reconciliarPagos(contratacionId);
        model.addAttribute("reconciliacion", reconciliacion);
        return "pagos/reconciliacion";
    }
    
    @GetMapping("/reconciliar-todos")
    public String reconciliarTodos(Model model) {
        var reconciliaciones = pagoService.reconciliarTodosLosPagos();
        model.addAttribute("reconciliaciones", reconciliaciones);
        return "pagos/reconciliacion-todos";
    }
}

