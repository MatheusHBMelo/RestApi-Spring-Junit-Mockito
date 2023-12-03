package br.com.mhbm.api.services.impl;

import br.com.mhbm.api.models.User;
import br.com.mhbm.api.models.dto.UserDTO;
import br.com.mhbm.api.repositories.UserRepository;
import br.com.mhbm.api.services.exceptions.EmptyListException;
import br.com.mhbm.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {

    public static final int ID = 1;
    public static final String NAME = "Matheus";
    public static final String EMAIL = "matheus@email.com";
    public static final String PASSWORD = "12345";
    public static final int INDEX = 0;
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
    @DisplayName("FindById Validate")
    @Order(1)
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
    @DisplayName("FindById Exception")
    @Order(2)
    void deveRetornarUmaExceptionAoBuscarPorIdInexistente() {
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
    @DisplayName("FindAll Validate")
    @Order(3)
    void deveRetornarUmaListaDeUsuariosValidos() {
        when(repository.findAll()).thenReturn(List.of(user));
        List<User> users = service.findAll();
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(User.class, users.get(INDEX).getClass());
        assertAll("Validações do User",
                () -> assertEquals(ID, users.get(INDEX).getId()),
                () -> assertEquals(NAME, users.get(INDEX).getName()),
                () -> assertEquals(EMAIL, users.get(INDEX).getEmail()),
                () -> assertEquals(PASSWORD, users.get(INDEX).getPassword())
        );
        verify(repository).findAll();
    }

    @Test
    @DisplayName("FindAll Exception")
    @Order(4)
    void deveRetornarUmaExceptionSeListaDeUsuariosEstiverVazia() {
        when(repository.findAll()).thenThrow(new EmptyListException("User list is empty"));
        RuntimeException ex = Assertions.assertThrows(EmptyListException.class,
                () -> service.findAll()
        );
        assertEquals(EmptyListException.class, ex.getClass());
        assertEquals("User list is empty", ex.getMessage());
        verify(repository).findAll();
    }

    @Test
    @Order(5)
    void create() {
    }

    @Test
    @Order(6)
    void update() {
    }

    @Test
    @Order(7)
    void delete() {
    }

    private void initObjects() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        userOptional = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}