package com.demo.agent.repository;

import com.demo.agent.domain.Album;
import com.demo.agent.domain.Celebrity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class AlbumJdbcRepository implements AlbumRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AlbumJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Album album) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("albumId", album.getAlbumId().toString());
        paramMap.put("celebrity", album.getCelebrity().toString());
        paramMap.put("albumName", album.getAlbumName());
        paramMap.put("price", album.getPrice());
        paramMap.put("image", album.getImage());
        paramMap.put("createdAt", album.getCreatedAt());
        paramMap.put("updatedAt", album.getUpdatedAt());
        return paramMap;
    }

    private UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private final RowMapper<Album> rowMapper = ((rs, rowNum) -> {
        UUID albumId = toUUID(rs.getBytes("album_id"));
        Celebrity celebrity = Celebrity.valueOf(rs.getString("celebrity"));
        String albumName = rs.getString("album_name");
        int price = rs.getInt("price");
        String image = rs.getString("image");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
        return new Album(albumId, celebrity, albumName, price, image, createdAt, updatedAt);
    });

    @Override
    public Album insert(Album album) {
        jdbcTemplate.update("INSERT INTO album(album_id, celebrity, album_name, price, image, created_at, updated_at) " +
                "VALUES(UUID_TO_BIN(:albumId), :celebrity, :albumName, :price, :image, :createdAt, :updatedAt)", toParamMap(album));
        return album;
    }

    @Override
    public Optional<Album> findById(UUID albumId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM album WHERE album_id = UUID_TO_BIN(:albumId)",
                Collections.singletonMap("albumId", albumId.toString()),
                rowMapper));
    }

    @Override
    public List<Album> findAll() {
        return jdbcTemplate.query("SELECT * FROM album", rowMapper);
    }

    @Override
    public void deleteById(UUID albumId) {
        jdbcTemplate.update("DELETE FROM album WHERE album_id = UUID_TO_BIN(:albumId)", Collections.singletonMap("albumId", albumId.toString()));
    }
}
