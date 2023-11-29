package br.com.mhbm.api.controllers;

import br.com.mhbm.api.models.dto.UserDTO;
import br.com.mhbm.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(mapper.map(service.findById(id), UserDTO.class));
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> findAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAll()
                        .stream()
                        .map(x -> mapper.map(x, UserDTO.class))
                        .toList());
    }
}
