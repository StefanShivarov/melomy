package softuni.javaweb.melomy.service.impl;

import org.springframework.stereotype.Service;
import softuni.javaweb.melomy.model.entity.RoleEntity;
import softuni.javaweb.melomy.model.entity.enums.RoleNameEnum;
import softuni.javaweb.melomy.repository.RoleRepository;
import softuni.javaweb.melomy.service.RoleService;

import java.util.Arrays;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initializeRoles() {

        if(roleRepository.count() == 0){

            Arrays.stream(RoleNameEnum.
                    values()).forEach(roleNameEnum -> roleRepository.save(new RoleEntity(roleNameEnum)));
        }

    }
}
