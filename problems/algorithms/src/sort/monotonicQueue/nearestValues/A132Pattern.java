package sort.monotonicQueue.nearestValues;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 456
 *
 * ======
 *
 * Task.
 *
 * Given a sequence of n integers a1, a2, ..., an, a 132 pattern is a
 * subsequence ai, aj, ak such that i < j < k and ai < ak < aj. Design an
 * algorithm that takes a list of n numbers as input and checks whether there is
 * a 132 pattern in the list.
 *
 * ======
 *
 * Source: Leetcode
 */
public class A132Pattern {
	/**
	 * $INSERT_EXPLANATION.
	 */
	public static class Solution {
		public boolean find132pattern(int[] a) {
			MQ q = new MQ();
			int n = a.length;
			for (int i = n - 1; i >= 0; i--) {
				if (q.push(a[i])) {
					return true;
				}
			}
			return false;
		}

		private class MQ {
			private Deque<Integer> q = new ArrayDeque<>();
			private int prev = Integer.MIN_VALUE;

			public boolean push(int cur) {
				if (prev > cur) {
					return true;
				}
				while (!q.isEmpty() && q.peekLast() < cur) {
					prev = q.peekLast();
					q.removeLast();
				}
				q.addLast(cur);
				return false;
			}
		}
	}
}