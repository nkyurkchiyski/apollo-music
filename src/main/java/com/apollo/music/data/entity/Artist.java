package com.apollo.music.data.entity;

import com.apollo.music.data.commons.EntityConfiguration;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = EntityConfiguration.ARTIST_TABLE_NAME)
public class Artist extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = EntityConfiguration.NAME_COLUMN_NAME)
    private String name;

    @Lob
    @Column(name = EntityConfiguration.IMAGE_URL_COLUMN_NAME)
    private String imageUrl;

    @OneToMany(mappedBy = EntityConfiguration.ARTIST_FIELD_NAME, fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Album> albums = new ArrayList<>();

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

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(final List<Album> albums) {
        this.albums = albums;
    }
}
