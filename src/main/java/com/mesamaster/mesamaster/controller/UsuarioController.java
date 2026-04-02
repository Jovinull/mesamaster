package com.mesamaster.mesamaster.controller;

import com.mesamaster.mesamaster.entidades.Usuario;
import com.mesamaster.mesamaster.enums.Role;
import com.mesamaster.mesamaster.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/gestor/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "gestor/usuarios";
    }

    @GetMapping("/novo")
    public String novoUsuarioForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", Role.values());
        return "gestor/usuario-form";
    }

    @PostMapping("/novo")
    public String salvarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.salvar(usuario);
        return "redirect:/gestor/usuarios";
    }

    @PostMapping("/{id}/excluir")
    public String excluirUsuario(@PathVariable UUID id, Authentication authentication,
                                  RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario.getLogin().equals(authentication.getName())) {
            redirectAttributes.addFlashAttribute("erro", "Você não pode excluir sua própria conta.");
            return "redirect:/gestor/usuarios";
        }
        usuarioService.excluir(id);
        return "redirect:/gestor/usuarios";
    }
}
