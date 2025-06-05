package com.portfolio.manager.project_manager; // <--- Este es el paquete correcto para ProjectService y todas las clases



import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Value("${project.guest.expiration.hours:24}")
    private double guestProjectExpirationHours;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // --- Métodos para el Administrador (Tú) ---

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Project createAdminProject(Project project) {
        // La línea que daba error: project.setGuestProject(false);
        // Ahora funcionará porque Project está en el mismo paquete y @Data funciona.
        project.setGuestProject(false);
        return projectRepository.save(project);
    }

    public Project updateAdminProject(Long id, Project updatedProject) {
        return projectRepository.findById(id).map(project -> {
            project.setName(updatedProject.getName());
            project.setDescription(updatedProject.getDescription());
            project.setImageUrl(updatedProject.getImageUrl());
            project.setProjectUrl(updatedProject.getProjectUrl());
            project.setTechnologies(updatedProject.getTechnologies());
            return projectRepository.save(project);
        }).orElseThrow(() -> new RuntimeException("Project not found with id " + id));
    }

    public void deleteAdminProject(Long id) {
        projectRepository.deleteById(id);
    }

    // --- Métodos para Invitados ---

    public Project createGuestProject(Project project) {
        // La línea que daba error: project.setGuestProject(true);
        // Ahora funcionará.
        project.setGuestProject(true);
        return projectRepository.save(project);
    }

    // --- Lógica de Limpieza Automática ---

    @Transactional
    public void deleteExpiredGuestProjects() {
        long minutes = Math.round(guestProjectExpirationHours * 60);  // convierte horas decimales a minutos
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(minutes);
        List<Project> expiredProjects = projectRepository.findByIsGuestProjectTrueAndCreatedAtBefore(threshold);
        projectRepository.deleteAll(expiredProjects);
        System.out.println("Deleted " + expiredProjects.size() + " expired guest projects.");
    }
}