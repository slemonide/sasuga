package geometry.parallelepipedSpace;

import geometry.Parallelepiped;
import geometry.ParallelepipedSpace;
import geometry.Position;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.Difference;
import util.SetObserver;

import java.util.*;

import static geometry.parallelepipedSpace.ParallelepipedSpaceTestHelpers.buildParallelepiped;
import static geometry.parallelepipedSpace.ParallelepipedSpaceTestHelpers.buildRandomCloud;
import static org.junit.Assert.*;

public class ParallelepipedSpaceTest {
    static final int MAX_POSITIONS = 8917;
    private static final int MAX_STEPS_RANDOMWALK = 100;
    private static final int RANDOM_FILL_RADIUS = 4;
    private static final int MAX_POSITIONS_RANDOM_FILL = (RANDOM_FILL_RADIUS + 3) *
                    (RANDOM_FILL_RADIUS + 3) *
                    (RANDOM_FILL_RADIUS + 3);
    private static final int MAX_STEPS_RANDOMWALK_REMOVE = 20;
    private static final int MAX_POSITIONS_RANDOM_LINE = 100;
    private static final int MAX_POSITIONS_CUBE_RANDOM = 5;
    private ParallelepipedSpace testSpace;
    private SetObserver<Parallelepiped> parallelepipedSpaceObserver;

    @Before
    public void runBefore() {
        testSpace = new ParallelepipedSpace();
        parallelepipedSpaceObserver = new SetObserver<>(testSpace.getParallelepipeds());
    }

    @Test
    public void testConstructor() {
        assertTrue(testSpace.isEmpty());
        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.getRemoved().isEmpty());
        assertTrue(difference.getAdded().isEmpty());
        assertEquals(0, testSpace.size());
    }

    @Test
    public void testGetVolumeBasic() {
        testSpace.add(new Position(0,0,0));

        assertEquals(1, testSpace.getVolume());
    }

    @Test
    public void testGetVolumeDouble() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,0,0));

        assertEquals(1, testSpace.getVolume());
    }

    @Test
    public void testGetVolumeThree() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(0,1,0));

        assertEquals(3, testSpace.getVolume());
    }

    @Test
    public void testGetVolumeHard() {
        buildParallelepiped(testSpace);

        Assert.assertEquals(ParallelepipedSpaceTestHelpers.MAX_POSITIONS_X * ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Y * ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Z, testSpace.getVolume());
    }

    @Test
    public void testGetVolumeRandomFill() {
        Random random = new Random();

        Set<Position> positions = new HashSet<>();
        for (int i = 0; i < MAX_POSITIONS_RANDOM_FILL; i++) {
            Position position = new Position(
                    random.nextInt(),
                    random.nextInt(),
                    random.nextInt());

            positions.add(position);
            testSpace.add(position);

            assertTrue(testSpace.contains(position));
            assertEquals(positions.size(), testSpace.getVolume());
        }
    }

    @Test
    public void testGetVolumeRandomFillConfined() {
        Random random = new Random();

        Set<Position> positions = new HashSet<>();
        for (int i = 0; i < MAX_POSITIONS_RANDOM_FILL; i++) {
            Position position = new Position(
                    random.nextInt(RANDOM_FILL_RADIUS),
                    random.nextInt(RANDOM_FILL_RADIUS),
                    random.nextInt(RANDOM_FILL_RADIUS));

            positions.add(position);
            testSpace.add(position);

            assertTrue(testSpace.contains(position));
            assertEquals(positions.size(), testSpace.getVolume());
        }
    }

    @Test
    public void testGetVolumeRandomWalk() {
        Position currentPosition = new Position(0,0,0);

        Set<Position> positions = new HashSet<>();
        for (int i = 0; i < MAX_STEPS_RANDOMWALK; i++) {
            positions.add(currentPosition);
            testSpace.add(currentPosition);

            assertTrue(testSpace.contains(currentPosition));
            assertEquals(positions.size(), testSpace.getVolume());

            currentPosition = currentPosition.add(ParallelepipedSpaceTestHelpers.nextPosition());
        }
    }

    @Test
    public void testRandomWalkRemove() {
        ParallelepipedSpaceTestHelpers.buildCube(testSpace, MAX_STEPS_RANDOMWALK_REMOVE * 2);

        Position currentPosition = new Position(MAX_STEPS_RANDOMWALK_REMOVE,
                MAX_STEPS_RANDOMWALK_REMOVE,MAX_STEPS_RANDOMWALK_REMOVE);

        Set<Position> removedPositions = new HashSet<>();
        for (int i = 0; i < MAX_STEPS_RANDOMWALK_REMOVE; i++) {
            removedPositions.add(currentPosition);

            assertTrue(testSpace.contains(currentPosition) || removedPositions.contains(currentPosition));
            testSpace.remove(currentPosition);

            assertFalse(testSpace.contains(currentPosition));
            assertEquals(((long) MAX_STEPS_RANDOMWALK_REMOVE * 2) *
                    (MAX_STEPS_RANDOMWALK_REMOVE * 2) *
                    (MAX_STEPS_RANDOMWALK_REMOVE * 2) - removedPositions.size(), testSpace.getVolume());

            currentPosition = currentPosition.add(ParallelepipedSpaceTestHelpers.nextPosition());
        }
    }

    @Test
    public void testBuildLineRandomly() {
        List<Position> cubePositions = new ArrayList<>();
        for (int x = 0; x < MAX_POSITIONS_RANDOM_LINE; x++) {
            cubePositions.add(new Position(x, 0, 0));
        }

        Collections.shuffle(cubePositions);

        for (Position position : cubePositions) {
            testSpace.add(position);
        }

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(
                new Position(0,0,0),
                MAX_POSITIONS_RANDOM_LINE, 1, 1)));
    }

    @Test
    public void testBuildThinStripeRandomly() {
        List<Position> cubePositions = new ArrayList<>();
        for (int x = 0; x < MAX_POSITIONS_RANDOM_LINE; x++) {
            for (int y = 0; y < 2; y++) {
                cubePositions.add(new Position(x, y, 0));
            }
        }

        Collections.shuffle(cubePositions);

        for (Position position : cubePositions) {
            testSpace.add(position);
        }

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(
                new Position(0,0,0),
                MAX_POSITIONS_RANDOM_LINE, 2, 1)));
    }

    @Test
    public void testBuildCubeRandomly() {
        List<Position> cubePositions = new ArrayList<>();
        for (int x = 0; x < MAX_POSITIONS_CUBE_RANDOM; x++) {
            for (int y = 0; y < MAX_POSITIONS_CUBE_RANDOM; y++) {
                for (int z = 0; z < MAX_POSITIONS_CUBE_RANDOM; z++) {
                    cubePositions.add(new Position(x, y, z));
                }
            }
        }

        Collections.shuffle(cubePositions);

        for (Position position : cubePositions) {
            testSpace.add(position);
        }

        assertEquals(MAX_POSITIONS_CUBE_RANDOM *
                MAX_POSITIONS_CUBE_RANDOM *
                MAX_POSITIONS_CUBE_RANDOM, testSpace.getVolume());

        // We should at least gain a slight improvement
        assertTrue(testSpace.size() < MAX_POSITIONS_CUBE_RANDOM *
                MAX_POSITIONS_CUBE_RANDOM * MAX_POSITIONS_CUBE_RANDOM);

        /* Algorithm is not perfect :(
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(
                new Position(0,0,0),
                MAX_POSITIONS_CUBE_RANDOM, MAX_POSITIONS_CUBE_RANDOM, MAX_POSITIONS_CUBE_RANDOM)));*/
    }

    // Implementation-dependent tests

    @Test
    public void testRemoveCenterOfAnOddCube() {
        ParallelepipedSpaceTestHelpers.buildCube(testSpace, 3);
        assertEquals(1, testSpace.size());
        assertEquals(3 * 3 * 3, testSpace.getVolume());

        // remove center
        testSpace.remove(new Position(1,1,1));

        assertEquals(6, testSpace.getParallelepipeds().size());

        // Z-axis
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                3,3,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,2),
                3,3,1)));

        // Y-axis
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,1),
                3,1,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,2,1),
                3,1,1)));

        // X-axis
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,1,1),
                1,1,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(2,1,1),
                1,1,1)));
    }

    @Test
    public void testRemoveCenterOfAnEvenCube() {
        ParallelepipedSpaceTestHelpers.buildCube(testSpace, 4);
        assertEquals(1, testSpace.size());
        assertEquals(4 * 4 * 4, testSpace.getVolume());

        // remove center
        testSpace.remove(new Position(1,1,1));

        assertEquals(6, testSpace.getParallelepipeds().size());

        // Z-axis
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                4,4,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,2),
                4,4,2)));

        // Y-axis
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,1),
                4,1,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,2,1),
                4,2,1)));

        // X-axis
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,1,1),
                1,1,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(2,1,1),
                2,1,1)));
    }

    // End of implementation-dependent tests

    @Test
    public void testIterator() {
        buildRandomCloud(testSpace);

        int volume = 0;

        for (Parallelepiped parallelepiped : testSpace) {
            volume += parallelepiped.getVolume();
        }

        assertEquals(volume, testSpace.getVolume());
    }

    @Test
    public void testPositions() {
        buildRandomCloud(testSpace);

        int volume = 0;

        for (Position position : testSpace.positions()) {
            volume++;
        }

        assertEquals(volume, testSpace.getVolume());
    }
}
