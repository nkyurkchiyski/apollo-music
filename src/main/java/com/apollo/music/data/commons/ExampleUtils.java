package com.apollo.music.data.commons;

import org.springframework.data.domain.ExampleMatcher;

public class ExampleUtils {

    public final static ExampleMatcher CONTENT_MANAGER_EXAMPLE_MATCHER = ExampleMatcher.matching()
            .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withIgnoreNullValues();


    public static ExampleMatcher getExactIgnoreCaseExampleMatcher(final String property) {
        return ExampleMatcher.matching()
                .withMatcher(property, ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase())
                .withIgnoreNullValues();
    }
}
