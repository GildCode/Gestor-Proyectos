package com.portfolio.manager.project_manager; // Asegúrate de que este sea el paquete correcto

import jakarta.persistence.*;
// Elimina todas las importaciones de Lombok (lombok.AllArgsConstructor, etc.)
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private String imageUrl;
    private String projectUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_technologies", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "technology")
    private Set<String> technologies;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean isGuestProject;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // --- CONSTRUCTOR SIN ARGUMENTOS ---
    public Project() {
    }

    // --- CONSTRUCTOR CON TODOS LOS ARGUMENTOS (Opcional, si lo necesitas) ---
    public Project(Long id, String name, String description, String imageUrl, String projectUrl, Set<String> technologies, LocalDateTime createdAt, boolean isGuestProject) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.projectUrl = projectUrl;
        this.technologies = technologies;
        this.createdAt = createdAt;
        this.isGuestProject = isGuestProject;
    }

    // --- GETTERS Y SETTERS ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public Set<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(Set<String> technologies) {
        this.technologies = technologies;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // No se necesita setCreatedAt si siempre se establece en @PrePersist
    // public void setCreatedAt(LocalDateTime createdAt) {
    //     this.createdAt = createdAt;
    // }

    public boolean isGuestProject() { // Getter para boolean es 'is'
        return isGuestProject;
    }

    public void setGuestProject(boolean guestProject) { // Setter para boolean
        isGuestProject = guestProject;
    }

    // Opcional: Si usabas el patrón Builder, tendrías que implementarlo manualmente o quitar las llamadas a .builder()
    // Si no lo usabas explícitamente, puedes ignorar esto.
}