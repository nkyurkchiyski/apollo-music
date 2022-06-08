package com.apollo.music.jade.entityexecutor;

import com.apollo.music.data.entity.Artist;
import com.apollo.music.jade.OntologyConfigurator;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.OWLEntityRemover;

public class ArtistExecutor extends EntityExecutor<Artist> {
    public ArtistExecutor(final OntologyConfigurator ontologyConfigurator) {
        super(ontologyConfigurator);
    }

    @Override
    public void insert(final Artist entity) {
        final String artistName = removeWhitespaces(entity.getName());
        final OWLClass artistClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Performer"));

        final OWLNamedIndividual artistIndividual = dataFactory.getOWLNamedIndividual(ontologyIRIStr + artistName);
        final OWLClassAssertionAxiom classAssertion = dataFactory.getOWLClassAssertionAxiom(artistClass, artistIndividual);
        final AddAxiom classAssertAxiom = new AddAxiom(musicOntology, classAssertion);

        final OWLDataProperty hasName = dataFactory.getOWLDataProperty(ontologyIRIStr + "hasName");
        final OWLDataPropertyAssertionAxiom nameAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasName, artistIndividual, entity.getName());
        final AddAxiom nameAssertionAxiom = new AddAxiom(musicOntology, nameAssertion);

        applyAndSaveChanges(classAssertAxiom, nameAssertionAxiom);
    }

    @Override
    public void delete(final Artist entity) {
        final String artistName = removeWhitespaces(entity.getName());
        final OWLClass artistClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Performer"));
        final OWLEntityRemover remover = new OWLEntityRemover(musicOntology);

        final ReasonerFactory factory = new ReasonerFactory();
        final OWLReasoner reasoner = factory.createReasoner(musicOntology);
        reasoner.instances(artistClass)
                .filter(x -> x.getIRI().toString().contains(artistName))
                .forEach(x -> x.accept(remover));
        applyAndSaveChanges(remover.getChanges());
    }
}
