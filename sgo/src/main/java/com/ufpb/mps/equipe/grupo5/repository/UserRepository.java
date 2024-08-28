package com.ufpb.mps.equipe.grupo5.repository;

package com.ufpb.mps.equipe.grupo5.repository;

import java.util.List;
import java.util.Optional;

import com.ufpb.mps.equipe.grupo5.model.User;

import jakarta.persistence.EntityManager;

public class UserRepository {

    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            System.out.println("Usuário salvo com sucesso.");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println("Erro ao tentar persistir um usuário no banco.");
        }
    }

    public Optional<List<User>> findAll() {
        List<User> usersRecovered;
        try {
            usersRecovered = entityManager.createQuery("from User", User.class).getResultList();            
        } catch (Exception e) {
            System.out.println("Erro ao tentar listar usuários no banco.");
            entityManager.getTransaction().rollback();
            return Optional.empty();
        }
        return Optional.ofNullable(usersRecovered);
    }

    public Optional<User> findByLogin(String login) {
        List<User> usersRecovered;
        try {
            usersRecovered = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class)
                .setParameter("login", login)
                .getResultList();           
        } catch (Exception e) {
            System.out.println("Erro ao tentar recuperar o login no banco.");
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return Optional.empty();
        }

        if (!usersRecovered.isEmpty()) {
            return Optional.of(usersRecovered.get(0));
        } else {
            return Optional.empty();
        }
    }
}
