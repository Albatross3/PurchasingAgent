package com.demo.agent.domain;

import java.util.UUID;

public class OrderItem {
    private final UUID albumId;
    private final String albumName;
    private final Celebrity celebrity;
    private final int price;
    private int quantity;

    public OrderItem(UUID albumId, String albumName, Celebrity celebrity, int price, int quantity) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.celebrity = celebrity;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public Celebrity getCelebrity() {
        return celebrity;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
