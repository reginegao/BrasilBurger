package utils;

public class Validator {

	// Vérifie si une chaîne ressemble à un email simple
	public static boolean isEmail(String s) {
		if (s == null) return false;
		s = s.trim();
		return s.contains("@") && s.contains(".") && s.indexOf('@') > 0 && s.indexOf('.') > s.indexOf('@');
	}

	// Vérifie si le téléphone contient seulement des chiffres et a une longueur raisonnable
	public static boolean isPhone(String s) {
		if (s == null) return false;
		s = s.trim();
		if (s.length() < 6 || s.length() > 15) return false;
		for (char c : s.toCharArray()) {
			if (!Character.isDigit(c)) return false;
		}
		return true;
	}
}
