package model;

import java.time.LocalDate;

public class Paciente extends Usuario {
  public Paciente() {
    super();
  }

	public Paciente(int id, String nome, String cpf, String email, String senha, String telefone, char sexo, LocalDate nascimento, String urlFoto, String cep) {
    super(id, nome, cpf, email, senha, telefone, sexo, nascimento, urlFoto, cep);
  }
}