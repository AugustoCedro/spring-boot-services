package org.example.springbootservices.auditTest;

import jakarta.transaction.Transactional;
import org.example.springbootservices.model.audit.User;
import org.example.springbootservices.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5432/guilda_db",
        "spring.datasource.username=postgres",
        "spring.datasource.password=adm",
        "spring.jpa.hibernate.ddl-auto=validate"
})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldLoadUsersWithRolesAndOrganization(){
        List<User> userList = userRepository.findAll();

        assertFalse(userList.isEmpty());

        User user = userList.getFirst();

        assertNotNull(user.getOrganization());

        assertNotNull(user.getUserRoleList());
        assertFalse(user.getUserRoleList().isEmpty());

        user.getUserRoleList().forEach(userRole -> {
            assertNotNull(userRole.getRole().getPermissionList());
        });
    }


}
