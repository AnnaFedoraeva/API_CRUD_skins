package apiCRUD.apiSkinVideogame.model;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userID")
    private Integer id;

    private String name;
    private String email;
    private String password;
}
