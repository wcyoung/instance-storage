package wcyoung.storage.instance.loader;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.ClassStorage;
import wcyoung.storage.instance.classes.ClassA;
import wcyoung.storage.instance.scanner.ClassScanner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ScannedClassesInstanceLoaderTest {

    @Test
    void load() {
        ClassStorage storage = new ClassStorage();
        ClassScanner<Set<Class<?>>> scanner = new ClassScanner<Set<Class<?>>>() {
            @Override
            public Set<Class<?>> scan() {
                Set<Class<?>> classes = new HashSet<>();
                classes.add(ClassA.class);
                return classes;
            }
        };

        ScannedClassesInstanceLoader loader = new ScannedClassesInstanceLoader(storage, scanner);
        boolean isLoaded = loader.load();
        assertTrue(isLoaded);
        assertTrue(storage.has(ClassA.class));
    }

    @Test
    void loadFailBecauseStorageNull() {
        ClassScanner<Set<Class<?>>> scanner = new ClassScanner<Set<Class<?>>>() {
            @Override
            public Set<Class<?>> scan() {
                return null;
            }
        };

        ScannedClassesInstanceLoader loader = new ScannedClassesInstanceLoader(null, scanner);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseClassesNull() {
        ClassStorage storage = new ClassStorage();

        ScannedClassesInstanceLoader loader = new ScannedClassesInstanceLoader(storage, null);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

}