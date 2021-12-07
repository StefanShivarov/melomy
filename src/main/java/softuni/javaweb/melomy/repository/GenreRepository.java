package softuni.javaweb.melomy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.javaweb.melomy.model.entity.GenreEntity;
import softuni.javaweb.melomy.model.entity.enums.GenreNameEnum;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    GenreEntity findByName(GenreNameEnum name);
}
