import java.text.DecimalFormat;
import java.util.Scanner;

public class KermisTwee {
	static Scanner scan = new Scanner(System.in);
	static Attractie[] kermis = { new Botsautos("Botsautos", 2.50, 1000), new Spin("Spin", 2.25, 500),
			new Spiegelpaleis("Spiegelpaleis", 2.75, 800), new Spookhuis("Spookhuis", 3.20, 1100),
			new Hawaii("Hawaii", 2.90, 700), new Ladderklimmen("Ladderklimmen", 5, 400) };

	static Kassa kassa = new Kassa();
	static BelastingInspectuur felix = new BelastingInspectuur();
	static Monteur stef = new Monteur();

	public static void main(String[] args) {

		System.out.println("1. Botsautos");
		System.out.println("2. Spin");
		System.out.println("3. Spiegelpaleis");
		System.out.println("4. Spookhuis");
		System.out.println("5. Hawaii");
		System.out.println("6. Ladderklimmen");
		System.out.println("'o' Omzet");
		System.out.println("'k' Kaarten verkoop");
		System.out.println("'b' Belastingsdienst");
		System.out.println("'m' Monteur\n");

		for (int i = 0; i < 2000; i++) {
			String invoer = scan.nextLine();

			switch (invoer) {
			case "1":
				kermis[0].draaien();
				break;
			case "2":
				kermis[1].draaien();
				break;
			case "3":
				kermis[2].draaien();
				break;
			case "4":
				kermis[3].draaien();
				break;
			case "5":
				kermis[4].draaien();
				break;
			case "6":
				kermis[5].draaien();
				break;
			case "o":
				omzettonen();
				break;
			case "k":
				kaartentonen();
				break;
			case "b":
				felix.belastingophalen(kermis);
			case "m":
				stef.Keuringgeven(kermis);
			default:
				System.out.println();
			}
		}
	}

	static void omzettonen() {
		System.out.println(kassa.omzet + "\tOmzet");
		for (int i = 0; i < kermis.length; i++) {
			System.out.println( String.format( "%.2f", kermis[i].omzet) + "\t" + kermis[i].naam);
			
		}
	}

	static void kaartentonen() {
		int totaal = kermis[0].kaarten + kermis[1].kaarten + kermis[2].kaarten + kermis[3].kaarten + kermis[4].kaarten
				+ kermis[5].kaarten;
		System.out.println(totaal + "\tKaarten");

		for (int i = 0; i < kermis.length; i++) {
			System.out.println(kermis[i].kaarten + "\t" + kermis[i].naam);
		}
	}
}

class Attractie {
	double prijs;
	String naam;
	double oppervlakte;
	double omzet;
	int kaarten;
	double omzettebelasten;

	void draaien() {
		omzet += prijs;
		Kassa.omzet += prijs;
		kaarten += 1;
		omzettebelasten += prijs;
		System.out.println("Draaien " + naam);

	}

}

class Botsautos extends Attractie {
	Botsautos(String name, double price, double surface) {
		naam = name;
		prijs = price;
		oppervlakte = surface;

	}

}

class Spin extends RisicoRijkeAttracties implements GokAttractie {
	Spin(String name, double price, double surface) {
		naam = name;
		prijs = price;
		oppervlakte = surface;
		draailimiet = 5;
	}

	public void kansSpelBelastingBetalen() {
		double belasting = omzettebelasten * 0.3;
		Kassa.omzet -= belasting;
		omzet -= belasting;
		omzettebelasten = 0;
		System.out.println(naam + " " + belasting + " belasting betaald.");

	}

}

class Spiegelpaleis extends Attractie {
	Spiegelpaleis(String name, double price, double surface) {
		naam = name;
		prijs = price;
		oppervlakte = surface;
	}

}

class Spookhuis extends Attractie {
	Spookhuis(String name, double price, double surface) {
		naam = name;
		prijs = price;
		oppervlakte = surface;
	}

}

class Hawaii extends RisicoRijkeAttracties {
	Hawaii(String name, double price, double surface) {
		naam = name;
		prijs = price;
		oppervlakte = surface;
		draailimiet = 10;
	}

}

class Ladderklimmen extends Attractie implements GokAttractie {

	Ladderklimmen(String name, double price, double surface) {
		naam = name;
		prijs = price;
		oppervlakte = surface;
	}

	public void kansSpelBelastingBetalen() {
		double belasting = omzettebelasten * 0.3;
		Kassa.omzet -= belasting;
		omzet -= belasting;
		omzettebelasten = 0;
		System.out.println(naam + " " + belasting + " belasting betaald.");

	}

}

class Kassa {
	static double omzet;
	static int kaarten;
}

abstract class RisicoRijkeAttracties extends Attractie {
	boolean keuring;
	int draailimiet;
	int gedraait;

	boolean opstellingsKeuring() {
		if (keuring == true) {
			System.out.println(naam + " heeft geen keuring nodig.");
			return true;
		}
		System.out.println("Keuring ondergaan van " + naam);
		keuring = true;
		return keuring;

	}

	void draaien() {
		if (keuring == true) {
			omzettebelasten += prijs;
			omzet += prijs;
			Kassa.omzet += prijs;
			kaarten += 1;
			gedraait += 1;
			System.out.println("Draaien " + naam);

			if (gedraait == draailimiet) {
				keuring = false;
				System.out.println("De " + naam
						+ "heeft zijn draailimiet bereikt, Het heeft een keuring nodig. Enter m voor een keuring van de monteur.");
			}

		} else {
			System.out.println("Je hebt eerst een keuring nodig om " + naam
					+ " dit te draaien. Enter m voor een keuring van de monteur.");
		}
	}
}

interface GokAttractie {
	void kansSpelBelastingBetalen();

}

class BelastingInspectuur {
	int belasting;

	void belastingophalen(Attractie[] attractie) {
		for (int i = 0; i < attractie.length; i++)

			if (attractie[i] instanceof GokAttractie) {
				((GokAttractie) attractie[i]).kansSpelBelastingBetalen();
			}
	}
}

class Monteur {

	void Keuringgeven(Attractie[] kermis) {
		
		System.out.println("Wil je dat de monteur de Spin (2) of Hawaii (5) gaat keuren?");

		Scanner scan2 = new Scanner(System.in);
		String invoer2 = scan2.nextLine();

		switch (invoer2) {
		case "2":
			System.out.println("2");
			((RisicoRijkeAttracties) kermis[1]).opstellingsKeuring();
			break;
		case "5":
			System.out.println("5");
			((RisicoRijkeAttracties) kermis[4]).opstellingsKeuring();
			break;
			
		}
	}

}
