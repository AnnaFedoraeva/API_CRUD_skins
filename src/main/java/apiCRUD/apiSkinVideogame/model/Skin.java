package apiCRUD.apiSkinVideogame.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "skins")
public class Skin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="skinID")
    private Integer id;

    private String name;
    private String type;
    private float price;
    private String colour;
    private String description;
    private boolean availability;
}
