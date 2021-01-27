package wcyoung.storage.instance.loader;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.ClassStorage;

import static org.junit.jupiter.api.Assertions.*;

class AbstractInstanceLoaderTest {

    @Test
    void getStorage() {
        ClassStorage storage = new ClassStorage();
        AbstractInstanceLoader<Class<?>, Object> loader = new AbstractInstanceLoader<Class<?>, Object>(storage) {
            @Override
            public boolean load() {
                return false;
            }
        };

        assertSame(storage, loader.getStorage());
    }

    @Test
    void loadAndGetStorage() {
        ClassStorage storage = new ClassStorage();
        AbstractInstanceLoader<Class<?>, Object> loader = new AbstractInstanceLoader<Class<?>, Object>(storage) {
            @Override
            public boolean load() {
                return true;
            }
        };

        assertSame(storage, loader.loadAndGet());
    }

    @Test
    void loadAndGetNull() {
        AbstractInstanceLoader<Class<?>, Object> loader = new AbstractInstanceLoader<Class<?>, Object>(null) {
            @Override
            public boolean load() {
                return false;
            }
        };

        assertNull(loader.loadAndGet());
    }

}
