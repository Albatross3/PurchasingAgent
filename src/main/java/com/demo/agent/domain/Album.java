package com.demo.agent.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Album {
    private final UUID albumId;
    private final Celebrity celebrity;
    private final String albumName;
    private int price;
    private String image;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Album(UUID albumId, Celebrity celebrity, String albumName, int price, String image, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.albumId = albumId;
        this.celebrity = celebrity;
        this.albumName = albumName;
        this.price = price;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getAlbumId() {
        return albumId;
    }

    public Celebrity getCelebrity() {
        return celebrity;
    }

    public String getAlbumName() {
        return albumName;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
