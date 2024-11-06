package com.Alura.Literatura;

import com.Alura.Literatura.principal.Principal;
import com.Alura.Literatura.repository.AutoresRespositorio;
import com.Alura.Literatura.repository.LibroRespositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication  implements CommandLineRunner {

	@Autowired
	private AutoresRespositorio autoresRespositorio;
	@Autowired
	private LibroRespositorio libroRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal( autoresRespositorio, libroRepositorio);
		principal.muestraMenu();
	}
}
