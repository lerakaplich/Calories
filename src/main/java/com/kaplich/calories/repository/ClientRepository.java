package com.kaplich.calories.repository;

import com.kaplich.calories.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByClientName(String nameOfClient);

    List<Client> findAll();

    Client saveAndFlush(Client client);

    void delete(Client entity);

    @Override
    <S extends Client> List<S> saveAll(Iterable<S> entities);
}
