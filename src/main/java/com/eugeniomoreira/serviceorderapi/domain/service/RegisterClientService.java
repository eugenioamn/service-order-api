package com.eugeniomoreira.serviceorderapi.domain.service;

import com.eugeniomoreira.serviceorderapi.domain.exception.DomainException;
import com.eugeniomoreira.serviceorderapi.domain.model.Client;
import com.eugeniomoreira.serviceorderapi.domain.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client save(Client client) {
        Client existingClient = clientRepository
                .findByEmail(client.getEmail());

        if (existingClient != null && !existingClient.equals(client)) {
            throw new DomainException("There is already a client registered" +
                    " with this email");
        }

        return clientRepository.save(client);
    }

    public void delete(Long clientId) {
        clientRepository.deleteById(clientId);
    }
}
