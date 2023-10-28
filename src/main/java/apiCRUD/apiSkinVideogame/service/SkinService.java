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

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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


//    public Skin findSkinByIDinJson(Long skinID) {
//        List<Skin> skins = readFileAndGetAllAvailable();
//        System.out.println("skinID: " + skinID);
//        return skins.stream().filter(sk -> sk.getId().equals(skinID)).findFirst()
//                .orElseThrow(() -> new SkinNotFoundException(skinID));
//    }
public Skin findSkinByIDinJson(Long skinID) {
    List<Skin> skins = readFileAndGetAllAvailable();
    System.out.println("skinID: " + skinID);
    for (Skin skin : skins) {
        System.out.println(skin);
        if (skin.getId().equals(skinID)) {
            System.out.println(skin.getId() + " = " + skinID);
            return skin;
        }
    }
    throw new SkinNotFoundException(skinID);
}




//    public void addSkinByUser(Long skinID, User user) throws IOException {
//        Skin skinToAdd = findSkinByIDinJson(skinID);
//        skinToAdd.setUser(user);
//        user.getMyListOfSkins().add(skinToAdd);
//        userRepository.save(user);
//        skinRepository.save(skinToAdd);
//        List<Skin> skins = removeSkinFromListOfAvailableSkins(skinToAdd.getId());
//        saveSkinsToJson(skins);
//    }

    public void addSkinByUser(Long skinID, Long id) throws IOException {
        User user = findUserByid(id);
        System.out.println("user in add skin: " + user);
        Skin skinToAdd = findSkinByIDinJson(skinID);
        System.out.println("skin to add: " + skinToAdd);
        skinToAdd.setUser(user);
        user.getMyListOfSkins().add(skinToAdd);
        System.out.println("list of skin: " + user.getMyListOfSkins().get(skinToAdd.getId().intValue()));
        //System.out.println("list of user in repo: " + userRepository.getReferenceById(id));
        //System.out.println("list of skin in repo: " + skinRepository.getReferenceById(skinID));
        //skinRepository.save(skinToAdd);
        System.out.println("skinSaved: " + skinRepository.save(skinToAdd));
        //userRepository.save(user);
        System.out.println("userSaved: " + userRepository.save(user) + " " + skinRepository.findAll());
        List<Skin> skins = removeSkinFromListOfAvailableSkins(skinToAdd.getId());
        saveSkinsToJson(skins);
    }

    public User findUserByid (Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }


    public List<Skin> removeSkinFromListOfAvailableSkins(Long skinID) {
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





    public String getMySkins(Long id) {
        User user = findUserByid(id);
        List<Skin> skins = user.getMyListOfSkins();
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (Skin skin : skins) {
            sb.append("\n").append(skin.toString()).append("\n");
        }
        return sb.toString();
    }


    public String changeSkinColor(Long id, Long skinID, String newColor) {
        User user = findUserByid(id);
        List<Skin> skins = user.getMyListOfSkins();
        Skin skin = skins.stream().filter(sk -> sk.getId().equals(skinID)).findFirst()
                .orElseThrow(() -> new SkinNotFoundException(skinID));
        skin.setColour(newColor);
        skinRepository.save(skin);
        return "Skin color updated successfully";

    }


    public String deleteSkinByUser(Long skinID, Long id) throws IOException {
        User user = findUserByid(id);
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

    public Skin getSkinByID(Long skinID) {
        Optional<Skin> skin = skinRepository.findById(skinID);
        return skin.orElseThrow(() -> new SkinNotFoundException(skinID));
    }


}
