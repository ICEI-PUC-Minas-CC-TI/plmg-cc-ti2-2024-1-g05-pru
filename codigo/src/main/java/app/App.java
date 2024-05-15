package app;

import service.*;
import static spark.Spark.*;

public class App {
  private static LoginService loginService = new LoginService();
  private static MedicoService medicoService = new MedicoService();
  private static PacienteService pacienteService = new PacienteService();
  private static ConsultaService consultaService = new ConsultaService();
  private static ExameService exameService = new ExameService();
  private static VinculoService vinculoService = new VinculoService();
  private static MedicamentoService medicamentoService = new MedicamentoService();
  private static EspecialidadeService especialidadeService = new EspecialidadeService();

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
      get("/:id", (request, response) -> medicoService.read(request, response));
      post("/", (request, response) -> medicoService.create(request, response));
      put("/:id", (request, response) -> medicoService.update(request, response));
      delete("/:id", (request, response) -> medicoService.delete(request, response));

      // pacientes do medico
      get("/:id/pacientes", (request, response) -> medicoService.readAllPacientes(request, response));

      // especialidades do medico
      get("/:id/especialidades", (request, response) -> medicoService.readAllEspecialidades(request, response));
    });

    // endpoints paciente
    path("/paciente", () -> {
      get("/:id", (request, response) -> pacienteService.read(request, response));
      post("/", (request, response) -> pacienteService.create(request, response));
      put("/:id", (request, response) -> pacienteService.update(request, response));
      delete("/:id", (request, response) -> pacienteService.delete(request, response));

      // consultas do paciente
      get("/:id/consultas", (request, response) -> pacienteService.readAllConsultas(request, response));
      //get("/:id/consultas/:qtde", (request, response) -> pacienteService.readAllConsultas(request, response));

      // exames do paciente
      get("/:id/exames", (request, response) -> pacienteService.readAllExames(request, response));

      // medicos do paciente
      get("/:id/medicos", (request, response) -> pacienteService.readAllMedicos(request, response));

      // Ver as últimas consultas de um paciente
      get("/:id/consultas/:qtde", (request, response) -> pacienteService.readLastConsultas(request, response));

      // Ver os últimos exames de um paciente
      get("/:id/exames/:qtde", (request, response) -> pacienteService.readLastExames(request, response));
    });

    // endpoints consulta
    path("/consulta", () -> {
      get("/:id", (request, response) -> consultaService.read(request, response));
      post("/", (request, response) -> consultaService.create(request, response));
      put("/:id", (request, response) -> consultaService.update(request, response));
      delete("/:id", (request, response) -> consultaService.delete(request, response));

      get("/:id/exames", (request, response) -> consultaService.readAllExames(request, response));

      get("/:id/medicamentos", (request, response) -> consultaService.readAllMedicamentos(request, response));
    });

    // endpoints exame
    path("/exame", () -> {
      get("/:id", (request, response) -> exameService.read(request, response));
      post("/", (request, response) -> exameService.create(request, response));
      put("/:id", (request, response) -> exameService.update(request, response));
      delete("/:id", (request, response) -> exameService.delete(request, response));
    });

    // endpoints medicamento
    path("/medicamento", () -> {
      post("/", (request, response) -> medicamentoService.create(request, response));
      put("/:id", (request, response) -> medicamentoService.update(request, response));
      delete("/:id", (request, response) -> medicamentoService.delete(request, response));
    });

    // endpoints vinculo
    path("/vinculo", () -> {
      post("/", (request, response) -> vinculoService.create(request, response));
      put("/:id", (request, response) -> vinculoService.update(request, response));
      delete("/:id", (request, response) -> vinculoService.delete(request, response));
    });

    // endpoints especialidade
    path("/especialidade", () -> {
      post("/", (request, response) -> especialidadeService.create(request, response));
      put("/:id", (request, response) -> especialidadeService.update(request, response));
      delete("/:id", (request, response) -> especialidadeService.delete(request, response));
    });
  }
}
