package ProyectoFinal.ProyectoFinal_Ander.controller;

import ProyectoFinal.ProyectoFinal_Ander.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ServicioSeguridadService servicioService;
    
    @Autowired
    private ProgramaEntrenamientoService programaService;
    
    @Autowired
    private ContratacionService contratacionService;
    
    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private PagoService pagoService;
    
    @Autowired
    private InformeService informeService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("totalClientes", clienteService.listarTodos().size());
        model.addAttribute("totalServicios", servicioService.listarTodos().size());
        model.addAttribute("totalProgramas", programaService.listarTodos().size());
        model.addAttribute("totalContrataciones", contratacionService.listarTodos().size());
        model.addAttribute("totalReservas", reservaService.listarTodos().size());
        model.addAttribute("totalPagos", pagoService.listarTodos().size());
        
        var stats = informeService.generarEstadisticasGenerales();
        model.addAttribute("estadisticas", stats);
        
        return "dashboard";
    }
}

