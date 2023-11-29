package br.com.mhbm.api.services;

import br.com.mhbm.api.models.User;

import java.util.List;

public interface UserService {
    User findById(Integer id);

    List<User> findAll();
}
