package application;

import java.io.File;
import java.io.IOException;

import gui.Window;

public class Application {

	private static FileLogger log = null;

	static {
		try {
			log = new FileLogger();
		} catch (IllegalArgumentException | SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// TODO : Ajouter une limite pour les fichiers	
	// TODO : Ajouter la licence au git
	// TODO : Voir pourquoi le JLabel est invisible au dessus de la JProgressBar
	// TODO : Mettre un système de langue
	// TODO : Annuler une action fait revenir à l'était précédent
	// TODO : Ajouter un système de contraintes pour la vérification du préfix
	// TODO : Possibilité de changer le template

	public static void main(String[] args) {
		if (log == null) return;
		
		Window window = new Window(log);

		log.info("Selecting directory ...");
		File selectedDirectory = null;
		while (selectedDirectory == null) {
			selectedDirectory = window.selectDirectory();
			if (selectedDirectory == null && window.confirmClosing()) return;
		}

		log.info("Determining prefix ...");
		window.setStatusTitle("En attente d'un préfixe valide");
		
		DirectoryReader dirReader = new DirectoryReader(log, selectedDirectory);

		String prefix = window.showInputDialog(
				"Entrée du préfixe",
				"Le préfixe est automatiquement défini sur le nom du dossier\n" +
				"Si vous ne voulez pas l'utiliser, changez-le dans le champ ci-dessous puis validez.",
				selectedDirectory.getName());
		if (prefix == null && window.confirmClosing()) return;
		
		boolean isValid = dirReader.isPrefixValid(prefix);
		while (!isValid) {
			prefix = window.showInputDialog(
					"Préfixe incorrect",
					"Le préfixe entré est incorrect pour les raisons suivantes\n" +
					dirReader.getPrefixErrors() + "\n" +
					"Entrez un nouveau préfixe dans le champ ci-dessous puis validez.",
					prefix);
			if (prefix == null && window.confirmClosing()) return;
			isValid = dirReader.isPrefixValid(prefix);
		}
		dirReader.setPrefix(prefix);
		
		window.setStatusTitle("Lecture du dossier ...");
		if (dirReader.read()) {
			window.setStatusTitle("Renommage des fichiers ...");
			window.initProgressBar(dirReader.size());
			dirReader.addListener(window);
			
			if (dirReader.renameContent()) {
				window.setStatusTitle("Tous les fichiers ont été renommés");
				log.info("Program terminated, waiting for user exit ...");
			} else {
				window.setStatusTitle("Erreur de renommage");
				log.severe("Renaming process failed");
			}
			
			dirReader.removeListener(window);
		} else
			log.severe("Reading directory failed");
	}
}
