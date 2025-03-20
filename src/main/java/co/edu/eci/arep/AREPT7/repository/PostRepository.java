package co.edu.eci.arep.AREPT7.repository;

import co.edu.eci.arep.AREPT7.model.Post;
import co.edu.eci.arep.AREPT7.model.Stream;
import co.edu.eci.arep.AREPT7.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserOrderByCreatedAtDesc(User user);
    List<Post> findByStreamOrderByCreatedAtDesc(Stream stream);
    List<Post> findByParentPostOrderByCreatedAtDesc(Post parentPost);
    List<Post> findAllByOrderByCreatedAtDesc();
}