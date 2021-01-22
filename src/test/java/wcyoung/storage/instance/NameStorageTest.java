package wcyoung.storage.instance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.classes.ClassA;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
        void testGetWithType() {
            assertNotNull(storage.get("classA", ClassA.class));
            assertSame(ClassA.class, storage.get("classA", ClassA.class).getClass());
            assertNull(storage.get("classB"));
        }

        @Test
        void testKeys() {
            Set<String> keys = storage.keys();

            String[] expectedKeys = {"classA"};
            assertArrayEquals(expectedKeys, keys.toArray(new String[0]));
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

            assertNotNull(newClassA);
            assertNotSame(classA, newClassA);
        }

        @Test
        void testReplaceThroughSupplier() {
            ClassA classA = storage.get("classA");
            storage.replace("classA", ClassA::new);
            ClassA newClassA = storage.get("classA");

            assertNotNull(newClassA);
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
        void testReplaceThroughSupplierFail() {
            boolean isReplaced = storage.replace("classA", ClassA::new);
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
