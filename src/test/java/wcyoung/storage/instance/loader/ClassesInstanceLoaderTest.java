package wcyoung.storage.instance.loader;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.ClassStorage;
import wcyoung.storage.instance.classes.ClassA;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClassesInstanceLoaderTest {

    @Test
    void load() {
        ClassStorage storage = new ClassStorage();
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ClassA.class);

        ClassesInstanceLoader loader = new ClassesInstanceLoader(storage, classes);
        boolean isLoaded = loader.load();
        assertTrue(isLoaded);
        assertTrue(storage.has(ClassA.class));
    }

    @Test
    void loadFailBecauseStorageNull() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(ClassA.class);

        ClassesInstanceLoader loader = new ClassesInstanceLoader(null, classes);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseClassesNull() {
        ClassStorage storage = new ClassStorage();

        ClassesInstanceLoader loader = new ClassesInstanceLoader(storage, null);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

}
