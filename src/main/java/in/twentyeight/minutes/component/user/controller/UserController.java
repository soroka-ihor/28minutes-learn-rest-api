package in.twentyeight.minutes.component.user.controller;

import in.twentyeight.minutes.component.post.model.Post;
import in.twentyeight.minutes.component.post.model.PostRepository;
import in.twentyeight.minutes.component.user.model.User;
import in.twentyeight.minutes.component.user.model.UserRepository;
import in.twentyeight.minutes.component.user.service.UserDaoService;
import in.twentyeight.minutes.exception.UserNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserDaoService service;
    private final UserRepository repository;
    private final PostRepository repositoryPost;
    private final PostRepository postRepository;

    public UserController(UserDaoService service, UserRepository repository, PostRepository repositoryPost, PostRepository postRepository) {
        this.service = service;
        this.repository = repository;
        this.repositoryPost = repositoryPost;
        this.postRepository = postRepository;
    }

    @GetMapping
    public List<EntityModel<User>> retrieveAllUsers() {
        var users = service.findAll();
        var list = new ArrayList<EntityModel<User>>();
        users.forEach(
                u -> {
                    var entityModel = EntityModel.of(u);
                    entityModel.add(
                            linkTo(
                                    WebMvcLinkBuilder.methodOn(this.getClass()).retrieveUser(u.getId())
                            ).withRel("self")
                    );
                    list.add(entityModel);
                }
        );
        return list;
    }


    @GetMapping("/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        EntityModel<User> entityModel = EntityModel.of(service.findOne(id));
        WebMvcLinkBuilder link = linkTo(WebMvcLinkBuilder.methodOn(getClass()).retrieveAllUsers());
        List<Link> links = new ArrayList<>();
        links.add(link.withRel("self"));
        entityModel.add(links);
        return entityModel;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping("/jpa")
    public List<User> retrieveUsers() {
        return repository.findAll();
    }

    @GetMapping("/jpa/{id}")
    public User getUser(@PathVariable long id) {
        return repository.findById(id).orElseThrow(
                () -> new UserNotFoundException()
        );
    }

    @GetMapping("/jpa/{id}/posts")
    public List<Post> retrieveUserPosts(@PathVariable long id) {
        return postRepository.findAllByUser(
                repository.findById(id).get()
        );
    }
}
