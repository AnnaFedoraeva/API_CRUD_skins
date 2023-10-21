package apiCRUD.apiSkinVideogame.controller;

import apiCRUD.apiSkinVideogame.exception.SkinNotFoundException;
import apiCRUD.apiSkinVideogame.exception.UserNotFoundException;
import apiCRUD.apiSkinVideogame.service.SkinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/skins")
public class SkinController {

    @Autowired
    SkinService skinService;

    @GetMapping("/available")
    public ResponseEntity<?> getAllAvailable() throws IOException {
        return new ResponseEntity<>(skinService.readFileAndGetAllAvailable(), HttpStatus.OK);

    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<?> user(@PathVariable(value = "userID") Integer userID) {
        return new ResponseEntity<>("user " + skinService.findUserByID(userID).getMyListOfSkins()
                , HttpStatus.OK);

    }

    @GetMapping("/skin/{skinID}")
    public ResponseEntity<?> skin(@PathVariable(value = "skinID") Integer skinID) throws IOException {
        return new ResponseEntity<>("skin " + skinService.findSkinByIDinJson(skinID)
                , HttpStatus.OK);

    }


    @PostMapping("/buy/{userID}/{skinID}")
    public ResponseEntity<?> buySkin(@PathVariable(value = "userID") Integer userID, @PathVariable(value = "skinID") Integer skinID) {
        try {
            skinService.addSkinByUser(userID, skinID);
            return new ResponseEntity<>("Skin with ID " + skinID + " has been added to user with ID " + userID, HttpStatus.OK);
        } catch (UserNotFoundException | SkinNotFoundException | IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userid}/myskins")
    public ResponseEntity<?> getMySkinsByUserId(@PathVariable(value = "userid") Integer id) {
        return new ResponseEntity<>("User with ID " + id + " has this skins: " + skinService.getMySkinsByUserId(id), HttpStatus.OK);
    }

    @PostMapping("/{userID}/{skinID}/{color}")
    public ResponseEntity<?> changeSkinColor(@PathVariable(value = "userID") Integer userID, @PathVariable(value = "skinID") Integer skinID, @PathVariable(value = "color") String color) {
        return new ResponseEntity<>(skinService.changeSkinColor(skinID, userID, color), HttpStatus.OK);
    }

    @PostMapping("/delete/{userID}/{skinID}")
    public ResponseEntity<?> deleteSkinById(@PathVariable(value = "skinID") Integer skinID, @PathVariable(value = "userID") Integer userID) throws IOException {
        return new ResponseEntity<>(skinService.deleteSkinById(skinID, userID), HttpStatus.OK);

    }

    @GetMapping("getSkin/{skinID}")
    public ResponseEntity<?> getSkin(@PathVariable(value = "skinID") Integer skinID) {
        return new ResponseEntity<>("Skin with ID " + skinID + ": " + skinService.getSkinByID(skinID), HttpStatus.OK);

    }


}




