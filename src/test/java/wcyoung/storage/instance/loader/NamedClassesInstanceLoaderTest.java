package wcyoung.storage.instance.loader;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.NameStorage;
import wcyoung.storage.instance.classes.ClassA;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NamedClassesInstanceLoaderTest {

    @Test
    void load() {
        NameStorage storage = new NameStorage();
        Map<String, Class<?>> classes = new HashMap<>();
        classes.put("classA", ClassA.class);

        NamedClassesInstanceLoader loader = new NamedClassesInstanceLoader(classes);
        boolean isLoaded = loader.load(storage);
        assertTrue(isLoaded);
        assertTrue(storage.has("classA"));
    }

    @Test
    void loadFailBecauseStorageNull() {
        Map<String, Class<?>> classes = new HashMap<>();
        classes.put("classA", ClassA.class);

        NamedClassesInstanceLoader loader = new NamedClassesInstanceLoader(classes);
        boolean isLoaded = loader.load(null);
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseClassesNull() {
        NameStorage storage = new NameStorage();

        NamedClassesInstanceLoader loader = new NamedClassesInstanceLoader(null);
        boolean isLoaded = loader.load(storage);
        assertFalse(isLoaded);
    }

}
