package apiCRUD.apiSkinVideogame.service;

import apiCRUD.apiSkinVideogame.exception.FileReadException;
import apiCRUD.apiSkinVideogame.exception.SkinNotAvailableException;
import apiCRUD.apiSkinVideogame.exception.SkinNotFoundException;
import apiCRUD.apiSkinVideogame.exception.UserNotFoundException;
import apiCRUD.apiSkinVideogame.model.Skin;
import apiCRUD.apiSkinVideogame.model.User;
import apiCRUD.apiSkinVideogame.repository.SkinRepository;
import apiCRUD.apiSkinVideogame.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Service
@RequiredArgsConstructor
public class SkinService {

    private final UserRepository userRepository;

    private final SkinRepository skinRepository;

    private static final String ROUTE_OF_JSON_FILE_OF_SKINS = "classpath:json/list_of_skins.json";
    //"classpath:src/main/resources/json/list_of_skins.json"
    private final ResourceLoader resourceLoader;

    public List<Skin> readSkinsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Skin> skins;
        try {
            Resource resource = resourceLoader.getResource(ROUTE_OF_JSON_FILE_OF_SKINS);
            InputStream inputStream = resource.getInputStream();
            skins = objectMapper.readValue(inputStream, new TypeReference<>() {
            });

        } catch (IOException e) {
            throw new FileReadException("Error occurred while reading the skin file", e);
        }
        return removeSkinsFromListIfExistsInRepository(skins);
    }


    public Skin findSkinByIDinJson(Long skinID) {
        List<Skin> skins = readSkinsFromJson();
        for (Skin skin : skins) {
            if (skin.getId().equals(skinID)) {
                return skin;
            }
        }
        throw new SkinNotFoundException(skinID);
    }

    public boolean isSkinIsAlreadyPurchased(Long skinID) {
        return skinRepository.existsById(skinID);
    }

    public void addSkinToUserAndRemoveFromJson(Long skinID, Long userID) throws IOException {
        // Check if the skin is already owned by a user.
        if (isSkinIsAlreadyPurchased(skinID)) {
            throw new SkinNotAvailableException(skinID);
        }

        User user = findUserByID(userID);
        Skin skin = findSkinByIDinJson(skinID);
        skin.setUser(user);
        user.getMyListOfSkins().add(skin);
        skinRepository.save(skin);
        userRepository.save(user);

    }

    public List<Skin> removeSkinsFromListIfExistsInRepository(List<Skin> skins) {
        return skins.stream()
                .filter(skin -> !skinRepository.existsById(skin.getId()))
                .collect(Collectors.toList());
    }

    public String deleteSkinByUserAndAddToJson(Long skinID, Long userID) {
        User user = findUserByID(userID);
        List<Skin> mySkins = user.getMyListOfSkins();

        Skin skinToDelete = mySkins.stream()
                .filter(sk -> sk.getId().equals(skinID))
                .findFirst()
                .orElseThrow(() -> new SkinNotFoundException(skinID));

        mySkins.remove(skinToDelete);
        skinRepository.delete(skinToDelete);
        userRepository.save(user);
        return "Skin " + skinID + " deleted successfully";
    }

    public String changeSkinColor(Long id, Long skinID, String newColor) {
        User user = findUserByID(id);
        List<Skin> skins = user.getMyListOfSkins();
        skins.stream()
                .filter(sk -> sk.getId().equals(skinID))
                .findFirst()
                .map(skin -> {
                    skin.setColour(newColor);
                    return skinRepository.save(skin);
                })
                .orElseThrow(() -> new SkinNotFoundException(skinID));

        return "Skin color updated successfully";

    }






    public User findUserByID(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }







    public String getMySkins(Long id) {
        User user = findUserByID(id);
        List<Skin> skins = user.getMyListOfSkins();
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (Skin skin : skins) {
            sb.append("\n").append(skin.toString()).append("\n");
        }
        return sb.toString();
    }






    public String getSkinByID(Long skinID, Long userID) {
        User user = findUserByID(userID);
        List<Skin> skins = user.getMyListOfSkins();

        for (Skin skin : skins) {
            if (skin.getId().equals(skinID)) {
                return skin.toString();
            }
        }

        throw new SkinNotFoundException(skinID);
    }


}