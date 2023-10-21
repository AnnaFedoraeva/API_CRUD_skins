package apiCRUD.apiSkinVideogame.exception;

public class SkinNotFoundException extends  RuntimeException {
    public SkinNotFoundException(Integer id) {

        super("Could not find skin with id: " + id);
    }
}
