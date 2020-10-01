package org.moloshnikov.userdataservice.web;

import org.junit.jupiter.api.Test;
import org.moloshnikov.userdataservice.model.User;
import org.moloshnikov.userdataservice.service.UserService;
import org.moloshnikov.userdataservice.util.exception.IllegalRequestDataException;
import org.moloshnikov.userdataservice.util.exception.Info;
import org.moloshnikov.userdataservice.util.exception.NotFoundException;
import org.moloshnikov.userdataservice.web.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.moloshnikov.userdataservice.TestData.*;
import static org.moloshnikov.userdataservice.TestUtil.readFromJson;
import static org.moloshnikov.userdataservice.web.ExceptionInfoHandler.bindingResultAsArray;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(locations = "classpath:spring-config.xml")
@Transactional
class UserControllerTest {
    private static final String REST_URL = UserController.REST_URL + '/';

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }

    public ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER_LOGIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> userService.get(USER_LOGIN));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + 1))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithLocation() throws Exception {
        User newUser = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUser)))
                .andExpect(status().isCreated());

        Info status = readFromJson(action, Info.class);
        assertTrue(status.isSuccess());
        assertNull(status.getErrors());
        User created = userService.get(NEW_USER_LOGIN);
        assertEquals(newUser, created);
    }

    @Test
    void createInvalid() throws Exception {
        User expected = new User(null, null, "tttT");
        System.out.println(expected);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(Arrays.equals(errorArr, bindingResultAsArray(((MethodArgumentNotValidException)
                        Objects.requireNonNull(result.getResolvedException())).getBindingResult()))))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        User expected = new User("Test user", USER_LOGIN, "Password1", USER_ROLE, OPERATOR_ROLE);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalRequestDataException))
                .andExpect(result -> assertEquals("User with login: userlogin already exist.", Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andDo(print());
    }

    @Test
    void updateInvalid() throws Exception {
        User updated = new User(USER);
        updated.setName("");
        perform(MockMvcRequestBuilders.put(REST_URL + USER_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void update() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        perform(MockMvcRequestBuilders.put(REST_URL + USER_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}