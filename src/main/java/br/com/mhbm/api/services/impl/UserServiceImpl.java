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
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto de id:" + id + " não encontrado"));
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
    public User update(UserDTO usuarioAtualizado) {
        User usuarioAtualDB = findById(usuarioAtualizado.getId());
        findUserByEmail(usuarioAtualizado);
        validaCampos(usuarioAtualizado, usuarioAtualDB);
        return repository.save(mapper.map(usuarioAtualizado, User.class));
    }

    @Override
    public void delete(Integer id) {
        User user = findById(id);
        repository.deleteById(user.getId());
    }

    private void validaCampos(UserDTO usuarioAtualizado, User usuarioAtualDB) {
        if (usuarioAtualizado.getName() == null) {
            usuarioAtualizado.setName(usuarioAtualDB.getName());
        }
        if (usuarioAtualizado.getEmail() == null) {
            usuarioAtualizado.setEmail(usuarioAtualDB.getEmail());
        }
        if (usuarioAtualizado.getPassword() == null) {
            usuarioAtualizado.setPassword(usuarioAtualDB.getPassword());
        }
    }

    private void findUserByEmail(UserDTO userDTO) {
        Optional<User> user = repository.findUserByEmail(userDTO.getEmail());
        if (user.isPresent() && !user.get().getId().equals(userDTO.getId())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema");
        }
    }
}
