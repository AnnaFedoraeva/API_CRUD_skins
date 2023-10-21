package apiCRUD.apiSkinVideogame.controller;

import apiCRUD.apiSkinVideogame.model.User;
import apiCRUD.apiSkinVideogame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/save")
    private ResponseEntity<?>  saveUser (@RequestBody User user){
        userRepo.save(user);
        return new ResponseEntity<>(
                "The user " + user.getName() + " has been successfully created with id: " + user.getUserID(),
                HttpStatus.OK);

    }




}
