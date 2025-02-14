package io.ISSProject.game.model;

import java.util.HashMap;
import java.util.Map;

public class DialogManager {
    private static final Map<String, String> DIALOGS = new HashMap<>();

    static{
        //Intro
        DIALOGS.put("INTRO1", "Un giovane e promettente investigatore privato riceve un messaggio di aiuto dal fratello...");
        DIALOGS.put("INTRO2", "Il fratello dell'investigatore è un banchiere, dice di essere in pericolo e per questo stabilisce un incontro al bar con il fratello per discuterne.");
        DIALOGS.put("INTRO3", "L'investigatore attende al bar, ma dopo un paio d'ore di attesa non vi è alcuna traccia del fratello...");
        DIALOGS.put("INTRO4", "Inspospettito, decide di recarsi a casa del fratello per capire cosa sia successo.");
        DIALOGS.put("INTRO5", "Giunto nell'appartamento capisce subito che qualcosa non va...");

        //BrotherBedRoom
        DIALOGS.put("BROTHERBEDROOM1", "*In cucina sembra esserci un pasto freddo consumato solo a metà...*pensa l'investigatore tra sé e sé ");
        DIALOGS.put("BROTHERBEDROOM2", "*...come se fosse stato interrotto da qualcosa o qualcuno. *");
        DIALOGS.put("BROTHERBEDROOM3","*Vediamo se trovo qualcosa di utile...forse mi conviene controllare la scrivania in camera da letto...*");
        DIALOGS.put("BROTHERBEDROOM4", "*Qui mio fartello conserva i suoi documenti più importanti.*");

        //Store
        DIALOGS.put("STORE1", "Investigatore: Salve, sa dirmi cosa è successo al ragazzo che è venuto qualche ora fa a comprare questi oggetti?");
        DIALOGS.put("STORE2", "Commesso: No mi dispiace, non posso aiutarla...");
        DIALOGS.put("STORE3", "Investigatore: Non mi faccia perdere tempo, è importante, si tratta di mio fratello. Dica quello che sa.");
        DIALOGS.put("STORE4", "Commesso: Va bene, va bene, si calmi... un uomo è entrato mentre suo fratello acquistava una torcia e dei guanti...");
        DIALOGS.put("STORE5", "Commesso: ...sembrava una discussione accesa ma alla fine ha concluso l'acquisto e se n'è andato.Non so altro...");

        //BrothrLivingRoomView
        DIALOGS.put("BROTHERLIVROOM1", "*Mmmh...una discussione accesa...al momento non ho molti indizi, meglio tornare all'appartamento...*");
        DIALOGS.put("BROTHERLIVROOM2", "*Devo controllare bene le altre stanze... magari mi sono fatto sfuggire qualche altro indizio.*");

        //BeforeAbandonedShelter
        DIALOGS.put("BEFSHELTER1","L'indirizzo sembra portare ad un rifugio abbandonato fuori città..." );
        DIALOGS.put("BEFSHELTER2", "Il vento sferza tra gli alberi, facendo scricchiolare i rami secchi..." );
        DIALOGS.put("BEFSHELTER3", "L'investigatore si ferma davanti alla casa... inghiottito dall'oscurità del bosco.");
        DIALOGS.put("BEFSHELTER4", "Il legno marcio e le finestre infrante suggeriscono decenni di abbandono.");
        DIALOGS.put("BEFSHELTER5", "L'investigatore a bassa voce esclama tra sé e sé: \"Se è qui che dovrei trovarlo, non promette bene...\"");
        DIALOGS.put("BEFSHELTER6", "Spinge la porta, che si apre con un cigolio sinistro...");

        //AbandonedShelter
        DIALOGS.put("SHELTER1", "L'aria è umida, densa di polvere e muffa...");
        DIALOGS.put("SHELTER2", "L'investigatore entra con cautela, ogni passo fa scricchiolare il pavimento sotto di lui.");
        DIALOGS.put("SHELTER3", "I suoi occhi scrutano l'ambiente: mobili rotti, pareti scrostate, vetri sparsi sul pavimento, detriti e cianfrusaglie ovunque...");
        DIALOGS.put("SHELTER4", "Nessuna traccia di suo fratello.");
        DIALOGS.put("SHELTER5", "L'investigatore, stringendo i denti, esclama: Dannazione!!!...");
        DIALOGS.put("SHELTER6", "Accende la torcia, illuminando ogni angolo: nessun segno di lotta, nessun oggetto utile, nessun indizio concreto...");
        DIALOGS.put("SHELTER7", "... solo il vuoto di un posto dimenticato.");
        DIALOGS.put("SHELTER8", "L'investigatore sospira in modo frustrato e riflette: *ho solo perso tempo...*");
        DIALOGS.put("SHELTER9", "*...qualcuno sta giocando a mandarmi fuori strada. Ma ha appena commesso un errore: ora sono io a cercarlo!*");
        DIALOGS.put("SHELTER10", "Si volta ed esce, il suo passo più deciso che mai. Il gioco è appena iniziato... e stavolta è lui a dettare le regole.");

        //CallView
        DIALOGS.put("CALL1","Drin... drin.. drin... drin..." );
        DIALOGS.put("CALL2","Investigatore:  chi mi chiama a quest'ora...");
        DIALOGS.put("CALL3","Esita un momento, un pò confuso e sospettoso, poi risponde..." );
        DIALOGS.put("CALL4","Voce misteriosa: So cosa stai cercando..." );
        DIALOGS.put("CALL5","Investigatore: ... chi sei?" );
        DIALOGS.put("CALL6","Voce misteriosa: Vuoi salvare tuo fratello, vero?" );
        DIALOGS.put("CALL7","Le dita dell’investigatore si stringono attorno al telefono e urla: DIMMI DOVE SI TROVA" );
        DIALOGS.put("CALL8","Voce misteriosa: Vai a questo indirizzo... troverai quello che cerchi." );
        DIALOGS.put("CALL9","L'investigatore afferra rapidamente un taccuino e annota l'indirizzo ed esclama: perché dovrei fidarmi?" );
        DIALOGS.put("CALL10","Una breve pausa, poi un sussurro quasi ironico: perché non hai altra scelta." );
        DIALOGS.put("CALL11","La chiamata si interrompe. Il battito dell’investigatore accelera. Resta un attimo immobile, scrutando il telefono, come se potesse dargli altre risposte." );
        DIALOGS.put("CALL12","Con un sospiro, si alza. Infila il taccuino nel cappotto e si prepara." );
        DIALOGS.put("CALL13","Afferra la pistola... se è una trappola, vuole essere pronto." );
        DIALOGS.put("CALL14","Ma se c’è anche una sola possibilità di trovare suo fratello, deve andare." );


        //BeforeBossHiddenOut
        DIALOGS.put("BEFBOSS1", "L'investigatore si dirige verso la villa.");
        DIALOGS.put("BEFBOSS2", "Luna piena. Il vento soffia tra i rami scheletrici degli alberi.");
        DIALOGS.put("BEFBOSS3", "L'Investigatore, sospirando, scruta la casa nell'oscurità: * eccolo… il nuovo covo dell'organizzazione. Se i documenti erano corretti, qui avviene il traffico di denaro. *");
        DIALOGS.put("BEFBOSS4", "L'investigatore prende un respiro profondo, poi si incammina lentamente verso la villa, con l'adrenalina che pulsa nelle vene.");

        //ExBossHIddenOutView1
        DIALOGS.put("HIDBOSSV1_1","L'edificio è davvero fatiscente" );
        DIALOGS.put("HIDBOSSV1_2","Questo dovrebbe essere il posto... ma qualcosa non torna..." );
        DIALOGS.put("HIDBOSSV1_3", "Finestre sporche, porte socchiuse, nessun segno di attività recente." );
        DIALOGS.put("HIDBOSSV1_4", "Si avvicina cautamente alla porta e la spinge.");
        DIALOGS.put("HIDBOSSV1_5", "Entra, l'interno è spoglio: solamente documenti strappati e segni di un'evacuazione frettolosa.");
        DIALOGS.put("HIDBOSSV1_6", "*Dannazione... Sapevano che sarei arrivato.* pensa l'investigatore");
        DIALOGS.put("HIDBOSSV1_7", "Si avvicina a un tavolo polveroso, dove trova un mozzicone di sigaretta ancora fumante.");
        DIALOGS.put("HIDBOSSV1_8", "*Non saranno andati via da molto… forse posso ancora raggiungerli.*");
        DIALOGS.put("HIDBOSSV1_9", "Stringe il pugno attorno al bigliettino e si guarda intorno, cercando il prossimo indizio.");

        //EXBossHiddenOutView2
        DIALOGS.put("HIDBOSSV2_1", "L'investigatore si reca nuovamente all'ex covo del boss per cercare di trovare la stanza segreta." );

        //SecretRoom1
        DIALOGS.put("SECRETROOM1", "L'investigatore apre con cautela la botola e scende in una stanza buia e polverosa..." );
        DIALOGS.put("SECRETROOM2", "Una lampada illumina un vecchio mobile impolverato..." );
        DIALOGS.put("SECRETROOM3", "Investigatore: Non sembra esserci molto qui sotto... aspetta..." );

        //SecretRoom2
        DIALOGS.put("SECRETROOMV2_1","L'investigatore entra nella stanza segreta, una cripta di pietra fredda e umida.");
        DIALOGS.put("SECRETROOMV2_2", "Sul pavimento, sparsi tra la polvere, giacciono documenti e fogli sgualciti.");
        DIALOGS.put("SECRETROOMV2_3", "Alcuni sono strappati, altri coperti da macchie di umidità.." );
        DIALOGS.put("SECRETROOMV2_4", "Ma tutti raccontano la storia segreta dell'organizzazione.");

        //StudioView
        DIALOGS.put("STUDIO1", "L'investigatore ritorna nel suo studio... la scrivania è piena di documenti e appunti.");
        DIALOGS.put("STUDIO2", "* Dannazione… sono bloccato. Ogni pista sembra portare a un vicolo cieco.*");
        DIALOGS.put("STUDIO3", "Prende il biglietto con scritto \"Segui il denaro, non fermarti.\" da un fascicolo e lo osserva attentamente.");
        DIALOGS.put("STUDIO4", "* Forse è proprio questa la chiave... *");
        DIALOGS.put("STUDIO5", "* Se traccio i movimenti di denaro dell’organizzazione, posso arrivare al boss senza rischiare uno scontro diretto. *");
        DIALOGS.put("STUDIO6", "* Ecco... questi movimenti non tornano. Ci sono società di copertura... trasporti, magazzini... *");
        DIALOGS.put("STUDIO7", "* Tutto sembra lecito, ma il denaro passa sempre attraverso le stesse attività. *");
        DIALOGS.put("STUDIO8", "Mentre continua ad analizzare i dati... drin... drin... drin...");
        DIALOGS.put("STUDIO9", "Il telefono sulla scrivania squilla all;improvviso, interrompendo il flusso di pensieri.");


        //WarehouseView
        DIALOGS.put("WAR1", "La stessa notte l'investigatore si dirige verso il magazzino...");
        DIALOGS.put("WAR2", "Sembra un luogo desolato... ma si vede una luce accesa in una stanza...");
        DIALOGS.put("WAR3", "Non sembra esserci nessuno... ci sono solo attrezzi sparsi e un disordine evidente...");
        DIALOGS.put("WAR4", "... come se qualcuno fosse andato via di fretta.");


        //TrapDoor
        DIALOGS.put("TRAP1", "L’investigatore decide quindi di lasciare il rifugio e ritornare nel magazzino , sospettando che potrebbe ancora nascondere qualcosa di rilevante.");
        DIALOGS.put("TRAP2", "Investigatore: Strano... Ho già controllato questo posto, eppure ho la sensazione che mi sia sfuggito qualcosa...");
        DIALOGS.put("TRAP3", "Mentre scruta il pavimento, nota una botola parzialmente nascosta da alcune casse.");
        DIALOGS.put("TRAP4", "Investigatore: E questa? Non l'avevo vista prima... vediamo dove porta.");
        DIALOGS.put("TRAP5", "Dannazione... è chiusa, bisogna inserire un codice per aprirla.");
        DIALOGS.put("TRAP6", "Forse queste incisioni sulla botola possono essermi utili...");

        //VictoryView
        DIALOGS.put("VICTORY1", "Complimenti, hai risolto il caso e salvato tuo fratello");
        DIALOGS.put("VICTORY2", "Hai capito che Marco non poteva essere il colpevole. Tutti gli indizi, firmati da \"M\" ti hanno guidato fino al boss, dimostrando che qualcuno dall'interno voleva aiutarti");
        DIALOGS.put("VICTORY3", "Marco è stato coinvolto in questa vita criminale fin da giovane, e adesso vuole redimersi e pagare per ciò che ha fatto");
        DIALOGS.put("VICTORY4", "Il boss ha cercato di confonderti fino all'ultimo, ma tu hai messo insieme i pezzi e smascherato la verità. La giustizia ha vinto!");

        //Clue
        DIALOGS.put("CLUEPHONE", "Voce robotica: Hai poco tempo. Se vuoi arrivare in fondo, smetti di cercare e agisci." +
            "Come immaginavi, il covo è stato spostato. Non so dove sia ora... ma c'è qualcos'altro che dovresti controllare..."+
            "Torna all’ex covo. C’è una stanza segreta che merita più attenzione.... Click");

        //FinalView
        DIALOGS.put("BOSS1", "Un uomo anziano e elegante è seduto su una poltrona in pelle...");
        DIALOGS.put("BOSS2", "L'investigatore si trova di fornte a lui, presume sia il capo dell'organizzazione, gli punta la pistola e lo tiene sotto tiro");
        DIALOGS.put("BOSS3", "La tensione è palpabile");
        DIALOGS.put("BOSS4", "Investigatore: È finita. Ho trovato abbastanza prove per incastrati e distruggere la tua organizzazione criminale");
        DIALOGS.put("BOSS5", "Uomo anziano: La mia organizzazione?? Giovanotto, non so che idee ti sei fatto ma stai cadendo nel tranello del tuo nemico... ");
        DIALOGS.put("BOSS6", "Uomo anziano: [Scuote la testa con un sorriso divertito] Io eseguo solamente gli ordini, sono un semplice pezzo sulla scacchiera");
        DIALOGS.put("BOSS7", "Uomo anziano: E in questo momento stai seguendo perfettamente il piano del mio capo");
        DIALOGS.put("BOSS8", "Investigatore: [Stringe la presa sulla pistola] Gli indizi non mentono. Sei tu che hai orchestarto tutto e rapito mio fratello perchè stava smascherando le tue attività illecite");
        DIALOGS.put("BOSS9", "Investigatore: Beh...sentiamo...se tu sei solo uno scagnozzo... allora chi tira le fila?");
        DIALOGS.put("BOSS10", "Uomo anziano: Io lavoro per...Marco...");

        //DefeatView
        DIALOGS.put("DEFEAT1", "Dobbiamo ancora lavorare sulle tue capacità investigative...");
        DIALOGS.put("DEFEAT2", "Hai accusato Marco, ma era lui a lasciarti gli indizi per arrivare alla verità.");
        DIALOGS.put("DEFEAT3", "Gli indizi con la lettera \"M\" non erano un segnale di colpevolezza, ma il modo in cui Marco cercava di farti arrivare al boss.");
        DIALOGS.put("DEFEAT4", "Purtroppo hai abboccato al depistaggio del boss e il vero colpevole è sfuggito alla giustizia. Riprova e fai più attenzione ai dettagli!");
    }

    public static String get(String key){
        return DIALOGS.get(key);
    }

}
