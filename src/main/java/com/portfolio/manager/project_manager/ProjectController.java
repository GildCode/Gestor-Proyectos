package com.portfolio.manager.project_manager;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*") // Permitir requests desde tu frontend de Vue (cambia el puerto si es diferente)
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Endpoint público para obtener todos los proyectos (admin y guest)
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        projectService.deleteExpiredGuestProjects();
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);

    }

    // Endpoint público para que los invitados creen proyectos
    @PostMapping("/guest")
    public ResponseEntity<Project> createGuestProject(@RequestBody Project project) {
        Project createdProject = projectService.createGuestProject(project);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    // --- Endpoints para el Administrador (Protegidos con Spring Security) ---

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Solo accesible por usuarios con rol ADMIN
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> createAdminProject(@RequestBody Project project) {
        Project createdProject = projectService.createAdminProject(project);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PutMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> updateAdminProject(@PathVariable Long id, @RequestBody Project project) {
        try {
            Project updatedProject = projectService.updateAdminProject(id, project);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAdminProject(@PathVariable Long id) {
        projectService.deleteAdminProject(id);
        return ResponseEntity.noContent().build();
    }
}