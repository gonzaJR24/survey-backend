package com.survey.survey.service;

import com.survey.survey.model.UserEntity;
import com.survey.survey.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Obtener todos los usuarios
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    // Obtener un usuario por ID
    public Optional<UserEntity> getUserById(int id) {
        return userRepository.findById(id);
    }

    // Crear un nuevo usuario
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    // Actualizar un usuario existente
    public UserEntity updateUser(int id, UserEntity userDetails) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("UserEntity not found"));
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }

    // Eliminar un usuario
    public void deleteUser(int id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("UserEntity not found"));
        userRepository.delete(user);
    }

    // Buscar un usuario por nombre de usuario
    public Optional<UserEntity> findByUser(String username) {
        return userRepository.findByUsername(username);
    }
}