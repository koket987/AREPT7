package co.edu.eci.arep.AREPT7.repository;

import co.edu.eci.arep.AREPT7.model.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamRepository extends JpaRepository<Stream, Long> {
    Stream findByName(String name);
}