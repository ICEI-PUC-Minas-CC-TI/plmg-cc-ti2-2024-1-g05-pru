package service;

import model.Usuario;
import dao.MedicoDAO;
import dao.PacienteDAO;
import util.GsonUtil;
import util.JWTUtil;

import java.sql.Connection;

import com.google.gson.JsonObject;

import spark.Request;
import spark.Response;

public class LoginService {
  MedicoDAO medicoDAO;
  PacienteDAO pacienteDAO;

  public LoginService(Connection conexao) {
    medicoDAO = new MedicoDAO(conexao);
    pacienteDAO = new PacienteDAO(conexao);
  }

  public Object login(Request request, Response response) {
    JsonObject jsonBody = GsonUtil.GSON.fromJson(request.body(), JsonObject.class);

    String email = jsonBody.get("email").getAsString().toLowerCase();
    String senha = jsonBody.get("senha").getAsString();
    char tipo = jsonBody.get("tipo").getAsString().toUpperCase().charAt(0);

    Usuario usuario = null;
    if (tipo == 'M') {
      usuario = medicoDAO.get(email);
    } else if (tipo == 'P') {
      usuario = pacienteDAO.get(email);
    } else {
      response.status(400);
      return "Tipo de usuário inválido.";
    }

    if (usuario != null && usuario.checkSenha(senha)) {
      String jwt = JWTUtil.generateToken(usuario);

      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("token", jwt);
      response.status(200);
      response.type("application/json");
      return GsonUtil.GSON.toJson(jsonObject);
    } else {
      response.status(401);
      return "Falha na autenticação.";
    }
  }
}
