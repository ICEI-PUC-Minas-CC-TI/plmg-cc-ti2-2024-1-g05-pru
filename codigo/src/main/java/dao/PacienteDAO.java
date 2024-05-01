package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Paciente;

public class PacienteDAO extends DAO {
	public PacienteDAO() {
		super();
	}

  public Paciente insert(Paciente paciente) throws SQLException {
    // verifica se o usuario é nulo
    if (paciente == null) {
      return null;
    }

    try {
      String sql = "INSERT INTO usuario (nome, cpf, email, senha, telefone, sexo, nascimento, url_foto, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement st = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
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
      String sqlPaciente = "INSERT INTO paciente (usuario_id) VALUES (?)";

      PreparedStatement stPaciente = conexao.prepareStatement(sqlPaciente);
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
      String sql = "SELECT * FROM usuario INNER JOIN paciente ON usuario.id = paciente.usuario_id WHERE usuario.id = ?";
      st = conexao.prepareStatement(sql);
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
      String sql = "SELECT * FROM usuario INNER JOIN paciente ON usuario.id = paciente.usuario_id WHERE email = ?";
      st = conexao.prepareStatement(sql);
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
      String sql = "SELECT * FROM usuario INNER JOIN paciente ON usuario.id = paciente.usuario_id ORDER BY ?";

      st = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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

  public Paciente update(Paciente paciente) throws SQLException {
    // verifica se o usuario é nulo
    if (paciente == null) {
      return null;
    }

    try {
      String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, telefone = ?, sexo = ?, nascimento = ?, url_foto = ?, cep = ? WHERE id = ?";

      PreparedStatement st = conexao.prepareStatement(sql);
      st.setString(1, paciente.getNome());
      st.setString(2, paciente.getEmail());
      st.setString(3, paciente.getSenha());
      st.setString(4, paciente.getTelefone());
      st.setString(5, String.valueOf(paciente.getSexo()));
      st.setDate(6, java.sql.Date.valueOf(paciente.getNascimento()));
      st.setString(7, paciente.getUrlFoto());
      st.setString(8, paciente.getCep());
      st.setInt(9, paciente.getId());

      if (st.executeUpdate() == 0) {
        throw new SQLException("Falha ao atualizar usuário, nenhuma linha alterada.");
      }

      return paciente;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }
  }

  public boolean delete(int id) {
    boolean status = false;
    PreparedStatement st = null;

    try {
      String sql = "DELETE FROM paciente WHERE usuario_id = ?";
      st = conexao.prepareStatement(sql);
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
