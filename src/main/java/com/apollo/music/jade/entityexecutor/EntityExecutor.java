package com.apollo.music.jade.entityexecutor;

import com.apollo.music.data.entity.EntityWithId;
import com.apollo.music.jade.OntologyConfigurator;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.springframework.util.StringUtils;

import java.util.Collection;

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


    protected void applyAndSaveChanges(final OWLOntologyChange... changes) {
        ontoManager.applyChanges(changes);

        try {
            ontoManager.saveOntology(musicOntology);
        } catch (final OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }


    protected void applyAndSaveChanges(final Collection<? extends OWLOntologyChange> changes) {
        applyAndSaveChanges(changes.toArray(new OWLOntologyChange[0]));
    }

    protected String removeWhitespaces(final String arg) {
        return StringUtils.trimAllWhitespace(arg);
    }
}
