package com.mcts.cms.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_user_permission")
@NoArgsConstructor
public class UserPermission {

    @EmbeddedId
    private UserPermissionPK id = new UserPermissionPK();

    public UserPermission(User user, Permission permission) {
        id.setUser(user);
        id.setPermission(permission);
    }

    public User getUser() {
        return id.getUser();
    }

    public void setUser(User user) {
        id.setUser(user);
    }

    public Permission getPermission() {
        return id.getPermission();
    }

    public void setPermission(Permission permission) {
        id.setPermission(permission);
    }


}
