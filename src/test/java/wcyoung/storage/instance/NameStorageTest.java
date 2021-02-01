package wcyoung.storage.instance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.classes.ClassA;
import wcyoung.storage.instance.classes.ClassB;

import java.util.HashSet;
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
        void copy() {
            NameStorage copiedStorage = new NameStorage(storage);
            assertNotSame(storage, copiedStorage);
            assertTrue(copiedStorage.has("classA"));
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

            Set<String> expectedKeys = new HashSet<>();
            expectedKeys.add("classA");

            assertEquals(expectedKeys, keys);
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
        void merge() {
            ClassA classA = storage.get("classA");

            NameStorage storageToMerge = new NameStorage();
            storageToMerge.add(new ClassA());
            storageToMerge.add(new ClassB());

            boolean isMerged = storage.merge(storageToMerge);
            assertTrue(isMerged);

            assertSame(classA, storage.get("classA"));
            assertTrue(storage.has("classB"));
        }

        @Test
        void mergeOverride() {
            ClassA newClassA = new ClassA();

            NameStorage storageToMerge = new NameStorage();
            storageToMerge.add(newClassA);
            storageToMerge.add(new ClassB());

            boolean isMerged = storage.merge(storageToMerge, true);
            assertTrue(isMerged);

            assertSame(newClassA, storage.get("classA"));
            assertTrue(storage.has("classB"));
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
        void mergeFail() {
            assertFalse(storage.merge(null));
        }

        @Test
        void mergeOverrideFail() {
            assertFalse(storage.merge(null, true));
        }

        @Test
        void remove() {
            boolean isRemoved = storage.remove("classA");
            assertFalse(isRemoved);
            assertFalse(storage.has("classA"));
        }

    }

}
