package com.kaplich.calories.repository;

import com.kaplich.calories.model.Client;
import com.kaplich.calories.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class ClientRepositoryTest {



    @Autowired
    private ClientRepository clientRepository;

    @Test
    void testFindByClientName() {
        // Arrange
        Client client = new Client();
        client.setClientName("John Doe");

        // Act
        Client foundClient = clientRepository.findByClientName("John Doe");

        // Assert
        Assertions.assertNotNull(foundClient);
        Assertions.assertEquals(client.getClientName(), foundClient.getClientName());
        Assertions.assertEquals(client.getId(), foundClient.getId());
    }

    @Test
    void testFindByClientName_WhenClientNotFound() {
        // Act
        Client foundClient = clientRepository.findByClientName("Nonexistent Client");

        // Assert
        Assertions.assertNull(foundClient);
    }

    @Test
    void testFindAll() {
        // Arrange
        Client client1 = new Client();
        client1.setClientName("John Doe");

        Client client2 = new Client();
        client2.setClientName("Jane Smith");

        // Act
        List<Client> clients = clientRepository.findAll();

        // Assert
        Assertions.assertEquals(2, clients.size());
        Assertions.assertTrue(clients.contains(client1));
        Assertions.assertTrue(clients.contains(client2));
    }

    @Test
    void testSaveAndFlush() {
        // Arrange
        Client client = new Client();
        client.setClientName("John Doe");

        // Act
        Client savedClient = clientRepository.saveAndFlush(client);

        // Assert
        Assertions.assertNotNull(savedClient);
        Assertions.assertNotNull(savedClient.getId());
        Assertions.assertEquals(client.getClientName(), savedClient.getClientName());
    }

    @Test
    void testDelete() {
        // Arrange
        Client client = new Client();
        client.setClientName("John Doe");

        // Act
        clientRepository.delete(client);

        // Assert
        Optional<Client> deletedClient = clientRepository.findById(client.getId());
        Assertions.assertFalse(deletedClient.isPresent());
    }
}
