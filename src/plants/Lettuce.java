package plants;

import utilities.MessageUtility;

/**
 * @author baroh
 *
 */
public abstract class Lettuce extends Plant {
	public Lettuce() {
		MessageUtility.logConstractor("Lettuce", "Lettuce");
	}
}
