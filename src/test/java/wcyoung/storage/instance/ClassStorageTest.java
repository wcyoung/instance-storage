package wcyoung.storage.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import wcyoung.storage.instance.classes.ClassA;
import wcyoung.storage.instance.classes.ClassB;

class ClassStorageTest {

    private ClassStorage storage;

    @Nested
    class TestWithSetupData {

        @BeforeEach
        void setUp() throws Exception {
            storage = new ClassStorage();
            storage.add(ClassA.class, new ClassA());
        }

        @Test
        void testHas() {
            assertTrue(storage.has(ClassA.class));
            assertFalse(storage.has(ClassB.class));
        }

        @Test
        void testGet() {
            assertNotNull(storage.get(ClassA.class));
            assertNull(storage.get(ClassB.class));
        }

        @Test
        void testSize() {
            assertEquals(1, storage.size());
        }

        @Test
        void testAddObjectFail() {
            boolean isAdded = storage.add(new ClassA());
            assertFalse(isAdded);
            assertTrue(storage.has(ClassA.class));
        }

        @Test
        void testAddObjectWithClassFail() {
            boolean isAdded = storage.add(ClassA.class, new ClassA());
            assertFalse(isAdded);
            assertTrue(storage.has(ClassA.class));
        }

        @Test
        void testReplace() {
            ClassA classA = storage.get(ClassA.class);
            storage.replace(ClassA.class, new ClassA());
            ClassA newClassA = storage.get(ClassA.class);

            assertNotSame(classA, newClassA);
        }

        @Test
        void testRemove() {
            boolean isRemoved = storage.remove(ClassA.class);
            assertTrue(isRemoved);
            assertFalse(storage.has(ClassA.class));
        }

        @Test
        void testClear() {
            storage.clear();
            assertEquals(0, storage.size());
        }

    }

    @Nested
    class TestWithoutSetupData {

        @BeforeEach
        void setUp() throws Exception {
            storage = new ClassStorage();
        }

        @Test
        void testAddObject() {
            boolean isAdded = storage.add(new ClassA());
            assertTrue(isAdded);
            assertTrue(storage.has(ClassA.class));
        }

        @Test
        void testAddObjectWithClass() {
            boolean isAdded = storage.add(ClassA.class, new ClassA());
            assertTrue(isAdded);
            assertTrue(storage.has(ClassA.class));
        }

        @Test
        void testReplaceFail() {
            boolean isReplaced = storage.replace(ClassA.class, new ClassA());
            assertFalse(isReplaced);
            assertFalse(storage.has(ClassA.class));
        }

        @Test
        void testRemoveFail() {
            boolean isRemoved = storage.remove(ClassA.class);
            assertFalse(isRemoved);
            assertFalse(storage.has(ClassA.class));
        }

    }

}
