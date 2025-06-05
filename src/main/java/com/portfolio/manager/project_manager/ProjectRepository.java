package com.portfolio.manager.project_manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Método para encontrar proyectos de invitados creados antes de una fecha/hora
    List<Project> findByIsGuestProjectTrueAndCreatedAtBefore(LocalDateTime threshold);
}