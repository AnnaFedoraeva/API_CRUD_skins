package apiCRUD.apiSkinVideogame.repository;

import apiCRUD.apiSkinVideogame.model.Skin;
import apiCRUD.apiSkinVideogame.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkinRepository extends JpaRepository<Skin, Long> {

    Skin findByUserAndId(User user, Long skinId);
}
