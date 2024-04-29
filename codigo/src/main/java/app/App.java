package app;

import static spark.Spark.*;

import service.*;

public class App {
  private static LoginService loginService = new LoginService();
  private static MedicoService medicoService = new MedicoService();
  private static PacienteService pacienteService = new PacienteService();
  private static ConsultaService consultaService = new ConsultaService();
  private static ExameService exameService = new ExameService();

  public static void main(String[] args) {
    port(6789);

    // CORS
    before((request, response) -> {
      response.header("Access-Control-Allow-Origin", "*"); // Change * to your frontend domain for production
      response.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
      response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
      response.header("Access-Control-Allow-Credentials", "true");
    });

    // options (preflight request)
    options("/*", (request, response) -> "OK");

    // endpoint login
    post("/login", (request, response) -> loginService.login(request, response));

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
    path("/exame", () -> {
      get("/:id", (request, response) -> exameService.read(request, response));
      put("/:id", (request, response) -> exameService.update(request, response));
      delete("/:id", (request, response) -> exameService.delete(request, response));
    });
  }
}
