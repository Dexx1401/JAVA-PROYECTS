package org.example.Controllers;

import org.example.Models.Address;
import org.example.Models.User;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")

public class ControllerUser {

    private List<User> users = new ArrayList<>();


    public ControllerUser() {
        //EJEMPLO DE USUARIO

        List<Address> addresses1 = List.of(new Address(1, "street No. 1", "UK"),
                new Address(2, "street No. 2", "AU"));

        users.add(new User(123, "User1", "user1@mail.com", cifradoPassword("123456"), addresses1 ));
    }

    //CIFRADO DE CONTRASEÑAS
    public String cifradoPassword(String password) {
        return DigestUtils.sha1Hex(password);
    }

    //METODO GET /users?sortedBy=email
    @GetMapping
    public List<User> getUsers(@RequestParam String sortedBy) {
        users.sort((u1, u2) -> u1.getEmail().compareTo(u2.getEmail()));
        return users;
    }
    //METODO /users/{user_id}/addresses
    @GetMapping("/users/{user_id}/addresses")
    public List<Address> getUserAddresses(@PathVariable int user_id) {
        Optional<User> user = users.stream().filter(u -> u.getId() == user_id).findFirst();
        return user.map (User::getAddresses).orElse(null);
    }

    // METODO PUT /users/{user_id}/addresses/{address_id}
    @PutMapping("/{user_id}/addresses/{address_id}")
    public String updateUserAddress(@PathVariable int user_id, @PathVariable int address_id, @RequestBody Address newAddress) {
        Optional<User> user = users.stream().filter(u -> u.getId() == user_id).findFirst();
        if (user.isPresent()) {
            user.get().getAddresses().removeIf(address -> address.getId() == address_id);
            user.get().getAddresses().add(newAddress);
            return "Address Actualizado Correctamente";
        }
        return "User NO Encontrado";
    }

    // POST /users
    @PostMapping
    public String createUser(@RequestBody User newUser) {
        User userToAdd = new User(newUser.getId(), newUser.getEmail(), newUser.getName(), cifradoPassword(newUser.getPassword()), newUser.getAddresses());
        users.add(userToAdd);
        return "User Creado Correctamente";
    }

    //METODO PATCH /users/{id}
    @PatchMapping("/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        Optional<User> user = users.stream().filter(u -> u.getId() == id).findFirst();
        if (user.isPresent()) {
            user.get().setEmail(updatedUser.getEmail());
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                user.get().setPassword(cifradoPassword(updatedUser.getPassword()));
            }
            return "User Actualizado Correctamente";
        }
        return "User NO Encontrado";
    }

    //METODO DELETE /users/{id}
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        users.removeIf(user -> user.getId() == id);
        return "User Eliminado Correctamente";
    }

}
