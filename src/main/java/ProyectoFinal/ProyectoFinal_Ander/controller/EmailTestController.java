package ProyectoFinal.ProyectoFinal_Ander.controller;

import ProyectoFinal.ProyectoFinal_Ander.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmailTestController {
    
    @Autowired
    private EmailService emailService;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @GetMapping("/test-email")
    public String mostrarFormularioTest(Model model) {
        model.addAttribute("title", "Test de Email");
        model.addAttribute("fromEmail", fromEmail);
        return "test-email";
    }
    
    @PostMapping("/test-email/enviar")
    public String enviarEmailTest(@RequestParam String para, 
                                   @RequestParam(required = false) String asunto,
                                   @RequestParam(required = false) String mensaje,
                                   RedirectAttributes redirectAttributes) {
        try {
            String asuntoFinal = asunto != null && !asunto.isEmpty() 
                ? asunto 
                : "Email de Prueba - Sistema de Seguridad";
            String mensajeFinal = mensaje != null && !mensaje.isEmpty() 
                ? mensaje 
                : "Este es un email de prueba desde el Sistema de Seguridad y Entrenamiento.";
            
            emailService.enviarEmail(para, asuntoFinal, mensajeFinal);
            redirectAttributes.addFlashAttribute("message", 
                "Email de prueba enviado exitosamente a: " + para);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al enviar email: " + e.getMessage());
        }
        return "redirect:/test-email";
    }
}

