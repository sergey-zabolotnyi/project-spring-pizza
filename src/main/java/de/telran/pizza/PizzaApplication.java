package de.telran.pizza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения для заказа пиццы.
 */
@SpringBootApplication
public class PizzaApplication {

	/**
	 * Точка входа в приложение.
	 *
	 * @param args Аргументы командной строки.
	 */
	public static void main(String[] args) {
		SpringApplication.run(PizzaApplication.class, args);
	}

}
