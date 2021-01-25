package wcyoung.storage.instance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.classes.ClassA;
import wcyoung.storage.instance.classes.ClassB;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClassStorageTest {

    private ClassStorage storage;

    @BeforeEach
    void setUp() {
        storage = new ClassStorage();
    }

    @Nested
    class TestWithSetupData {

        @BeforeEach
        void setUpData() {
            storage.add(ClassA.class, new ClassA());
        }

        @Test
        void has() {
            assertTrue(storage.has(ClassA.class));
            assertFalse(storage.has(ClassB.class));
        }

        @Test
        void get() {
            assertNotNull(storage.get(ClassA.class));
            assertNull(storage.get(ClassB.class));
        }

        @Test
        void getWithType() {
            assertNotNull(storage.get(ClassA.class, ClassA.class));
            assertSame(ClassA.class, storage.get(ClassA.class, ClassA.class).getClass());
            assertNull(storage.get(ClassB.class));
        }

        @Test
        void keys() {
            Set<Class<?>> keys = storage.keys();

            Class<?>[] expectedKeys = {ClassA.class};
            assertArrayEquals(expectedKeys, keys.toArray(new Class<?>[0]));
        }

        @Test
        void size() {
            assertEquals(1, storage.size());
        }

        @Test
        void addObjectFail() {
            boolean isAdded = storage.add(new ClassA());
            assertFalse(isAdded);
            assertTrue(storage.has(ClassA.class));
        }

        @Test
        void addObjectWithClassFail() {
            boolean isAdded = storage.add(ClassA.class, new ClassA());
            assertFalse(isAdded);
            assertTrue(storage.has(ClassA.class));
        }

        @Test
        void addObjectThroughSupplierFail() {
            boolean isAdded = storage.add(ClassA::new);
            assertFalse(isAdded);
            assertTrue(storage.has(ClassA.class));
        }

        @Test
        void addObjectWithClassThroughSupplierFail() {
            boolean isAdded = storage.add(ClassA.class, ClassA::new);
            assertFalse(isAdded);
            assertTrue(storage.has(ClassA.class));
        }

        @Test
        void replace() {
            ClassA classA = storage.get(ClassA.class);
            storage.replace(ClassA.class, new ClassA());
            ClassA newClassA = storage.get(ClassA.class);

            assertNotNull(newClassA);
            assertNotSame(classA, newClassA);
        }

        @Test
        void replaceThroughSupplier() {
            ClassA classA = storage.get(ClassA.class);
            storage.replace(ClassA.class, ClassA::new);
            ClassA newClassA = storage.get(ClassA.class);

            assertNotNull(newClassA);
            assertNotSame(classA, newClassA);
        }

        @Test
        void merge() {
            ClassA classA = storage.get(ClassA.class);

            ClassStorage storageToMerge = new ClassStorage();
            storageToMerge.add(new ClassA());
            storageToMerge.add(new ClassB());

            boolean isMerged = storage.merge(storageToMerge);
            assertTrue(isMerged);

            assertSame(classA, storage.get(ClassA.class));
            assertTrue(storage.has(ClassB.class));
        }

        @Test
        void mergeOverride() {
            ClassA newClassA = new ClassA();

            ClassStorage storageToMerge = new ClassStorage();
            storageToMerge.add(newClassA);
            storageToMerge.add(new ClassB());

            boolean isMerged = storage.merge(storageToMerge, true);
            assertTrue(isMerged);

            assertSame(newClassA, storage.get(ClassA.class));
            assertTrue(storage.has(ClassB.class));
        }

        @Test
        void remove() {
            boolean isRemoved = storage.remove(ClassA.class);
            assertTrue(isRemoved);
            assertFalse(storage.has(ClassA.class));
        }

        @Test
        void clear() {
            storage.clear();
            assertEquals(0, storage.size());
        }

    }

    @Nested
    class TestWithoutSetupData {

        @Test
        void addObject() {
            boolean isAdded = storage.add(new ClassA());
            assertTrue(isAdded);
            assertTrue(storage.has(ClassA.class));
        }

        @Test
        void addObjectWithClass() {
            boolean isAdded = storage.add(ClassA.class, new ClassA());
            assertTrue(isAdded);
            assertTrue(storage.has(ClassA.class));
        }

        @Test
        void addObjectThroughSupplier() {
            boolean isAdded = storage.add(ClassA::new);
            assertTrue(isAdded);
            assertTrue(storage.has(ClassA.class));
        }

        @Test
        void addObjectWithClassThroughSupplier() {
            boolean isAdded = storage.add(ClassA.class, ClassA::new);
            assertTrue(isAdded);
            assertTrue(storage.has(ClassA.class));
        }

        @Test
        void replaceFail() {
            boolean isReplaced = storage.replace(ClassA.class, new ClassA());
            assertFalse(isReplaced);
            assertFalse(storage.has(ClassA.class));
        }

        @Test
        void replaceThroughSupplierFail() {
            boolean isReplaced = storage.replace(ClassA.class, ClassA::new);
            assertFalse(isReplaced);
            assertFalse(storage.has(ClassA.class));
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
        void removeFail() {
            boolean isRemoved = storage.remove(ClassA.class);
            assertFalse(isRemoved);
            assertFalse(storage.has(ClassA.class));
        }

    }

}
