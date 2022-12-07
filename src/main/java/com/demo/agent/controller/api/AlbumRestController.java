package com.demo.agent.controller.api;

import com.demo.agent.domain.Album;
import com.demo.agent.service.AlbumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AlbumRestController {
    private final AlbumService albumService;

    public AlbumRestController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/albums")
    public List<Album> getAllAlbums() {
        return albumService.findAllAlbums();
    }
}
