package softuni.javaweb.melomy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.javaweb.melomy.model.entity.AlbumEntity;
import softuni.javaweb.melomy.model.entity.ArtistEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {

    List<AlbumEntity> findAllByNameContaining(String input);

    List<AlbumEntity> findAllByArtistOrderByYearDesc(ArtistEntity artist);

    Optional<AlbumEntity> findByNameAndArtist_Id(String name, Long artistId);
}
