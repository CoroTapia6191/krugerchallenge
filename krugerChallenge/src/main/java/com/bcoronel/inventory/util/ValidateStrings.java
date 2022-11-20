package com.bcoronel.inventory.util;

import org.springframework.stereotype.Component;

@Component
public class ValidateStrings {

	
	public boolean nullOrEmpty(String valor) {
        return valor == null || valor.isEmpty();
    }
	
	public boolean validChar(String cadena) {
		if (cadena != null && !cadena.isEmpty()) {
			for (int x = 0; x < cadena.length(); x++) {
				char c = cadena.charAt(x);
				if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
