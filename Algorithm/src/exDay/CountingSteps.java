import org.junit.Test;

public class CountingSteps {


    @Test
    public void a() {
//        int[] A = new int[] {1, 2, 3};
//        String A = "011100";
//        String A = "011100";
//        String A = "011100";
        String A = "001111001";
//        String A = "100";
        System.out.println("step: " + solution(A));
    }

    public int solution(String S) {
        int sub = parseInteger(S, 0, 0);
        System.out.println("sub: " + sub);
        return getStep(sub);
    }

    private int parseInteger(String s, int index, int acc) {
        if (index >= s.length()) return acc;
        int beginIndex = s.length() - index - 1;
        int endIndex = s.length() - index;
        String nextBinary = s.substring(beginIndex, endIndex);
        int accumulate = acc + (int) Math.pow(2, index) * Integer.parseInt(nextBinary);
        return parseInteger(s, index + 1, accumulate);
    }

    private int getStep(int sub) {
        int step = 0;
        while (sub > 0) {
            sub = sub % 2 == 0 ? sub / 2 : sub - 1;
            step++;
        }
        return step;
    }


    // -----------------------------------------
    // count step 3
    // given array is person queue, top floor, max people, max weight
    // they are should be go to where every each want to go.
    // example says
    // person:
    //   1: 200(weight), 2(want to go)
    //   2: 80(weight), 3(want to go)
    //   3: 40(weight), 5(want to go)
    // top floor: 5
    // elevator:
    //   max people: 2
    //   max weight: 200
//    @Test
//    public void a() {
//        int[] ints = new int[]{200, 80, 40};
//        int[] ints1 = new int[]{2, 3, 5};
//        int i = 5;
//        int i1 = 2;
//        int i2 = 200;
////        int[] ints = new int[]{40, 40, 100, 80, 20};
////        int[] ints1 = new int[]{3, 3, 2, 2, 3};
////        int i = 3;
////        int i1 = 5;
////        int i2 = 200;
//        System.out.println(solution(ints, ints1, i, i1, i2));
//    }
//
//    public int solution(int[] A, int[] B, int M, int X, int Y) {
//        int totalStop = 0;
//        int queIndex = 0;
//        int leftWeight = Y;
//        int tempLimit = X;
//        Set<Integer> floors = new HashSet<>();
//
//        while (queIndex < A.length) {
//            if (queIndex >= A.length) break;
//            while (--tempLimit >= 0) {
//                if (queIndex >= A.length) break;
//                leftWeight -= A[queIndex];
//                if (leftWeight >= 0) {
//                    floors.add(B[queIndex++]);
//                } else {
//                    break;
//                }
//            }
//            totalStop += floors.size() + 1;
//            floors.clear();
//            tempLimit = X;
//            leftWeight = Y;
//        }
//
//        return totalStop;
//    }
//



}
