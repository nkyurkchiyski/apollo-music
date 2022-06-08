package com.apollo.music.data.entity;

import com.apollo.music.data.commons.EntityConfiguration;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = EntityConfiguration.ARTIST_TABLE_NAME)
public class Artist extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Column(name = EntityConfiguration.NAME_COLUMN_NAME)
    private String name;

    @Lob
    @Column(name = EntityConfiguration.IMAGE_URL_COLUMN_NAME)
    private String imageUrl;

    @OneToMany(mappedBy = EntityConfiguration.ARTIST_FIELD_NAME, fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    private Set<Album> albums = new LinkedHashSet<>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(final Set<Album> albums) {
        this.albums = albums;
    }

    public void removeAlbum(final Album album) {
        getAlbums().remove(album);
    }

    @Override
    public Map<String, Object> createFieldValueMap() {
        final Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        return map;
    }
}
