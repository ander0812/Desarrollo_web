package ProyectoFinal.ProyectoFinal_Ander.controller;

import ProyectoFinal.ProyectoFinal_Ander.model.Cliente;
import ProyectoFinal.ProyectoFinal_Ander.service.ClienteService;
import ProyectoFinal.ProyectoFinal_Ander.service.ContratacionService;
import ProyectoFinal.ProyectoFinal_Ander.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ContratacionService contratacionService;
    
    @Autowired
    private ReservaService reservaService;
    
    @GetMapping
    public String listar(Model model, 
                        @RequestParam(required = false) String busqueda,
                        @RequestParam(required = false) String tipoCliente,
                        @RequestParam(required = false) String ciudad,
                        @RequestParam(required = false) String email,
                        @RequestParam(required = false) String telefono,
                        @RequestParam(required = false) String documento) {
        List<Cliente> clientes;
        
        if (busqueda != null && !busqueda.isEmpty()) {
            clientes = clienteService.buscarPorTexto(busqueda);
        } else {
            boolean hayFiltros = (tipoCliente != null && !tipoCliente.isEmpty()) ||
                                (ciudad != null && !ciudad.isEmpty()) ||
                                (email != null && !email.isEmpty()) ||
                                (telefono != null && !telefono.isEmpty()) ||
                                (documento != null && !documento.isEmpty());
            
            if (hayFiltros) {
                clientes = clienteService.buscarConFiltros(tipoCliente, ciudad, email, telefono, documento);
            } else {
                clientes = clienteService.listarTodos();
            }
        }
        
        model.addAttribute("clientes", clientes);
        model.addAttribute("busqueda", busqueda);
        model.addAttribute("tipoCliente", tipoCliente);
        model.addAttribute("ciudad", ciudad);
        model.addAttribute("email", email);
        model.addAttribute("telefono", telefono);
        model.addAttribute("documento", documento);
        return "clientes/list";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var cliente = clienteService.buscarPorId(id);
        if (cliente.isEmpty()) {
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", cliente.get());
        return "clientes/form";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Cliente cliente, BindingResult result, 
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "clientes/form";
        }
        clienteService.guardar(cliente);
        redirectAttributes.addFlashAttribute("message", "Cliente guardado exitosamente");
        return "redirect:/clientes";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        clienteService.eliminar(id);
        redirectAttributes.addFlashAttribute("message", "Cliente eliminado exitosamente");
        return "redirect:/clientes";
    }
    
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        var cliente = clienteService.buscarPorId(id);
        if (cliente.isEmpty()) {
            return "redirect:/clientes";
        }
        Cliente clienteObj = cliente.get();
        model.addAttribute("cliente", clienteObj);
        
        // Cargar contrataciones y reservas por separado para evitar LazyInitializationException
        var contrataciones = contratacionService.buscarPorCliente(id);
        var reservas = reservaService.buscarPorCliente(id);
        
        model.addAttribute("contrataciones", contrataciones);
        model.addAttribute("reservas", reservas);
        
        return "clientes/detalle";
    }
}

