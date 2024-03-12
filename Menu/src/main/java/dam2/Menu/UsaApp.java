package dam2.Menu;

public class UsaApp{
    public static void main(String[] args) {
        AccesoMenu accesoMenu = new AccesoMenu();
        
        accesoMenu.conectar();
        accesoMenu.mostrarMenus();
        
        accesoMenu.desconectar();
    }
}
