package wcyoung.storage.instance.loader;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.ClassStorage;

import static org.junit.jupiter.api.Assertions.*;

class AbstractInstanceLoaderTest {

    @Test
    void getStorage() {
        ClassStorage storage = new ClassStorage();
        AbstractInstanceLoader loader = new AbstractInstanceLoader(storage) {
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
        AbstractInstanceLoader loader = new AbstractInstanceLoader(storage) {
            @Override
            public boolean load() {
                return true;
            }
        };

        assertSame(storage, loader.loadAndGet());
    }

    @Test
    void loadAndGetNull() {
        AbstractInstanceLoader loader = new AbstractInstanceLoader(null) {
            @Override
            public boolean load() {
                return false;
            }
        };

        assertNull(loader.loadAndGet());
    }

}
