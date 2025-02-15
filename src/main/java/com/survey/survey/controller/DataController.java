package com.survey.survey.controller;

import com.survey.survey.model.Data;
import com.survey.survey.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @PostMapping
    public ResponseEntity<Map<String, String>> submitData(@RequestBody Data data) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Data received: " + data.toString());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        data.setDate(LocalDateTime.parse(formattedDateTime, formatter));

        dataService.createData(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Data>> getAllData() {
        // Obtener todos los datos de la base de datos
        return ResponseEntity.ok(dataService.getAllData());
    }
}