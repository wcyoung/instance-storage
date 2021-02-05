package wcyoung.storage.instance.loader;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.ClassStorage;
import wcyoung.storage.instance.classes.*;
import wcyoung.storage.instance.collector.DependencyCollector;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DependentClassesLoaderTest {

    @Test
    void load() {
        ClassStorage storage = new ClassStorage();

        Map<Class<?>, List<Class<?>>> dependencies = new HashMap<>();
        dependencies.put(MechanicalPencil.class, Collections.singletonList(MechanicalPencilLead.class));
        dependencies.put(Student.class, Arrays.asList(MechanicalPencil.class, Eraser.class));

        DependentClassesLoader loader = new DependentClassesLoader(storage, dependencies);
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

        DependentClassesLoader loader = new DependentClassesLoader(storage, collector);
        boolean isLoaded = loader.load();
        assertTrue(isLoaded);
        assertTrue(storage.has(MechanicalPencil.class));
        assertTrue(storage.has(Student.class));

        MechanicalPencil pencil = storage.get(MechanicalPencil.class);
        Student student = storage.get(Student.class);
        assertSame(pencil, student.getMechanicalPencil());
    }

    @Test
    void loadFailBecauseStorageNull() {
        Map<Class<?>, List<Class<?>>> dependencies = new HashMap<>();
        dependencies.put(MechanicalPencil.class, Collections.singletonList(MechanicalPencilLead.class));

        DependentClassesLoader loader = new DependentClassesLoader(null, dependencies);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseDependenciesNull() {
        ClassStorage storage = new ClassStorage();

        DependentClassesLoader loader = new DependentClassesLoader(storage, (Map<Class<?>, List<Class<?>>>) null);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseCollectorNull() {
        ClassStorage storage = new ClassStorage();

        DependentClassesLoader loader = new DependentClassesLoader(storage,
                (DependencyCollector<Map<Class<?>, List<Class<?>>>>) null);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseInfiniteLoop() {
        ClassStorage storage = new ClassStorage();

        Map<Class<?>, List<Class<?>>> dependencies = new HashMap<>();
        dependencies.put(InfiniteLoop.class, Collections.singletonList(InfiniteLoop.class));

        DependentClassesLoader loader = new DependentClassesLoader(storage, dependencies);
        assertThrows(CircularReferenceException.class, loader::load);
    }

    @Test
    void loadFailBecauseDependenceOnEachOther() {
        ClassStorage storage = new ClassStorage();

        Map<Class<?>, List<Class<?>>> dependencies = new HashMap<>();
        dependencies.put(BestFriendA.class, Collections.singletonList(BestFriendB.class));
        dependencies.put(BestFriendB.class, Collections.singletonList(BestFriendA.class));

        DependentClassesLoader loader = new DependentClassesLoader(storage, dependencies);
        assertThrows(CircularReferenceException.class, loader::load);
    }

    @Test
    void loadFailBecauseCirculationDependence() {
        ClassStorage storage = new ClassStorage();

        Map<Class<?>, List<Class<?>>> dependencies = new HashMap<>();
        dependencies.put(Breakfast.class, Collections.singletonList(Lunch.class));
        dependencies.put(Lunch.class, Collections.singletonList(Dinner.class));
        dependencies.put(Dinner.class, Collections.singletonList(Breakfast.class));

        DependentClassesLoader loader = new DependentClassesLoader(storage, dependencies);
        assertThrows(CircularReferenceException.class, loader::load);
    }

}