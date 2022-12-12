package com.demo.agent.controller.user;

import com.demo.agent.service.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final AlbumService albumService;

    public MainController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/main")
    public String viewMainPage(Model model) {
        var albums = albumService.findAllAlbums();
        model.addAttribute("albums", albums);

        return "main";
    }
}
