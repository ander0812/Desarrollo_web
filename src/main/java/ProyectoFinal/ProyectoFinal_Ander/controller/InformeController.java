package ProyectoFinal.ProyectoFinal_Ander.controller;

import ProyectoFinal.ProyectoFinal_Ander.service.InformeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;

@Controller
@RequestMapping("/informes")
public class InformeController {
    
    @Autowired
    private InformeService informeService;
    
    @GetMapping
    public String informes(Model model) {
        var estadisticas = informeService.generarEstadisticasGenerales();
        var servicios = informeService.generarInformeServicios();
        var cursos = informeService.generarInformeCursos();
        
        model.addAttribute("estadisticas", estadisticas);
        model.addAttribute("servicios", servicios);
        model.addAttribute("cursos", cursos);
        
        return "informes/index";
    }
    
    @GetMapping("/ingresos")
    public String ingresos(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
                          Model model) {
        if (inicio == null) {
            inicio = LocalDate.now().minusMonths(1);
        }
        if (fin == null) {
            fin = LocalDate.now();
        }
        
        var informe = informeService.generarInformeIngresos(inicio, fin);
        model.addAttribute("informe", informe);
        model.addAttribute("inicio", inicio);
        model.addAttribute("fin", fin);
        
        return "informes/ingresos";
    }
    
    @GetMapping("/frecuencia")
    public String frecuencia(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
                            Model model) {
        if (inicio == null) {
            inicio = LocalDate.now().minusMonths(6);
        }
        if (fin == null) {
            fin = LocalDate.now();
        }
        
        var informe = informeService.generarFrecuenciaContrataciones(inicio, fin);
        model.addAttribute("informe", informe);
        model.addAttribute("inicio", inicio);
        model.addAttribute("fin", fin);
        
        return "informes/frecuencia";
    }
}

