package softuni.javaweb.melomy.model.entity;

import softuni.javaweb.melomy.model.entity.enums.RoleNameEnum;

import javax.management.relation.Role;
import javax.persistence.*;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity{

    private RoleNameEnum name;

    public RoleEntity(){

    }

    public RoleEntity(RoleNameEnum name){
        this.name = name;
    }

    @Column(name = "name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    public RoleNameEnum getName() {
        return name;
    }

    public RoleEntity setName(RoleNameEnum name) {
        this.name = name;
        return this;
    }
}
