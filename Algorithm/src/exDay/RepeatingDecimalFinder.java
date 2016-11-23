
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class RepeatingDecimalFinder {

    public static void main(final String[] args) {
        Finder.findAndPrint(2, 100);
    }

    static class Finder {
        public static void findAndPrint(final int fromIn, final int toEx) {
            IntStream.range(fromIn, toEx).filter(hasRepeatingDecimal()).forEach(
                    number -> print(cal(new ArrayList<>(), 1, number, 0)));
        }

        private static void print(final String result) {
            System.out.println("result: " + result);
        }

        private static IntPredicate hasRepeatingDecimal() {
            return targetNumber -> {
                try {
                    new BigDecimal(1.0).divide(new BigDecimal(targetNumber));
                    return false;
                } catch (final Exception e) {
                    return true;
                }
            };
        }

        private static String cal(final List<History> histories, int c,
                final int a, final int counter) {
            c = c * 10;
            if (a > c) {
                final Optional<String> findRepeater = findRepeater(histories,
                        0);
                if (findRepeater.isPresent()) {
                    return findRepeater.get();
                }
                return cal(histories, c, a, counter + 1);
            }
            final int b = c / a;
            final Optional<String> findRepeater = findRepeater(histories, b);
            if (findRepeater.isPresent()) {
                return findRepeater.get();
            }

            return cal(histories, c - b * a, a, counter + 1);
        }

        private static Optional<String> findRepeater(
                final List<History> histories, final int b) {
            addHistoryWith(histories, b);
            for (int i = 0; i < histories.size(); i++) {
                final History h = addAnotherHistoryWithClean(histories, i, b);
                if (h.checkRepeat())
                    return Optional.of(h.getRepeater());
            }
            return Optional.empty();
        }

        private static History addAnotherHistoryWithClean(
                final List<History> histories, final int ith,
                final int number) {
            final History h = histories.get(ith);
            h.deleteStartZero();
            if (ith != histories.size() - 1) {
                h.add(number);
            }
            return h;
        }

        private static void addHistoryWith(final List<History> histories,
                final int number) {
            final History history = new History();
            history.add(number);
            histories.add(history);
        }
    }

    static class History {
        private List<Integer> tails = new ArrayList<>();
        private int level;

        public void add(final int b) {
            tails.add(b);
        }

        public boolean checkRepeat() {
            if (tails.isEmpty())
                return false;
            checkLevel(tails.get(tails.size() - 1));
            if (level > 0 && tails.size() > level * 2) {
                for (int ith = 0; ith < level; ith++) {
                    if (!isSameWith(ith, ith + level)) {
                        level = 0;
                        return false;
                    }
                }
                return true;
            }
            level = 0;
            return false;
        }

        private boolean isSameWith(final int ith, final int nextIth) {
            return tails.get(ith) == tails.get(nextIth);
        }

        private void checkLevel(final int targetNumber) {
            if (!tails.isEmpty()) {
                for (int ith = 0; ith < tails.size() / 2; ith++) {
                    if (isMatched(ith, targetNumber)) {
                        level = tails.size() / 2;
                        return;
                    }
                }
            }
        }

        private boolean isMatched(final int i, final int targetNumber) {
            return tails.get(i) == targetNumber;
        }

        public String getRepeater() {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < level; i++) {
                sb.append(tails.get(i));
            }
            return sb.toString();
        }

        public void deleteStartZero() {
            if (tails.isEmpty())
                return;
            if (tails.get(0) == 0)
                tails.remove(0);
        }

    }
}
