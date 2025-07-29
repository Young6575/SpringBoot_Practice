package edu.pnu.machine;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class DripCoffeeMachine implements CoffeeMachine {
	
	public String brew() {
		
		return "Drip Coffee Machine에서 커피를 추출합니다";
	}

}
