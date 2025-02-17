package queue.monotonicQueue.nearestValues;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 862
 */
public class ShortestSubarrayWithSumAtLeastK {

	class Solution {

		public int shortestSubarray(int[] A, int K) {
			IMQ q = new IMQ(K);
			q.push(new Item(0, -1));
			long cur = 0, prev = 0;

			for (int i = 0; i < A.length; i++) {
				cur = prev + A[i];
				prev = cur;
				q.push(new Item(cur, i));
			}

			return q.min != Integer.MAX_VALUE ? q.min : -1;
		}

		// increasing monotone queue
		private class IMQ {

			private final Deque<Item> q = new ArrayDeque<>();
			private final int K;
			private int min = Integer.MAX_VALUE;

			public IMQ(int K) {
				this.K = K;
			}

			private void push(Item newItem) {
				while (!q.isEmpty() && newItem.val < q.peekLast().val) {
					q.removeLast();
				}

				// sliding window (minimum) part - bigger subarray satisfies our condition
				// that's why we can short it (move left pointer)
				// prefixSum[j] - prefixSum[i] >= K => prefixSum[i] <= prefixSum[j] - K
				// while pre[r] - pre[l] >= k
				while (!q.isEmpty() && newItem.val - q.peekFirst().val >= K) {
					min = Math.min(min, newItem.ind - q.peekFirst().ind);
					q.removeFirst();
				}

				q.addLast(newItem);
			}
		}

		private class Item {

			long val;
			int ind;

			Item(long val, int ind) {
				this.val = val;
				this.ind = ind;
			}
		}
	}
}