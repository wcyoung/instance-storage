package wcyoung.storage.instance.loader;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.NameStorage;
import wcyoung.storage.instance.classes.ClassA;

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

}
