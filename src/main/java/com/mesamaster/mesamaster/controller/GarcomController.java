package com.mesamaster.mesamaster.controller;

import com.mesamaster.mesamaster.entidades.Comanda;
import com.mesamaster.mesamaster.entidades.Mesa;
import com.mesamaster.mesamaster.enums.StatusMesa;
import com.mesamaster.mesamaster.service.ComandaService;
import com.mesamaster.mesamaster.service.MesaService;
import com.mesamaster.mesamaster.service.PedidoService;
import com.mesamaster.mesamaster.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/garcom")
public class GarcomController {

    private final MesaService mesaService;
    private final ComandaService comandaService;
    private final PedidoService pedidoService;
    private final ProdutoService produtoService;

    public GarcomController(MesaService mesaService, ComandaService comandaService,
                            PedidoService pedidoService, ProdutoService produtoService) {
        this.mesaService = mesaService;
        this.comandaService = comandaService;
        this.pedidoService = pedidoService;
        this.produtoService = produtoService;
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

    @GetMapping("/mesas/{id}")
    public String detalharMesa(@PathVariable UUID id, Model model) {
        Mesa mesa = mesaService.buscarPorId(id);

        if (mesa.getStatus() == StatusMesa.LIVRE) {
            comandaService.abrirNovaComanda(id);
            mesa = mesaService.buscarPorId(id);
        }

        model.addAttribute("mesa", mesa);
        model.addAttribute("comandas", comandaService.listarComandasAbertasPorMesa(id));
        return "garcom/detalhe-mesa";
    }

    @PostMapping("/mesas/{id}/nova-comanda")
    public String abrirNovaComanda(@PathVariable UUID id) {
        comandaService.abrirNovaComanda(id);
        return "redirect:/garcom/mesas/" + id;
    }

    @GetMapping("/comandas/{id}")
    public String detalharComanda(@PathVariable UUID id, Model model) {
        Comanda comanda = comandaService.buscarPorId(id);
        model.addAttribute("comanda", comanda);
        model.addAttribute("produtos", produtoService.listarAtivos());
        return "garcom/detalhe-comanda";
    }

    @PostMapping("/comandas/{id}/lancar-produto")
    public String lancarProduto(@PathVariable UUID id,
                                @RequestParam UUID produtoId,
                                @RequestParam int quantidade) {
        pedidoService.adicionarItemNaComanda(id, produtoId, quantidade);
        return "redirect:/garcom/comandas/" + id;
    }
}
