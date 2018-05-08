package pl.mysior;

import pl.mysior.AllWorkers;
import pl.mysior.AllWorkersImplService;
import pl.mysior.BuisnessObject.Dealer;
import pl.mysior.BuisnessObject.Director;
import pl.mysior.BuisnessObject.Worker;
import pl.mysior.DAO.DealerDAO;
import pl.mysior.DAO.DirectorDAO;
import pl.mysior.DAO.WorkerDAO;
import pl.mysior.Services.RMIServiceClient;
import pl.mysior.Services.Serializer;
import pl.mysior.Services.ServerConnection;
import pl.mysior.Services.ConnectionStrategy;

import java.math.BigDecimal;
import java.util.*;

import static pl.mysior.Services.Serializer.getFileNameWithDate;
import static pl.mysior.InputScanner.*;

public class UserInterface {

    String authKey = null;

    public UserInterface() {
        menu();
    }

    public void menu() {
        boolean selected = false;
        while (!selected) {
            System.out.println("\nMENU");
            System.out.println("\t 1. Lista pracowników" +
                    "\n\t 2. Dodaj pracownika" +
                    "\n\t 3. Usuń pracownika" +
                    "\n\t 4. Kopia zapasowa" +
                    "\n\t 5. Pobierz dane" +
                    "\n\t 0. Wyjdź");
            System.out.print("Wybór>");
            int choose = userInputInt();
            switch (choose) {
                case 1:
                    listWorkers();
                    break;
                case 2:
                    addWorker();
                    break;
                case 3:
                    deleteWorker();
                    break;
                case 4:
                    backup();
                    break;
                case 5:
                    selectMethodOfConnection();
                    break;
                case 0:
                    selected = true;
                    break;
                default:
                    System.out.println("Zły wybór, wprowadź jeszcze raz");
                    break;
            }
        }
        System.out.println("Siema!");
    }

    private void showWorker(Object workerObject) {
        if (workerObject.getClass() == Director.class || workerObject.getClass() == Dealer.class) {
            Worker worker = (Worker) workerObject;
            System.out.println("Identyfikator PESEL: \t\t" + worker.getID());
            System.out.println("Imię: \t\t\t\t\t\t" + worker.getName());
            System.out.println("Nazwisko: \t\t\t\t\t" + worker.getLastName());
            System.out.println("Stanowisko: \t\t\t\t" + worker.getPosition());
            System.out.println("Wynagrodzenie(zł): \t\t\t" + worker.getSalary());
            System.out.println("Telefon służbowy numer: \t" + worker.getPhoneNumber());
            if (workerObject.getClass() == Director.class) {
                Director director = (Director) workerObject;
                System.out.println("Karta służbowa numer: \t\t" + director.getCreditCardNumber());
                System.out.println("Limit kosztów/miesiąc(zł): \t" + director.getCostLimit());
            } else if (workerObject.getClass() == Dealer.class) {
                Dealer dealer = (Dealer) workerObject;
                System.out.println("Prowizja(%): \t\t\t\t" + dealer.getCommission());
                System.out.println("Limit prowizji/miesiąc(zł): " + dealer.getMaxCommission());
            } else throw new RuntimeException("Nie znaleziono pracowników!");
        }
    }

    private void listWorkers() {
        System.out.println("1. Lista Pracowników:");
        WorkerDAO workerDAO = new WorkerDAO();
        List<Object> all = workerDAO.getAllWorkers();
        int foreachIndex = 1;
        if (!all.isEmpty()) {
            for (Object work : all) {
                boolean goNext = false;
                showWorker(work);
                System.out.println("\t\t\t\t\t\t\t\t\t\tPozycja " + foreachIndex + "/" + all.size());
                System.out.println("[Enter] - następny");
                System.out.println("[Q] - powrót");
                boolean exitForeach = false;
                while (!goNext) {
                    String response = userInput();
                    if (response.equals("")) {
                        goNext = true;
                    } else if (response.charAt(0) == 'Q') {
                        exitForeach = true;
                    } else {
                        System.out.println("Wprowadz jeszcze raz");
                        goNext = false;
                    }
                }
                if (exitForeach) break;
                foreachIndex++;
            }
        } else {
            System.out.println("Nie znaleziono pracownika!");
        }
    }

    private void backup() {
        System.out.println("4. Kopia zapasowa\n");
        boolean correctInput = false;
        while (!correctInput) {
            System.out.println("[Z]achowaj/[O]dtwórz: ");
            char operationType = userInput().charAt(0);
            switch (operationType) {
                case 'Z':
                    correctInput = true;
                    Serializer.serializeAll(getFileNameWithDate());
                    System.out.println("Pomyslnie zapisano!");
                    break;
                case 'O':
                    correctInput = true;
                    Serializer.deserializeAll(getFileNameWithDate());
                    System.out.println("Pomyślnie odczytano!");
                    break;
                default:
                    correctInput = false;
                    System.out.println("Wprowadź jeszcze raz!");
                    break;
            }
        }
    }

    private void deleteWorker() {
        System.out.println("3. Usuń pracownika");
        System.out.println("Podaj identyfikator PESEL");
        System.out.print("PESEL>");
        String idToDeleteStr = userInput();
        Long idToDelete = new Long(idToDeleteStr);
        try {
            Director director = null;
            Dealer dealer = null;

            if ((director = new DirectorDAO().getDirectorById(idToDelete)) != null) {
                showWorker(director);
                deleteWorkerMessage(idToDelete);

            } else if ((dealer = new DealerDAO().getDealerById(idToDelete)) != null) {
                showWorker(dealer);
                deleteWorkerMessage(idToDelete);
            } else {
                System.out.println("Nie ma takiego pracownika.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteWorkerMessage(Long id) {

        WorkerDAO workerDAO = null;
        boolean goNext = false;
        System.out.println("[Enter] - potwierdź");
        System.out.println("[Q] - powrót");

        while (!goNext) {
            String response = userInput();
            if (response.equals("")) {
                goNext = true;
                workerDAO = new WorkerDAO();
                workerDAO.deleteWorker(id);
                System.out.println("Usunięto!");
            } else if (response.charAt(0) == 'Q') {
                break;
            } else {
                System.out.println("Wprowadz jeszcze raz");
                goNext = false;
            }
        }
    }

    private void addWorker() {
        System.out.println("2. Dodaj pracownika");
        boolean correctInput = false;
        Dealer dealer;
        Director director;
        DealerDAO dealerDAO;
        DirectorDAO directorDAO;

        while (!correctInput) {
            System.out.print("[D]yrektor/[H]handlowiec:");
            String inputPosition = userInput();
            switch (inputPosition.charAt(0)) {
                case 'H':
                    correctInput = true;
                    dealer = addDealer(new Dealer());
                    dealerDAO = new DealerDAO();
                    dealerDAO.addDealer(dealer);
                    break;
                case 'D':
                    correctInput = true;
                    director = addDirector(new Director());
                    directorDAO = new DirectorDAO();
                    directorDAO.addDirector(director);
                    break;
                default:
                    correctInput = false;
                    System.out.println("Wprowadź jeszcze raz!");
                    break;
            }
        }
    }

    private Dealer addDealer(Dealer dealer) {
        System.out.print("Identyfikator PESEL: \t\t");
        dealer.setID(new Long(userInput()));
        System.out.print("Imię: \t\t\t\t\t\t");
        dealer.setName(userInput());
        System.out.print("Nazwisko: \t\t\t\t\t");
        dealer.setLastName(userInput());
        dealer.setPosition("Handlowiec");
        System.out.print("Wynagrodzenie(zł): \t\t\t");
        dealer.setSalary(new BigDecimal(userInput()));
        System.out.print("Telefon służbowy numer: \t");
        dealer.setPhoneNumber(userInput());
        System.out.print("Prowizja(%): \t\t\t\t");
        dealer.setCommission(new BigDecimal(userInput()));
        System.out.print("Limit prowizji/miesiąc(zł): ");
        dealer.setMaxCommission(new BigDecimal(userInput()));
        return dealer;
    }

    private Director addDirector(Director director) {
        System.out.print("Identyfikator PESEL: \t\t");
        director.setID(new Long(userInput()));
        System.out.print("Imię: \t\t\t\t\t\t");
        director.setName(userInput());
        System.out.print("Nazwisko: \t\t\t\t\t");
        director.setLastName(userInput());
        director.setPosition("Dyrektor");
        System.out.print("Wynagrodzenie(zł): \t\t\t");
        director.setSalary(new BigDecimal(userInput()));
        System.out.print("Telefon służbowy numer: \t");
        director.setPhoneNumber(userInput());
        System.out.print("Karta służbowa numer: \t\t");
        director.setCreditCardNumber(userInput());
        System.out.print("Limit kosztów/miesiąc(zł): \t");
        director.setCostLimit(new BigDecimal(userInput()));
        return director;
    }

    private void authorize() {
        boolean correctAuth = false;
        while (!correctAuth) {
            System.out.println("Podaj użytkownika: \t\t");
            String user = userInput();
            System.out.println("Podaj hasło: \t\t\t");
            String password = userInput();
            authKey = getAuthKeyThrowRMI(user, password);
            if (authKey == null) {
                correctAuth = false;
                System.out.println("Błąd autoryzacji, spróbuj ponownie!");
            } else {
                correctAuth = true;
                System.out.println("\nZalogowano pomyslnie!\n----------------------------------------");
            }
        }
    }

    private void selectMethodOfConnection() {

        boolean correctInput = false;
        while (correctInput) {
            System.out.println("Wybierz metodę połączenia: [1] Web Socket, [2] Web Service.\n");
            int choose = userInputInt();
            if (choose == 1) {
                download();
                correctInput = true;
            } else if (choose == 2) {
                webServiceTest();
                correctInput = true;
            } else {
                System.out.println("Błędny wybór!");
                correctInput = false;
            }
        }

    }

    //TODO:
    // zamienić miejscami logowanie i połączenie, bo najpierw sie laczy na default
    // a pozniej jeszcze raz sie laczy na wybranym porcie. bez sensu
    private void download() {
        Object receivedData = null;

        System.out.println("5. Pobierz dane z sieci\n");
        authorize();
        System.out.println("\nPodaj dane serwera, naciśnij ENTER jeśli domyślne.\n");
        ServerConnection server = new ServerConnection();//<-------------------
        System.out.print("Adres: \t\t\t\t");
        if (userInput().equals("")) System.out.println(server.getIpAddress() + "\n");
        else server.setIpAddress(userInput());
        System.out.print("Port: \t\t\t\t");
        if (userInput().equals("")) System.out.println(server.getPort() + "\n");
        else server.setPort(userInputInt());
        try {
            ConnectionStrategy conServer = new ServerConnection();//<------------
            if (conServer.connect()) {
                receivedData = conServer.getCustomers(authKey);
                if (!receivedData.equals(null)) {
                    System.out.println("----------------------------------------\n\nDane pobrano prawidłowo!\n");
                    System.out.println("Zapisać pobrane dane? [T]/[N]");
                    if (userInput().toUpperCase().charAt(0) == 'T') {
                        System.out.print("Zapisywanie... ");
                        Serializer.replaceDeserializedInDatabase((ArrayList) receivedData);
                        System.out.println("Sukces!");
                    } else {
                        System.out.println("Dane nie zostały zapisane");
                    }
                } else {
                    System.out.println("Błąd w pobieraniu danych! Spróbuj jeszcze raz");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getAuthKeyThrowRMI(String userName, String password) {
        RMIServiceClient rmiClient = new RMIServiceClient();
        String authKey = rmiClient.getAuthKey(userName, password);
        return authKey;
    }

    private void webServiceTest() {
        AllWorkersImplService allWorkersImplService = new AllWorkersImplService();
        AllWorkers workers = allWorkersImplService.getAllWorkersImplPort();
        List<pl.mysior.Dealer> allDealers = workers.getAllDealers();
        List<pl.mysior.Director> allDirectors = workers.getAllDirectors();
        if(!allDealers.isEmpty()||!allDirectors.isEmpty()){
            replaceDirectorsInDatabase(allDirectors);
        }



        System.out.println();
    }
    private void replaceDirectorsInDatabase(List<Director> directors){

    }

}
