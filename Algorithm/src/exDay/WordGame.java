public class WordGame {

    static List<String> words = new ArrayList<>();
    private static int wrongCount;
    private static String prob;
    static Set<String> alphas = new HashSet<>();

    public static void main(final String[] args) throws IOException {
        startGame(true);
    }

    static final Scanner scan = new Scanner(System.in);

    private static void startGame(final boolean reload)
            throws FileNotFoundException {
        init(reload);
        System.out.println("============================================");
        System.out.println("To quit - type \"quit\"");
        System.out.println("To reload - \"reload\"");
        System.out.println("You can wrong answer 7 times then quit");
        System.out.println("============================================");
        System.out.print("Type a alphabet: ");
        String alpha = scan.nextLine();
        final int random = (int) (Math.random() * words.size());
        prob = words.get(random);
        do {
            if (isOrder(alpha)) {
                doOrder(alpha);
            }
            checkGameStatus(matchingAlphabet(prob, alpha), alpha);

            System.out.println();
            System.out.print(
                    "Type a alphabet(wrong count - " + wrongCount + "): ");
            alpha = scan.nextLine();
        } while (!alpha.equalsIgnoreCase("quit"));
    }

    private static void checkGameStatus(final boolean isFinish,
            final String alpha) throws FileNotFoundException {
        if (isFinish) {
            System.out.println();
            System.out.println("Done!");
            startGame(false);
        }
        if (!prob.contains(alpha)) {
            wrongCount++;
        }
        if (wrongCount > 6) {
            System.out.println();
            System.out.println("The answer is \"" + prob + "\"");
            System.out.println("Restart!");
            System.out.println();
            startGame(false);
        }
    }

    private static void init(final boolean reload)
            throws FileNotFoundException {
        if (reload)
            words = readFile();
        alphas = new HashSet<>();
        wrongCount = 0;
    }

    @SuppressWarnings("resource")
    private static List<String> readFile() throws FileNotFoundException {
        return new BufferedReader(new FileReader(new File("problems.txt")))
                .lines().collect(Collectors.toList());
    }

    private static boolean isOrder(final String alpha) {
        return alpha.equalsIgnoreCase("quit")
                || alpha.equalsIgnoreCase("reload");
    }

    private static void doOrder(final String alpha)
            throws FileNotFoundException {
        if (alpha.equalsIgnoreCase("quit")) {
            System.out.println("shut down");
            System.exit(0);
            return;
        }
        if (alpha.equalsIgnoreCase("reload")) {
            words = readFile();
            startGame(true);
            return;
        }
    }

    private static boolean matchingAlphabet(final String prob,
            final String alpha) throws FileNotFoundException {
        boolean isFinish = true;
        alphas.add(alpha);
        for (int i = 0; i < prob.length(); i++) {
            if (alphas.contains(String.valueOf(prob.charAt(i)))) {
                System.out.print(prob.charAt(i));
            } else {
                System.out.print("*");
                isFinish = false;
            }
        }
        return isFinish;
    }

}
