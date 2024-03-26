package com.example.portfolio.Repository;

import com.example.portfolio.Domain.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProjectRepository {

    @PersistenceContext
    EntityManager em;

    public List<Project> findProjectByUserId (String userId) {
        List<Project> projects = em.createQuery("SELECT p FROM Project p WHERE p.owner.id = :userId", Project.class)
                .setParameter("userId", userId)
                .getResultList();
        return projects;
    }

    public Project findProjectById (Long projectId) {
        Project project = em.createQuery("SELECT p FROM Project p WHERE p.id = :id", Project.class)
                .setParameter("id", projectId)
                .getSingleResult();
        return project;
    }

    @Transactional
    public void deleleProjectByProjectId (Long projectId) {
        em.createQuery("DELETE FROM Project p WHERE p.id = :productId")
                .setParameter("productId", projectId)
                .executeUpdate();
    }

    public void save (Project project) {
        em.persist(project);
    }
}
