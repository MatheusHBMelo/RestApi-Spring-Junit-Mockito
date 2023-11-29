package br.com.mhbm.api.services.impl;

import br.com.mhbm.api.models.User;
import br.com.mhbm.api.repositories.UserRepository;
import br.com.mhbm.api.services.UserService;
import br.com.mhbm.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public User findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(String.format("Objeto de id:%d não encontrado", id)));
    }
}
