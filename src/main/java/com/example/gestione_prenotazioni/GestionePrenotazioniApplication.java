package com.example.gestione_prenotazioni;

import com.example.gestione_prenotazioni.model.*;
import com.example.gestione_prenotazioni.repository.EdificioRepository;
import com.example.gestione_prenotazioni.repository.PostazioneRepository;
import com.example.gestione_prenotazioni.repository.PrenotazioneRepository;
import com.example.gestione_prenotazioni.repository.UtenteRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class GestionePrenotazioniApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionePrenotazioniApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(EdificioRepository edificioRepository,
							 PostazioneRepository postazioneRepository,
							 UtenteRepository utenteRepository,
							 PrenotazioneRepository prenotazioneRepository) {
		return args -> {
			Scanner scanner = new Scanner(System.in);
			int scelta;

			do {
				System.out.println("\n=== Menu Gestione Prenotazioni ===");
				System.out.println("1. Aggiungi Edificio");
				System.out.println("2. Aggiungi Postazione");
				System.out.println("3. Aggiungi Utente");
				System.out.println("4. Visualizza Edifici");
				System.out.println("5. Visualizza Postazioni");
				System.out.println("6. Visualizza Utenti");
				System.out.println("7. Ricerca Postazioni per Tipo e Città");
				System.out.println("8. Prenota Postazione");
				System.out.println("9. Crea Dati Finti");
				System.out.println("10. Esci");
				System.out.print("Scegli un'opzione: ");
				scelta = scanner.nextInt();
				scanner.nextLine();  // Consuma la nuova linea

				switch (scelta) {
					case 1 -> aggiungiEdificio(scanner, edificioRepository);
					case 2 -> aggiungiPostazione(scanner, edificioRepository, postazioneRepository);
					case 3 -> aggiungiUtente(scanner, utenteRepository);
					case 4 -> visualizzaEdifici(edificioRepository);
					case 5 -> visualizzaPostazioni(postazioneRepository);
					case 6 -> visualizzaUtenti(utenteRepository);
					case 7 -> ricercaPostazioni(scanner, postazioneRepository);
					case 8 -> prenotaPostazione(scanner, utenteRepository, postazioneRepository, prenotazioneRepository);
					case 9 -> creaDatiFinti(utenteRepository, postazioneRepository, edificioRepository);
					case 10 -> System.out.println("Uscita dal sistema.");
					default -> System.out.println("Scelta non valida. Riprova.");
				}
			} while (scelta != 10);
		};
	}

	// Aggiungi Edificio
	private void aggiungiEdificio(Scanner scanner, EdificioRepository edificioRepository) {
		try {
			System.out.println("\n--- Aggiungi Edificio ---");
			System.out.print("Nome: ");
			String nome = scanner.nextLine();
			System.out.print("Indirizzo: ");
			String indirizzo = scanner.nextLine();
			System.out.print("Città: ");
			String citta = scanner.nextLine();

			Edificio edificio = new Edificio();
			edificio.setNome(nome);
			edificio.setIndirizzo(indirizzo);
			edificio.setCitta(citta);
			edificioRepository.save(edificio);

			System.out.println("Edificio aggiunto con successo.");
		} catch (Exception e) {
			System.out.println("Errore nell'aggiunta dell'edificio: " + e.getMessage());
		}
	}

	// Aggiungi Postazione
	private void aggiungiPostazione(Scanner scanner, EdificioRepository edificioRepository, PostazioneRepository postazioneRepository) {
		try {
			System.out.println("\n--- Aggiungi Postazione ---");
			System.out.print("Codice: ");
			String codice = scanner.nextLine();
			System.out.print("Descrizione: ");
			String descrizione = scanner.nextLine();
			System.out.print("Tipo (PRIVATO, OPENSPACE, SALA_RIUNIONI): ");
			TipoPostazione tipo = TipoPostazione.valueOf(scanner.nextLine().toUpperCase());
			System.out.print("Numero massimo di occupanti: ");
			int maxOccupanti = scanner.nextInt();
			scanner.nextLine(); // Consuma la newline
			System.out.print("ID Edificio associato: ");
			Long idEdificio = scanner.nextLong();
			scanner.nextLine(); // Consuma la newline

			Optional<Edificio> edificio = edificioRepository.findById(idEdificio);
			if (edificio.isPresent()) {
				Postazione postazione = new Postazione();
				postazione.setCodice(codice);
				postazione.setDescrizione(descrizione);
				postazione.setTipo(tipo);
				postazione.setMaxOccupanti(maxOccupanti);
				postazione.setEdificio(edificio.get());
				postazioneRepository.save(postazione);

				System.out.println("Postazione aggiunta con successo.");
			} else {
				System.out.println("Edificio non trovato. Impossibile aggiungere la postazione.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: Tipo di postazione non valido.");
		} catch (Exception e) {
			System.out.println("Errore nell'aggiunta della postazione: " + e.getMessage());
		}
	}

	// Aggiungi Utente
	private void aggiungiUtente(Scanner scanner, UtenteRepository utenteRepository) {
		try {
			System.out.println("\n--- Aggiungi Utente ---");
			System.out.print("Username: ");
			String username = scanner.nextLine();
			System.out.print("Nome completo: ");
			String nomeCompleto = scanner.nextLine();
			System.out.print("Email: ");
			String email = scanner.nextLine();

			Utente utente = new Utente();
			utente.setUsername(username);
			utente.setNomeCompleto(nomeCompleto);
			utente.setEmail(email);
			utenteRepository.save(utente);

			System.out.println("Utente aggiunto con successo.");
		} catch (Exception e) {
			System.out.println("Errore nell'aggiunta dell'utente: " + e.getMessage());
		}
	}

	// Visualizza Edifici
	private void visualizzaEdifici(EdificioRepository edificioRepository) {
		System.out.println("\n--- Lista Edifici ---");
		List<Edificio> edifici = edificioRepository.findAll();
		if (edifici.isEmpty()) {
			System.out.println("Nessun edificio trovato.");
		} else {
			edifici.forEach(edificio -> System.out.println(edificio.getNome() + ", " + edificio.getIndirizzo() + " - " + edificio.getCitta()));
		}
	}

	// Visualizza Postazioni
	private void visualizzaPostazioni(PostazioneRepository postazioneRepository) {
		System.out.println("\n--- Lista Postazioni ---");
		List<Postazione> postazioni = postazioneRepository.findAll();
		if (postazioni.isEmpty()) {
			System.out.println("Nessuna postazione trovata.");
		} else {
			postazioni.forEach(postazione -> System.out.println(postazione.getCodice() + ": " + postazione.getDescrizione() + " - Tipo: " + postazione.getTipo()));
		}
	}

	// Visualizza Utenti
	private void visualizzaUtenti(UtenteRepository utenteRepository) {
		System.out.println("\n--- Lista Utenti ---");
		List<Utente> utenti = utenteRepository.findAll();
		if (utenti.isEmpty()) {
			System.out.println("Nessun utente trovato.");
		} else {
			utenti.forEach(utente -> System.out.println(utente.getUsername() + " - " + utente.getNomeCompleto() + " - " + utente.getEmail()));
		}
	}

	// Ricerca Postazioni per Tipo e Città
	private void ricercaPostazioni(Scanner scanner, PostazioneRepository postazioneRepository) {
		try {
			System.out.println("\n--- Ricerca Postazioni ---");
			System.out.print("Tipo (PRIVATO, OPENSPACE, SALA_RIUNIONI): ");
			TipoPostazione tipo = TipoPostazione.valueOf(scanner.nextLine().toUpperCase());
			System.out.print("Città: ");
			String citta = scanner.nextLine().trim().toLowerCase();


			List<Postazione> tutteLePostazioni = postazioneRepository.findAll();
			System.out.println("Debug: Postazioni attualmente salvate nel database:");
			tutteLePostazioni.forEach(p ->
					System.out.println("Postazione " + p.getCodice() + " - Tipo: " + p.getTipo() +
							" - Città: " + p.getEdificio().getCitta()));


			List<Postazione> postazioni = tutteLePostazioni.stream()
					.filter(p -> p.getTipo() == tipo && p.getEdificio().getCitta().equalsIgnoreCase(citta))
					.toList();

			if (postazioni.isEmpty()) {
				System.out.println("Nessuna postazione trovata.");
			} else {
				postazioni.forEach(postazione -> System.out.println(postazione.getCodice() + ": " + postazione.getDescrizione()));
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Errore: Tipo di postazione non valido.");
		} catch (Exception e) {
			System.out.println("Errore nella ricerca delle postazioni: " + e.getMessage());
		}
	}


	// Prenota Postazione
	private void prenotaPostazione(Scanner scanner, UtenteRepository utenteRepository, PostazioneRepository postazioneRepository, PrenotazioneRepository prenotazioneRepository) {
		try {
			System.out.println("\n--- Prenota Postazione ---");


			System.out.print("Inserisci il codice della postazione da prenotare: ");
			String codicePostazione = scanner.nextLine();

			Optional<Postazione> postazioneOpt = postazioneRepository.findByCodice(codicePostazione);
			if (postazioneOpt.isPresent()) {
				Postazione postazione = postazioneOpt.get();

				// Chiedi all'utente di scegliere un utente
				System.out.print("Inserisci l'ID dell'utente che effettua la prenotazione: ");
				Long idUtente = scanner.nextLong();
				scanner.nextLine(); // Consuma la nuova linea

				Optional<Utente> utenteOpt = utenteRepository.findById(idUtente);
				if (utenteOpt.isPresent()) {
					Utente utente = utenteOpt.get();

					// Chiedi la data della prenotazione
					System.out.print("Inserisci la data della prenotazione (YYYY-MM-DD): ");
					String dataString = scanner.nextLine();
					LocalDate dataPrenotazione = LocalDate.parse(dataString);

					// Crea la prenotazione
					Prenotazione prenotazione = new Prenotazione();
					prenotazione.setPostazione(postazione);
					prenotazione.setUtente(utente);
					prenotazione.setData(dataPrenotazione);

					// Salva la prenotazione
					prenotazioneRepository.save(prenotazione);

					System.out.println("Prenotazione effettuata con successo.");
				} else {
					System.out.println("Utente non trovato.");
				}
			} else {
				System.out.println("Postazione non trovata.");
			}
		} catch (Exception e) {
			System.out.println("Errore nella prenotazione della postazione: " + e.getMessage());
		}
	}



	private void creaDatiFinti(UtenteRepository utenteRepository, PostazioneRepository postazioneRepository, EdificioRepository edificioRepository) {
		try {
			Faker faker = new Faker();


			for (int i = 0; i < 5; i++) {
				Edificio edificio = new Edificio();
				edificio.setNome(faker.company().name());
				edificio.setIndirizzo(faker.address().streetAddress());
				edificio.setCitta(faker.address().city());
				edificioRepository.save(edificio);
			}


			for (int i = 0; i < 5; i++) {
				Utente utente = new Utente();
				utente.setUsername(faker.name().username());
				utente.setNomeCompleto(faker.name().fullName());
				utente.setEmail(faker.internet().emailAddress());
				utenteRepository.save(utente);
			}


			for (int i = 0; i < 5; i++) {
				Postazione postazione = new Postazione();
				postazione.setCodice(faker.code().asin());
				postazione.setDescrizione(faker.lorem().sentence());
				postazione.setTipo(TipoPostazione.values()[faker.random().nextInt(TipoPostazione.values().length)]);
				postazione.setMaxOccupanti(faker.random().nextInt(1, 10));


				Edificio edificio = edificioRepository.findAll().get(faker.random().nextInt(edificioRepository.findAll().size()));
				postazione.setEdificio(edificio);
				postazioneRepository.save(postazione);
			}

			System.out.println("Dati finti creati con successo.");
		} catch (Exception e) {
			System.out.println("Errore nella creazione dei dati finti: " + e.getMessage());
		}
	}
}
