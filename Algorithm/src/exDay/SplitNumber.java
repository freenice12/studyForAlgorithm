// I solved using while statements and REGEX for coding test T_T.
// Now I want to use recursive solving without REGEX.
// It is simple than before. lol~
// Please write package!

import java.util.Arrays;
import java.util.List;

public class SplitNumber {

  public static void main(final String[] args) {
        final String s1 = "1231231212";
        final String s2 = "12312312";
        final String s3 = "123123121231231231231232311";
        final String s4 = "1231231212312312312312323111";
        final String s5 = "123";
        final String s6 = "12";
        final String s7 = "12312";
        final String s8 = "1234";
        final StringBuffer result = new StringBuffer();
        final List<String> ss = Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8);
        for (final String s : ss) {
            getResult(s, result);
            System.out.println("result: " + result.toString());
            result.delete(0, result.length());
        }
        
    }

  private static void getResult(final String s, final StringBuffer result) {
        final int sLength = s.length();
        final String DASH = "-";

        if (sLength == 2 || sLength == 3) {
            result.append(s);
            return;
        } else if (sLength == 4) {
            result.append(s.substring(0, 2)).append(DASH)
                    .append(s.substring(2));
            return;
        }

        result.append(s.substring(0, 3)).append(DASH);
        getResult(s.substring(3), result);
    }
}
