package org.example.Models;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String createdAt;
    private List<Address> addresses;

    public User(int id, String name, String email, String password, List<Address> addresses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.addresses = addresses;

        //CREADOR DE HORA AUTOMATICO
        this.createdAt = LocalDateTime.now(ZoneId.of("Europe/London"))
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAt() {
        return createdAt;
    }


    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
