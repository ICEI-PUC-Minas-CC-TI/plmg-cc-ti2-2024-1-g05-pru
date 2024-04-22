package app;

import static spark.Spark.*;

import service.*;

public class App {
  private static MedicoService medicoService = new MedicoService();
  private static PacienteService pacienteService = new PacienteService();
  private static LoginService loginService = new LoginService();

  public static void main(String[] args) {
    port(6789);

    // endpoints medico
    path("/medico", () -> {
      get("/", (request, response) -> medicoService.readAll(request, response));
      get("/:id", (request, response) -> medicoService.read(request, response));
      post("/", (request, response) -> medicoService.create(request, response));
      put("/:id", (request, response) -> medicoService.update(request, response));
      delete("/:id", (request, response) -> medicoService.delete(request, response));
    });

    // endpoints paciente
    path("/paciente", () -> {
      get("/", (request, response) -> pacienteService.readAll(request, response));
      get("/:id", (request, response) -> pacienteService.read(request, response));
      post("/", (request, response) -> pacienteService.create(request, response));
      put("/:id", (request, response) -> pacienteService.update(request, response));
      delete("/:id", (request, response) -> pacienteService.delete(request, response));
    });

    path("/login", () -> {
      post("/", (request, response) -> loginService.login(request, response));
    });

    // endpoints consulta

    // endpoints exame

    // endpoints especialidade
  }
}
