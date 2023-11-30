package br.com.mhbm.api.services;

import br.com.mhbm.api.models.User;
import br.com.mhbm.api.models.dto.UserDTO;

import java.util.List;

public interface UserService {
    User findById(Integer id);

    List<User> findAll();

    User create(UserDTO userDTO);

    User update(UserDTO userDTO);

    void delete(Integer id);
}
