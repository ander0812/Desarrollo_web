package ProyectoFinal.ProyectoFinal_Ander.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "programa_entrenamiento_id", nullable = false)
    @NotNull(message = "El programa de entrenamiento es obligatorio")
    private ProgramaEntrenamiento programaEntrenamiento;
    
    private LocalDate fechaReserva;
    
    private String estado;
    
    private String observaciones;
    
    private boolean confirmacionEnviada = false;
    
    public Reserva() {
        this.fechaReserva = LocalDate.now();
        this.estado = "PENDIENTE";
    }
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public ProgramaEntrenamiento getProgramaEntrenamiento() {
        return programaEntrenamiento;
    }
    
    public void setProgramaEntrenamiento(ProgramaEntrenamiento programaEntrenamiento) {
        this.programaEntrenamiento = programaEntrenamiento;
    }
    
    public LocalDate getFechaReserva() {
        return fechaReserva;
    }
    
    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public boolean isConfirmacionEnviada() {
        return confirmacionEnviada;
    }
    
    public void setConfirmacionEnviada(boolean confirmacionEnviada) {
        this.confirmacionEnviada = confirmacionEnviada;
    }
}

