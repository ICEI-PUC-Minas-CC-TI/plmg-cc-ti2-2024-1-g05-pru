package model;

import java.time.LocalDate;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;

public class Paciente extends Usuario {
  public Paciente() {
    super();
  }

	public Paciente(int id, String nome, String cpf, String email, String senha, String telefone, char sexo, LocalDate nascimento, String urlFoto, String cep) {
    super(id, nome, cpf, email, senha, telefone, sexo, nascimento, urlFoto, cep);
  }

  public Paciente(int id, String nome, String email, String telefone, String urlFoto, String cep) {
    super(id, nome, email, telefone, urlFoto, cep);
  }

  // Deserialização
  public static Paciente deserialize(JsonElement json, Type TypeOfT, JsonDeserializationContext context) {
    JsonObject jsonObject = json.getAsJsonObject();

    int id = jsonObject.get("id").getAsInt();
    String nome = jsonObject.get("nome").getAsString();
    String email = jsonObject.get("email").getAsString();
    String telefone = jsonObject.get("telefone").getAsString();
    String urlFoto = jsonObject.get("urlFoto").getAsString();
    String cep = jsonObject.get("cep").getAsString();

    if (jsonObject.has("cpf") && jsonObject.has("senha") && jsonObject.has("sexo") && jsonObject.has("nascimento")) {
      id = 0;
      String cpf = jsonObject.get("cpf").getAsString();
      String senha = jsonObject.get("senha").getAsString();
      char sexo = jsonObject.get("sexo").getAsString().charAt(0);
      LocalDate nascimento = LocalDate.parse(jsonObject.get("nascimento").getAsString());

      return new Paciente(id, nome, cpf, email, senha, telefone, sexo, nascimento, urlFoto, cep);
    }

    return new Paciente(id, nome, email, telefone, urlFoto, cep);
  }
}
