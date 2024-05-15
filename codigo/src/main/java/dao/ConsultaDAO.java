package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Consulta;

public class ConsultaDAO extends DAO {
	public ConsultaDAO() {
		super();
	}

	public void finalize() {
		close();
	}

	public Consulta insert(Consulta consulta) {
		if (consulta == null) {
			return null;
		}

		try {
			PreparedStatement st = conexao.prepareStatement("INSERT INTO consulta (titulo, diagnostico, data_hora, paciente_id, medico_id) VALUES (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

			st.setString(1, consulta.getTitulo());
			st.setString(2, consulta.getDiagnostico());
			st.setTimestamp(3, java.sql.Timestamp.valueOf(consulta.getDataHora()));
			st.setInt(4, consulta.getPacienteId());
			st.setInt(5, consulta.getMedicoId());

			if (st.executeUpdate() == 0) {
				throw new SQLException("Falha ao criar consulta, nenhuma linha alterada.");
			}

			ResultSet generatedKeys = st.getGeneratedKeys();
      if (generatedKeys.next())
        consulta.setId(generatedKeys.getInt(1));

			return consulta;
		} catch (SQLException u) {
			throw new RuntimeException("Falha ao criar consulta.", u);
		}
	}

	public Consulta get(int id) {
		Consulta consulta = null;

		try {
			String sql = "SELECT c.*, med.nome as medico, pac.nome as paciente FROM consulta c INNER JOIN usuario med ON med.id = c.medico_id INNER JOIN usuario pac ON pac.id = c.paciente_id WHERE c.id = ?";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				consulta = new Consulta(
					rs.getInt("id"),
					rs.getString("titulo"),
					rs.getString("diagnostico"),
					rs.getTimestamp("data_hora").toLocalDateTime(),
					rs.getString("paciente"),
					rs.getInt("paciente_id"),
					rs.getString("medico"),
					rs.getInt("medico_id")
				);
			}
		} catch (SQLException u) {
			throw new RuntimeException("Falha ao obter consulta.", u);
		}

		return consulta;
	}

	public boolean update(Consulta consulta) throws SQLException {
		if (consulta == null) {
			return false;
		}

		try {
			PreparedStatement st = conexao.prepareStatement("UPDATE consulta SET titulo = ?, diagnostico = ? WHERE id = ?");

			st.setString(1, consulta.getTitulo());
			st.setString(2, consulta.getDiagnostico());
			st.setInt(3, consulta.getId());

			if (st.executeUpdate() == 0) {
				throw new SQLException("Falha ao atualizar consulta, nenhuma linha alterada.");
			}

			return true;
		} catch (SQLException u) {
			throw new RuntimeException("Falha ao atualizar consulta.", u);
		}
	}

	public boolean delete(int id) {
		boolean status = false;

		try {
			String sql = "DELETE FROM consulta WHERE id = ?";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, id);

			if (st.executeUpdate() > 0) {
				status = true;
			}
		} catch (SQLException u) {
			throw new RuntimeException("Falha ao deletar consulta, nenhuma linha alterada.", u);
		}

		return status;
	}

	// Pegar todas as consultas de um paciente
	public List<Consulta> getAllConsultasPaciente(int pacienteId) {
		List<Consulta> consultas = new ArrayList<Consulta>();

		try {
			String sql = "SELECT c.*, med.nome as medico, pac.nome as paciente FROM consulta c INNER JOIN usuario med ON med.id = c.medico_id INNER JOIN usuario pac ON pac.id = c.paciente_id WHERE paciente_id = ?";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, pacienteId);

			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				consultas.add(new Consulta(
					rs.getInt("id"),
					rs.getString("titulo"),
					rs.getString("diagnostico"),
					rs.getTimestamp("data_hora").toLocalDateTime(),
					rs.getString("paciente"),
					rs.getInt("paciente_id"),
					rs.getString("medico"),
					rs.getInt("medico_id")
				));
			}
		} catch (SQLException u) {
			throw new RuntimeException("Falha ao obter consultas do paciente.", u);
		}

		return consultas;
	}

	public List<Consulta> getLastConsultas(int pacienteId, int qtde) {
		List<Consulta> consultas = new ArrayList<Consulta>();

		try {
			String sql = "SELECT * FROM consulta WHERE paciente_id = ? ORDER BY data_hora DESC LIMIT ?";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, pacienteId);
			st.setInt(2, qtde);

			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				consultas.add(new Consulta(
					rs.getInt("id"),
					rs.getString("titulo"),
					rs.getString("diagnostico"),
					rs.getTimestamp("data_hora").toLocalDateTime(),
					rs.getInt("paciente_id"),
					rs.getInt("medico_id")
				));
			}
		} catch (SQLException u) {
			throw new RuntimeException("Falha ao obter as últimas consultas do paciente.", u);
		}

		return consultas;
	}
}
