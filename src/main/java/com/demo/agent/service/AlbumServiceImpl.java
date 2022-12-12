package com.demo.agent.service;

import com.demo.agent.domain.Album;
import com.demo.agent.domain.Celebrity;
import com.demo.agent.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public Album createAlbum(Celebrity celebrity, String albumName, int price, String image) {
        LocalDateTime createdTime = LocalDateTime.now();
        Album album = new Album(UUID.randomUUID(), celebrity, albumName, price, image, createdTime, createdTime);
        return albumRepository.insert(album);
    }

    @Override
    public Optional<Album> findAlbumByAlbumId(UUID albumId) {
        return albumRepository.findById(albumId);
    }

    @Override
    public List<Album> findAllAlbums() {
        return albumRepository.findAll();
    }

    @Override
    public void deleteByAlbumId(UUID albumId) {
        albumRepository.deleteById(albumId);
    }
}
