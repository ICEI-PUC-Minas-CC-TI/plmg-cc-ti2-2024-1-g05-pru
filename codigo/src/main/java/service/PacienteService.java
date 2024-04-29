package service;

import model.Vinculo;
import model.Consulta;
import model.Paciente;
import dao.ConsultaDAO;
import dao.PacienteDAO;
import dao.VinculoDAO;
import util.GsonUtil;

import java.util.List;
import spark.Request;
import spark.Response;


public class PacienteService {
  private PacienteDAO pacienteDAO;
  private VinculoDAO vinculoDAO;
  private ConsultaDAO consultaDAO;

  public PacienteService() {
    pacienteDAO = new PacienteDAO();
    vinculoDAO = new VinculoDAO();
    consultaDAO = new ConsultaDAO();
  }

  public Object readAll(Request request, Response response) {
    List<Paciente> pacientes = pacienteDAO.getAll();
    response.header("Content-Type", "application/json");
    response.header("Content-Encoding", "UTF-8");

    return GsonUtil.GSON.toJson(pacientes);
  }

  public Object readAllMedicos(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    List<Vinculo> vinculos = vinculoDAO.getAllMedicos(id);
    response.header("Content-Type", "application/json");
    response.header("Content-Encoding", "UTF-8");

    return GsonUtil.GSON.toJson(vinculos);
  }

  public Object read(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    Paciente paciente = pacienteDAO.get(id);

    if (paciente == null) {
      response.status(404); // 404 Not found
      return "Paciente ID #" + id + " não encontrado.";
    }

    response.header("Content-Type", "application/json");
    response.header("Content-Encoding", "UTF-8");

    return GsonUtil.GSON.toJson(paciente);
  }

  public Object create(Request request, Response response) {
    Paciente paciente = GsonUtil.GSON.fromJson(request.body(), Paciente.class);

    if (pacienteDAO.insert(paciente)) {
      response.status(201); // 201 Created
      return "Paciente (" + paciente.getNome() + ") inserido!";
    } else {
      response.status(404); // 404 Not found
      return "Paciente (" + paciente.getNome() + ") não inserido!";
    }
  }

  public Object update(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    Paciente paciente = GsonUtil.GSON.fromJson(request.body(), Paciente.class);

    if (paciente.getId() != id) {
      response.status(400); // 400 Bad request
      return "ID do paciente diferente do ID da URL!";
    }

    if (pacienteDAO.update(paciente)) {
      response.status(200); // 200 OK
      return "Paciente (ID #" + paciente.getId() + ") atualizado!";
    } else {
      response.status(404); // 404 Not found
      return "Paciente (ID #" + paciente.getId() + ") não encontrado!";
    }
  }

  public Object delete(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));

    if (pacienteDAO.delete(id)) {
      response.status(200); // 200 OK
      return "Paciente (" + id + ") excluído!";
    } else {
      response.status(404); // 404 Not found
      return "Paciente (" + id + ") não encontrado!";
    }
  }

  // Ver todas as consultas de um paciente
  public Object readAllConsultas(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    List<Consulta> consultas = consultaDAO.getAllConsultasPaciente(id);

    if (consultas == null) {
      response.status(404); // 404 Not found
      return "Paciente ID #" + id + " não encontrado.";
    }

    response.header("Content-Type", "application/json");
    response.header("Content-Encoding", "UTF-8");

    return GsonUtil.GSON.toJson(consultas);
  }
}
