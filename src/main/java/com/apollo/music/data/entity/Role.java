package com.apollo.music.data.entity;

import com.apollo.music.data.commons.EntityConfiguration;

import java.util.Comparator;

public enum Role {
    ANON(EntityConfiguration.ANON, 0), USER(EntityConfiguration.USER, 10), ADMIN(EntityConfiguration.ADMIN, 20), SYSTEM(EntityConfiguration.SYSTEM, 30);

    private final String roleName;
    private final int weight;

    Role(final String roleName, final int weight) {
        this.roleName = roleName;
        this.weight = weight;
    }

    public static Role getHighestRole(final User user) {
        return user.getRoles().stream().max(Comparator.comparingInt(x -> x.weight)).orElse(ANON);
    }

    public String getRoleName() {
        return roleName;
    }

}
