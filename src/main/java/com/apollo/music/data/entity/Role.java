package com.apollo.music.data.entity;

import com.apollo.music.data.commons.EntityConfiguration;

public enum Role {
    USER(EntityConfiguration.USER), ADMIN(EntityConfiguration.ADMIN), SYSTEM(EntityConfiguration.SYSTEM);

    private final String roleName;

    Role(final String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

}
