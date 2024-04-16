package model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Usuario implements Serializable {
	private int id;
	private String nome;
  private String cpf;
  private String email;
  private String senha;
  private String telefone;
  private char sexo;
  private LocalDate nascimento;
  private String urlFoto;
  private String cep;


  public Usuario() { }

	public Usuario(int id, String nome, String cpf, String email, String senha, String telefone, char sexo, LocalDate nascimento, String urlFoto, String cep) {
    setId(id);
    setNome(nome);
    setCpf(cpf);
    setEmail(email);
    setSenha(senha);
    setTelefone(telefone);
    setSexo(sexo);
    setNascimento(nascimento);
    setUrlFoto(urlFoto);
    setCep(cep);
  }


  // id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

  // nome
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome.length() >= 3)
			this.nome = nome;
	}

  // cpf
  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    // TODO - validar o cpf
    this.cpf = cpf;
  }

  // email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    // TODO - validar email
    this.email = email;
  }

  // senha
  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    // verificar e criptografar senha
    this.senha = senha;
  }

  // telefone
  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  // sexo
  public char getSexo() {
    return sexo;
  }

  public void setSexo(char sexo) {
    this.sexo = sexo;
  }

  // nascimento
  public LocalDate getNascimento() {
    return nascimento;
  }

  public void setNascimento(LocalDate nascimento) {
    this.nascimento = nascimento;
  }

  // foto
  public String getUrlFoto() {
    return urlFoto;
  }

  public void setUrlFoto(String urlFoto) {
    this.urlFoto = urlFoto;
  }

  // cep
  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  // utils

  @Override
  public String toString() {
    return "Usuário #" + getId() +
      " - Nome: " + getNome() +
      " - CPF: " + getCpf() +
      " - E-mail: " + getEmail() +
      " - Senha: " + getSenha() +  // TODO - remover essa senha
      " - Telefone: " + getTelefone() +
      " - Sexo: " + getSexo() +
      " - Nascimento: " + getNascimento() +
      " - Foto: " + getUrlFoto() +
      " - CEP: " + getCep();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Usuario usuario = (Usuario) obj;
    return id == usuario.id;
  }
}