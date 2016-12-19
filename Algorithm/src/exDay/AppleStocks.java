public class AppleStocks {

    public static void main(final String[] args) {
        final Integer[] stock_prices_yesterday0 = new Integer[] { 10, 7, 5, 8, 11, 9 }; // 6
        final Integer[] stock_prices_yesterday1 = new Integer[] { 10, 7, 5, 8, 11, 12 }; // 7
        final Integer[] stock_prices_yesterday2 = new Integer[] { 10, 7, 5, 8, 11, 9, 1, 10 }; // 9
        final Integer[] stock_prices_yesterday3 = new Integer[] { 5, 5, 5 }; // 0
        final Integer[] stock_prices_yesterday4 = new Integer[] { 10, 7, 5, 5, 8, 8 }; // 3

        final List<List<Integer>> stocks = new ArrayList<>();
        stocks.add(Arrays.asList(stock_prices_yesterday0));
        stocks.add(Arrays.asList(stock_prices_yesterday1));
        stocks.add(Arrays.asList(stock_prices_yesterday2));
        stocks.add(Arrays.asList(stock_prices_yesterday3));
        stocks.add(Arrays.asList(stock_prices_yesterday4));
        for (final List<Integer> eachStocks : stocks) {
            final int result = get_max_profit(eachStocks);
            System.out.println("result: " + result);
        }
    }

    private static int get_max_profit(
            final List<Integer> stock_prices_yesterday) {
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
