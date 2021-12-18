package nl.pekict.year2021;


import java.nio.file.Paths;
import java.util.*;

public class Day12 {
    // Totally my own solution...
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        Day12 day12 = new Day12();
        List<String> inputs = new ArrayList<>();
        try (Scanner scanner = new Scanner(Paths.get("day12.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                day12.addData(line.split("-"));
                inputs.add(line);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        int smallPathCount = day12.getPathsWithSmallCaves();
        int bigPathCount = day12.getPathsWithSmallCavesWithOneRepeat();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Antwoord 1: " + smallPathCount);
        System.out.println("Antwoord 2: " + bigPathCount);
        System.out.println("Tijd: " + totalTime);
    }

    private Map<String, Cave> caveSystem = new HashMap<>();
    private Cave startCave = null;
    private Cave endCave = null;

    private static final String START = "start";
    private static final String END = "end";

    public void addData(String[] pieces) {
        Cave firstCave = createCave(pieces[0]);
        Cave secondCave = createCave(pieces[1]);
        firstCave.addCave(secondCave);
        secondCave.addCave(firstCave);

        if (pieces[0].equals(START) && startCave == null) {
            startCave = firstCave;
        } else if (pieces[1].equals(START) && startCave == null) {
            startCave = secondCave;
        }

        if (pieces[1].equals(END) && endCave == null) {
            endCave = secondCave;
        }
    }

    private Cave createCave(String value) {
        if (caveSystem.containsKey(value)) {
            return caveSystem.get(value);
        } else {
            Cave cave;
            if (value.equals(value.toLowerCase())) {
                cave = new SmallCave(value);
            } else {
                cave = new BigCave(value);
            }
            caveSystem.put(value, cave);
            return cave;
        }
    }

    public int getPathsWithSmallCaves() {
        int paths = walk(new ArrayList<>(), startCave);
        return paths;
    }

    public int walk(List<Cave> visited, Cave currentCave) {
        if (currentCave.getValue().equals(END)) {
            return 1;
        } else if (currentCave.isSmallCave() && visited.contains(currentCave)) {
            return 0;
        }

        visited.add(currentCave);
        int sum = 0;
        for (Cave cave : currentCave.getLinkedCaves()) {
            List<Cave> newlyVisited = new ArrayList<>(visited);
            sum += walk(newlyVisited, cave);
        }
        return sum;
    }

    public int getPathsWithSmallCavesWithOneRepeat() {
        return walkWithOneRepeat(new ArrayList<>(), startCave, false);
    }

    public int walkWithOneRepeat(List<Cave> visited, Cave currentCave, boolean hasUsedDuplicate) {
        if (currentCave.getValue().equals(END)) {
            return 1;
        } else if (currentCave.isSmallCave() && visited.contains(currentCave) && hasUsedDuplicate ||
                (visited.contains(currentCave) && currentCave.getValue().equals(START))) {
            return 0;
        } else if (currentCave.isSmallCave() && visited.contains(currentCave) && !currentCave.getValue().equals(START)) {
            hasUsedDuplicate = true;
        }

        visited.add(currentCave);
        int sum = 0;
        for (Cave cave : currentCave.getLinkedCaves()) {
            List<Cave> newlyVisited = new ArrayList<>(visited);
            sum += walkWithOneRepeat(newlyVisited, cave, hasUsedDuplicate);
        }
        return sum;
    }

    private static class BigCave extends Cave {
        public BigCave(String value) {
            super(value);
            hasMultipleVisits = true;
        }
    }

    private static class SmallCave extends Cave {
        public SmallCave(String value) {
            super(value);
            hasMultipleVisits = false;
        }
    }

    private static abstract class Cave {
        protected final String value;
        protected final Map<String, Cave> links;
        protected boolean hasMultipleVisits;

        public Cave(String value) {
            this.value = value;
            links = new HashMap<>();
        }

        public boolean isSmallCave() {
            return !hasMultipleVisits;
        }

        public String getValue() {
            return value;
        }

        public void addCave(Cave cave) {
            if (!links.containsKey(cave.getValue())) {
                links.put(cave.getValue(), cave);
            }
        }

        public Collection<Cave> getLinkedCaves() {
            return links.values();
        }
    }
}