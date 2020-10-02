import java.sql.ResultSet;
import java.util.HashMap;

/**
 * permet d'afficher le borderau de livraison
 */
public class Livraison {


    public static void main(String[] args) {
        PostgreSQLConnection psql = new PostgreSQLConnection();
        HashMap<String, String> res = psql.getQuaiAndDate();

        printBordereau("Arthur DENT", res.get("quai"), res.get("date"));
        System.out.println();
        printLivraison(psql);
    }


    public static void printBordereau(String chauffeur, String quai, String date) {
        System.out.format("%15s%15s\n", "Chauffeur", chauffeur);
        System.out.format("%15s    %15s\n", "Quai chargement", quai);
        System.out.format("%15s    %15s\n", "Date chargement", date);
    }

    public static void printLivraison(PostgreSQLConnection psql) {
        System.out.format("%15s%15s  %15s       %15s%15s\n", "Société", "Dépot", "Date", "Produit", "Quantité");

        ResultSet res = psql.getCommandes();
        try {
            while (res.next()) {
                System.out.format("%15s   %15s %15s %15s %15s\n",
                        res.getString("nom"),
                        res.getString("adresse"),
                        res.getString("date_livraison"),
                        res.getString("produit"),
                        res.getString("quantite"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
