package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Medico;

public class MedicoDAO extends DAO {
	public MedicoDAO() {
		super();
	}

  public boolean insert(Medico medico) {
    boolean status = false;

    // verifica se o usuario é nulo
    if (medico == null) {
      return status;
    }

    try {
      String sql = "INSERT INTO usuario (nome, cpf, email, senha, telefone, sexo, nascimento, url_foto, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement st = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      st.setString(1, medico.getNome());
      st.setString(2, medico.getCpf());
      st.setString(3, medico.getEmail());
      st.setString(4, medico.getSenha());
      st.setString(5, medico.getTelefone());
      st.setString(6, String.valueOf(medico.getSexo()));
      st.setDate(7, java.sql.Date.valueOf(medico.getNascimento()));
      st.setString(8, medico.getUrlFoto());
      st.setString(9, medico.getCep());

      if (st.executeUpdate() == 0) {
        throw new SQLException("Falha ao criar usuário, nenhuma linha alterada.");
      }

      ResultSet generatedKeys = st.getGeneratedKeys();
      if (generatedKeys.next())
        medico.setId(generatedKeys.getInt(1));
    } catch (SQLException u) {
      throw new RuntimeException("Falha ao criar usuário, ID não foi obtido.", u);
    }

    try {
      String sqlMedico = "INSERT INTO medico (usuario_id, crm) VALUES (?, ?)";

      PreparedStatement stMedico = conexao.prepareStatement(sqlMedico);
      stMedico.setInt(1, medico.getId());
      stMedico.setString(2, medico.getCrm());

      int affectedRowsMedico = stMedico.executeUpdate();

      if (affectedRowsMedico == 0) {
        throw new SQLException("Falha ao criar médico, nenhuma linha alterada.");
      }

      return true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }
  }

  public Medico get(int id) {
    Medico medico = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      String sql = "SELECT * FROM usuario INNER JOIN medico ON usuario.id = medico.usuario_id WHERE usuario.id = ?";
      st = conexao.prepareStatement(sql);
      st.setInt(1, id);

      rs = st.executeQuery();
      if (rs.next()) {
        medico = new Medico(
          rs.getInt("id"),
          rs.getString("nome"),
          rs.getString("cpf"),
          rs.getString("email"),
          rs.getString("senha"),
          rs.getString("telefone"),
          rs.getString("sexo").charAt(0),
          rs.getDate("nascimento").toLocalDate(),
          rs.getString("url_foto"),
          rs.getString("cep"),
          rs.getString("crm")
        );
      }
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return medico;
  }

  public Medico get(String email) {
    Medico medico = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      String sql = "SELECT * FROM usuario INNER JOIN medico ON usuario.id = medico.usuario_id WHERE email = ?";
      st = conexao.prepareStatement(sql);
      st.setString(1, email);

      rs = st.executeQuery();
      if (rs.next()) {
        medico = new Medico(
          rs.getInt("id"),
          rs.getString("nome"),
          rs.getString("cpf"),
          rs.getString("email"),
          rs.getString("senha"),
          rs.getString("telefone"),
          rs.getString("sexo").charAt(0),
          rs.getDate("nascimento").toLocalDate(),
          rs.getString("url_foto"),
          rs.getString("cep"),
          rs.getString("crm")
        );
      }
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return medico;
  }

  public List<Medico> getAll() {
    return getAll("nome");
  }

  public List<Medico> getAll(String orderBy) {
    List<Medico> medicos = new ArrayList<Medico>();
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      String sql = "SELECT * FROM usuario INNER JOIN medico ON usuario.id = medico.usuario_id ORDER BY ?";

      st = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      st.setString(1, orderBy);

      rs = st.executeQuery();
      while(rs.next()) {
        Medico medico = new Medico(
          rs.getInt("id"),
          rs.getString("nome"),
          rs.getString("cpf"),
          rs.getString("email"),
          rs.getString("senha"),
          rs.getString("telefone"),
          rs.getString("sexo").charAt(0),
          rs.getDate("nascimento").toLocalDate(),
          rs.getString("url_foto"),
          rs.getString("cep"),
          rs.getString("crm")
        );

        medicos.add(medico);
      }
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return medicos;
  }

  public boolean update(Medico medico) {
    boolean status = false;

    // verifica se o usuario é nulo
    if (medico == null) {
      return status;
    }

    try {
      String sql = "UPDATE usuario SET nome = ?, cpf = ?, email = ?, senha = ?, telefone = ?, sexo = ?, nascimento = ?, url_foto = ?, cep = ? WHERE id = ?";

      PreparedStatement st = conexao.prepareStatement(sql);
      st.setString(1, medico.getNome());
      st.setString(2, medico.getCpf());
      st.setString(3, medico.getEmail());
      st.setString(4, medico.getSenha());
      st.setString(5, medico.getTelefone());
      st.setString(6, String.valueOf(medico.getSexo()));
      st.setDate(7, java.sql.Date.valueOf(medico.getNascimento()));
      st.setString(8, medico.getUrlFoto());
      st.setString(9, medico.getCep());
      st.setInt(10, medico.getId());

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
      String sql = "DELETE FROM medico WHERE usuario_id = ?";
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
