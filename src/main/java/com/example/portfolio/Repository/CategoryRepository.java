package com.example.portfolio.Repository;

import com.example.portfolio.Common.ErrorCode;
import com.example.portfolio.Domain.Category;
import com.example.portfolio.Exception.Global.UserApplicationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CategoryRepository {

    @PersistenceContext
    EntityManager em;

    public void save (Category category) {
        em.persist(category);
    }

    public Category findCategoryByCategoryId (Long categoryId) {
        try {
            System.out.println(categoryId + "확인5555555");
            Category category = em.createQuery("SELECT c FROM Category c WHERE c.id = :categoryId", Category.class)
                    .setParameter("categoryId", categoryId)
                    .getSingleResult();
            return category;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new UserApplicationException(ErrorCode.NO_MATCHING_CATEGORY_WITH_ID);
        }

    }

    @Transactional
    public void deleteCategoryByCategoryId (Long categoryId) {
        em.createQuery("DELETE FROM ProjectCategory pc WHERE pc.category.id = :categoryId")
                .setParameter("categoryId", categoryId)
                .executeUpdate();
        em.createQuery("DELETE FROM Category c WHERE c.id = :categoryId")
                .setParameter("categoryId", categoryId)
                .executeUpdate();
    }

    @Transactional
    public void deleteLikeById (Long likeId) {
        em.createQuery("DELETE FROM Project p WHERE p.id = :likeId")
                .setParameter("likeId", likeId)
                .executeUpdate();
    }

    public Long countCategoryByName (String name) {
            return em.createQuery("SELECT COUNT(c) FROM Category c WHERE c.name = :categoryName", Long.class)
                .setParameter("categoryName", name)
                .getSingleResult();
    }

    public List<Category> findAllCategory () {
        List<Category> categories = em.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList();
        return categories;
    }
}
