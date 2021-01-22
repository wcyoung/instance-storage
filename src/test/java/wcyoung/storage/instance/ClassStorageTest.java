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
        void testGetWithType() {
            assertNotNull(storage.get(ClassA.class, ClassA.class));
            assertSame(ClassA.class, storage.get(ClassA.class, ClassA.class).getClass());
            assertNull(storage.get(ClassB.class));
        }

        @Test
        void testKeys() {
            Set<Class<?>> keys = storage.keys();

            Class<?>[] expectedKeys = {ClassA.class};
            assertArrayEquals(expectedKeys, keys.toArray(new Class<?>[0]));
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
        void testAddObjectThroughSupplierFail() {
            boolean isAdded = storage.add(ClassA::new);
            assertFalse(isAdded);
            assertTrue(storage.has(ClassA.class));
        }

        @Test
        void testAddObjectWithClassThroughSupplierFail() {
            boolean isAdded = storage.add(ClassA.class, ClassA::new);
            assertFalse(isAdded);
            assertTrue(storage.has(ClassA.class));
        }

        @Test
        void testReplace() {
            ClassA classA = storage.get(ClassA.class);
            storage.replace(ClassA.class, new ClassA());
            ClassA newClassA = storage.get(ClassA.class);

            assertNotNull(newClassA);
            assertNotSame(classA, newClassA);
        }

        @Test
        void testReplaceThroughSupplier() {
            ClassA classA = storage.get(ClassA.class);
            storage.replace(ClassA.class, ClassA::new);
            ClassA newClassA = storage.get(ClassA.class);

            assertNotNull(newClassA);
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
        void testAddObjectThroughSupplier() {
            boolean isAdded = storage.add(ClassA::new);
            assertTrue(isAdded);
            assertTrue(storage.has(ClassA.class));
        }

        @Test
        void testAddObjectWithClassThroughSupplier() {
            boolean isAdded = storage.add(ClassA.class, ClassA::new);
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
        void testReplaceThroughSupplierFail() {
            boolean isReplaced = storage.replace(ClassA.class, ClassA::new);
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
