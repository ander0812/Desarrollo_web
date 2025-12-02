package ProyectoFinal.ProyectoFinal_Ander.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contrataciones")
public class Contratacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "servicio_seguridad_id", nullable = false)
    @NotNull(message = "El servicio de seguridad es obligatorio")
    private ServicioSeguridad servicioSeguridad;
    
    private LocalDate fechaInicio;
    
    private LocalDate fechaFin;
    
    private String estado;
    
    private String observaciones;
    
    private LocalDate fechaContratacion;
    
    private boolean confirmacionEnviada = false;
    
    @OneToMany(mappedBy = "contratacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Pago> pagos = new ArrayList<>();
    
    public Contratacion() {
        this.fechaContratacion = LocalDate.now();
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
    
    public ServicioSeguridad getServicioSeguridad() {
        return servicioSeguridad;
    }
    
    public void setServicioSeguridad(ServicioSeguridad servicioSeguridad) {
        this.servicioSeguridad = servicioSeguridad;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
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
    
    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }
    
    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }
    
    public boolean isConfirmacionEnviada() {
        return confirmacionEnviada;
    }
    
    public void setConfirmacionEnviada(boolean confirmacionEnviada) {
        this.confirmacionEnviada = confirmacionEnviada;
    }
    
    public List<Pago> getPagos() {
        return pagos;
    }
    
    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }
}

