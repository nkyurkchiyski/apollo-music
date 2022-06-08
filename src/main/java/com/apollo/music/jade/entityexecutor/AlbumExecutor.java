package com.apollo.music.jade.entityexecutor;

import com.apollo.music.data.entity.Album;
import com.apollo.music.jade.OntologyConfigurator;
import com.apollo.music.views.commons.ViewConstants;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.OWLEntityRemover;

public class AlbumExecutor extends EntityExecutor<Album> {
    public AlbumExecutor(final OntologyConfigurator ontologyConfigurator) {
        super(ontologyConfigurator);
    }

    @Override
    public void insert(final Album entity) {
        final String albumName = removeWhitespaces(entity.getName() + entity.getArtist().getName());
        final String artistName = removeWhitespaces(entity.getArtist().getName());

        final OWLClass albumClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Album"));

        final OWLNamedIndividual albumIndividual = dataFactory.getOWLNamedIndividual(ontologyIRIStr + albumName);
        final OWLClassAssertionAxiom classAssertion = dataFactory.getOWLClassAssertionAxiom(albumClass, albumIndividual);
        final AddAxiom classAssertAxiom = new AddAxiom(musicOntology, classAssertion);

        final OWLNamedIndividual artistIndividual = dataFactory.getOWLNamedIndividual(ontologyIRIStr + artistName);

        final OWLObjectProperty hasPerformer = dataFactory.getOWLObjectProperty(ontologyIRIStr + "hasPerformer");
        final OWLObjectPropertyAssertionAxiom performerAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom(hasPerformer, albumIndividual, artistIndividual);
        final AddAxiom performerAssertionAxiom = new AddAxiom(musicOntology, performerAssertion);

        final OWLDataProperty hasReleaseDate = dataFactory.getOWLDataProperty(ontologyIRIStr + "hasReleaseDate");
        final OWLDatatype datatype = dataFactory.getOWLDatatype(ontologyIRIStr + "xsd:datetime");
        final OWLLiteral literal = dataFactory.getOWLLiteral(ViewConstants.DATE_FORMAT.format(entity.getReleasedOn()), datatype);
        final OWLDataPropertyAssertionAxiom releaseDateAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasReleaseDate, albumIndividual, literal);
        final AddAxiom releaseDateAssertionAxiom = new AddAxiom(musicOntology, releaseDateAssertion);

        final OWLDataProperty hasTitle = dataFactory.getOWLDataProperty(ontologyIRIStr + "hasTitle");
        final OWLDataPropertyAssertionAxiom titleAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasTitle, albumIndividual, entity.getName());
        final AddAxiom titleAssertionAxiom = new AddAxiom(musicOntology, titleAssertion);

        applyAndSaveChanges(classAssertAxiom, performerAssertionAxiom, releaseDateAssertionAxiom, titleAssertionAxiom);

    }

    @Override
    public void delete(final Album entity) {
        final String albumName = removeWhitespaces(entity.getName() + entity.getArtist().getName());

        final OWLClass albumClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Album"));
        final OWLEntityRemover remover = new OWLEntityRemover(musicOntology);

        final ReasonerFactory factory = new ReasonerFactory();
        final OWLReasoner reasoner = factory.createReasoner(musicOntology);
        reasoner.instances(albumClass)
                .filter(x -> x.getIRI().toString().contains(albumName))
                .forEach(x -> x.accept(remover));
        applyAndSaveChanges(remover.getChanges());

    }
}
