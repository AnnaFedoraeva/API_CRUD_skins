package apiCRUD.apiSkinVideogame.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private Integer userID;

    private String name;
    private String email;
    private String password;

    //@OneToMany (mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "user_id")
    @OneToMany(mappedBy = "user")
    private List<Skin> myListOfSkins;

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

