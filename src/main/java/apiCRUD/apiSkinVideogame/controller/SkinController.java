package apiCRUD.apiSkinVideogame.controller;

import apiCRUD.apiSkinVideogame.exception.SkinNotFoundException;
import apiCRUD.apiSkinVideogame.exception.UserNotFoundException;
import apiCRUD.apiSkinVideogame.security.jwt.JwtUtils;
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
    public ResponseEntity<?> getAllAvailable() throws IOException {
        return new ResponseEntity<>(skinService.readSkinsFromJson(), HttpStatus.OK);

    }


    @PostMapping("/buy/{skinID}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> buySkin(@PathVariable(value = "skinID") Long skinID) {
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

    @GetMapping("/myskins")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getMySkins() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>("User with ID " + userDetails.getId() + " has this skins: " + skinService.getMySkins(userDetails.getId()), HttpStatus.OK);
    }

    @PutMapping("/{skinID}/{color}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> changeSkinColor(@PathVariable(value = "skinID") Long skinID, @PathVariable(value = "color") String color) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(skinService.changeSkinColor(userDetails.getId(), skinID, color), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{skinID}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteSkinById(@PathVariable(value = "skinID") Long skinID) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(skinService.deleteSkinByUserAndAddToJson(skinID, userDetails.getId()), HttpStatus.OK);

    }

    @GetMapping("/getskin/{skinID}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getSkin(@PathVariable(value = "skinID") Long skinID) {
        return new ResponseEntity<>(skinService.getSkinByID(skinID), HttpStatus.OK);

    }




}




