package in.twentyeight.minutes.component.user.service;

import in.twentyeight.minutes.component.user.model.User;
import in.twentyeight.minutes.exception.UserNotFoundException;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<User>();

    static {
        users.add(new User(1, "Adam", LocalDate.now().minusYears(30)));
        users.add(new User(2, "Eve", LocalDate.now().minusYears(15)));
        users.add(new User(3, "Jim", LocalDate.now().minusYears(14)));
        users.add(new User(4, "Ihor", LocalDate.now().minusYears(22)));
        users.add(new User(5, "Jameson", LocalDate.now().minusYears(88)));
    }

    public User findOne(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElseThrow(
                () -> new UserNotFoundException()
        );
    }

    public List<User> findAll() {
        return users;
    }

    public User createUser(User user) {
        users.add(user);
        return user;
    }
}
