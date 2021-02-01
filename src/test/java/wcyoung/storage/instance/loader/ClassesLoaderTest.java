package wcyoung.storage.instance.loader;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.ClassStorage;
import wcyoung.storage.instance.classes.ClassA;
import wcyoung.storage.instance.scanner.ClassScanner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClassesLoaderTest {

    @Test
    void load() {
        ClassStorage storage = new ClassStorage();
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ClassA.class);

        ClassesLoader loader = new ClassesLoader(storage, classes);
        boolean isLoaded = loader.load();
        assertTrue(isLoaded);
        assertTrue(storage.has(ClassA.class));
    }

    @Test
    void loadWithScanner() {
        ClassStorage storage = new ClassStorage();
        ClassScanner<Set<Class<?>>> scanner = new ClassScanner<Set<Class<?>>>() {
            @Override
            public Set<Class<?>> scan() {
                Set<Class<?>> classes = new HashSet<>();
                classes.add(ClassA.class);
                return classes;
            }
        };

        ClassesLoader loader = new ClassesLoader(storage, scanner);
        boolean isLoaded = loader.load();
        assertTrue(isLoaded);
        assertTrue(storage.has(ClassA.class));
    }

    @Test
    void loadFailBecauseStorageNull() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ClassA.class);

        ClassesLoader loader = new ClassesLoader(null, classes);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseClassesNull() {
        ClassStorage storage = new ClassStorage();

        ClassesLoader loader = new ClassesLoader(storage, (Set<Class<?>>) null);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseScannerNull() {
        ClassStorage storage = new ClassStorage();

        ClassesLoader loader = new ClassesLoader(storage, (ClassScanner<Set<Class<?>>>) null);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

}
