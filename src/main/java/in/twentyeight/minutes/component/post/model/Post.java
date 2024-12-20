package in.twentyeight.minutes.component.post.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.twentyeight.minutes.component.user.model.User;
import jakarta.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Post() {}

    public Post(String description, User user) {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
