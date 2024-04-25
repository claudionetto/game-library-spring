package com.claudionetto.gamelibrary;

import com.claudionetto.gamelibrary.enums.Role;
import com.claudionetto.gamelibrary.models.User;
import com.claudionetto.gamelibrary.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class GameLibraryApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public GameLibraryApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(GameLibraryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User userAdmin = new User();
		userAdmin.setName("Administrador");
		userAdmin.setEmail("admin@gmail.com");
		userAdmin.setUsername("admin");
		userAdmin.setPassword(passwordEncoder.encode("admin123"));
		userAdmin.setRoles(List.of(Role.ADMIN, Role.USER));

		userRepository.save(userAdmin);
	}
}
