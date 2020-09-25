import org.moloshnikov.userdataservice.model.Role;
import org.moloshnikov.userdataservice.model.User;
import org.moloshnikov.userdataservice.service.RoleService;
import org.moloshnikov.userdataservice.service.UserService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-mvc.xml", "db/spring-db.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            UserService userService = appCtx.getBean(UserService.class);
            RoleService roleService = appCtx.getBean(RoleService.class);

//            for (User user : userService.getAll()) {
//                System.out.println(user);
//            }
//
//            for (Role role : roleService.getAll()) {
//                System.out.println(role);
//            }

//            roleService.create(new Role("sfsfsf"));
//            for (Role role : roleService.getAll()) {
//                System.out.println(role);
//            }


        }
    }
}
