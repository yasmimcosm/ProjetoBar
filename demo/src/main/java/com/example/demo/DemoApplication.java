package com.example.demo;

import com.example.demo.yasmimJose.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// cria 2 usuários padrão no bootstrap (se não existirem)
	@Bean
	CommandLineRunner init(UsuarioService usuarioService) {
		return args -> {
			// admin@demo.com / senha: admin123
			try { usuarioService.criarUsuario("Admin", "admin@demo.com", "admin123", "ADMIN"); } catch (Exception ignored) {}
			// garcom@demo.com / senha: garcom123
			try { usuarioService.criarUsuario("Garcom", "garcom@demo.com", "garcom123", "GARCOM"); } catch (Exception ignored) {}
		};
	}
}
