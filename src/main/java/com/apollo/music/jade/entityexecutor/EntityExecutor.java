package com.apollo.music.jade.entityexecutor;

import com.apollo.music.data.entity.EntityWithId;
import com.apollo.music.jade.OntologyConfigurator;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public abstract class EntityExecutor<T extends EntityWithId> implements IEntityExecutor<T> {

    protected final OWLOntologyManager ontoManager;
    protected final OWLOntology musicOntology;
    protected final OWLDataFactory dataFactory;
    protected final String ontologyIRIStr;

    public EntityExecutor(final OntologyConfigurator ontologyConfigurator) {
        ontoManager = ontologyConfigurator.getOntoManager();
        musicOntology = ontologyConfigurator.getMusicOntology();
        dataFactory = ontologyConfigurator.getDataFactory();
        ontologyIRIStr = ontologyConfigurator.getOntologyIRIStr();
    }
}
