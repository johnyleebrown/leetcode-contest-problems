package math.operations;

/**
 * 43
 *
 * ======
 *
 * Task.
 *
 * Given two non-negative integers num1 and num2 represented as strings, return the product of num1
 * and num2, also represented as a string.
 *
 * ======
 *
 * Source: Leetcode
 */
public class MultiplyStrings {
	/**
	 * Vstolbik - summarizing with each new row(i)
	 * @formatter:off
	 *     9111
	 *     8111
	 *  -------
	 *     9111
	 *    9111
	 *   9111
	 * 56888
	 * --------
	 * 57899321
	 * @formatter:on
	 * k - offset from the end of the result string - with each row(i) it increases by 1
	 * t - offset for each new digit of the row when multiplying
	 */
	public static class Solution {
		public String multiply(String num1, String num2) {
			if ("0".equals(num1) || "0".equals(num2)) return "0";
			return num1.length() >= num2.length() ? h(num1, num2) : h(num2, num1);
		}

		String h(String num1, String num2) {
			StringBuilder ans = new StringBuilder();
			int sumRemainder = 0, multRemainder = 0;

			for (int i = num2.length() - 1, k = 0; i >= 0; i--, k++, multRemainder = sumRemainder = 0) {
				int cur2 = getint(num2.charAt(i));
				for (int j = num1.length() - 1, t = 0; j >= 0; j--, t++) {
					int cur1 = getint(num1.charAt(j));
					int x = cur1 * cur2 + multRemainder;
					multRemainder = x / 10;

					int insertPos = ans.length() - 1 - k - t;
					int val = x % 10 + sumRemainder;
					if (insertPos < 0) {
						ans.insert(0, tochar(val % 10));
					} else {
						val += getint(ans.charAt(insertPos));
						ans.setCharAt(insertPos, tochar(val % 10));
					}

					sumRemainder = val / 10;
				}

				// if we finished calculations for the digit but have remainders left
				if (sumRemainder + multRemainder != 0) {
					ans.insert(0, tochar(sumRemainder + multRemainder));
				}
			}

			return ans.toString();
		}

		char tochar(int i) {
			return (char) (i + '0');
		}

		int getint(char c) {
			return c - '0';
		}
	}
}