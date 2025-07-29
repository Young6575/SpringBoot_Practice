package edu.pnu.machine;

import org.springframework.stereotype.Component;

@Component
public class EspressoMachine implements CoffeeMachine {
	
	public String brew() {
		
		return "Espresso Machine에서 커피를 추출합니다";
	}

}
