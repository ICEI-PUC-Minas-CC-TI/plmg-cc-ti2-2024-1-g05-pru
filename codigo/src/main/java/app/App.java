package app;

import static spark.Spark.*;

import service.*;

public class App {
  private static LoginService loginService = new LoginService();
  private static MedicoService medicoService = new MedicoService();
  private static PacienteService pacienteService = new PacienteService();
  private static ConsultaService consultaService = new ConsultaService();

  public static void main(String[] args) {
    port(6789);

    // endpoint login
    path("/login", () -> {
      post("/", (request, response) -> loginService.login(request, response));
    });

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

    // endpoints consulta
    path("/consulta", () -> {
      get("/:id", (request, response) -> consultaService.read(request, response));
      post("/", (request, response) -> consultaService.create(request, response));
      put("/:id", (request, response) -> consultaService.update(request, response));
      delete("/:id", (request, response) -> consultaService.delete(request, response));
    });

    // endpoints exame
  }
}
