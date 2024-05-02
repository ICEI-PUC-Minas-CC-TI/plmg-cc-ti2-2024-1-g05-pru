package service;

import model.Vinculo;
import model.Consulta;
import model.Paciente;
import dao.ConsultaDAO;
import dao.PacienteDAO;
import dao.VinculoDAO;
import util.GsonUtil;

import java.sql.SQLException;
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

  public Object read(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    Paciente paciente = pacienteDAO.get(id);

    if (paciente == null) {
      response.status(404); // 404 Not found
      return "Paciente ID #" + id + " não encontrado.";
    }

    response.header("Content-Type", "application/json");
    return GsonUtil.GSON.toJson(paciente);
  }

  public Object create(Request request, Response response) {
    try {
      Paciente paciente = GsonUtil.GSON.fromJson(request.body(), Paciente.class);
      paciente = pacienteDAO.insert(paciente);

      response.status(201); // 201 Created
      response.header("Content-Type", "application/json");
      return GsonUtil.GSON.toJson(paciente);
    }
    catch (IllegalArgumentException e) {
      response.status(400); // 400 Bad request
      return "Erro ao criar paciente: " + e.getMessage();
    }
    catch (SQLException e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao criar paciente: " + e.getMessage();
    }
    catch (Exception e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao criar paciente: " + e.getCause().getMessage();
    }
  }

  public Object update(Request request, Response response) {
    try {
      int id = Integer.parseInt(request.params(":id"));
      Paciente paciente = GsonUtil.GSON.fromJson(request.body(), Paciente.class);

      if (paciente.getId() != id) {
        response.status(400); // 400 Bad request
        return "ID do paciente diferente do ID da URL!";
      }

      if (pacienteDAO.update(paciente)) {
        response.status(204); // 204 No content
        return response;
      } else {
        response.status(404); // 404 Not found
				return "Paciente ID #" + paciente.getId() + " não encontrado!";
      }
    }
    catch (SQLException e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao atualizar paciente: " + e.getMessage();
    }
    catch (Exception e) {
      response.status(500); // 500 Internal Server Error
      return "Erro ao atualizar paciente: " + e.getMessage();
    }
  }

  public Object delete(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));

    if (pacienteDAO.delete(id)) {
      response.status(204); // 204 No content
      return response;
    } else {
      response.status(404); // 404 Not found
      return "Paciente ID #" + id + " não encontrado!";
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
    return GsonUtil.GSON.toJson(consultas);
  }

  // Ver todos os exames de um paciente

  // Ver todos os médicos de um paciente
  public Object readAllMedicos(Request request, Response response) {
    int id = Integer.parseInt(request.params(":id"));
    List<Vinculo> vinculos = vinculoDAO.getAllMedicos(id);
    
    response.header("Content-Type", "application/json");
    return GsonUtil.GSON.toJson(vinculos);
  }
}
