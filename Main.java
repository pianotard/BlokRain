public class Main {

    public static boolean debug = true;

    public static void main(String[] args) { 
        GridManager manager = new GridManager();
        manager.run();

        System.out.println(manager.getSeed());
    }
}
