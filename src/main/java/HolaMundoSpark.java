import static spark.Spark.*;

public class HolaMundoSpark {

    public static void main(String[] args) {

        port(80);

        get("/hola", (req, res) -> "Hola Mundo");

        get("/hola/:nombre", (req, res) -> {
            return "Hola " + req.params(":nombre");
        });
    }

}
