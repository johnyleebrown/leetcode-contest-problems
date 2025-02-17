package queue.monotonicQueue.nearestValues;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * 334
 */
public class IncreasingTripletSubsequence {

	/**
	 * Using MQ and set. Keeping a decreasing queue, going from the back.
	 */
	public static class Solution {

		public boolean increasingTriplet(int[] a) {
			MQ mq = new MQ();
			for (int i = a.length - 1; i >= 0; i--) {
				mq.push(a[i]);
				if (mq.q.size() == 3) {
					return true;
				}
			}
			return false;
		}

		private static class MQ {

			private Deque<Integer> q = new ArrayDeque<>();
			private Set<Integer> s = new HashSet<>();

			public void push(int cur) {
				if (!s.add(cur)) return;
				while (q.size() > 0 && q.peekLast() <= cur) {
					s.remove(q.peekLast());
					q.removeLast();
				}
				q.addLast(cur);
			}
		}
	}
}
