package com.survey.survey.service;

import com.survey.survey.model.Data;
import com.survey.survey.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataService {

    @Autowired
    private DataRepository dataRepository;

    // Obtener todos los registros
    public List<Data> getAllData() {
        return dataRepository.findAll();
    }

    // Obtener un registro por ID
    public Optional<Data> getDataById(int id) {
        return dataRepository.findById(id);
    }

    // Crear un nuevo registro
    public Data createData(Data data) {

        return dataRepository.save(data);
    }

    // Actualizar un registro existente
    public Data updateData(int id, Data dataDetails) {
        Data data = dataRepository.findById(id).orElseThrow(() -> new RuntimeException("Data not found"));
        data.setEntrada(dataDetails.getEntrada());
        data.setHcu(dataDetails.getHcu());
        data.setNumero(dataDetails.getNumero());
        data.setMedio(dataDetails.getMedio());
        data.setMunicipio(dataDetails.getMunicipio());
        data.setSector(dataDetails.getSector());
        return dataRepository.save(data);
    }

    // Eliminar un registro
    public void deleteData(int id) {
        Data data = dataRepository.findById(id).orElseThrow(() -> new RuntimeException("Data not found"));
        dataRepository.delete(data);
    }
}
