package com.demo.agent.controller.dto;

import com.demo.agent.domain.Celebrity;

public record AlbumCreateRequest(Celebrity celebrity, String albumName, int price) {
}
