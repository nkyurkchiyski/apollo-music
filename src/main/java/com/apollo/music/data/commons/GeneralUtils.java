package com.apollo.music.data.commons;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class GeneralUtils {

    public static <T> void performConditionalConsumer(final T subject, final Predicate<T> predicate, Consumer<T> consumer) {
        if (predicate.test(subject)) {
            consumer.accept(subject);
        }
    }
}
