package exDay;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {
	
	public static void main(String[] args) {
		Fibonacci fib = new Fibonacci();
		System.out.println(fib.fibonacciRecursive(40));
		System.out.println(Fibonacci.fibonacciLoop(1000000));
		System.out.println(Fibonacci.fibonacciLoop(1000000));
	}
	/*
		Use Recursion It's normal but not fit for stack
	*/
	public int fibonacciRecursive(int n) {
	    if (n < 2)
	        return n;
	    return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
	}
	/*
		So I want to use cache.
		I don't want to use stack but I think it's more effective way.
	*/
	
	@SuppressWarnings("boxing")
	public static int fibonacciLoop(int n) {
		Map<Integer, Integer> cacheFib = new HashMap<Integer, Integer>();
		for (int i = 0; i <= n; i++) {
			if (i == 0) {
				cacheFib.put(i, 0);
				continue;
			}
			if (i == 1) {
				cacheFib.put(i, 1);
				continue;
			}
			int result = cacheFib.get(i-2) + cacheFib.get(i-1);
			cacheFib.put(i, result);
		}
		return cacheFib.get(n);
	}
	
	/*
		This is the best. What I think.
		FYI. I searched on the internet.
		I have to learn think different way to solve something.
	*/
	public static int fibonacciOther(int n) {
		int temp = 0;
		int cached_0 = 0;
		int cached_1 = 1;
		    if (n < 2)
		        return n;
		    for (int i = 2 ; i <= n ; i++ ) {
		    	temp = cached_1;
		    	cached_1 += cached_0;
		    	cached_0 = temp;
		    }
		    return cached_1;
	}
}
