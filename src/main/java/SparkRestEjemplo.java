import com.google.gson.Gson;

import static spark.Spark.*;

public class SparkRestEjemplo {

    public static void main(String[] args) {

        final IntegranteService integranteService =
                new IntegranteServiceMapImpl();

        post("/integrante", (request, response) -> {
            response.type("application/json");
            Integrante integrante = new Gson().fromJson(request.body()
                    , Integrante.class);
            integranteService.addIntegrante(integrante);
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS));
        });

        get("/integrante", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(
                            integranteService.getIntegrantes()
                    )));
        });

        get("/integrante/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(
                            integranteService.getIntegrante(
                                    request.params(":id"))
                    )));
        });

        put("/integrante", (request, response) -> {
            response.type("application/json");
            Integrante integrante = new Gson().fromJson(request.body()
                    , Integrante.class);
            Integrante integranteEditado = integranteService
                    .editIntegrante(integrante);
            if(integranteEditado != null) {
                return new Gson().toJson(new StandardResponse(
                        StatusResponse.SUCCESS,
                        new Gson().toJsonTree(
                                integranteEditado
                        )));
            } else {
                return new Gson().toJson(new StandardResponse(
                        StatusResponse.ERROR,
                        "Error al editar el integrante."
                        ));
            }


        });

        delete("/integrante/:id", (request, response) -> {
            response.type("application/json");
            integranteService.deleteIntegrante(request.params(":id"));
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS, "Integrante borrado"));
        });

        options("/integrante/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    (integranteService.integranteExists(request
                            .params(":id")) ? "El integrante existe."
                            : "El integrante no existe")
                    ));
        });
    }
}
