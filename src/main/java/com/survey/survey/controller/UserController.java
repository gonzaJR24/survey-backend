package com.survey.survey.controller;

import com.survey.survey.model.UserDTO;
import com.survey.survey.model.UserEntity;
import com.survey.survey.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Obtener todos los usuarios
    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }



    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable int id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo usuario
    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable int id, @RequestBody UserEntity userDetails) {
        return ResponseEntity.ok(userService.updateUser(id, userDetails));
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar un usuario por nombre de usuario
    @GetMapping("/username/{username}")
    public ResponseEntity<UserEntity> findByUser(@PathVariable String username) {
        return userService.findByUser(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO user, HttpServletRequest request) {
        try {
            // Autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.username(), user.password())
            );

            // Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Crear una sesión para el usuario
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            // Obtener detalles del usuario
            Optional<UserEntity> existingUser = userService.findByUser(user.username());
            if (existingUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
            }

            UserEntity userObj = existingUser.get();
            return ResponseEntity.ok(userObj);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidar la sesión
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Limpiar la cookie JSESSIONID
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        Map<String, String> responseText = new HashMap<>();
        responseText.put("response", "Logged out successfully");
        return ResponseEntity.ok(responseText);
    }
        @GetMapping("/current")
        public ResponseEntity<?> getCurrentUser(Authentication authentication) {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Fetch user details
            String username = authentication.getName();
            UserEntity user = userService.findByUser(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return ResponseEntity.ok(user);
        }
    }
