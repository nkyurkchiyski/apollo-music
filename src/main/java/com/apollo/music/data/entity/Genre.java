package com.apollo.music.data.entity;

import com.apollo.music.data.commons.EntityConfiguration;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = EntityConfiguration.GENRE_TABLE_NAME)
public class Genre extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Column(name = EntityConfiguration.NAME_COLUMN_NAME)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
