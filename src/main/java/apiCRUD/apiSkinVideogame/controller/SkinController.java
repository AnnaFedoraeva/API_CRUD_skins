package apiCRUD.apiSkinVideogame.controller;

import apiCRUD.apiSkinVideogame.exception.SkinNotFoundException;
import apiCRUD.apiSkinVideogame.exception.UserNotFoundException;
import apiCRUD.apiSkinVideogame.security.jwt.services.UserDetailsImpl;
import apiCRUD.apiSkinVideogame.service.SkinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/skins")
public class SkinController {

    @Autowired
    SkinService skinService;

    @GetMapping("/available")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllAvailable() {
        return new ResponseEntity<>(skinService.readSkinsFromJson(), HttpStatus.OK);

    }


    @PostMapping("{userID}/buy/{skinID}")
    @PreAuthorize("#userID == principal.id or hasRole('ADMIN')")
    public ResponseEntity<?> buySkin(@PathVariable Long userID, @PathVariable(value = "skinID") Long skinID) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println("skinID: " + skinID + "user: " + userDetails);
            skinService.addSkinToUserAndRemoveFromJson(skinID, userDetails.getId());
            //skinService.addSkinByUser(skinID, user);
            return new ResponseEntity<>("Skin with ID " + skinID + " has been added successfully", HttpStatus.OK);
        } catch (UserNotFoundException | SkinNotFoundException | IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{userID}/myskins")
    @PreAuthorize("#userID == principal.id or hasRole('ADMIN')")
    public ResponseEntity<?> getMySkins(@PathVariable Long userID) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>("The list of skins of the user with id " + userDetails.getId() + " :" + skinService.getMySkins(userDetails.getId()), HttpStatus.OK);
    }

    @PutMapping("{userID}/{skinID}/{color}")
    @PreAuthorize("#userID == principal.id or hasRole('ADMIN')")
    public ResponseEntity<?> changeSkinColor(@PathVariable Long userID, @PathVariable(value = "skinID") Long skinID, @PathVariable(value = "color") String color) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(skinService.changeSkinColor(userDetails.getId(), skinID, color), HttpStatus.OK);
    }

    @DeleteMapping("{userID}/delete/{skinID}")
    @PreAuthorize("#userID == principal.id or hasRole('ADMIN')")
    public ResponseEntity<?> deleteSkinById(@PathVariable Long userID, @PathVariable(value = "skinID") Long skinID) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(skinService.deleteSkinByUserAndAddToJson(skinID, userDetails.getId()), HttpStatus.OK);

    }

    @GetMapping("{userID}/getskin/{skinID}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or #userID == principal.id")
    public ResponseEntity<?> getSkin(@PathVariable Long userID, @PathVariable(value = "skinID") Long skinID) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(skinService.getSkinByID(skinID, userDetails.getId()), HttpStatus.OK);

    }

}




