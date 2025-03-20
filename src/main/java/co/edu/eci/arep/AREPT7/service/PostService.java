package co.edu.eci.arep.AREPT7.service;

import co.edu.eci.arep.AREPT7.model.Post;
import co.edu.eci.arep.AREPT7.model.Stream;
import co.edu.eci.arep.AREPT7.model.User;
import co.edu.eci.arep.AREPT7.repository.PostRepository;
import co.edu.eci.arep.AREPT7.repository.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private StreamRepository streamRepository;

    public Post createPost(String content, User user, Stream stream, Post parentPost) {
        if (content.length() > 140) {
            throw new RuntimeException("Post content exceeds 140 characters");
        }

        Post post = new Post();
        post.setContent(content);
        post.setUser(user);
        post.setStream(stream);
        post.setParentPost(parentPost);
        post.setCreatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    public List<Post> getPostsByUser(User user) {
        return postRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public List<Post> getPostsByStream(Stream stream) {
        return postRepository.findByStreamOrderByCreatedAtDesc(stream);
    }

    public List<Post> getRepliesByPost(Post post) {
        return postRepository.findByParentPostOrderByCreatedAtDesc(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }
}