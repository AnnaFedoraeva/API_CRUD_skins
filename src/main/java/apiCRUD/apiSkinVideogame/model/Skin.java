package apiCRUD.apiSkinVideogame.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "skins")
public class Skin {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSkin")
    private Long id;
    private String name;
    private String type;

    private String price;
    private String colour;
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Override
    public String toString() {
        return "Skin " +
                "id " + id +
                ": " + "\n" + "- name: '" + name + '\'' +
                "\n" +
                "- type: '" + type + '\'' +
                "\n" +
                "- price: '" + price + '\'' +
                "\n" +
                "- colour: '" + colour + '\'' +
                "\n" +
                "- description: '" + description + '\'' +
                '}';
    }
}
