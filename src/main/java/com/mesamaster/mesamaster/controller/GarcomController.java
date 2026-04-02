package com.mesamaster.mesamaster.controller;

import com.mesamaster.mesamaster.service.MesaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/garcom")
public class GarcomController {

    private final MesaService mesaService;

    public GarcomController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping
    public String index() {
        return "redirect:/garcom/mesas";
    }

    @GetMapping("/mesas")
    public String listarMesas(Model model) {
        model.addAttribute("mesas", mesaService.listarTodas());
        return "garcom/mapa-mesas";
    }
}
