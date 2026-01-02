package com.anusha.vulnerableappsec.repository;

import com.anusha.vulnerableappsec.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VulnerableUserSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    //  SQL Injection prevented
    public List<User> searchByUsername(String username) {

        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";

        return entityManager
                .createNativeQuery(sql, User.class)
                .setParameter(1, username)
                .getResultList();
    }
}
