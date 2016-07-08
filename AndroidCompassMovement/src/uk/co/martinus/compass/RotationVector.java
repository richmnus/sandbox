package uk.co.martinus.compass;

public class RotationVector {

	private static final int HALF_CIRCLE_DEGREES = 180;
	private static final int FULL_CIRCLE_DEGREES = 360;
	private static final int MIN_STEP_DEGREES = 1;
	private static final int MAX_STEP_DEGREES = 360;

	/** A deadband value of 0 disables the function */
	private static final int STEP_SIZE_RATIO = 2;

	/** A deadband value of 0 disables the function */
	private static final int DEADBAND_DEGREES = 0;

	/**
	 * Computes the step size and direction for moving the compass needle from
	 * its currentBearing value to the targetBearing value. Assumes the values
	 * range from -180 (due South) clockwise through 0 degrees (due North)
	 * around to +179 degrees. Note that when using the returned step value to
	 * rotate a compass bitmap, the sign may have to be reversed in the calling
	 * method because the bitmap is rotated in the opposite direction.
	 * 
	 * @param targetBearing
	 *            Target bearing in degrees (-180 to +179)
	 * @param currentBearing
	 *            Current bearing in degrees (-180 to +179)
	 * @return The step size, with the polarity indicating direction - positive
	 *         is clockwise and negative is anti-clockwise
	 */
	public static int getStep(int targetBearing, int currentBearing) {

		// Calculate the delta and its modulus
		int delta = targetBearing - currentBearing;
		int modDelta = delta < 0 ? -delta : delta;

		// Apply deadband
		if (modDelta <= DEADBAND_DEGREES) {
			return 0;
		}

		// Direction is an XOR function
		boolean posDelta = delta >= 0;
		boolean largeDelta = modDelta > HALF_CIRCLE_DEGREES;
		boolean direction = posDelta ^ largeDelta;

		// Calculate step size, apply min and max
		modDelta = largeDelta ? FULL_CIRCLE_DEGREES - modDelta : modDelta;
		int step = modDelta / STEP_SIZE_RATIO;
		step = step < MIN_STEP_DEGREES ? MIN_STEP_DEGREES : step;
		step = step > MAX_STEP_DEGREES ? MAX_STEP_DEGREES : step;

		// Apply direction to the step value
		step = direction ? step : -step;

		return step;
	}

}
