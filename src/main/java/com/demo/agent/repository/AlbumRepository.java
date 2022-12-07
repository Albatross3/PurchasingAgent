package com.demo.agent.repository;

import com.demo.agent.domain.Album;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumRepository {

    Album insert(Album album);

    Optional<Album> findById(UUID albumId);

    List<Album> findAll();

    void deleteById(UUID albumId);
}
