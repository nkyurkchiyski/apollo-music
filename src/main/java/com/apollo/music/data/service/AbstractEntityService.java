package com.apollo.music.data.service;

import com.apollo.music.data.entity.EntityWithId;
import org.vaadin.artur.helpers.CrudService;

public abstract class AbstractEntityService<T extends EntityWithId> extends CrudService<T, String> {


}
