package wcyoung.storage.instance.loader;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.NameStorage;
import wcyoung.storage.instance.classes.ClassA;
import wcyoung.storage.instance.scanner.ClassScanner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NamedClassesLoaderTest {

    @Test
    void load() {
        NameStorage storage = new NameStorage();
        Map<String, Class<?>> classes = new HashMap<>();
        classes.put("classA", ClassA.class);

        NamedClassesLoader loader = new NamedClassesLoader(storage, classes);
        boolean isLoaded = loader.load();
        assertTrue(isLoaded);
        assertTrue(storage.has("classA"));
    }

    @Test
    void loadWithScanner() {
        NameStorage storage = new NameStorage();
        ClassScanner<Map<String, Class<?>>> scanner = new ClassScanner<Map<String, Class<?>>>() {
            @Override
            public Map<String, Class<?>> scan() {
                Map<String, Class<?>> classes = new HashMap<>();
                classes.put("classA", ClassA.class);
                return classes;
            }
        };

        NamedClassesLoader loader = new NamedClassesLoader(storage, scanner);
        boolean isLoaded = loader.load();
        assertTrue(isLoaded);
        assertTrue(storage.has("classA"));
    }

    @Test
    void loadFailBecauseStorageNull() {
        Map<String, Class<?>> classes = new HashMap<>();
        classes.put("classA", ClassA.class);

        NamedClassesLoader loader = new NamedClassesLoader(null, classes);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseClassesNull() {
        NameStorage storage = new NameStorage();

        NamedClassesLoader loader = new NamedClassesLoader(storage, (Map<String, Class<?>>) null);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseScannerNull() {
        NameStorage storage = new NameStorage();

        NamedClassesLoader loader = new NamedClassesLoader(storage, (ClassScanner<Map<String, Class<?>>>) null);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

}
