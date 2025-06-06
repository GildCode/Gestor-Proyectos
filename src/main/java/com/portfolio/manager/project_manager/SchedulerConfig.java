package com.portfolio.manager.project_manager;

import com.portfolio.manager.project_manager.ProjectService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling // Habilita la programación de tareas
public class SchedulerConfig {

    private final ProjectService projectService;

    public SchedulerConfig(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Ejecuta este método cada hora (en milisegundos)
    // Puedes ajustar el tiempo (ej. cada 24 horas para menos frecuencia: 86400000)
    //@Scheduled(fixedRate = 3600000) // Cada hora
    public void cleanExpiredGuestProjects() {
        projectService.deleteExpiredGuestProjects();
    }
}
