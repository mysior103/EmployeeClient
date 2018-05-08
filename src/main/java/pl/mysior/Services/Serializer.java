package pl.mysior.Services;

import pl.mysior.BuisnessObject.Dealer;
import pl.mysior.BuisnessObject.Director;
import pl.mysior.DAO.DealerDAO;
import pl.mysior.DAO.DirectorDAO;
import pl.mysior.DAO.WorkerDAO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class Serializer {

    public static void serializeAll(String fileName) {
        WorkerDAO workerDAO = new WorkerDAO();
        List<Object> allOut = workerDAO.getAllWorkers();
        try {
            FileOutputStream fs = new FileOutputStream(fileName);
            GZIPOutputStream gz = new GZIPOutputStream(fs);
            ObjectOutputStream os = new ObjectOutputStream(gz);
            os.writeObject(allOut);
            os.close();
            gz.close();
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String getActualDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    public static String getFileNameWithDate() {
        return getActualDate() + ".gzip";
    }



    public static void replaceDeserializedInDatabase(List<Object> allObjects) throws Exception {
        WorkerDAO workerDAO = new WorkerDAO();
        workerDAO.deleteAllWorkers();

        for (Object obj : allObjects) {
            if (obj.getClass() == Dealer.class) {
                DealerDAO dealerDAO = new DealerDAO();
                Dealer dealer = (Dealer) obj;
                dealerDAO.addDealer(dealer);
            } else if (obj.getClass() == Director.class) {
                DirectorDAO directorDAO = new DirectorDAO();
                Director director = (Director) obj;
                directorDAO.addDirector(director);
            } else {
                throw new Exception("FATAL ERROR - DANE UTRACONE!");
            }
        }
    }

    public static List<Object> deserializeAll(String fileName) {
        List<Object> allIn = null;
        try {
            FileInputStream fs = new FileInputStream(fileName);
            GZIPInputStream gz = new GZIPInputStream(fs);
            ObjectInputStream is = new ObjectInputStream(gz);
            allIn = (ArrayList) is.readObject();
            replaceDeserializedInDatabase(allIn);
            is.close();
            gz.close();
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allIn;
    }

}
