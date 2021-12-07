package softuni.javaweb.melomy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.javaweb.melomy.model.entity.RoleEntity;
import softuni.javaweb.melomy.model.entity.enums.RoleNameEnum;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByName(RoleNameEnum name);
}
