package wcyoung.storage.instance.loader;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.ClassStorage;
import wcyoung.storage.instance.classes.ClassA;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MappedClassesInstanceLoaderTest {

    @Test
    void load() {
        ClassStorage storage = new ClassStorage();
        Map<Class<?>, Class<?>> classes = new HashMap<>();
        classes.put(ClassA.class, ClassA.class);

        MappedClassesInstanceLoader loader = new MappedClassesInstanceLoader(storage, classes);
        boolean isLoaded = loader.load();
        assertTrue(isLoaded);
        assertTrue(storage.has(ClassA.class));
    }

    @Test
    void loadFailBecauseStorageNull() {
        Map<Class<?>, Class<?>> classes = new HashMap<>();
        classes.put(ClassA.class, ClassA.class);

        MappedClassesInstanceLoader loader = new MappedClassesInstanceLoader(null, classes);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseClassesNull() {
        ClassStorage storage = new ClassStorage();

        MappedClassesInstanceLoader loader = new MappedClassesInstanceLoader(storage, null);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

}
