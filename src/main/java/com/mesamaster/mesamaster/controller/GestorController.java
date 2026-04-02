package com.mesamaster.mesamaster.controller;

import com.mesamaster.mesamaster.entidades.Produto;
import com.mesamaster.mesamaster.service.DashboardService;
import com.mesamaster.mesamaster.service.MesaService;
import com.mesamaster.mesamaster.service.ProdutoService;
import com.mesamaster.mesamaster.service.RelatorioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/gestor")
public class GestorController {

    private final MesaService mesaService;
    private final ProdutoService produtoService;
    private final DashboardService dashboardService;
    private final RelatorioService relatorioService;

    public GestorController(MesaService mesaService, ProdutoService produtoService, DashboardService dashboardService, RelatorioService relatorioService) {
        this.mesaService = mesaService;
        this.produtoService = produtoService;
        this.dashboardService = dashboardService;
        this.relatorioService = relatorioService;
    }

    @GetMapping
    public String index() {
        return "redirect:/gestor/dashboard";
    }

    // ── Dashboard ──────────────────────────────────────────────────────────

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        model.addAttribute("faturamentoDoDia", dashboardService.getFaturamentoDoDia());
        model.addAttribute("faturamentoUltimos7Dias", dashboardService.getFaturamentoUltimos7Dias());
        model.addAttribute("faturamentoMesVigente", dashboardService.getFaturamentoMesVigente());
        return "gestor/dashboard";
    }

    // ── Mesas ──────────────────────────────────────────────────────────────

    @GetMapping("/mesas")
    public String listarMesas(Model model) {
        model.addAttribute("mesas", mesaService.listarTodas());
        return "gestor/mesas";
    }

    @GetMapping("/mesas/nova")
    public String novaMesaForm() {
        return "gestor/mesa-form";
    }

    @PostMapping("/mesas/nova")
    public String salvarMesa(@RequestParam Integer numeroMesa) {
        mesaService.criarMesa(numeroMesa);
        return "redirect:/gestor/mesas";
    }

    // ── Produtos ───────────────────────────────────────────────────────────

    @GetMapping("/produtos")
    public String listarProdutos(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return "gestor/produtos";
    }

    @GetMapping("/produtos/novo")
    public String novoProdutoForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "gestor/produto-form";
    }

    @PostMapping("/produtos/novo")
    public String salvarProduto(@ModelAttribute Produto produto) {
        if (produto.getAtivo() == null) {
            produto.setAtivo(false);
        }
        produtoService.salvar(produto);
        return "redirect:/gestor/produtos";
    }

    // ── Relatórios ─────────────────────────────────────────────────────────

    @GetMapping("/relatorios/curva-abc")
    public String relatorioCurvaABC(Model model) {
        model.addAttribute("dados", relatorioService.gerarRelatorioCurvaABC());
        return "gestor/relatorio-curva-abc";
    }
}
