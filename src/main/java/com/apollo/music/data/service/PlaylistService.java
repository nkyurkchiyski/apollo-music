package com.apollo.music.data.service;

import com.apollo.music.data.commons.EntityConfiguration;
import com.apollo.music.data.commons.GeneralServiceException;
import com.apollo.music.data.entity.Playlist;
import com.apollo.music.data.entity.Role;
import com.apollo.music.data.entity.Song;
import com.apollo.music.data.entity.SongPlaylist;
import com.apollo.music.data.entity.User;
import com.apollo.music.data.repository.PlaylistRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PlaylistService extends AbstractEntityService<Playlist> {
    private final PlaylistRepository playlistRepository;
    private final SongService songService;

    @Autowired
    public PlaylistService(final PlaylistRepository playlistRepository, final SongService songService) {
        this.playlistRepository = playlistRepository;
        this.songService = songService;
    }

    @Override
    protected JpaRepository<Playlist, String> getRepository() {
        return playlistRepository;
    }

    public LikeActionResult likeSong(final User user, final Song song) {
        final AtomicReference<LikeActionResult> result = new AtomicReference<>(LikeActionResult.LIKED);
        final Playlist probe = new Playlist();
        probe.setName(EntityConfiguration.LIKED_SONGS);
        probe.setCreatedBy(Role.SYSTEM);
        probe.setUser(user);
        final Optional<Playlist> likedSongsPlaylistOpt = playlistRepository.getLikedSongsPlaylist(user.getId());

        final Playlist likedSongsPlaylist;
        if (likedSongsPlaylistOpt.isEmpty()) {
            likedSongsPlaylist = update(probe);
        } else {
            likedSongsPlaylist = likedSongsPlaylistOpt.get();
        }
        addSongToPlaylist(likedSongsPlaylist, song, sp -> {
            removeSongFromPlaylist(likedSongsPlaylist, sp);
            result.set(LikeActionResult.DISLIKED);
        });

        if (result.get().equals(LikeActionResult.DISLIKED)) {
            songService.dislike(song);
        } else {
            songService.like(song);
        }

        return result.get();
    }

    public void addSongToPlaylist(final Playlist playlist, final Song song) {
        addSongToPlaylist(playlist, song, pl -> {
            throw new GeneralServiceException("Song is already part of the playlist!");
        });
    }

    private void addSongToPlaylist(final Playlist playlist, final Song song, Consumer<SongPlaylist> alreadyExistsConsumer) {
        final SongPlaylist alreadyExistingEntry = playlist.getSongs()
                .stream()
                .filter(sp -> sp.getSong().equals(song))
                .findFirst()
                .orElse(null);
        if (alreadyExistingEntry != null) {
            alreadyExistsConsumer.accept(alreadyExistingEntry);
            return;
        }
        final SongPlaylist songPlaylist = new SongPlaylist(song, playlist, playlist.getSongs().size() + 1);
        playlist.addSong(songPlaylist);
        update(playlist);
    }

    public void removeSongFromPlaylist(final Playlist playlist, final Song song) {
        final SongPlaylist songPlaylist = playlist.getSongs()
                .stream()
                .filter(sp -> sp.getSong().equals(song))
                .findFirst().orElseThrow(() -> new GeneralServiceException("Song is not part of the playlist!"));

        removeSongFromPlaylist(playlist, songPlaylist);
    }

    private void removeSongFromPlaylist(final Playlist playlist, final SongPlaylist songPlaylist) {
        if (playlist.getSongs().contains(songPlaylist)) {
            playlist.removeSong(songPlaylist);
            playlistRepository.deleteSongPlaylist(songPlaylist);
            recalculateTrackNumbers(playlist);
            update(playlist);
        }
    }

    private void recalculateTrackNumbers(final Playlist playlist) {
        final AtomicInteger currentNum = new AtomicInteger(1);
        playlist.getSongs().forEach(sp -> sp.setTrackNumber(currentNum.getAndIncrement()));
    }


    public Stream<Playlist> fetchByUserAndName(final Pageable paging, final User user, final Optional<String> name) {
        final String filterToUse = name.isEmpty() ? null : StringUtils.firstNonBlank(name.get(), null);
        return playlistRepository.findAllByUserId(paging, user.getId(), filterToUse).stream();
    }


    public int countByUserAndName(final User user, final Optional<String> name) {
        final String filterToUse = name.isEmpty() ? null : StringUtils.firstNonBlank(name.get(), null);
        return (int) playlistRepository.countByUserId(user.getId(), filterToUse);
    }

    public List<Song> getLikedSongsByUser(final User user) {
        final Optional<Playlist> playlistOpt = playlistRepository.getLikedSongsPlaylist(user.getId());
        if (playlistOpt.isEmpty()) {
            return Collections.emptyList();
        }

        return playlistOpt.get().getSongs().stream().map(SongPlaylist::getSong).collect(Collectors.toList());
    }

    public List<Playlist> findByUser(final User user) {
        return playlistRepository.findAllByUserId(user.getId());
    }
}
