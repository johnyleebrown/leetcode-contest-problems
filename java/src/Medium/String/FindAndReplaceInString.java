package Medium.String;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 833
 *
 * To some string S, we will perform some replacement operations that replace groups of letters with new ones (not necessarily the same size).
 * Each replacement operation has 3 parameters: a starting index i, a source word x and a target word y.  The rule is that if x starts at position i in the original string S, then we will replace that occurrence of x with y.  If not, we do nothing.
 * For example, if we have S = "abcd" and we have some replacement operation i = 2, x = "cd", y = "ffff", then because "cd" starts at position 2 in the original string S, we will replace it with "ffff".
 * Using another example on S = "abcd", if we have both the replacement operation i = 0, x = "ab", y = "eee", as well as another replacement operation i = 2, x = "ec", y = "ffff", this second operation does nothing because in the original string S[2] = 'c', which doesn't match x[0] = 'e'.
 * All these operations occur simultaneously.  It's guaranteed that there won't be any overlap in replacement: for example, S = "abc", indexes = [0, 1], sources = ["ab","bc"] is not a valid test case.
 */
public class FindAndReplaceInString {
    /**
     * Time complexity: O()
     * Space complexity: O()
     */
    public String findReplaceString(String S, int[] indexes, String[] sources, String[] targets) {
        List<int[]> sorted = new ArrayList<>();
        for (int i = 0 ; i < indexes.length; i++) sorted.add(new int[]{indexes[i], i});
        Collections.sort(sorted, Comparator.comparing(i -> -i[0]));
        for (int[] ind: sorted) {
            int i = ind[0], j = ind[1];
            String s = sources[j], t = targets[j];
            if (S.substring(i, i + s.length()).equals(s)) S = S.substring(0, i) + t + S.substring(i + s.length());
        }
        return S;
    }
}
