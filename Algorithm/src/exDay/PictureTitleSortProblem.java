
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

// codility 2
public class PictureTitleSortProblem {
    // rawTitle.extension, location, time
    // It should be rename the title like below:
    // from
    // abc.jpg, A, 2013-09-04
    // see.jpg, A, 2012-09-05
    // to
    // A1.jpg, A, 2012-09-05
    // A2.jpg, A, 2013-09-04
    // when the picture which same location is over 10
    // then title must be prefix 0 like '01' ~ '09' and 10 and so on.
    // The result shoud be:
    // A1.jpg
    // A2.jpg

    @Test
    public void a() {
        String S = "photo.jpg, Warsaw, 2013-09-05 14:08:15\n";
        S += "john.png, London, 2015-06-20 15:13:22\n";
        S += "myFriends.png, Warsaw, 2013-09-05 14:07:13\n";
        S += "Eiffel.jpg, Paris, 2015-07-23 08:03:02\n";
        S += "pisatower.jpg, Paris, 2015-07-22 23:59:59\n";
        S += "BOB.jpg, London, 2015-08-05 00:02:03\n";
        S += "notredame.png, Paris, 2015-09-01 12:00:00\n";
        S += "me.jpg, Warsaw, 2013-09-06 15:40:22\n";
        S += "a.png, Warsaw, 2016-02-13 13:33:50\n";
        S += "b.jpg, Warsaw, 2016-01-02 15:12:22\n";
        S += "c.jpg, Warsaw, 2016-01-02 14:34:30\n";
        S += "d.jpg, Warsaw, 2016-01-02 15:15:01\n";
        S += "e.png, Warsaw, 2016-01-02 09:49:09\n";
        S += "f.png, Warsaw, 2016-01-02 10:55:32\n";
        S += "g.png, Warsaw, 2016-02-29 11:55:32\n";
        S += "h.png, Warsaw, 2016-03-29 11:55:32\n";


        String rrrr = "Warsaw02.jpgLondon1.pngWarsaw01.pngParis2.jpgParis1.jpgLondon2.jpgParis3.pngWarsaw03.jpgWarsaw09.pngWarsaw07.jpgWarsaw06.jpgWarsaw08.jpgWarsaw04.pngWarsaw05.pngWarsaw10.pngWarsaw11.png";
        System.out.println();
        System.out.println(rrrr.equals(solution(S).replace("\n", "")));
    }
    public String solution(String S) {
        List<SavedPhoto> photos = new ArrayList<>();
        Map<String, List<LocalDateTime>> locationDatetimeMap = new HashMap<>();

        // Parse raw data
        Arrays.stream(S.split("\n")).forEach(p -> {
            SavedPhoto newPhoto = new SavedPhoto(p);
            photos.add(newPhoto);
            if (!locationDatetimeMap.containsKey(newPhoto.getLocation())) {
                locationDatetimeMap.put(newPhoto.getLocation(), new ArrayList<>());
            }
            locationDatetimeMap.get(newPhoto.getLocation()).add(newPhoto.getDateTime());
        });

        List<SavedPhoto> savedPhotos = updateFilename(photos, sort(locationDatetimeMap));
        return savedPhotos.stream()
                .map(SavedPhoto::getNewFilename)
                .collect(Collectors.joining( "\n" ));
    }

    private Map<String, List<LocalDateTime>> sort(Map<String, List<LocalDateTime>> raws) {
        raws.values().forEach(Collections::sort);
        return raws;
    }

    private List<SavedPhoto> updateFilename(List<SavedPhoto> photos, Map<String, List<LocalDateTime>> sortedDateTimes) {
        photos.forEach(photo -> {
            List<LocalDateTime> indexedDateTimes = sortedDateTimes.get(photo.getLocation());
            int index = indexedDateTimes.indexOf(photo.getDateTime()) + 1;
            boolean isNeedPrefixZero = indexedDateTimes.size() >= 10 && index < 10;
            String newIndex = isNeedPrefixZero ? "0" + index : String.valueOf(index);
            photo.setNewFilename(newIndex);
        });
        return photos;
    }

    class SavedPhoto {
        private String newFilename;
        private String extension;
        private String location;
        private LocalDateTime dateTime;

        SavedPhoto(String p) {
            String[] split = p.split(",");
            setRawFilename(split[0].trim());
            setLocation(split[1].trim());
            setDateTime(split[2].trim());
        }

        public void setNewFilename(String newIndex) {
            this.newFilename = this.location + newIndex + "." + this.extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.dateTime = LocalDateTime.parse(dateTime, formatter);
        }

        public void setRawFilename(String rawFilename) {
            String[] split = rawFilename.split("\\.");
            setExtension(split[1]);
        }

        public String getNewFilename() {
            return newFilename;
        }
    }
}
