package common;

public enum Attack {
	NONE,
	THRUST,
	SWING,
	SMASH;

	
	public static boolean isValid(String attack) {
		return (attack.equalsIgnoreCase(THRUST.name()))
				|| (attack.equalsIgnoreCase(SWING.name()))
				|| (attack.equalsIgnoreCase(SMASH.name()));
	}
	
	
	
	public static Attack getAttack(String attack) {
		if (attack.equalsIgnoreCase(THRUST.name())) {
			return THRUST;
		} else if (attack.equalsIgnoreCase(SWING.name())) {
			return SWING;
		} else if (attack.equalsIgnoreCase(SMASH.name())) {
			return SMASH;
		}
		return NONE;
	}

	
}
