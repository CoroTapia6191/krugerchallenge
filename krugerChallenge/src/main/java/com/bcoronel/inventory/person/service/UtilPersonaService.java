package com.bcoronel.inventory.person.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcoronel.inventory.util.ValidateStrings;

@Component
public class UtilPersonaService {

	@Autowired
	private ValidateStrings validate;

	public boolean validateRequieredFields(String id, String name, String lastname, String email) {
		return !validate.nullOrEmpty(id) && !validate.nullOrEmpty(name) && !validate.nullOrEmpty(lastname)
				&& !validate.nullOrEmpty(email);
	}

	public boolean validateEmail(String email) {
		if (email != null) {
			Pattern pattern = Pattern.compile(
					"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
			Matcher mather = pattern.matcher(email);
			return mather.find();
		}
		return false;
	}


	public boolean validarCedEcuador(String id) {

		byte sum = 0;
		try {
			if (id != null && id.trim().length() == 10 && id.matches("[+-]?\\d*(\\.\\d+)?")) {
				String[] data = id.split("");
				byte verifier = Byte.parseByte(data[0] + data[1]);
				if (verifier < 1 || verifier > 24) {
					return false;
				}
				byte[] digits = new byte[data.length];
				for (byte i = 0; i < digits.length; i++) {
					digits[i] = Byte.parseByte(data[i]);
				}
				if (digits[2] > 6) {
					return false;
				}
				for (byte i = 0; i < digits.length - 1; i++) {
					if (i % 2 == 0) {
						verifier = (byte) (digits[i] * 2);
						if (verifier > 9) {
							verifier = (byte) (verifier - 9);
						}
					} else {
						verifier = (byte) (digits[i] * 1);
					}
					sum = (byte) (sum + verifier);
				}
				if ((sum - (sum % 10) + 10 - sum) == digits[9]) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
