package br.com.mhbm.api.controllers;

import br.com.mhbm.api.models.User;
import br.com.mhbm.api.models.dto.UserDTO;
import br.com.mhbm.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    public static final int INDEX = 0;
    @InjectMocks
    private UserController controller;

    @Mock
    private UserServiceImpl service;

    @Mock
    private ModelMapper mapper;

    public static final int ID = 1;
    public static final String NAME = "Matheus";
    public static final String EMAIL = "matheus@email.com";
    public static final String PASSWORD = "12345";

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        initObjects();
    }

    @Test
    @DisplayName("FindById test")
    @Order(1)
    void whenFindByIdThenReturnUserDTO() {
        when(service.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);
        ResponseEntity<UserDTO> resposta = controller.findById(ID);
        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(UserDTO.class, resposta.getBody().getClass());
        assertAll("Validações do User",
                () -> assertEquals(ID, resposta.getBody().getId()),
                () -> assertEquals(NAME, resposta.getBody().getName()),
                () -> assertEquals(EMAIL, resposta.getBody().getEmail()),
                () -> assertEquals(PASSWORD, resposta.getBody().getPassword())
        );
        verify(service, times(1)).findById(anyInt());
        verify(mapper, times(1)).map(any(), any());
        verifyNoMoreInteractions(service);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    @DisplayName("FindAll test")
    @Order(2)
    void findAll() {
        when(service.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(), any())).thenReturn(userDTO);
        ResponseEntity<List<UserDTO>> resposta = controller.findAll();
        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(ArrayList.class, resposta.getBody().getClass());
        assertEquals(UserDTO.class, resposta.getBody().get(INDEX).getClass());
        assertAll("Validações de User",
                () -> assertEquals(ID, resposta.getBody().get(INDEX).getId()),
                () -> assertEquals(NAME, resposta.getBody().get(INDEX).getName()),
                () -> assertEquals(EMAIL, resposta.getBody().get(INDEX).getEmail()),
                () -> assertEquals(PASSWORD, resposta.getBody().get(INDEX).getPassword())
        );
        verify(service, times(1)).findAll();
        verify(mapper, times(1)).map(any(), any());
        verifyNoMoreInteractions(service);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    @DisplayName("Create test")
    @Order(3)
    void create() {
        when(service.create(any())).thenReturn(user);
        ResponseEntity<UserDTO> resposta = controller.create(userDTO);
        assertNotNull(resposta);
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertNotNull(resposta.getHeaders().get("Location"));
        verify(service, times(1)).create(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Update test")
    @Order(4)
    void update() {
        when(service.update(any())).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);
        ResponseEntity<UserDTO> resposta = controller.update(ID, userDTO);
        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(UserDTO.class, resposta.getBody().getClass());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertAll("Validações de User",
                () -> assertEquals(ID, resposta.getBody().getId()),
                () -> assertEquals(NAME, resposta.getBody().getName()),
                () -> assertEquals(EMAIL, resposta.getBody().getEmail()),
                () -> assertEquals(PASSWORD, resposta.getBody().getPassword())
        );
        verify(service, times(1)).update(any());
        verify(mapper, times(1)).map(any(), any());
        verifyNoMoreInteractions(service);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    @DisplayName("Delete test")
    @Order(5)
    void delete() {
        doNothing().when(service).delete(anyInt());
        ResponseEntity<UserDTO> resposta = controller.delete(ID);
        assertNotNull(resposta);
        assertNull(resposta.getBody());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
        verify(service, times(1)).delete(anyInt());
        verifyNoMoreInteractions(service);
    }

    private void initObjects() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}