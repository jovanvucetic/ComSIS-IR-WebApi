package comsis.core.serviceInterface;

import comsis.core.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends UserDetailsService {

    User getByUsername(String username);

    UUID registerUser(String username, String password);
}
