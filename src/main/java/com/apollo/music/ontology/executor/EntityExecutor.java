package com.apollo.music.ontology.executor;

import com.apollo.music.data.entity.EntityWithId;
import com.apollo.music.ontology.OntologyConfigurator;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.OWLEntityRemover;

import java.util.Collection;

public abstract class EntityExecutor<T extends EntityWithId> implements IEntityExecutor<T> {

    protected final OWLOntologyManager ontoManager;
    protected final OWLOntology musicOntology;
    protected final OWLDataFactory dataFactory;
    protected final String ontologyIRIStr;
    protected final Reasoner reasoner;

    public EntityExecutor(final OntologyConfigurator ontologyConfigurator) {
        ontoManager = ontologyConfigurator.getOntoManager();
        musicOntology = ontologyConfigurator.getMusicOntology();
        dataFactory = ontologyConfigurator.getDataFactory();
        ontologyIRIStr = ontologyConfigurator.getOntologyIRIStr();

        final ReasonerFactory factory = new ReasonerFactory();
        reasoner = (Reasoner) factory.createReasoner(musicOntology);
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

    protected AddAxiom createObjPropertyAddAxiom(final OWLNamedIndividual songIndividual,
                                                 final String objPropName,
                                                 final String refIndividualName) {
        final OWLNamedIndividual refIndividual = dataFactory.getOWLNamedIndividual(ontologyIRIStr + refIndividualName);
        final OWLObjectProperty objProp = dataFactory.getOWLObjectProperty(ontologyIRIStr + objPropName);
        final OWLObjectPropertyAssertionAxiom objPropAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom(objProp, songIndividual, refIndividual);
        return new AddAxiom(musicOntology, objPropAssertion);
    }


    protected void removeInstances(final String className, final String individualName) {
        final OWLClass entityClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + className));
        final OWLEntityRemover remover = new OWLEntityRemover(musicOntology);

        reasoner.instances(entityClass)
                .filter(x -> x.getIRI().toString().contains(individualName))
                .forEach(x -> x.accept(remover));
        applyAndSaveChanges(remover.getChanges());
    }

}
