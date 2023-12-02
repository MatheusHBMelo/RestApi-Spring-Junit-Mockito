package br.com.mhbm.api.services.impl;

import br.com.mhbm.api.models.User;
import br.com.mhbm.api.models.dto.UserDTO;
import br.com.mhbm.api.repositories.UserRepository;
import br.com.mhbm.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    public static final int ID = 1;
    public static final String NAME = "Matheus";
    public static final String EMAIL = "matheus@email.com";
    public static final String PASSWORD = "12345";
    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> userOptional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        initObjects();
    }

    @Test
    @DisplayName("Deve retornar um usuário válido ao buscar por ID")
    void deveRetornarUsuarioExistenteAoBuscarPorId() {
        when(repository.findById(anyInt())).thenReturn(userOptional);
        User resultado = service.findById(ID);
        assertNotNull(resultado);
        assertEquals(User.class, resultado.getClass());
        assertAll("Validações do User",
                () -> assertEquals(ID, resultado.getId()),
                () -> assertEquals(NAME, resultado.getName()),
                () -> assertEquals(EMAIL, resultado.getEmail()),
                () -> assertEquals(PASSWORD, resultado.getPassword())
        );
        verify(repository).findById(ID);
    }

    @Test
    @DisplayName("Deve retornar uma exception de ObjectNotFound ao buscar por ID")
    void deveRetornarUmaExceptionAoBuscarPorId() {
        when(repository.findById(ID)).thenThrow(new ObjectNotFoundException("Objeto de id:" + ID + " não encontrado"));
//        try {
//            service.findById(ID);
//        } catch (RuntimeException ex){
//            assertEquals(ObjectNotFoundException.class, ex.getClass());
//            assertEquals("Objeto de id:"+ ID + " não encontrado", ex.getMessage());
//        }
        RuntimeException ex = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> service.findById(ID)
        );
        assertEquals(ObjectNotFoundException.class, ex.getClass());
        assertEquals("Objeto de id:" + ID + " não encontrado", ex.getMessage());
        verify(repository).findById(ID);
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void initObjects() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        userOptional = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}