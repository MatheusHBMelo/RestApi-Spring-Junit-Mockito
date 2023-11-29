package br.com.mhbm.api.services.impl;

import br.com.mhbm.api.models.User;
import br.com.mhbm.api.models.dto.UserDTO;
import br.com.mhbm.api.repositories.UserRepository;
import br.com.mhbm.api.services.UserService;
import br.com.mhbm.api.services.exceptions.DataIntegrityViolationException;
import br.com.mhbm.api.services.exceptions.EmptyListException;
import br.com.mhbm.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository repository;

    @Override
    public User findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(String.format("Objeto de id:%d não encontrado", id)));
    }

    @Override
    public List<User> findAll() {
        List<User> users = repository.findAll();
        if (users.isEmpty()){
            throw new EmptyListException("User list is empty");
        }
        return users;
    }
}
