package model;

import java.io.Serializable;
import java.time.LocalDate;
import org.mindrot.jbcrypt.BCrypt;

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

  public Usuario(int id, String nome, String email, String telefone, String urlFoto, String cep) {
    setId(id);
    setNome(nome);
    setEmail(email);
    setTelefone(telefone);
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
		if (nome.length() < 3)
      throw new IllegalArgumentException("O nome deve ter no mínimo 3 caracteres.");

    this.nome = nome;
	}

  // cpf
  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    if (cpf.length() != 11)
      throw new IllegalArgumentException("CPF inválido.");

    if (!cpf.matches("[0-9]+"))
      throw new IllegalArgumentException("CPF deve conter apenas números.");

    // Validações mais complexas do CPF ficam para revisões futuras

    this.cpf = cpf;
  }

  // email
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    if (!email.contains("@") || !email.contains("."))
      throw new IllegalArgumentException("E-mail inválido.");

    this.email = email.toLowerCase();
  }

  // senha
  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    if (senha.length() < 6)
      throw new IllegalArgumentException("A senha deve ter no mínimo 6 caracteres.");

    // Verifica se a senha já está criptografada
    if (!senha.matches("\\$2[ayb]\\$.{56}")) {
      senha = BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    this.senha = senha;
  }

  public boolean checkSenha(String senha) {
    return BCrypt.checkpw(senha, getSenha());
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
