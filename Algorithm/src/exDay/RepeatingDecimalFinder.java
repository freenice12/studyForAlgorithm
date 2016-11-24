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
            		divisor -> print(divisor, startTracking(new ArrayList<>(), 1, divisor)));
        }

        private static void print(final int divisor, final String result) {
            System.out.println(divisor + " " + result);
        }

        private static IntPredicate hasRepeatingDecimal() {
            return divisor -> {
                try {
                    new BigDecimal(1.0).divide(new BigDecimal(divisor));
                    return false;
                } catch (final Exception e) {
                    return true;
                }
            };
        }

        private static String startTracking(final List<DecimalTracker> trackers, int dividend,
                final int divisor) {
            dividend = dividend * 10;
            if (divisor > dividend) {
                final Optional<String> optionalRepeater = findRepeaterAfterAddQuotient(trackers, 0);
                if (optionalRepeater.isPresent()) {
                    return optionalRepeater.get();
                }
                return startTracking(trackers, dividend, divisor);
            }
            final int quotient = dividend / divisor;
            final Optional<String> optionalRepeater = findRepeaterAfterAddQuotient(trackers, quotient);
            if (optionalRepeater.isPresent()) {
                return optionalRepeater.get();
            }

            return startTracking(trackers, dividend - quotient * divisor, divisor);
        }

        private static Optional<String> findRepeaterAfterAddQuotient(
                final List<DecimalTracker> trackers, final int quotient) {
            addTrackerWithQuotient(trackers, quotient);
            for (int ith = 0; ith < trackers.size(); ith++) {
                final DecimalTracker tracker = addAnotherHistoryWithClean(trackers, ith, quotient);
                if (tracker.hasRepeat()) {
					return Optional.of(tracker.getRepeater());
				}
            }
            return Optional.empty();
        }

        private static DecimalTracker addAnotherHistoryWithClean(
                final List<DecimalTracker> trackers, final int ith,
                final int quotient) {
            final DecimalTracker tracker = trackers.get(ith);
            tracker.deleteFirstZero();
            if (ith != trackers.size() - 1) {
                tracker.addQuotient(quotient);
            }
            return tracker;
        }

        private static void addTrackerWithQuotient(final List<DecimalTracker> trackers,
                final int quotient) {
            final DecimalTracker tracker = new DecimalTracker();
            tracker.addQuotient(quotient);
            trackers.add(tracker);
        }
    }

    static class DecimalTracker {
        private final List<Integer> tails = new ArrayList<>();
        private int gap;

        public void addQuotient(final int quotient) {
            tails.add(quotient);
        }

        public boolean hasRepeat() {
            if (tails.isEmpty()) {
				return false;
			}
            updateGap(tails.get(tails.size() - 1));
            if (gap > 0 && tails.size() > gap * 2) {
                for (int ith = 0; ith < gap; ith++) {
                    if (!isSameWith(ith, ith + gap)) {
                        gap = 0;
                        return false;
                    }
                }
                return true;
            }
            gap = 0;
            return false;
        }

        private boolean isSameWith(final int ith, final int gapPlusIth) {
            return tails.get(ith) == tails.get(gapPlusIth);
        }

        private void updateGap(final int targetNumber) {
            if (!tails.isEmpty()) {
                for (int ith = 0; ith < tails.size() / 2; ith++) {
                    if (isMatched(ith, targetNumber)) {
                        gap = tails.size() / 2;
                        return ;
                    }
                }
            }
        }

        private boolean isMatched(final int i, final int targetNumber) {
            return tails.get(i) == targetNumber;
        }

        public String getRepeater() {
            final StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < gap; i++) {
                stringBuilder.append(tails.get(i));
            }
            return stringBuilder.toString();
        }

        public void deleteFirstZero() {
            if (tails.isEmpty()) {
				return ;
			}
            if (tails.get(0) == 0) {
				tails.remove(0);
			}
        }

    }
}
