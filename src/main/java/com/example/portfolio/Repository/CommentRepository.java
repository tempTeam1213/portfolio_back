package com.example.portfolio.Repository;

import com.example.portfolio.Common.ErrorCode;
import com.example.portfolio.Domain.Comment;
import com.example.portfolio.Exception.Global.UserApplicationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CommentRepository {

    @PersistenceContext
    EntityManager em;

    public void save (Comment comment) {
        em.persist(comment);
    }

    public Long countCommentsByCommentId(Long commentId) {
        try {
            Long countResult = em.createQuery("SELECT COUNT(c) FROM Comment c WHERE c.id = :commentId", Long.class)
                    .setParameter("commentId", commentId)
                    .getSingleResult();
            return countResult;
        } catch (Exception ex) {
            throw new UserApplicationException(ErrorCode.NO_MATCHING_COMMENT_FOUND_WITH_COMMENTID);
        }
    }

    public List<Comment> findCommentsByProjectId(Long projectId) {
        try {
            List<Comment> comments = em.createQuery("SELECT c FROM Comment c WHERE c.project.id = :projectId", Comment.class)
                    .setParameter("projectId", projectId)
                    .getResultList();
            return comments;
        } catch (Exception ex) {
            throw new UserApplicationException(ErrorCode.NO_MATCHING_COMMENT_FOUND_WITH_PROJECTID);
        }
    }

    public Comment findCommentByCommentId(Long commentId) {
        try {
            System.out.println(commentId + "??");
            Comment comment = em.createQuery("SELECT c FROM Comment c WHERE c.id = :commentId", Comment.class)
                    .setParameter("commentId", commentId)
                    .getSingleResult();
            return comment;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new UserApplicationException(ErrorCode.NO_MATCHING_COMMENT_FOUND_WITH_COMMENTID);
        }
    }

    @Transactional
    public void deleteCommentByCommentId (Long commentId) {
        em.createQuery("DELETE FROM Comment c WHERE c.id = :commentId")
                .setParameter("commentId", commentId)
                .executeUpdate();
    }

    public List<Comment> findAllComment() {
        List<Comment> comments = em.createQuery("SELECT c FROM Comment c", Comment.class)
                .getResultList();

        return comments;
    }
}
