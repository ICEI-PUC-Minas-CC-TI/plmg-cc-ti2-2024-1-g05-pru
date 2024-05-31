package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Medico;

public class MedicoDAO extends DAO {
  public MedicoDAO(Connection conexao) {
    this.conexao = conexao;
  }

  public Medico insert(Medico medico) throws SQLException {
    // verifica se o usuario é nulo
    if (medico == null) {
      return null;
    }

    try {
      PreparedStatement st = conexao.prepareStatement("INSERT INTO usuario (nome, cpf, email, senha, telefone, sexo, nascimento, url_foto, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
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
      PreparedStatement stMedico = conexao.prepareStatement("INSERT INTO medico (usuario_id, crm, validado) VALUES (?, ?, ?)");
      stMedico.setInt(1, medico.getId());
      stMedico.setString(2, medico.getCrm());
      stMedico.setBoolean(3, medico.getValidado());

      int affectedRowsMedico = stMedico.executeUpdate();

      if (affectedRowsMedico == 0) {
        throw new SQLException("Falha ao criar médico, nenhuma linha alterada.");
      }

      return medico;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }
  }

  public Medico get(int id) {
    Medico medico = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
      st = conexao.prepareStatement("SELECT * FROM usuario INNER JOIN medico ON usuario.id = medico.usuario_id WHERE usuario.id = ?");
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
          rs.getString("crm"),
          rs.getBoolean("validado")
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
      st = conexao.prepareStatement("SELECT * FROM usuario INNER JOIN medico ON usuario.id = medico.usuario_id WHERE email = ?");
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
          rs.getString("crm"),
          rs.getBoolean("validado")
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
      st = conexao.prepareStatement("SELECT * FROM usuario INNER JOIN medico ON usuario.id = medico.usuario_id ORDER BY ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
          rs.getString("crm"),
          rs.getBoolean("validado")
        );

        medicos.add(medico);
      }
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }

    return medicos;
  }

  public boolean update(Medico medico) throws SQLException {
    // verifica se o usuario é nulo
    if (medico == null) {
      return false;
    }

    try {
      PreparedStatement st = conexao.prepareStatement("UPDATE usuario SET nome = ?, email = ?, telefone = ?, url_foto = ?, cep = ? WHERE id = ?");
      st.setString(1, medico.getNome());
      st.setString(2, medico.getEmail());
      st.setString(3, medico.getTelefone());
      st.setString(4, medico.getUrlFoto());
      st.setString(5, medico.getCep());
      st.setInt(6, medico.getId());

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
      st = conexao.prepareStatement("DELETE FROM medico WHERE usuario_id = ?");
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

  public boolean validar(int id) {
    try {
      PreparedStatement st = conexao.prepareStatement("UPDATE medico SET validado = true WHERE usuario_id = ?");
      st.setInt(1, id);

      int affectedRows = st.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Falha ao validar médico, nenhuma linha alterada.");
      }

      return true;
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }
  }
}
