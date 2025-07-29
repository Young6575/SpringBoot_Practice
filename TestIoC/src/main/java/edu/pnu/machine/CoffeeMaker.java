package edu.pnu.machine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class CoffeeMaker {
	
	@Autowired
	private CoffeeMachine coffeeMachine;
	
	public void setCoffeeMachine(CoffeeMachine coffeeMachine) {
		this.coffeeMachine = coffeeMachine;
	}
	
	@PostConstruct
	public void makeCoffee() {
		System.out.println(coffeeMachine.brew());
	}
	
}