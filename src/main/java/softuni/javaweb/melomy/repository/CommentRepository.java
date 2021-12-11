package softuni.javaweb.melomy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.javaweb.melomy.model.entity.CommentEntity;
import softuni.javaweb.melomy.model.entity.SongEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    void deleteAllBySong(SongEntity song);
}
