package testing;

public class Testing {
	public static void main(System args[]) {
		int velocity = 10;
		for(int i = 0; i < 1000; i++) {
			jump(velocity);
			int v2 = v2(velocity);
			velocity = v2;
			
		}
	}
	public static int jump(int velocity) {
		int yDistance = (int)(-velocity * 0.0016 + (1/2) * 9.8 * Math.pow(0.0016, 2));
		return yDistance;
	}
	public static int v2(int velocity) {
		return (int)(velocity + 9.8 * 0.0016);
	}
}