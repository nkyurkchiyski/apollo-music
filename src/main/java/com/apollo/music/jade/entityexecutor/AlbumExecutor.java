package com.apollo.music.jade.entityexecutor;

import com.apollo.music.data.commons.GeneralUtils;
import com.apollo.music.data.entity.Album;
import com.apollo.music.jade.OntologyConfigurator;
import com.apollo.music.views.commons.ViewConstants;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

public class AlbumExecutor extends EntityExecutor<Album> {
    public AlbumExecutor(final OntologyConfigurator ontologyConfigurator) {
        super(ontologyConfigurator);
    }

    @Override
    public void insert(final Album entity) {
        final String albumName = GeneralUtils.stripWhitespaces(entity.getName() + entity.getArtist().getName());
        final String artistName = GeneralUtils.stripWhitespaces(entity.getArtist().getName());

        final OWLClass albumClass = dataFactory.getOWLClass(IRI.create(ontologyIRIStr + "Album"));

        final OWLNamedIndividual albumIndividual = dataFactory.getOWLNamedIndividual(ontologyIRIStr + albumName);
        final OWLClassAssertionAxiom classAssertion = dataFactory.getOWLClassAssertionAxiom(albumClass, albumIndividual);
        final AddAxiom classAssertAxiom = new AddAxiom(musicOntology, classAssertion);

        final AddAxiom isAlbumOfAssertionAxiom = createObjPropertyAddAxiom(albumIndividual, "isMadeAlbumBy", artistName);

        final OWLDataProperty hasReleaseDate = dataFactory.getOWLDataProperty(ontologyIRIStr + "hasPublishDate");
        final OWLDatatype datatype = dataFactory.getOWLDatatype(ontologyIRIStr + "xsd:dateTime");
        final OWLLiteral literal = dataFactory.getOWLLiteral(ViewConstants.DATE_FORMAT.format(entity.getReleasedOn()), datatype);
        final OWLDataPropertyAssertionAxiom releaseDateAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasReleaseDate, albumIndividual, literal);
        final AddAxiom releaseDateAssertionAxiom = new AddAxiom(musicOntology, releaseDateAssertion);

        final OWLDataProperty hasTitle = dataFactory.getOWLDataProperty(ontologyIRIStr + "hasFullTitle");
        final OWLDataPropertyAssertionAxiom titleAssertion = dataFactory.getOWLDataPropertyAssertionAxiom(hasTitle, albumIndividual, entity.getName());
        final AddAxiom titleAssertionAxiom = new AddAxiom(musicOntology, titleAssertion);

        applyAndSaveChanges(classAssertAxiom, isAlbumOfAssertionAxiom, releaseDateAssertionAxiom, titleAssertionAxiom);

    }

    @Override
    public void delete(final Album entity) {
        final String albumName = GeneralUtils.stripWhitespaces(entity.getName() + entity.getArtist().getName());
        removeInstances("Album", albumName);
    }
}
