package hashtable.geometry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 939
 */
public class MinimumAreaRectangle {

	public static class Solution {

		public int minAreaRect(int[][] points) {
			Map<Integer, Set<Integer>> map = new HashMap<>();
			for (int[] p : points) {
				map.putIfAbsent(p[0], new HashSet<>());
				map.get(p[0]).add(p[1]);
			}
			int min = Integer.MAX_VALUE;
			for (int[] p1 : points) {
				for (int[] p2 : points) {
					if (p1[0] == p2[0] || p1[1] == p2[1]) {
						continue;
					}

					int localArea = Math.abs(p1[0] - p2[0]) * Math.abs(p1[1] - p2[1]);
					if (localArea >= min) {
						continue;
					}

					if (map.get(p1[0]).contains(p2[1]) && map.get(p2[0]).contains(p1[1])) {
						min = localArea;
					}
				}
			}

			return min == Integer.MAX_VALUE ? 0 : min;
		}
	}
}