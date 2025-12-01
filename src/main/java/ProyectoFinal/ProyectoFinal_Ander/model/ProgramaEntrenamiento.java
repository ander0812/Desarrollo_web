package ProyectoFinal.ProyectoFinal_Ander.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "programas_entrenamiento")
public class ProgramaEntrenamiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre del programa es obligatorio")
    private String nombre;
    
    @NotBlank(message = "El contenido es obligatorio")
    @Column(length = 2000)
    private String contenido;
    
    private String requisitos;
    
    private String instructor;
    
    @DecimalMin(value = "0.0", message = "El costo debe ser mayor o igual a 0")
    private BigDecimal costo;
    
    @Min(value = 1, message = "La duración debe ser al menos 1 día")
    private Integer duracionDias;
    
    @Min(value = 1, message = "El cupo debe ser al menos 1")
    private Integer cupo;
    
    private Integer cupoDisponible;
    
    private LocalDate fechaInicio;
    
    private LocalDate fechaFin;
    
    private String temario;
    
    private boolean activo = true;
    
    @OneToMany(mappedBy = "programaEntrenamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();
    
    public ProgramaEntrenamiento() {}
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public String getRequisitos() {
        return requisitos;
    }
    
    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }
    
    public String getInstructor() {
        return instructor;
    }
    
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    
    public BigDecimal getCosto() {
        return costo;
    }
    
    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }
    
    public Integer getDuracionDias() {
        return duracionDias;
    }
    
    public void setDuracionDias(Integer duracionDias) {
        this.duracionDias = duracionDias;
    }
    
    public Integer getCupo() {
        return cupo;
    }
    
    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }
    
    public Integer getCupoDisponible() {
        return cupoDisponible;
    }
    
    public void setCupoDisponible(Integer cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
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
    
    public String getTemario() {
        return temario;
    }
    
    public void setTemario(String temario) {
        this.temario = temario;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public List<Reserva> getReservas() {
        return reservas;
    }
    
    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}

