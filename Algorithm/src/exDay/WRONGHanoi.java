package jpa.example.demo.algo;

public class WRONGHanoi {

    public static void main(String[] args) {
        hanoi(3, "A", "B", "C");
        System.out.println("count: " + count);
    }

    private static void hanoi(int size, String from, String by, String to) {
        if (size == 1) {
            move(from, to, size);
        } else {
            hanoi(size-1, from, to, by);
            move(from, to, size);
            hanoi(size-1, by, from, to);
        }
    }
    private static int count;
    private static void move(String from, String to, int size) {
        count++;
        System.out.println("SIZE: " + size + " / " + from + " => " + to);

    }

}
