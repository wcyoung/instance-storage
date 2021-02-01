package wcyoung.storage.instance.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import wcyoung.storage.instance.Storage;
import wcyoung.storage.instance.collector.DependencyCollector;
import wcyoung.storage.instance.generator.InstanceGenerator;

public class DependentClassesLoader extends AbstractStorageLoader<Class<?>, Object> {

    private Map<Class<?>, List<Class<?>>> dependencies;
    private DependencyCollector<Map<Class<?>, List<Class<?>>>> collector;

    public DependentClassesLoader(Storage<Class<?>, Object> storage, Map<Class<?>, List<Class<?>>> dependencies) {
        super(storage);
        this.dependencies = dependencies;
    }

    public DependentClassesLoader(Storage<Class<?>, Object> storage,
                                  DependencyCollector<Map<Class<?>, List<Class<?>>>> collector) {
        super(storage);
        this.collector = collector;
    }

    @Override
    public boolean load() {
        if (storage == null) {
            return false;
        }

        if (collector != null) {
            dependencies = collector.collect();
        }

        if (dependencies == null) {
            return false;
        }

        for (Map.Entry<Class<?>, List<Class<?>>> entry : dependencies.entrySet()) {
            Class<?> clazz = entry.getKey();
            if (storage.has(clazz)) {
                continue;
            }

            List<Class<?>> references = new ArrayList<>();

            Object instance = generateWithDependencies(clazz, references);
            storage.add(clazz, instance);
        }

        return true;
    }

    private Object generateWithDependencies(Class<?> reference, List<Class<?>> references) {
        references.add(reference);

        List<Object> parameters = new ArrayList<>();

        for (Class<?> clazz : dependencies.get(reference)) {
            if (references.contains(clazz)) {
                List<Class<?>> circularReferences = new ArrayList<>(
                        references.subList(references.indexOf(clazz), references.size()));
                throw new CircularReferenceException(getExceptionMessage(circularReferences));
            }

            Object parameter;
            if (storage.has(clazz)) {
                parameter = storage.get(clazz);
            } else if (dependencies.containsKey(clazz)) {
                parameter = generateWithDependencies(clazz, references);
            } else {
                parameter = null;
            }

            parameters.add(parameter);
        }

        references.remove(reference);

        Object instance = InstanceGenerator.generate(reference, parameters.toArray(new Object[0]));
        storage.add(reference, instance);

        return instance;
    }

    private String getExceptionMessage(List<Class<?>> circularReferences) {
        StringBuilder sb = new StringBuilder("Circular reference error.\n");
        sb.append("┌─────┐\n");

        String[] classes = circularReferences.stream()
                .map(clazz -> "↑    " + clazz + "\n")
                .toArray(String[]::new);

        sb.append(String.join("│     ↓\n", classes));
        sb.append("└─────┘");

        return sb.toString();
    }

}
