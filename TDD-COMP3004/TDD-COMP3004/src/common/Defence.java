package common;

public enum Defence {
	NONE,
	CHARGE,
	DODGE,
	DUCK;
	
	public static boolean isValid(String defence) {
		return (defence.equalsIgnoreCase(CHARGE.name()))
				|| (defence.equalsIgnoreCase(DODGE.name()))
				|| (defence.equalsIgnoreCase(DUCK.name()));
	}
	
	public static Defence getDefence(String defence) {
		if (defence.equalsIgnoreCase(Defence.CHARGE.name())) {
			return Defence.CHARGE;
		} else if (defence.equalsIgnoreCase(Defence.DODGE.name())) {
			return Defence.DODGE;
		} else if (defence.equalsIgnoreCase(Defence.DUCK.name())) {
			return Defence.DUCK;
		}
		return Defence.NONE;
	}
}
