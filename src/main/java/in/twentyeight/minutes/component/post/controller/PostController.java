package in.twentyeight.minutes.component.post.controller;

import in.twentyeight.minutes.component.post.model.PostRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository repository;

    public PostController(final PostRepository repository) {
        this.repository = repository;
    }
}
