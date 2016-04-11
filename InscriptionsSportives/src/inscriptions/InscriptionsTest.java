package inscriptions;

import static org.junit.Assert.*;

import org.junit.Test;

public class InscriptionsTest {

	@Test
	public void testCreatePersonneStringStringString() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty@hotmail.fr");
		assertTrue(tony.getNom(), tony.getNom().equals("Tony"));
		assertTrue(tony.getPrenom(), tony.getPrenom().equals("Dent de plomb"));
		assertTrue(tony.getMail(), tony.getMail().equals("azerty@hotmail.fr"));
	}

	@Test
	public void testCreateCompetition() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition flechettes = inscriptions.createCompetition("Mondial de foot", null, false);
		assertTrue(flechettes.getNom(), flechettes.getNom().equals("Mondial de foot"));
	}
}
