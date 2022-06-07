package com.apollo.music.jade.agent.editor;

import com.apollo.music.data.entity.EntityWithId;
import com.apollo.music.jade.OntologyConfigurator;
import com.apollo.music.jade.entityexecutor.IEntityExecutor;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public abstract class EntityEditorAgent<T extends EntityWithId> extends Agent {
    private static final String ADD = "add";
    private static final String REMOVE = "remove";

    @Override
    protected void setup() {
        final String operation = (String) getArguments()[0];
        final T entity = createEntity();
        final OntologyConfigurator configurator = new OntologyConfigurator();
        final IEntityExecutor<T> executor = createNewExecutor(configurator);

        if (operation.equals(ADD)) {
            addBehaviour(new CreateContentBehaviour<>(executor, entity));
        } else if (operation.equals(REMOVE)) {
            addBehaviour(new DeleteContentBehaviour<>(executor, entity));
        }
    }

    protected abstract T createEntity();

    protected abstract IEntityExecutor<T> createNewExecutor(final OntologyConfigurator configurator);

    private static class CreateContentBehaviour<T extends EntityWithId> extends OneShotBehaviour {
        private final IEntityExecutor<T> executor;
        private final T entity;

        public CreateContentBehaviour(final IEntityExecutor<T> executor,
                                      final T entity) {
            this.executor = executor;
            this.entity = entity;
        }

        @Override
        public void action() {
            executor.insert(entity);
            myAgent.doDelete();
        }
    }


    private static class DeleteContentBehaviour<T extends EntityWithId> extends OneShotBehaviour {
        private final IEntityExecutor<T> executor;
        private final T entity;

        public DeleteContentBehaviour(final IEntityExecutor<T> executor,
                                      final T entity) {
            this.executor = executor;
            this.entity = entity;
        }

        @Override
        public void action() {
            executor.delete(entity);
            myAgent.doDelete();
        }
    }
}
