package geometry.parallelepipedSpace;

import geometry.Parallelepiped;
import geometry.ParallelepipedSpace;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static geometry.parallelepipedSpace.Helpers.*;
import static org.junit.Assert.*;

/**
 * Tests for the get(Parallelepiped) method of the ParallelepipedSpace
 */
public class getParallelepipedTest {
    private ParallelepipedSpace testSpace;

    @Before
    public void runBefore() {
        testSpace = new ParallelepipedSpace();
    }

    @Test
    public void testReturnEmptyStream() {
        for (int i = 0; i < MAX_RANDOM_ITERATIONS; i++) {
            assertEquals(0, testSpace.get(getRandomParallelepiped()).count());
        }
    }

    @Test
    public void testOnlyGetTheOnesInside() {
        Set<Parallelepiped> addedParallelepipeds = new HashSet<>();

        for (int i = 0; i < MAX_ENTITIES; i++) {
            Parallelepiped randomParallelepiped = getRandomParallelepiped();

            addedParallelepipeds.add(randomParallelepiped);
            testSpace.add(randomParallelepiped);
        }

        for (int i = 0; i < MAX_RANDOM_ITERATIONS_HARD; i++) {
            Parallelepiped randomParallelepiped = getRandomParallelepiped(getRandomPosition());

            Set<Parallelepiped> toBeFound = addedParallelepipeds.parallelStream()
                    .filter(randomParallelepiped::contains)
                    .collect(Collectors.toSet());

            assertTrue(testSpace.get(randomParallelepiped).allMatch(toBeFound::contains));
            assertFalse(testSpace.getParallelepipeds().stream()
                    .filter(parallelepiped -> !toBeFound.contains(parallelepiped))
                    .noneMatch(randomParallelepiped::contains)
            );

            addedParallelepipeds.forEach(position -> assertTrue(testSpace.contains(position)));
        }
    }

    @Test
    public void testOnlyGetTheOnesInsideConcentrated() {
        Set<Parallelepiped> addedParallelepipeds = new HashSet<>();

        for (int i = 0; i < MAX_ENTITIES; i++) {
            Parallelepiped randomParallelepiped = getRandomParallelepiped(BOUND);

            addedParallelepipeds.add(randomParallelepiped);
            testSpace.add(randomParallelepiped);
        }

        for (int i = 0; i < MAX_RANDOM_ITERATIONS_HARD; i++) {
            Parallelepiped randomParallelepiped = getRandomParallelepiped(getRandomPosition(2 * BOUND));

            Set<Parallelepiped> toBeFound = addedParallelepipeds.parallelStream()
                    .filter(randomParallelepiped::contains)
                    .collect(Collectors.toSet());

            assertTrue(testSpace.get(randomParallelepiped).allMatch(toBeFound::contains));
            assertFalse(testSpace.getParallelepipeds().stream()
                    .filter(parallelepiped -> !toBeFound.contains(parallelepiped))
                    .noneMatch(randomParallelepiped::contains)
            );

            addedParallelepipeds.forEach(position -> assertTrue(testSpace.contains(position)));
        }
    }
}
