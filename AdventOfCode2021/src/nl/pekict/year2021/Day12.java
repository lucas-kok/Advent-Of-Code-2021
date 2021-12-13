package nl.pekict.year2021;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day12 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("day12.csv");
//        File file = new File("sample.csv");
        // sample part 1: 10 paths
//        File file = new File("test.csv");
        // test part 1: 226

        Day12 day12 = new Day12();
        List<String> inputs = new ArrayList<>();
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                day12.addData(data.split("-"));
                inputs.add(data);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            System.err.println(e);
        }

        /*
        part 1
        find the total amount of paths WITH just small caves
        lowercase is small cave, visit once
        uppercase is large cave, visit many times
         */
        System.out.println(day12.getPathsWithSmallCaves());
        // 3118 is too low? 4659 is the solution

        /*
        part 2
        find the total amount of paths where you can revisit one small cave twice
        start and end cannot be revisited more than one
         */
        System.out.println(day12.getPathsWithSmallCavesWithOneRepeat());
        // 148962 is the answer
    }

    private Map<String, Cave> caveSystem = new HashMap<>();
    private Cave startCave = null;
    private Cave endCave = null;

    private static final String START = "start";
    private static final String END = "end";

    public void addData(String[] splitData) {
        // first element is the first node
        // second element is the joining node
        // we need to join both together
        Cave firstCave = createCave(splitData[0]);
        Cave secondCave = createCave(splitData[1]);
        firstCave.addCave(secondCave);
        secondCave.addCave(firstCave);

        if (splitData[0].equals(START) && startCave == null) {
            startCave = firstCave;
        } else if (splitData[1].equals(START) && startCave == null) {
            startCave = secondCave;
        }

        if (splitData[1].equals(END) && endCave == null) {
            endCave = secondCave;
        }
    }

    private Cave createCave(String value) {
        if (caveSystem.containsKey(value)) {
            return caveSystem.get(value);
        } else {
            // determine the type of cave
            Cave cave;
            if (value.equals(value.toLowerCase())) {
                // then we know its lower case
                cave = new SmallCave(value);
            } else {
                // it is upper case
                cave = new BigCave(value);
            }
            caveSystem.put(value, cave);
            return cave;
        }
    }

    public int getPathsWithSmallCaves() {
        // the goal here is to do a recursive walk
        // from start to end until either you reach the end
        // or you run out of steps (typically denoted by the "multipleVisits" variable)
        int paths = walk(new ArrayList<>(), startCave);

        return paths;
    }

    // this I think is like O(k ^ n) where k is the number of branches and n is the number of nodes
    public int walk(List<Cave> visited, Cave currentCave) {
        if (currentCave.getValue().equals(END)) {
            return 1;
        } else if (currentCave.isSmallCave() && visited.contains(currentCave)) {
            return 0;
        }

        visited.add(currentCave);
        // now we want to walk the branches
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
        // now we want to walk the branches
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