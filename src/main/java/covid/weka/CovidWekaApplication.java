package covid.weka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// A anotação @SpringBootApplication indica que esta é uma classe de configuração e ajuda o Spring Boot a iniciar a configuração automática.
// Ela também habilita a varredura automática de componentes no pacote em que está localizada e nos subpacotes.
@SpringBootApplication
public class CovidWekaApplication {
    // Método principal que serve como ponto de entrada para a aplicação Spring Boot.
    public static void main(String[] args) {
        // Inicia a aplicação Spring Boot, passando a classe atual como argumento para configurar o contexto do Spring.
        SpringApplication.run(CovidWekaApplication.class, args);
    }
}
