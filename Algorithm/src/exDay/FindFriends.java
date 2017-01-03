public class FindFriends {
    static Map<String, Set<String>> snsMap = new HashMap<>();

    public static void main(final String[] args) throws IOException {
        final File sns = new File("friends.txt");
        final BufferedReader br = new BufferedReader(new FileReader(sns));
        final BufferedReader br2 = new BufferedReader(new FileReader(sns));
        final BufferedReader br3 = new BufferedReader(new FileReader(sns));
        
        final String target = "둘리";
        
        final Set<String> frinedsOfMine = new HashSet<>();
        br.lines().filter(line -> line.contains(target)).forEach(result -> {
            frinedsOfMine.add(result.replace(target, "").trim());
        });
        System.out.println("내 친구(stream): " + frinedsOfMine);
        final List<String> subFriends = br2.lines().filter(line -> {
            return line.contains(target);
        }).map(result -> {
            return result.replace(target, "").trim();
        }).collect(Collectors.toList());
        final Set<String> subFoF = new HashSet<>();
        br3.lines().filter(line -> !line.contains(target)).forEach(line -> {
            final String[] splited = line.split(" ");
            if (subFriends.contains(splited[0])) {
                subFoF.add(splited[1]);
            } else if (subFriends.contains(splited[1])) {
                subFoF.add(splited[0]);
            }
        });
        System.out.println("내 친구의 친구(stream): " + subFoF);

        final BufferedReader br4 = new BufferedReader(new FileReader(sns));
        br4.lines().forEach(friends -> {
            final String[] splitedFriends = friends.split(" ");
            addFriends(splitedFriends[0], splitedFriends[1]);
            addFriends(splitedFriends[1], splitedFriends[0]);
        });

        
        final Set<String> friendsList = snsMap.get(target);
        final Set<String> friendsOfYours = new HashSet<>();
        System.out.println("내 친구: " + friendsList);
        for (final String frinedOfFriends : friendsList) {
            for (final String endFriend : snsMap.get(frinedOfFriends))
                if (!endFriend.equals(target)
                        && !friendsList.contains(endFriend))
                    friendsOfYours.add(endFriend);
        }
        System.out.println("내 친구의 친구: " + friendsOfYours);

        System.out.println(frinedsOfMine.containsAll(friendsList));
        System.out.println(subFoF.containsAll(friendsOfYours));
    }

    private static void addFriends(final String fr1, final String fr2) {
        if (snsMap.containsKey(fr1)) {
            final Set<String> friends = snsMap.get(fr1);
            friends.add(fr2);
        } else {
            final Set<String> newList = new HashSet<>();
            newList.add(fr2);
            snsMap.put(fr1, newList);
        }
    }

}
