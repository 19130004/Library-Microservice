package com.anle.userservice.model;

import com.anle.userservice.data.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String employeeId;
    private String token;
    private String refreshtoken;



    public static UserDTO entityToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPassword(user.getPassword());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmployeeId(user.getEmployeeId());
        return userDTO;
    }

    public static User dtoToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setEmployeeId(userDTO.getEmployeeId());
        return user;
    }
}