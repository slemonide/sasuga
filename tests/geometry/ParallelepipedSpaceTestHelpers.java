package geometry;

import java.util.Random;
import java.util.stream.Stream;

/**
 * Helpers for the ParallelepipedSpaceTest class
 */
final class ParallelepipedSpaceTestHelpers {
    private static final Random RANDOM = new Random();
    static final int MAX_POSITIONS_X = 10;
    static final int MAX_POSITIONS_Y = 20;
    static final int MAX_POSITIONS_Z = 17;

    static void buildCube(ParallelepipedSpace space, int size) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    space.add(new Position(x, y, z));
                }
            }
        }
    }

    static Position nextPosition() {
        switch (RANDOM.nextInt(6)) {
            case 0:
                return new Position(1, 0, 0);
            case 1:
                return new Position(-1, 0, 0);
            case 2:
                return new Position(0, 1, 0);
            case 3:
                return new Position(0, -1, 0);
            case 4:
                return new Position(0, 0, 1);
            default:
                return new Position(0, 0, -1);
        }
    }

    static void cutParallelepipedRight(ParallelepipedSpace space) {
        for (int x = 0; x < MAX_POSITIONS_X; x++) {
            for (int z = 0; z < MAX_POSITIONS_Z; z++) {
                space.remove(new Position(x, 0, z));
            }
        }
    }

    static void cutParallelepipedLeft(ParallelepipedSpace space) {
        for (int x = 0; x < MAX_POSITIONS_X; x++) {
            for (int z = 0; z < MAX_POSITIONS_Z; z++) {
                space.remove(new Position(x, MAX_POSITIONS_Y - 1, z));
            }
        }
    }

    static void cutParallelepipedTop(ParallelepipedSpace space) {
        for (int x = 0; x < MAX_POSITIONS_X; x++) {
            for (int y = 0; y < MAX_POSITIONS_Y; y++) {
                space.remove(new Position(x, y, MAX_POSITIONS_Z - 1));
            }
        }
    }

    static void cutParallelepipedBottom(ParallelepipedSpace space) {
        for (int x = 0; x < MAX_POSITIONS_X; x++) {
            for (int y = 0; y < MAX_POSITIONS_Y; y++) {
                space.remove(new Position(x, y, 0));
            }
        }
    }

    static void buildRandomCloud(ParallelepipedSpace space) {
        Stream.iterate(1, i -> i+1).limit(1000).forEach(integer -> {
            if (RANDOM.nextInt(10) == 1) {
                space.add(positionFromIndex(integer));
            }
        });
    }

    private static Position positionFromIndex(int integer) {
        // TODO: finish

        return new Position(0,0,0);
    }
}
