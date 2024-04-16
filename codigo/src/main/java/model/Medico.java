package model;

import java.time.LocalDate;

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
    // TODO - validar crm
    this.crm = crm;
  }


  // utils

  @Override
  public String toString() {
    return super.toString() + " - CRM: " + getCrm();
  }
}