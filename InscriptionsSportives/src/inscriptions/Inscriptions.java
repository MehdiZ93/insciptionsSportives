package inscriptions;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Point d'entrée dans l'application, un seul objet de type Inscription
 * permet de gérer les compétitions, candidats (de type equipe ou personne)
 * ainsi que d'inscrire des candidats à des compétition.
 */

public class Inscriptions implements Serializable
{
	private static final long serialVersionUID = -3095339436048473524L;
	private static final String FILE_NAME = "Inscriptions.srz";
	private static Inscriptions inscriptions;
	
	private SortedSet<Competition> competitions = new TreeSet<>();
	private SortedSet<Candidat> candidats = new TreeSet<>();

	private Inscriptions()
	{
	}
	
	/**
	 * Retourne les compétitions.
	 * @return
	 */
	
	public SortedSet<Competition> getCompetitions()
	{
		return Collections.unmodifiableSortedSet(competitions);
	}
	
	/**
	 * Retourne tous les candidats (personnes et équipes confondues).
	 * @return
	 */
	
	public SortedSet<Candidat> getCandidats()
	{
		return Collections.unmodifiableSortedSet(candidats);
	}

	/**
	 * Retourne toutes les personnes.
	 * @return
	 */
	
	public SortedSet<Personne> getPersonnes()
	{
		SortedSet<Personne> personnes = new TreeSet<>();
		for (Candidat c : getCandidats())
			if (c instanceof Personne)
				personnes.add((Personne)c);
		return Collections.unmodifiableSortedSet(personnes);
	}

	/**
	 * Retourne toutes les équipes.
	 * @return
	 */
	
	public SortedSet<Equipe> getEquipes()
	{
		SortedSet<Equipe> equipes = new TreeSet<>();
		for (Candidat c : getCandidats())
			if (c instanceof Equipe)
				equipes.add((Equipe)c);
		return Collections.unmodifiableSortedSet(equipes);
	}

	/**
	 * Créée une compétition. Ceci est le seul moyen, il n'y a pas
	 * de constructeur public dans {@link Competition}.
	 * @param nom
	 * @param dateCloture
	 * @param enEquipe
	 * @return
	 */
	
	public Competition createCompetition(String nom, 
			LocalDate dateCloture, boolean enEquipe)
	{
		Competition competition = new Competition(this, nom, dateCloture, enEquipe);
		competitions.add(competition);
		return competition;
	}

	/**
	 * Créée une Candidat de type Personne. Ceci est le seul moyen, il n'y a pas
	 * de constructeur public dans {@link Personne}.

	 * @param nom
	 * @param prenom
	 * @param mail
	 * @return
	 */
	public Personne createPersonne(String nom, String prenom, String mail)
	{
		Personne personne = new Personne(this, nom, prenom, mail);
		candidats.add(personne);
		return personne;
	}
	
	public Personne createPersonne(Inscriptions inscription)
	{
		String nom = EntreesSorties.getString("Entrer le nom : ");
		String prenom = EntreesSorties.getString("Entrer le prenom : ");
		String mail = EntreesSorties.getString("Entrer le mail : ");
		Connection c = inscription.connexion("jdbc:mysql://localhost/inscription", "root", "");
		try
		{
		    String req = "INSERT INTO personne(nom, prenom, mail) VALUES ('" + nom + "','"+ prenom +"','"+ mail +"')";
			Statement s = c.createStatement();
			int nbMaj = s.executeUpdate(req);
			if (nbMaj == 0)
			System.out.println("Erreur lors de l'enregistrement en base de données");
		}
		catch (SQLException e)
		{
		        System.out.println(e);
		}
		inscription.deconnexion(c);
		Personne personne = new Personne(this, nom, prenom, mail);
		candidats.add(personne);
		return personne;
	}
	
	/**
	 * Créée une Candidat de type équipe. Ceci est le seul moyen, il n'y a pas
	 * de constructeur public dans {@link Equipe}.
	 * @param nom
	 * @param prenom
	 * @param mail
	 * @return
	 */
	
	public Equipe createEquipe(String nom)
	{
		Equipe equipe = new Equipe(this, nom);
		candidats.add(equipe);
		return equipe;
	}
	
	void remove(Competition competition)
	{
		competitions.remove(competition);
	}
	
	void remove(Candidat candidat)
	{
		candidats.remove(candidat);
	}
	
	/**
	 * Retourne l'unique instance de cette classe.
	 * Crée cet objet s'il n'existe déjà.
	 * @return l'unique objet de type {@link Inscriptions}.
	 */
	
	public static Inscriptions getInscriptions()
	{
		
		if (inscriptions == null)
		{
			inscriptions = readObject();
			if (inscriptions == null)
				inscriptions = new Inscriptions();
		}
		return inscriptions;
	}

	/**
	 * Retourne un object inscriptions vide. Ne modifie pas les compétitions
	 * et candidats déjà existants.
	 */
	
	public Inscriptions reinitialiser()
	{
		inscriptions = new Inscriptions();
		return getInscriptions();
	}

	/**
	 * Efface toutes les modifications sur Inscriptions depuis la dernière sauvegarde.
	 * Ne modifie pas les compétitions et candidats déjà existants.
	 */
	
	public Inscriptions recharger()
	{
		inscriptions = null;
		return getInscriptions();
	}
	
	private static Inscriptions readObject()
	{
		ObjectInputStream ois = null;
		try
		{
			FileInputStream fis = new FileInputStream(FILE_NAME);
			ois = new ObjectInputStream(fis);
			return (Inscriptions)(ois.readObject());
		}
		catch (IOException | ClassNotFoundException e)
		{
			return null;
		}
		finally
		{
				try
				{
					if (ois != null)
						ois.close();
				} 
				catch (IOException e){}
		}	
	}
	
	/**
	 * Sauvegarde le gestionnaire pour qu'il soit ouvert automatiquement 
	 * lors d'une exécution ultérieure du programme.
	 * @throws IOException 
	 */
	
	public void sauvegarder() throws IOException
	{
		ObjectOutputStream oos = null;
		try
		{
			FileOutputStream fis = new FileOutputStream(FILE_NAME);
			oos = new ObjectOutputStream(fis);
			oos.writeObject(this);
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			try
			{
				if (oos != null)
					oos.close();
			} 
			catch (IOException e){}
		}
	}
	
	public Connection connexion(String bd,String utilisateur ,String mdp)
	{
		Connection c = null;
        try
        {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost/inscription", user = "root", password = "";
                c = DriverManager.getConnection(url, user, password);
        }
        catch (ClassNotFoundException e)
        {
                System.out.println("Pilote JDBC non installé.");
        }
        catch (SQLException e)
        {
                System.out.println(e);
        }
        return c;
	}
	
	public  boolean deconnexion(Connection c)
	{
		boolean deconnexion = true;
		try
        {
                if (c != null)
                        c.close();
        }
        catch (SQLException e)
        {
                System.out.println("Impossible de fermer la connection.");
                deconnexion = false;
        }
		return deconnexion;
	}
	@Override
	public String toString()
	{
		return "Candidats : " + getCandidats().toString()
			+ "\nCompetitions  " + getCompetitions().toString();
	}
	
	public static void main(String[] args)
	{
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
        Menu menu = new Menu("Menu Principal");
        Menu menuInscription = new Menu("Inscription", "1");
        Option personne = new Option("Ajouter une personne", "1");
        Option equipe = new Option("Ajouter une �quipe", "2");
        Option listePersonnes = new Option("Liste des personnes", "3");
        Option listeEquipe = new Option("Liste des equipes", "4");
        menuInscription.ajoute(personne);
        menuInscription.ajoute(equipe);
        menuInscription.ajoute(listePersonnes);
        menuInscription.ajoute(listeEquipe);
        menuInscription.ajouteRevenir("r");;
        Menu menuCompetition = new Menu("Competition", "2");
        Option competition = new Option("Ajouter une comp�tition", "1");
        Option listeCompetition = new Option("Liste des comp�titions", "2");
        menuCompetition.ajoute(competition);
        menuCompetition.ajoute(listeCompetition);
        menuCompetition.ajouteRevenir("r");
        menu.ajoute(menuInscription);
        menu.ajoute(menuCompetition);
        menu.ajouteQuitter("q");
        personne.setAction(new Action()
        //commentaire !
        		{
        			public void optionSelectionnee()
        			{
        				inscriptions.createPersonne(inscriptions);
        			}
        		});
        
        menu.start();
        
	}
}
