package apiCRUD.apiSkinVideogame.service;

import apiCRUD.apiSkinVideogame.exception.FileReadException;
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
import java.util.List;
import java.util.Optional;

@Getter
@Service
@RequiredArgsConstructor
public class SkinService {

    private final UserRepository userRepository;

    private final SkinRepository skinRepository;

   private static final String ROUTE_OF_JSON_FILE_OF_SKINS = "classpath:json/list_of_skins.json";

    private final ResourceLoader resourceLoader;

    public List<Skin> readFileAndGetAllAvailable() {
        try {
            Resource resource = resourceLoader.getResource(ROUTE_OF_JSON_FILE_OF_SKINS);
            try (InputStream inputStream = resource.getInputStream()) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(inputStream, new TypeReference<>() {
                });
            }
        } catch (IOException e) {
            throw new FileReadException("Error occurred while reading the skin file", e);
        }
    }


    public Skin findSkinByIDinJson(Integer skinID) {
            List<Skin> skins = readFileAndGetAllAvailable();
            return skins.stream().filter(sk -> sk.getId().equals(skinID)).findFirst()
                    .orElseThrow(() -> new SkinNotFoundException(skinID));
        }

    public User findUserByID (Integer userID)  {
        return userRepository.findById(userID).orElseThrow(() ->
                new UserNotFoundException(userID));
    }

    public void addSkinByUser(Integer skinID, Integer userID) throws IOException {
        User user = findUserByID(userID);
        Skin skinToAdd = findSkinByIDinJson(skinID);
        skinToAdd.setUser(user);
        user.getMyListOfSkins().add(skinToAdd);
        userRepository.save(user);
        skinRepository.save(skinToAdd);
        List<Skin> skins = removeSkinFromListOfAvailableSkins(skinToAdd.getId());
        saveSkinsToJson(skins);
    }

    public List<Skin> removeSkinFromListOfAvailableSkins(Integer skinID) {
        List<Skin> skins = readFileAndGetAllAvailable();
        skins.remove(findSkinByIDinJson(skinID));
        return skins;
    }

    public List<Skin> addSkinFromListOfAvailableSkins(Skin skin) {
        List<Skin> skins = readFileAndGetAllAvailable();
        skins.add(findSkinByIDinJson(skin.getId()));
        return skins;
    }

    public void saveSkinsToJson(List<Skin> skins) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = resourceLoader.getResource(ROUTE_OF_JSON_FILE_OF_SKINS);
        try (FileWriter fileWriter = new FileWriter(resource.getFile())) {
            objectMapper.writeValue(fileWriter, skins);
        }
    }

    public List<Skin> getMySkinsByUserId(Integer id) {
        User user = findUserByID(id);
        return user.getMyListOfSkins();
    }


    public String changeSkinColor(int userId, int skinID, String newColor) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Skin> skins = user.getMyListOfSkins();
            Skin skin = skins.stream().filter(sk -> sk.getId().equals(skinID)).findFirst()
                    .orElseThrow(() -> new SkinNotFoundException(skinID));
            skin.setColour(newColor);
            skinRepository.save(skin);
            return "Skin color updated successfully";
        } else {
            return "User not found";
        }

    }

    public String deleteSkinById(Integer skinID, Integer userID) throws IOException {
        User user = findUserByID(userID);
        List<Skin> mySkins = user.getMyListOfSkins();
        Skin skinToRemove = mySkins.stream().filter(sk -> sk.getId().equals(skinID)).findFirst()
                .orElseThrow(() -> new SkinNotFoundException(skinID));
        mySkins.remove(skinToRemove);
        skinRepository.delete(skinToRemove);
        userRepository.save(user);
        List<Skin> skins = addSkinFromListOfAvailableSkins(skinToRemove);
        saveSkinsToJson(skins);

        return "Skin color deleted successfully";
    }

    public Skin getSkinByID(Integer skinID) {
        Optional<Skin> skin = skinRepository.findById(skinID);
        return skin.orElseThrow(() -> new SkinNotFoundException(skinID));
    }


}