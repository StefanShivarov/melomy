package softuni.javaweb.melomy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.javaweb.melomy.model.entity.SongEntity;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {
}
