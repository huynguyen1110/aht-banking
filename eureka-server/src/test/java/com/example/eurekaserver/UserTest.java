//package com.example.userService;
//
//import com.example.userService.enitity.Role;
//import com.example.userService.enitity.User;
//import com.example.userService.enitity.UserRole;
//import com.example.userService.exception.UserNotFoundException;
//import com.example.userService.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@Rollback(value = false)
//public class UserTest {
//
//    @Autowired
//    private UserService userService;
//
//
//    @Test
//    public void findUser() throws UserNotFoundException {
//        User user = userService.getUserFromId(1L);
//        assertNotNull(user);
//    }
//
//    @Test
//    public void testCreateUser() throws Exception {
//        User mockUser = new User();
//        mockUser.setUsername("admin");
//        mockUser.setPassword("1234");
//        mockUser.setEmail("admin@mail.vn");
//        mockUser.setFull_name("Hieu Xuog");
//
//
//        // Mocking role and userRole
//        Role role = new Role();
//        role.setRole_id(46L);
//        role.setName("ADMIN");
//
//        UserRole userRole = new UserRole();
//        userRole.setUser(mockUser);
//        userRole.setRole(role);
//
//        Set<UserRole> roles = new HashSet<>();
//        roles.add(userRole);
//
//        User user = userService.createUser(mockUser, roles);
//
//    }
//
//}
