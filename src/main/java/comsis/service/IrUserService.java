package comsis.service;

import comsis.core.model.User;
import comsis.core.model.UserRole;
import comsis.core.serviceInterface.UserService;
import comsis.data.entity.UserDto;
import comsis.data.repositoryInterface.UserRepository;
import comsis.service.mapperinterface.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service(value = "userService")
public class IrUserService implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public User getByUsername(String username) {
        return getByUsernameInternal(username);
    }

    @Override
    public UUID registerUser(String username, String password) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encoder.encode(password));
        newUser.setRole(UserRole.USER);

        return userRepository.save(userMapper.toDto(newUser)).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByUsernameInternal(username);
        List<GrantedAuthority> authories = new ArrayList<>();
        authories.add(user.getRole());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authories);
    }
    
    private User getByUsernameInternal(String username) {
        UserDto dto = userRepository.findByUsername(username);
        return userMapper.toServiceModel(dto);
    }
}
