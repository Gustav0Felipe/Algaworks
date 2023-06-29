package com.inicio.teste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TesteApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteApplication.class, args);
	}

}
/*
 * repositórios é uma classe que implementa metodos que fazem as operações de persistencia de dados.
 * não é uma boa pratica fazer isto direto no controller, tende a gerar muito codigo espaguete, dificil manter e evoluir
 * por isso devo separar as responsabilidades, criando um componente que tem a responsabilidade de acessar os dados,
 * e a classe controller vai usar este componente.
 */