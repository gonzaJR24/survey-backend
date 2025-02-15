package com.survey.survey.repository;

import com.survey.survey.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    // Puedes agregar métodos personalizados aquí si es necesario
    Optional<UserEntity> findByUsername(String username); // Método para buscar un usuario por su nombre de usuario
}