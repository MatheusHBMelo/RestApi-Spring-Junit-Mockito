package br.com.mhbm.api.controllers.exceptions;

import br.com.mhbm.api.services.exceptions.DataIntegrityViolationException;
import br.com.mhbm.api.services.exceptions.EmptyListException;
import br.com.mhbm.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerExceptionHandlerTest {
    private static final Integer ID = 1;
    public static final String PATH = "/users/2";
    public static final String MESSAGE_EMPTY_LIST = "User list is empty";
    public static final String MESSAGE_EMAIL_JA_CADASTRADO = "E-mail já cadastrado no sistema";
    public static final String MESSAGE_OBJECT_NOT_FOUND = "Objeto de id:" + ID + " não encontrado";

    @InjectMocks
    private ControllerExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    @DisplayName("ObjectNotFoundException test")
    void deveRetornarUmaObjectNotFoundException() {
        ResponseEntity<StandardError> resposta = exceptionHandler.objectNotFound(
                new ObjectNotFoundException(MESSAGE_OBJECT_NOT_FOUND),
                new MockHttpServletRequest()
        );
        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(StandardError.class, resposta.getBody().getClass());
        assertEquals(MESSAGE_OBJECT_NOT_FOUND, resposta.getBody().getError());
        assertEquals(404, resposta.getBody().getStatus());
        assertNotEquals(LocalDateTime.now(), resposta.getBody().getTimestamp().minusSeconds(1));
        assertNotEquals(PATH, resposta.getBody().getPath());
    }

    @Test
    @Order(2)
    @DisplayName("EmptyListException test")
    void deveRetornarUmaEmptyListException() {
        ResponseEntity<StandardError> resposta = exceptionHandler.emptyList(
                new EmptyListException(MESSAGE_EMPTY_LIST),
                new MockHttpServletRequest()
        );
        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(StandardError.class, resposta.getBody().getClass());
        assertEquals(MESSAGE_EMPTY_LIST, resposta.getBody().getError());
        assertEquals(404, resposta.getBody().getStatus());
        assertNotEquals(LocalDateTime.now(), resposta.getBody().getTimestamp().minusSeconds(1));
        assertNotEquals(PATH, resposta.getBody().getPath());
    }

    @Test
    @Order(3)
    @DisplayName("DataIntegrityViolationException test")
    void deveRetornarUmaDataIntegrityViolationException() {
        ResponseEntity<StandardError> resposta = exceptionHandler.dataIntegrityViolation(
                new DataIntegrityViolationException(MESSAGE_EMAIL_JA_CADASTRADO),
                new MockHttpServletRequest()
        );
        assertNotNull(resposta);
        assertNotNull(resposta.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
        assertEquals(ResponseEntity.class, resposta.getClass());
        assertEquals(StandardError.class, resposta.getBody().getClass());
        assertEquals(MESSAGE_EMAIL_JA_CADASTRADO, resposta.getBody().getError());
        assertEquals(400, resposta.getBody().getStatus());
        assertNotEquals(LocalDateTime.now(), resposta.getBody().getTimestamp().minusSeconds(1));
        assertNotEquals(PATH, resposta.getBody().getPath());
    }
}