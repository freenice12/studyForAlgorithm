public class AppleStocks {

    public static void main(final String[] args) {
        final int[] stock_prices_yesterday0 = new int[] { 10, 7, 5, 8, 11, 9 }; // 6
        final int[] stock_prices_yesterday1 = new int[] { 10, 7, 5, 8, 11, 12 }; // 7
        final int[] stock_prices_yesterday2 = new int[] { 10, 7, 5, 8, 11, 9, 1, 10 }; // 9
        final int[] stock_prices_yesterday3 = new int[] { 5, 5, 5 }; // 0
        final int[] stock_prices_yesterday4 = new int[] { 10, 7, 5, 5, 8, 8 }; // 3

        final int result0 = get_max_profit(stock_prices_yesterday0);
        final int result1 = get_max_profit(stock_prices_yesterday1);
        final int result2 = get_max_profit(stock_prices_yesterday2);
        final int result3 = get_max_profit(stock_prices_yesterday3);
        final int result4 = get_max_profit(stock_prices_yesterday4);

        System.out.println("resutl0: " + result0);
        System.out.println("resutl1: " + result1);
        System.out.println("resutl2: " + result2);
        System.out.println("resutl2: " + result3);
        System.out.println("resutl2: " + result4);
    }

    private static int get_max_profit(final int[] stock_prices_yesterday) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int result = 0;

        for (final int stock : stock_prices_yesterday) {
            if (stock <= min) {
                min = stock;
                max = Integer.MIN_VALUE;
            } else if (stock >= max) {
                max = stock;
                final int diff = max - min;
                if (result <= diff) {
                    result = diff;
                }
            }
        }

        return result;

    }

}
