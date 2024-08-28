package com.ufpb.mps.equipe.grupo5.controller;

import java.util.ArrayList;
import java.util.List;

import com.ufpb.mps.equipe.grupo5.model.User;
import com.ufpb.mps.equipe.grupo5.service.UserCollectionService;
import com.ufpb.mps.equipe.grupo5.service.UserDatabaseService;
import com.ufpb.mps.equipe.grupo5.util.LoginValidator;
import com.ufpb.mps.equipe.grupo5.util.PasswordValidator;

public class UserController {
    
    private final UserCollectionService userCollectionService;
    private final UserDatabaseService userDatabaseService;

    public UserController() {
        this.userCollectionService = new UserCollectionService();
        this.userDatabaseService = new UserDatabaseService();
    }

    public void registerUserCollection(User user) {
        try {
            LoginValidator.validateLogin(user.getLogin());
            PasswordValidator.validatePassword(user);
            userCollectionService.save(user);
            System.out.println("Usuário registrado com sucesso na coleção.");

        } catch (Exception e) {
            System.err.println("Erro ao registrar usuário na coleção: " + e.getMessage());
        }
    }

    public void registerUserDatabase(User user) {
        try {
            LoginValidator.validateLogin(user.getLogin());
            PasswordValidator.validatePassword(user);
            userDatabaseService.save(user);
            System.out.println("Usuário registrado com sucesso no banco de dados.");
        } catch (Exception e) {
            System.err.println("Erro ao registrar usuário no banco de dados: " + e.getMessage());
        }
    }

    public List<User> listUsersCollection() {
        if(userCollectionService.findAll().isPresent())
            return userCollectionService.findAll().get();

        return new ArrayList<User>();
    }

    public List<User> listUsersDatabase() {
        if(userDatabaseService.findAll().isPresent())
            return userDatabaseService.findAll().get();

        return new ArrayList<User>();
    }

    public boolean loginUser(String login, String password) {
        return userDatabaseService.login(login, password);
    }

}
