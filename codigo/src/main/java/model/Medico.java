package model;

import java.lang.reflect.Type;
import java.time.LocalDate;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Medico extends Usuario {
	private String crm;

  public Medico() {
    super();
  }

	public Medico(int id, String nome, String cpf, String email, String senha, String telefone, char sexo, LocalDate nascimento, String urlFoto, String cep, String crm) {
    super(id, nome, cpf, email, senha, telefone, sexo, nascimento, urlFoto, cep);
    setCrm(crm);

  }

  // crm
  public String getCrm() {
    return crm;
  }

  public void setCrm(String crm) {
    if (crm.length() < 3 || crm.length() > 8)
      throw new IllegalArgumentException("O CRM deve ter no mínimo 3 e no máximo 8 caracteres.");

    // verfica se os últimos 2 digitos são letras
    if (!Character.isLetter(crm.charAt(crm.length() - 2)) || !Character.isLetter(crm.charAt(crm.length() - 1)))
      throw new IllegalArgumentException("Os dois últimos dígitos do CRM devem ser letras.");

    this.crm = crm;
  }
  // utils
  @Override
  public String toString() {
    return super.toString() + " - CRM: " + getCrm();
  }

  // Deserialização
  public static Medico deserialize(JsonElement json, Type TypeOfT, JsonDeserializationContext context) {
    JsonObject jsonObject = json.getAsJsonObject();

    int id = 0;
    String nome = jsonObject.get("nome").getAsString();
    String cpf = jsonObject.get("cpf").getAsString();
    String email = jsonObject.get("email").getAsString();
    String senha = jsonObject.get("senha").getAsString();
    String telefone = jsonObject.get("telefone").getAsString();
    char sexo = jsonObject.get("sexo").getAsString().charAt(0);
    LocalDate nascimento = context.deserialize(jsonObject.get("nascimento"), LocalDate.class);
    String urlFoto = jsonObject.get("urlFoto").getAsString();
    String cep = jsonObject.get("cep").getAsString();
    String crm = jsonObject.get("crm").getAsString();

    return new Medico(id, nome, cpf, email, senha, telefone, sexo, nascimento, urlFoto, cep, crm);
  }
}
