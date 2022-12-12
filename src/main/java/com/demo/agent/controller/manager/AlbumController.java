package com.demo.agent.controller.manager;

import com.demo.agent.controller.dto.AlbumCreateRequest;
import com.demo.agent.domain.Album;
import com.demo.agent.domain.Celebrity;
import com.demo.agent.service.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/albums")
    public String viewAlbumPage(Model model) {
        List<Album> allAlbums = albumService.findAllAlbums();
        model.addAttribute("albums", allAlbums);
        return "album";
    }

    @GetMapping("/albums/new")
    public String viewCreateAlbumPage(Model model) {
        model.addAttribute("celebrities", Celebrity.values());
        return "create-album";
    }

    @PostMapping("/albums/new")
    public String returnAlbumPage(AlbumCreateRequest request) {
        albumService.createAlbum(request.celebrity(), request.albumName(), request.price(), request.image());
        return "redirect:/albums";
    }

    @DeleteMapping("/albums/{albumId}")
    public String deleteAlbum(@PathVariable UUID albumId) {
        albumService.deleteByAlbumId(albumId);
        return "redirect:/albums";
    }
}
