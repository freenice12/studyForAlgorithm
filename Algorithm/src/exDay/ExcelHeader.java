private static HashMap<Integer, String> StringMap;

public static void main(final String[] args) {
  setMap();

  // A ... Z AA ... ZY ZZ AAA ...
  for (int i = 0; i < 26 * 27; i++) {
      System.out.println(fun(i));
  }
  System.out.println(fun(26 * 26 + 26));
}

private static void setMap() {
  StringMap = new HashMap<>();
  for (int i = 0; i < 26; i++) {
      StringMap.put(i, String.valueOf((char) (i + 65)));
  }
}

private static String fun(final int x) {
  if (x < 26) {
      return StringMap.get(x);
  } else {
      final int head = x / 26 - 1;
      final int rest = x % 26;
      return fun(head) + fun(rest);
  }
}
