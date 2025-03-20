package co.edu.eci.arep.AREPT7.controller;

import co.edu.eci.arep.AREPT7.model.Post;
import co.edu.eci.arep.AREPT7.model.Stream;
import co.edu.eci.arep.AREPT7.model.User;
import co.edu.eci.arep.AREPT7.service.PostService;
import co.edu.eci.arep.AREPT7.service.StreamService;
import co.edu.eci.arep.AREPT7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private StreamService streamService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPost(@RequestBody Map<String, Object> postData) {
        String content = (String) postData.get("content");
        Long userId = Long.valueOf(postData.get("userId").toString());
        Long parentPostId = postData.get("parentPostId") != null ?
                Long.valueOf(postData.get("parentPostId").toString()) : null;

        Map<String, Object> response = new HashMap<>();

        try {
            Optional<User> userOpt = userService.getUserById(userId);
            if (!userOpt.isPresent()) {
                throw new RuntimeException("User not found");
            }

            Stream mainStream = streamService.getMainStream();

            Post parentPost = null;
            if (parentPostId != null) {
                Optional<Post> parentPostOpt = postService.getPostById(parentPostId);
                if (!parentPostOpt.isPresent()) {
                    throw new RuntimeException("Parent post not found");
                }
                parentPost = parentPostOpt.get();
            }

            Post post = postService.createPost(content, userOpt.get(), mainStream, parentPost);

            Map<String, Object> postMap = new HashMap<>();
            postMap.put("id", post.getId());
            postMap.put("content", post.getContent());
            postMap.put("username", post.getUser().getUsername());
            postMap.put("createdAt", post.getCreatedAt().toString());
            postMap.put("parentPostId", post.getParentPost() != null ? post.getParentPost().getId() : null);

            response.put("success", true);
            response.put("post", postMap);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();

        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> postsList = new ArrayList<>();

        for (Post post : posts) {
            Map<String, Object> postMap = new HashMap<>();
            postMap.put("id", post.getId());
            postMap.put("content", post.getContent());
            postMap.put("username", post.getUser().getUsername());
            postMap.put("createdAt", post.getCreatedAt().toString());
            postMap.put("parentPostId", post.getParentPost() != null ? post.getParentPost().getId() : null);

            postsList.add(postMap);
        }

        response.put("success", true);
        response.put("posts", postsList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/replies")
    public ResponseEntity<Map<String, Object>> getPostReplies(@PathVariable Long id) {
        Optional<Post> postOpt = postService.getPostById(id);

        Map<String, Object> response = new HashMap<>();

        if (!postOpt.isPresent()) {
            response.put("success", false);
            response.put("message", "Post not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<Post> replies = postService.getRepliesByPost(postOpt.get());
        List<Map<String, Object>> repliesList = new ArrayList<>();

        for (Post reply : replies) {
            Map<String, Object> replyMap = new HashMap<>();
            replyMap.put("id", reply.getId());
            replyMap.put("content", reply.getContent());
            replyMap.put("username", reply.getUser().getUsername());
            replyMap.put("createdAt", reply.getCreatedAt().toString());

            repliesList.add(replyMap);
        }

        response.put("success", true);
        response.put("replies", repliesList);

        return ResponseEntity.ok(response);
    }
}
