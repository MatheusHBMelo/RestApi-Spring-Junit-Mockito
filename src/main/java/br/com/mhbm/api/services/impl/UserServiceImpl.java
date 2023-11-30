package br.com.mhbm.api.services.impl;

import br.com.mhbm.api.models.User;
import br.com.mhbm.api.models.dto.UserDTO;
import br.com.mhbm.api.repositories.UserRepository;
import br.com.mhbm.api.services.UserService;
import br.com.mhbm.api.services.exceptions.DataIntegrityViolationException;
import br.com.mhbm.api.services.exceptions.EmptyListException;
import br.com.mhbm.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.Conditions;
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
        if (users.isEmpty()) {
            throw new EmptyListException("User list is empty");
        }
        return users;
    }

    @Override
    public User create(UserDTO userDTO) {
        findUserByEmail(userDTO);
        return repository.save(mapper.map(userDTO, User.class));
    }

    @Override
    public User update(UserDTO userDTO) {
        User userDB = findById(userDTO.getId());
        findUserByEmail(userDTO);
        validaCampos(userDTO, userDB);
        return repository.save(mapper.map(userDTO, User.class));
    }

    private void validaCampos(UserDTO userDTO, User userDB) {
        if (userDTO.getName() == null){
            userDTO.setName(userDB.getName());
        }
        if (userDTO.getEmail() == null){
            userDTO.setEmail(userDB.getEmail());
        }
        if (userDTO.getPassword() == null){
            userDTO.setPassword(userDB.getPassword());
        }
    }

    private void findUserByEmail(UserDTO userDTO) {
        Optional<User> user = repository.findUserByEmail(userDTO.getEmail());
        if (user.isPresent() && !user.get().getId().equals(userDTO.getId())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema");
        }
    }
}
