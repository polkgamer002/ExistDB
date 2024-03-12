package dam2.Menu;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Database;

import java.util.Scanner;

public class App {
    private Collection col;
    private XPathQueryService servicio;
    private XUpdateQueryService xupdateServicio;

    public AccesoMenu() {
      
    }

    public void conectar() {
        String driver = "org.exist.xmldb.DatabaseImpl";
        String uri = "xmldb:exist://localhost:8080/exist/xmlrpc/db";
        String user = "admin";
        String password = "patata1234";

        try {
            Class<?> cl = Class.forName(driver);
            Database database = (Database) cl.getDeclaredConstructor().newInstance();
            DatabaseManager.registerDatabase(database);
            col = DatabaseManager.getCollection(uri, user, password);
            servicio = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desconectar() {
        if (col != null) {
            try {
                col.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void insertMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el nombre del menú:");
        String nombre = scanner.nextLine();
        System.out.println("Introduce el precio del menú:");
        String precio = scanner.nextLine();

        String xUpdate = "<xu:modifications version=\"1.0\" xmlns:xu=\"http://www.xmldb.org/xupdate\">" +
                "  <xu:append select=\"/menus\">" +
                "    <menu>" +
                "      <nombre>" + nombre + "</nombre>" +
                "      <precio>" + precio + "</precio>" +
                "    </menu>" +
                "  </xu:append>" +
                "</xu:modifications>";
        
        try {
            xupdateServicio.update(xUpdate);
            System.out.println("Menú insertado con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrarMenu(String nombre) {
        String xUpdate = "<xu:modifications version=\"1.0\" xmlns:xu=\"http://www.xmldb.org/xupdate\">" +
                "  <xu:remove select=\"/menus/menu[nombre='" + nombre + "']\"/>" +
                "</xu:modifications>";
        
        try {
            xupdateServicio.update(xUpdate);
            System.out.println("Menú borrado con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizaPrecio(int incremento) {
 
        try {
            ResourceSet result = servicio.query("collection('/db/menus')/menu");
            ResourceIterator it = result.getIterator();
            while (it.hasMoreResources()) {
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscarMasBaratoQue(int precio) {
        try {
            ResourceSet result = servicio.query(
                    "collection('/db/menus')/menu[precio < " + precio + "] order by precio ascending"
            );
            ResourceIterator it = result.getIterator();
            while (it.hasMoreResources()) {
                System.out.println(it.nextResource().getContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

