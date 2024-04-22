package service;

import model.Medico;
import model.Paciente;
import model.Usuario;
import dao.MedicoDAO;
import dao.PacienteDAO;
import util.GsonUtil;
import util.JWTUtil;

import com.google.gson.JsonObject;

import spark.Request;
import spark.Response;

public class LoginService {
  public LoginService() { }

  public Object login(Request request, Response response) {
    JsonObject jsonBody = GsonUtil.GSON.fromJson(request.body(), JsonObject.class);

    String email = jsonBody.get("email").getAsString();
    String senha = jsonBody.get("senha").getAsString();
    char tipo = jsonBody.get("tipo").getAsString()
      .toUpperCase().charAt(0);

    if (tipo == 'M') {
      MedicoDAO medicoDAO = new MedicoDAO();
      Medico medico = medicoDAO.get(email);

      if (medico != null && medico.getSenha().equals(senha)) {
        String jwt = JWTUtil.generateToken((Usuario) medico);
        return jwt;
      }
    } else if (tipo == 'P') {
      PacienteDAO pacienteDAO = new PacienteDAO();
      Paciente paciente = pacienteDAO.get(email);
      
      if (paciente != null && paciente.getSenha().equals(senha)) {
        String jwt = JWTUtil.generateToken((Usuario) paciente);
        return jwt;
      }
    }

    return null;
  }
}
