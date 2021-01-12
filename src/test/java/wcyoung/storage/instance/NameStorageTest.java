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

class NameStorageTest {

    private NameStorage storage;

    @Nested
    class TestWithSetupData {

        @BeforeEach
        void setUp() {
            storage = new NameStorage();
            storage.add("classA", new ClassA());
        }

        @Test
        void testHas() {
            assertTrue(storage.has("classA"));
            assertFalse(storage.has("classB"));
        }

        @Test
        void testGet() {
            assertNotNull(storage.get("classA"));
            assertNull(storage.get("classB"));
        }

        @Test
        void testSize() {
            assertEquals(1, storage.size());
        }

        @Test
        void testAddObjectFail() {
            boolean isAdded = storage.add(new ClassA());
            assertFalse(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void testAddObjectWithClassFail() {
            boolean isAdded = storage.add("classA", new ClassA());
            assertFalse(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void testAddObjectThroughSupplierFail() {
            boolean isAdded = storage.add(ClassA::new);
            assertFalse(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void testAddObjectWithClassThroughSupplierFail() {
            boolean isAdded = storage.add("classA", ClassA::new);
            assertFalse(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void testReplace() {
            ClassA classA = storage.get("classA");
            storage.replace("classA", new ClassA());
            ClassA newClassA = storage.get("classA");

            assertNotSame(classA, newClassA);
        }

        @Test
        void testRemove() {
            boolean isRemoved = storage.remove("classA");
            assertTrue(isRemoved);
            assertFalse(storage.has("classA"));
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
        void setUp() {
            storage = new NameStorage();
        }

        @Test
        void testAddObject() {
            boolean isAdded = storage.add(new ClassA());
            assertTrue(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void testAddObjectWithClass() {
            boolean isAdded = storage.add("classA", new ClassA());
            assertTrue(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void testAddObjectThroughSupplier() {
            boolean isAdded = storage.add(ClassA::new);
            assertTrue(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void testAddObjectWithClassThroughSupplier() {
            boolean isAdded = storage.add("classA", ClassA::new);
            assertTrue(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void testReplaceFail() {
            boolean isReplaced = storage.replace("classA", new ClassA());
            assertFalse(isReplaced);
            assertFalse(storage.has("classA"));
        }

        @Test
        void testRemove() {
            boolean isRemoved = storage.remove("classA");
            assertFalse(isRemoved);
            assertFalse(storage.has("classA"));
        }

    }

}
