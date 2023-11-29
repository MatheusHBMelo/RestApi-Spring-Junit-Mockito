package br.com.mhbm.api.services;

import br.com.mhbm.api.models.User;

public interface UserService {
    User findById(Integer id);
}
