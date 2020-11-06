package mongo;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;

import java.util.Arrays;


public class BofuriFiche {

    static Block<Document> printBlock = document -> System.out.println(document.toJson());

    public static void main(String[] args) {
        // initialise la collection
        MongoDatabase database = MongoConnection.getBofuriDatabase();
        MongoCollection<Document> ficheCollection = database.getCollection("fiche");

        // 1] – Nombre de fiches
        long totalFiches = ficheCollection.count();
        System.out.printf("----- Nombre de fiches : %s ----- \n\n", totalFiches);

        // 2] Fiche de Jacques WEBER
        long totalPrimeur = ficheCollection.count(Document.parse("{Societe: \"Primeur & co\"}"));
        System.out.printf("----- Fiche de Primeur & Co : %s \n\n", totalPrimeur);


        // 3] Fiche d’entreprise “Primeur & co”
        System.out.println("----- Fiche de Jacques WEBER --------");
        ficheCollection.find(Document.parse("{$or:[ {Nom_Prenom: \"Jacques WEBER\" }, {$and: [{Prenom: \"Jacques\", Nom: \"WEBER\"}]}]}"))
                .forEach(printBlock);
        System.out.println();

        // 4] Nombre de fiches de chaque collaborateur
        System.out.println("----- Nombre de fiches par collaborateur -------");
        ficheCollection.aggregate(
                Arrays.asList(Aggregates.group("$commercial", Accumulators.sum("count", 1))))
                .forEach(printBlock);

        MongoConnection.closeConnection();
    }
}
