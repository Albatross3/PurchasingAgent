package com.demo.agent.service;

import com.demo.agent.domain.Album;
import com.demo.agent.domain.Celebrity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumService {
    Album createAlbum(Celebrity celebrity, String albumName, int price);

    Optional<Album> findAlbumByAlbumId(UUID albumId);

    List<Album> findAllAlbums();

}
