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
        void has() {
            assertTrue(storage.has("classA"));
            assertFalse(storage.has("classB"));
        }

        @Test
        void get() {
            assertNotNull(storage.get("classA"));
            assertNull(storage.get("classB"));
        }

        @Test
        void getWithType() {
            assertNotNull(storage.get("classA", ClassA.class));
            assertSame(ClassA.class, storage.get("classA", ClassA.class).getClass());
            assertNull(storage.get("classB"));
        }

        @Test
        void keys() {
            Set<String> keys = storage.keys();

            String[] expectedKeys = {"classA"};
            assertArrayEquals(expectedKeys, keys.toArray(new String[0]));
        }

        @Test
        void size() {
            assertEquals(1, storage.size());
        }

        @Test
        void addObjectFail() {
            boolean isAdded = storage.add(new ClassA());
            assertFalse(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void addObjectWithClassFail() {
            boolean isAdded = storage.add("classA", new ClassA());
            assertFalse(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void addObjectThroughSupplierFail() {
            boolean isAdded = storage.add(ClassA::new);
            assertFalse(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void addObjectWithClassThroughSupplierFail() {
            boolean isAdded = storage.add("classA", ClassA::new);
            assertFalse(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void replace() {
            ClassA classA = storage.get("classA");
            storage.replace("classA", new ClassA());
            ClassA newClassA = storage.get("classA");

            assertNotNull(newClassA);
            assertNotSame(classA, newClassA);
        }

        @Test
        void replaceThroughSupplier() {
            ClassA classA = storage.get("classA");
            storage.replace("classA", ClassA::new);
            ClassA newClassA = storage.get("classA");

            assertNotNull(newClassA);
            assertNotSame(classA, newClassA);
        }

        @Test
        void remove() {
            boolean isRemoved = storage.remove("classA");
            assertTrue(isRemoved);
            assertFalse(storage.has("classA"));
        }

        @Test
        void clear() {
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
        void addObject() {
            boolean isAdded = storage.add(new ClassA());
            assertTrue(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void addObjectWithClass() {
            boolean isAdded = storage.add("classA", new ClassA());
            assertTrue(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void addObjectThroughSupplier() {
            boolean isAdded = storage.add(ClassA::new);
            assertTrue(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void addObjectWithClassThroughSupplier() {
            boolean isAdded = storage.add("classA", ClassA::new);
            assertTrue(isAdded);
            assertTrue(storage.has("classA"));
        }

        @Test
        void replaceFail() {
            boolean isReplaced = storage.replace("classA", new ClassA());
            assertFalse(isReplaced);
            assertFalse(storage.has("classA"));
        }

        @Test
        void replaceThroughSupplierFail() {
            boolean isReplaced = storage.replace("classA", ClassA::new);
            assertFalse(isReplaced);
            assertFalse(storage.has("classA"));
        }

        @Test
        void remove() {
            boolean isRemoved = storage.remove("classA");
            assertFalse(isRemoved);
            assertFalse(storage.has("classA"));
        }

    }

}
