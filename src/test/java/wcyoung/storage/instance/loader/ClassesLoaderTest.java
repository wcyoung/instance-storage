package wcyoung.storage.instance.loader;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.ClassStorage;
import wcyoung.storage.instance.classes.*;
import wcyoung.storage.instance.collector.DependencyCollector;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ClassesLoaderTest {

    @Test
    void load() {
        ClassStorage storage = new ClassStorage();

        Map<Class<?>, List<Class<?>>> dependencies = new HashMap<>();
        dependencies.put(MechanicalPencil.class, Collections.singletonList(MechanicalPencilLead.class));
        dependencies.put(Student.class, Arrays.asList(MechanicalPencil.class, Eraser.class));

        ClassesLoader loader = new ClassesLoader(storage, dependencies);
        boolean isLoaded = loader.load();
        assertTrue(isLoaded);
        assertTrue(storage.has(MechanicalPencil.class));
        assertTrue(storage.has(Student.class));

        MechanicalPencil pencil = storage.get(MechanicalPencil.class);
        Student student = storage.get(Student.class);
        assertSame(pencil, student.getMechanicalPencil());
    }

    @Test
    void loadWithCollector() {
        ClassStorage storage = new ClassStorage();

        DependencyCollector<Map<Class<?>, List<Class<?>>>> collector =
                new DependencyCollector<Map<Class<?>,List<Class<?>>>>() {
            @Override
            public Map<Class<?>, List<Class<?>>> collect() {
                Map<Class<?>, List<Class<?>>> dependencies = new HashMap<>();
                dependencies.put(MechanicalPencil.class, Collections.singletonList(MechanicalPencilLead.class));
                dependencies.put(Student.class, Arrays.asList(MechanicalPencil.class, Eraser.class));
                return dependencies;
            }
        };

        ClassesLoader loader = new ClassesLoader(storage, collector);
        boolean isLoaded = loader.load();
        assertTrue(isLoaded);
        assertTrue(storage.has(MechanicalPencil.class));
        assertTrue(storage.has(Student.class));

        MechanicalPencil pencil = storage.get(MechanicalPencil.class);
        Student student = storage.get(Student.class);
        assertSame(pencil, student.getMechanicalPencil());
    }

    @Test
    void loadFailBecauseStorageIsNull() {
        Map<Class<?>, List<Class<?>>> dependencies = new HashMap<>();
        dependencies.put(MechanicalPencil.class, Collections.singletonList(MechanicalPencilLead.class));

        ClassesLoader loader = new ClassesLoader(null, dependencies);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseDependenciesIsNull() {
        ClassStorage storage = new ClassStorage();

        ClassesLoader loader = new ClassesLoader(storage, (Map<Class<?>, List<Class<?>>>) null);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseCollectorIsNull() {
        ClassStorage storage = new ClassStorage();

        ClassesLoader loader = new ClassesLoader(storage, (DependencyCollector<Map<Class<?>, List<Class<?>>>>) null);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseInfiniteLoop() {
        ClassStorage storage = new ClassStorage();

        Map<Class<?>, List<Class<?>>> dependencies = new HashMap<>();
        dependencies.put(InfiniteLoop.class, Collections.singletonList(InfiniteLoop.class));

        ClassesLoader loader = new ClassesLoader(storage, dependencies);
        assertThrows(CircularReferenceException.class, loader::load);
    }

    @Test
    void loadFailBecauseDependenceOnEachOther() {
        ClassStorage storage = new ClassStorage();

        Map<Class<?>, List<Class<?>>> dependencies = new HashMap<>();
        dependencies.put(BestFriendA.class, Collections.singletonList(BestFriendB.class));
        dependencies.put(BestFriendB.class, Collections.singletonList(BestFriendA.class));

        ClassesLoader loader = new ClassesLoader(storage, dependencies);
        assertThrows(CircularReferenceException.class, loader::load);
    }

    @Test
    void loadFailBecauseCircularDependencies() {
        ClassStorage storage = new ClassStorage();

        Map<Class<?>, List<Class<?>>> dependencies = new HashMap<>();
        dependencies.put(Breakfast.class, Collections.singletonList(Lunch.class));
        dependencies.put(Lunch.class, Collections.singletonList(Dinner.class));
        dependencies.put(Dinner.class, Collections.singletonList(Breakfast.class));

        ClassesLoader loader = new ClassesLoader(storage, dependencies);
        assertThrows(CircularReferenceException.class, loader::load);
    }

}