package com.mesamaster.mesamaster.config;

import com.mesamaster.mesamaster.entidades.Usuario;
import com.mesamaster.mesamaster.enums.Role;
import com.mesamaster.mesamaster.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
        // Criar Gestor se não existir
        if (usuarioRepository.findByLogin("gestor").isEmpty()) {
            Usuario gestor = new Usuario();
            gestor.setNome("Administrador do Sistema");
            gestor.setLogin("gestor");
            gestor.setSenha(passwordEncoder.encode("gestor123"));
            gestor.setRole(Role.GESTOR);
            usuarioRepository.save(gestor);
            System.out.println("Usuário 'gestor' criado com sucesso!");
        }

        // Criar Garçom se não existir
        if (usuarioRepository.findByLogin("garcom").isEmpty()) {
            Usuario garcom = new Usuario();
            garcom.setNome("Garçom do Turno");
            garcom.setLogin("garcom");
            garcom.setSenha(passwordEncoder.encode("garcom123"));
            garcom.setRole(Role.GARCOM);
            usuarioRepository.save(garcom);
            System.out.println("Usuário 'garcom' criado com sucesso!");
        }
    }
}
