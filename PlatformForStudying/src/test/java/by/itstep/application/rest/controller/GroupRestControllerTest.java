package by.itstep.application.rest.controller;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import by.itstep.application.configuration.security.SecurityConfiguration;
import by.itstep.application.entity.User;
import by.itstep.application.rest.response.GroupResponse;
import by.itstep.application.service.group.GroupService;
import by.itstep.application.util.ApiResponse;
import by.itstep.application.util.GetEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
//@AutoConfigureMockMvc
//@WebMvcTest(SecurityConfiguration.class)
//@WithMockUser
//@Sql({
//        "classpath:sql/data.sql"
//})
public class GroupRestControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
    @InjectMocks
    private GroupRestController groupRestController;

    @Mock
    private GroupService groupService;
    @Mock
    private GetEntity getEntity;
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        groupService = new GroupService(getEntity);
    }

//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void testAddStudentToGroup() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/groups/1/addStudent/1")
//                        .contentType("application/json"))
//                .andExpect(status().isOk())
//                .andReturn();
//    }

    @Test
    public void testCreateGroup_Success() {
        User user = new User();
        String groupName = "Test Group";
        ApiResponse<String> responseExpected = ApiResponse.success("Group created successfully");

        when(groupService.createGroup(user, groupName)).thenReturn(responseExpected);

        ApiResponse<String> responseActual = groupRestController.createGroup(user, groupName);

        assertEquals(responseExpected, responseActual);
    }

    @Test
    public void testGetAllGroups_Success() {
        User user = new User();
        List<GroupResponse> groupResponses = List.of(new GroupResponse("Group 1"), new GroupResponse("Group 2"));
        ApiResponse<List<GroupResponse>> responseExpected = ApiResponse.success(groupResponses);

        when(groupService.getAllGroupResponses(user)).thenReturn(responseExpected);

        ApiResponse<List<GroupResponse>> responseActual = groupRestController.getAllGroups(user);

        assertEquals(responseExpected, responseActual);
    }

}