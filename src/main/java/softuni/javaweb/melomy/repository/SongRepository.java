package softuni.javaweb.melomy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.javaweb.melomy.model.entity.SongEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {

    List<SongEntity> findAllByAlbum_Id(Long albumId);

    List<SongEntity> findAllByNameContaining(String input);

    void deleteAllByAlbum_Id(Long albumId);

    List<SongEntity> findAllByArtist_Id(Long artistId);

    Optional<SongEntity> findByNameAndAlbum_Name(String name, String albumName);
}
