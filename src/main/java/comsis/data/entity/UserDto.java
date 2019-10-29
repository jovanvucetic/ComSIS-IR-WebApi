package comsis.data.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "IrUser")
public class UserDto {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;

    private String password;

    private UserRoleDto role;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoleDto getRole() {
        return role;
    }

    public void setRole(UserRoleDto role) {
        this.role = role;
    }

    public UserDto(){}

    public UserDto(String username, String password, UserRoleDto role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
