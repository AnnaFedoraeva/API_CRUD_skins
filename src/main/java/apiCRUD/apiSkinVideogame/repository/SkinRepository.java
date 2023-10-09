package apiCRUD.apiSkinVideogame.repository;

import apiCRUD.apiSkinVideogame.model.Skin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkinRepository extends JpaRepository <Skin, Integer> {
}
