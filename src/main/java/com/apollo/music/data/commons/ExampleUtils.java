package com.apollo.music.data.commons;

import org.springframework.data.domain.ExampleMatcher;

public class ExampleUtils {

    public final static ExampleMatcher CONTENT_MANAGER_EXAMPLE_MATCHER = ExampleMatcher.matching().withIgnoreNullValues();


    public final static ExampleMatcher NAME_EXAMPLE_MATCHER_ALL = ExampleMatcher.matchingAll()
            .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withIgnoreNullValues();
}
