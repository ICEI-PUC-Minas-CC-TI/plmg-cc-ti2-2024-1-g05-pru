package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Paciente;

public class PacienteDAO extends DAO {
  public PacienteDAO(Connection conexao) {
    this.conexao = conexao;
  }

  public Paciente insert(Paciente paciente) throws SQLException {
    // verifica se o usuario é nulo
    if (paciente == null) {
      return null;
    }

    try {
      PreparedStatement st = conexao.prepareStatement("INSERT INTO usuario (nome, cpf, email, senha, telefone, sexo, nascimento, url_foto, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
      st.setString(1, paciente.getNome());
      st.setString(2, paciente.getCpf());
      st.setString(3, paciente.getEmail());
      st.setString(4, paciente.getSenha());
      st.setString(5, paciente.getTelefone());
      st.setString(6, String.valueOf(paciente.getSexo()));
      st.setDate(7, java.sql.Date.valueOf(paciente.getNascimento()));
      st.setString(8, paciente.getUrlFoto());
      st.setString(9, paciente.getCep());

      if (st.executeUpdate() == 0) {
        throw new SQLException("Falha ao criar usuário, nenhuma linha alterada.");
      }

      ResultSet generatedKeys = st.getGeneratedKeys();
      if (generatedKeys.next())
        paciente.setId(generatedKeys.getInt(1));
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao criar usuário, ID não foi obtido.", u);
    }

    try {
      PreparedStatement stPaciente = conexao.prepareStatement("INSERT INTO paciente (usuario_id) VALUES (?)");
      stPaciente.setInt(1, paciente.getId());

      int affectedRowsPaciente = stPaciente.executeUpdate();

      if (affectedRowsPaciente == 0) {
        throw new SQLException("Falha ao criar paciente, nenhuma linha alterada.");
      }

      return paciente;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }
  }

  public Paciente get(int id) {
    Paciente paciente = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      st = conexao.prepareStatement("SELECT * FROM usuario INNER JOIN paciente ON usuario.id = paciente.usuario_id WHERE usuario.id = ?");
      st.setInt(1, id);

      rs = st.executeQuery();
      if (rs.next()) {
        paciente = new Paciente(
          rs.getInt("id"),
          rs.getString("nome"),
          rs.getString("cpf"),
          rs.getString("email"),
          rs.getString("senha"),
          rs.getString("telefone"),
          rs.getString("sexo").charAt(0),
          rs.getDate("nascimento").toLocalDate(),
          rs.getString("url_foto"),
          rs.getString("cep")
        );
      }
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return paciente;
  }

  public Paciente get(String email) {
    Paciente paciente = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      st = conexao.prepareStatement("SELECT * FROM usuario INNER JOIN paciente ON usuario.id = paciente.usuario_id WHERE email = ?");
      st.setString(1, email);

      rs = st.executeQuery();
      if (rs.next()) {
        paciente = new Paciente(
          rs.getInt("id"),
          rs.getString("nome"),
          rs.getString("cpf"),
          rs.getString("email"),
          rs.getString("senha"),
          rs.getString("telefone"),
          rs.getString("sexo").charAt(0),
          rs.getDate("nascimento").toLocalDate(),
          rs.getString("url_foto"),
          rs.getString("cep")
        );
      }
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return paciente;
  }

  public List<Paciente> getAll() {
    return getAll("nome");
  }

  public List<Paciente> getAll(String orderBy) {
    List<Paciente> pacientes = new ArrayList<Paciente>();
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      st = conexao.prepareStatement("SELECT * FROM usuario INNER JOIN paciente ON usuario.id = paciente.usuario_id ORDER BY ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      st.setString(1, orderBy);

      rs = st.executeQuery();
      while(rs.next()) {
        Paciente paciente = new Paciente(
          rs.getInt("id"),
          rs.getString("nome"),
          rs.getString("cpf"),
          rs.getString("email"),
          rs.getString("senha"),
          rs.getString("telefone"),
          rs.getString("sexo").charAt(0),
          rs.getDate("nascimento").toLocalDate(),
          rs.getString("url_foto"),
          rs.getString("cep")
        );

        pacientes.add(paciente);
      }
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return pacientes;
  }

  public boolean update(Paciente paciente) throws SQLException {
    // verifica se o usuario é nulo
    if (paciente == null) {
      return false;
    }

    try {
      PreparedStatement st = conexao.prepareStatement("UPDATE usuario SET nome = ?, email = ?, telefone = ?, url_foto = ?, cep = ? WHERE id = ?");
      st.setString(1, paciente.getNome());
      st.setString(2, paciente.getEmail());
      st.setString(3, paciente.getTelefone());
      st.setString(4, paciente.getUrlFoto());
      st.setString(5, paciente.getCep());
      st.setInt(6, paciente.getId());

      if (st.executeUpdate() == 0) {
        throw new SQLException("Falha ao atualizar usuário, nenhuma linha alterada.");
      }

      return true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }
  }

  public boolean delete(int id) {
    boolean status = false;
    PreparedStatement st = null;

    try {
      st = conexao.prepareStatement("DELETE FROM paciente WHERE usuario_id = ?");
      st.setInt(1, id);

      int affectedRows = st.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Falha ao deletar médico, nenhuma linha alterada.");
      }

      status = true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return status;
  }
}
