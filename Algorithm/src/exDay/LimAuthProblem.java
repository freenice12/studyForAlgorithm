package exDay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LimAuthProblem {

	public static void main(String[] args) {
		List<Integer> nums = new ArrayList<>();
		nums.add(1);
		nums.add(2);
//		nums.add(3);
		int targetNum = 4;
		
		Collections.sort(nums, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}
		});
		StringBuffer buffer = new StringBuffer();
		Set<String> resultSet = new HashSet<>(); 
		giveMeTheSingtonSolution(nums, targetNum, resultSet, buffer);
		System.out.println(resultSet);
		giveMeTheSolution(nums, targetNum, resultSet, buffer);
		System.out.println(resultSet);
	}

	private static void giveMeTheSingtonSolution(List<Integer> nums,
			int targetNum, Set<String> resultSet, StringBuffer buffer) {
		for (Integer num : nums) {
			int result = 0;
			int subTarget = targetNum - result;
			while(subTarget > 0 && subTarget >= num) {
				buffer.append(num).append(",");
				result += num;
				subTarget = targetNum - result;
			}
			if (nums.contains(subTarget)) {
				buffer.append(subTarget).append(",");
				subTarget = 0;
			}
			if (subTarget == 0) 
				resultSet.add(buffer.substring(0, buffer.length() - 1).toString());
			buffer.delete(0, buffer.length());
		}
	}

	private static void giveMeTheSolution(List<Integer> nums,
			int targetNum, Set<String> resultSet, StringBuffer buffer) {
		for (int i=0; i<nums.size(); i++) {
			int num = nums.get(i);
			int subTarget = targetNum - num;
			int result = 0;
			for (int j=i+1; j<nums.size(); j++) {
				int nextNum = nums.get(j);
				while (subTarget >= nextNum) {
					buffer.append(nextNum).append(",");
					result += num;
					subTarget = targetNum - result;
				}
				buffer.append(num).append(",");
				if (subTarget == 0)
					resultSet.add(buffer.substring(0, buffer.length() - 1).toString());
				buffer.delete(0, buffer.length());
			}
		}
	}

}
