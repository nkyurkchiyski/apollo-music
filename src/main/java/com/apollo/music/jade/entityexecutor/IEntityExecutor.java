package com.apollo.music.jade.entityexecutor;

import com.apollo.music.data.entity.EntityWithId;

public interface IEntityExecutor<T extends EntityWithId> {

    void insert(final T entity);

    void delete(final T entity);
}
