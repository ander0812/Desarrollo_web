package ProyectoFinal.ProyectoFinal_Ander.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "servicios_seguridad")
public class ServicioSeguridad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre del servicio es obligatorio")
    private String nombre;
    
    private String tipo;
    
    @NotBlank(message = "La descripción es obligatoria")
    @Column(length = 1000)
    private String descripcion;
    
    private String ubicacion;
    
    @DecimalMin(value = "0.0", message = "El precio debe ser mayor o igual a 0")
    private BigDecimal precio;
    
    private String duracion;
    
    private String personalAsignado;
    
    private String horarios;
    
    private String herramientas;
    
    private String condiciones;
    
    private boolean activo = true;
    
    @OneToMany(mappedBy = "servicioSeguridad", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Contratacion> contrataciones = new ArrayList<>();
    
    public ServicioSeguridad() {}
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
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getUbicacion() {
        return ubicacion;
    }
    
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public BigDecimal getPrecio() {
        return precio;
    }
    
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    
    public String getDuracion() {
        return duracion;
    }
    
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
    
    public String getPersonalAsignado() {
        return personalAsignado;
    }
    
    public void setPersonalAsignado(String personalAsignado) {
        this.personalAsignado = personalAsignado;
    }
    
    public String getHorarios() {
        return horarios;
    }
    
    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }
    
    public String getHerramientas() {
        return herramientas;
    }
    
    public void setHerramientas(String herramientas) {
        this.herramientas = herramientas;
    }
    
    public String getCondiciones() {
        return condiciones;
    }
    
    public void setCondiciones(String condiciones) {
        this.condiciones = condiciones;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public List<Contratacion> getContrataciones() {
        return contrataciones;
    }
    
    public void setContrataciones(List<Contratacion> contrataciones) {
        this.contrataciones = contrataciones;
    }
}

