package com.example.statista.repositories;

import com.example.statista.entities.DataSet;
import com.example.statista.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DataSetRepository extends JpaRepository<DataSet, Long> {

    List<DataSet> findByUser(User user);

    Optional<DataSet> findById(Long id);
}
