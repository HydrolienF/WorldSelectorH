package fr.formiko.worldselectorh;

import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<T, U, V> {
    void accept(T t, U u, V v);

    default TriConsumer<T, U, V> andThen(TriConsumer<? super T, ? super U, ? super V> tc) {
        Objects.requireNonNull(tc);
        return (t, u, v) -> {
            this.accept(t, u, v);
            tc.accept(t, u, v);
        };
    }
}
