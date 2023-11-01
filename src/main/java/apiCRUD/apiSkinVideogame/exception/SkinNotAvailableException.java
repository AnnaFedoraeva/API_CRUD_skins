package apiCRUD.apiSkinVideogame.exception;

public class SkinNotAvailableException extends RuntimeException{

    public SkinNotAvailableException(Long id) {

        super("Skin with id : " + id + " are not available.");
    }
}
