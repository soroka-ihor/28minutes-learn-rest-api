package in.twentyeight.minutes.component.post.model;

import in.twentyeight.minutes.component.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(User user);
}
