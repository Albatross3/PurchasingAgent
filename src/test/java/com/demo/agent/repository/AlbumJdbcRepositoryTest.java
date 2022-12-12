package com.demo.agent.repository;

import com.demo.agent.domain.Album;
import com.demo.agent.domain.Celebrity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class AlbumJdbcRepositoryTest {

    @Autowired
    AlbumRepository albumRepository;

    @Test
    @Transactional
    @DisplayName("앨범이 정상적으로 저장된다")
    void insert() {
        // given
        Album album = new Album(UUID.randomUUID(), Celebrity.ITZY, "IT'z ICY", 15000, "image",LocalDateTime.now(), LocalDateTime.now());
        albumRepository.insert(album);

        // when
        var retrievedAlbum = albumRepository.findById(album.getAlbumId());

        // then
        assertThat(retrievedAlbum).isNotEmpty();
        assertThat(retrievedAlbum.get()).usingRecursiveComparison().isEqualTo(album);

    }

    @Test
    @DisplayName("앨범이 정상적으로 삭제된다")
    void deleteById() {
        // given
        Album album = new Album(UUID.randomUUID(), Celebrity.ITZY, "IT'z ICY", 15000, "image",LocalDateTime.now(), LocalDateTime.now());
        albumRepository.insert(album);

        // when
        albumRepository.deleteById(album.getAlbumId());

        // then
        List<Album> allAlbum = albumRepository.findAll();
        assertThat(allAlbum.size()).isEqualTo(0);
    }
}