package jpa.example.demo.algo;

public class Sorter {

    public static void main(String[] args) {
//        int[] raw = new int[]{2, 3, 4, 1};
        int[] raw = new int[]{2, 3, 4, 1, 5};
        printArray(raw);
        quickSort(raw, 0, raw.length - 1);
        printArray(raw);
    }

//    private static void quickSort(int[] raw, int start, int end) {
//        int p = getP(raw, start, end);
//        if (start < p - 1) {
//            quickSort(raw, start, p - 1);
//        }
//        if (p < end) {
//            quickSort(raw, p, end);
//        }
//    }
//
//    private static int getP(int[] raw, int start, int end) {
//        int p = (start + end) / 2;
//        while (start <= end) {
//            while (raw[start] < raw[p]) start++;
//            while (raw[end] > raw[p]) end--;
//            if (start <= end) {
//                int temp = raw[start];
//                raw[start] = raw[end];
//                raw[end] = temp;
//                start++;
//                end--;
//            }
//        }
//        return start;
//    }

    private static void quickSort(int[] raw, int s, int e) {
        if (s >= e) return;
        int p = (s + e) / 2;

        int left = s;
        int right = e;

        quickSort(raw, s, left);
        quickSort(raw, left + 1, e);

        while (left < right) {
            while (raw[left] < p) {
                left++;
            }
            while (raw[right] > p) {
                right--;
            }
            if (left < right) {
                int temp = raw[left];
                raw[left] = raw[right];
                raw[right] = temp;
                left++;
                right--;
            }
        }

    }

//    public static void main(String[] args) {
//        int[] raw = new int[]{2,9,4,5,8,1,7,6,3};
////        int[] raw = new int[]{5, 2, 3, 4, 1};
////        int[] raw = new int[]{2, 3, 4, 1};
//        mergeSort(raw, new int[raw.length], 0, raw.length - 1);
//        printArray(raw);
//    }

    private static void printArray(int[] raw) {
        for (int i : raw) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private static void mergeSort(int[] raw, int[] tempArray, int left, int right) {
        if (left >= right) return;

        int center = (left + right) / 2;
        mergeSort(raw, tempArray, left, center);
        mergeSort(raw, tempArray, center + 1, right);

        mSort(raw, tempArray, left, center, right);
    }

    private static void mSort(int[] raw, int[] tempArray, int left, int center, int right) {
        if (right + 1 - left >= 0) System.arraycopy(raw, left, tempArray, left, right + 1 - left);

        int index = left;
        int laIndex = left;
        int raIndex = center + 1;
        while (laIndex <= center && raIndex <= right) {
            if (tempArray[laIndex] < tempArray[raIndex]) {
                raw[index] = tempArray[laIndex];
                laIndex++;
            } else {
                raw[index] = tempArray[raIndex];
                raIndex++;
            }
            index++;
        }

        for (int i = laIndex; i <= center; i++) {
            raw[index++] = tempArray[i];
        }
    }

}

//    public static void main(String[] args) {
//        int[] raw = new int[]{5, 2, 3, 4, 1};
//        quickSort();
//    }