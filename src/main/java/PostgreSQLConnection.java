import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

public class PostgreSQLConnection {


    /**
     * enable to read config values from a file
     * @param key of the value in the config file
     * @return the secret value
     */
    public String getConfigValue(String key) {
        Properties prop = new Properties();
        String fileName = "app.config";
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
            System.out.println("error");
            return "";
        }
        try {
            prop.load(is);
        } catch (IOException ex) {
            System.out.println("error");
            return "";
        }
        return prop.getProperty(key);
    }

    public ResultSet executeSQLQuerry(String sqlRequest) {
        Connection c = null;
        Statement stmt = null;
        ResultSet res = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://localhost:5432/bofuri",
                            this.getConfigValue("POSTGRES_USER"),
                            this.getConfigValue("POSTGRES_PASSWORD"));

            stmt = c.createStatement();
            res = stmt.executeQuery(sqlRequest);
            stmt.close();
            c.close();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return res;
    }


    public HashMap<String, String> getQuaiAndDate() {
        HashMap<String, String> resArray = new HashMap<>();
        ResultSet res = this.executeSQLQuerry("SELECT date_chargement, q.adresse, q.ville\n" +
                "FROM livraison\n" +
                "JOIN chauffeur c on c.pk = livraison.chauffeur_id\n" +
                "JOIN quai q on livraison.quai_id = q.pk\n" +
                "WHERE c.nom = 'Arthur DENT'");

        try {
            while (res.next()) {
                resArray.put("date", res.getString("date_chargement"));
                resArray.put("quai", res.getString("adresse") + ", " + res.getString("ville"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return resArray;
    }

    public ResultSet getCommandes() {
        ResultSet res = this.executeSQLQuerry("SELECT e.nom, d.adresse, date_livraison, c.produit, c.quantite\n" +
                "FROM livraison\n" +
                "         JOIN livraison_commande lc on livraison.pk = lc.livraison_id\n" +
                "         JOIN commande c on c.pk = lc.commande_id\n" +
                "         JOIN depot d on d.pk = c.depot_id\n" +
                "         JOIN entreprise e on e.pk = d.entreprise_id\n" +
                "         JOIN chauffeur on chauffeur.pk = livraison.chauffeur_id\n" +
                "WHERE chauffeur.nom = 'Arthur DENT';");

        return res;
    }
}
