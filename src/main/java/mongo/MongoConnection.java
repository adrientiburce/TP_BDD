package mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lib.Config;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class MongoConnection {

    private static MongoClient mongoClient;

    public static MongoDatabase getBofuriDatabase() {
        // setting up log4j
        Logger.getRootLogger().setLevel(Level.INFO);
        org.apache.log4j.BasicConfigurator.configure();

        mongoClient = MongoClients.create("mongodb://"
                + Config.getConfigValue("MONGO_USER") + ":"
                + Config.getConfigValue("MONGO_PASSWORD") + "@localhost:27017");
        return mongoClient.getDatabase("bofuri");
    }

    public static void closeConnection() {
        mongoClient.close();
    }
}
