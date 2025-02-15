package com.survey.survey.repository;

import com.survey.survey.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<Data, Integer> {
}
