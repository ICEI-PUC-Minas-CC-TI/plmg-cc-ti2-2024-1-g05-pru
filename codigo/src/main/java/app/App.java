package app;

import static spark.Spark.*;

import service.MedicoService;

public class App {
  private static MedicoService medicoService = new MedicoService();

	public static void main(String[] args) {
    port(6789);

    // endpoints medico

    get("/medico/", (request, response) -> medicoService.getAll(request, response));

    get("/medico/:id", (request, response) -> medicoService.get(request, response));

    post("/medico/", (request, response) -> medicoService.post(request, response));

    put("/medico/:id", (request, response) -> medicoService.put(request, response));

    delete("/medico/:id", (request, response) -> medicoService.delete(request, response));
  }
}
