package com.apollo.music.views.provider;

import com.apollo.music.data.entity.EntityWithId;
import com.apollo.music.data.service.AbstractEntityService;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;

import java.util.Arrays;
import java.util.List;

public class EntityServiceDataProvider<T extends EntityWithId, F> extends FilterablePageableDataProvider<T, F> {

    private final AbstractEntityService<T> service;
    private final List<QuerySortOrder> defaultSortOrders;

    public EntityServiceDataProvider(final AbstractEntityService<T> service, final QuerySortOrder... defaultSortOrders) {
        this.service = service;
        this.defaultSortOrders = Arrays.asList(defaultSortOrders);
    }

    @Override
    protected Page<T> fetchFromBackEnd(final Query<T, F> query, final Pageable pageable) {
        return service.list(pageable);
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        return defaultSortOrders;
    }

    @Override
    protected int sizeInBackEnd(final Query<T, F> query) {
        return service.count();
    }
}