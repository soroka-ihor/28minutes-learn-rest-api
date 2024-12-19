package in.twentyeight.minutes.component.user.controller;

import in.twentyeight.minutes.component.user.model.User;
import in.twentyeight.minutes.component.user.service.UserDaoService;
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

    public UserController(UserDaoService service) {
        this.service = service;
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
}
