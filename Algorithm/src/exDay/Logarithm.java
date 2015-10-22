package exDay;

public class Logarithm {
	public static void main(String[] args) {
//		for (double d = 1.0; d <= 10.0; d++)
		    System.out.format(" log2(%2.0f) = %.16f%n", 5.0, logB(5.0, 2.0));
		    
	}
	public static double logB(double x, double base) {
		return Math.log(x) / Math.log(base);
	}
}









