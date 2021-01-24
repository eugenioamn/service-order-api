package com.eugeniomoreira.serviceorderapi.api.controller;

import com.eugeniomoreira.serviceorderapi.domain.model.Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ClientController {

    @GetMapping("/clients")
    public List<Client> list() {
        var client1 = new Client();
        var client2 = new Client();

        client1.setId(1L);
        client1.setName("John");
        client1.setEmail("me@john.com");
        client1.setPhone("2125553456");

        client2.setId(2L);
        client2.setName("Mary");
        client2.setEmail("me@mary.com");
        client2.setPhone("2135553456");

        return Arrays.asList(client1, client2);
    }
}
