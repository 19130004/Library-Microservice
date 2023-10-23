package com.anle.userservice.service;

import com.anle.userservice.data.Role;
import com.anle.userservice.data.User;
import com.anle.userservice.data.UserRepository;
import com.anle.userservice.model.UserDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUser() {
        // TODO Auto-generated method stub
        return userRepository.findAll();
    }

    @Override
    public List<Role> getAllRole() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return UserDTO.entityToDTO(userRepository.save(userDTO.dtoToEntity(userDTO)));
    }

    @Override
    public Role saveRole(Role role) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addRole(String username, String roleName) {
        // TODO Auto-generated method stub

    }

    @Override
    public UserDTO login(String username, String password) {
        User user = userRepository.findByUsername(username);
        UserDTO userDTO = new UserDTO();
        if (user != null) {
            BeanUtils.copyProperties(user, userDTO);
            if (passwordEncoder.matches(password, userDTO.getPassword())) {
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                String accessToken = JWT.create().withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + (1 * 60 * 1000))).sign(algorithm);
                String refreshToken = JWT.create().withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + (10080 * 60 * 1000))).sign(algorithm);
                userDTO.setToken(accessToken);
                userDTO.setRefreshtoken(refreshToken);
            }
        }
        return userDTO;
    }

    @Override
    public UserDTO validateToken(String token) {
        // TODO Auto-generated method stub
        return null;
    }

}
