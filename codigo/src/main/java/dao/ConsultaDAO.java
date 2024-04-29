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

	public boolean insert(Consulta consulta) {
		boolean status = false;

		if (consulta == null) {
			return status;
		}

		try {
			PreparedStatement st = conexao.prepareStatement("INSERT INTO consulta (titulo, diagnostico, data_hora, url_anexo, paciente_id, medico_id) VALUES (?, ?, ?, ?, ?, ?)");

			st.setString(1, consulta.getTitulo());
			st.setString(2, consulta.getDiagnostico());
			st.setTimestamp(3, java.sql.Timestamp.valueOf(consulta.getDataHora()));
			st.setString(4, consulta.getUrlAnexo());
			st.setInt(5, consulta.getPacienteId());
			st.setInt(6, consulta.getMedicoId());

			if (st.executeUpdate() == 0) {
				throw new SQLException("Falha ao criar consulta, nenhuma linha alterada.");
			}

			return true;
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
					rs.getString("url_anexo"),
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

	public boolean update(Consulta consulta) {
		boolean status = false;

		if (consulta == null) {
			return status;
		}

		try {
			PreparedStatement st = conexao.prepareStatement("UPDATE consulta SET titulo = ?, diagnostico = ?, data_hora = ?, url_anexo = ?, paciente_id = ?, medico_id = ? WHERE id = ?");

			st.setString(1, consulta.getTitulo());
			st.setString(2, consulta.getDiagnostico());
			st.setTimestamp(3, java.sql.Timestamp.valueOf(consulta.getDataHora()));
			st.setString(4, consulta.getUrlAnexo());
			st.setInt(5, consulta.getPacienteId());
			st.setInt(6, consulta.getMedicoId());
			st.setInt(7, consulta.getId());

			if (st.executeUpdate() == 0) {
				throw new SQLException("Falha ao atualizar consulta, nenhuma linha alterada.");
			}

			status = true;
		} catch (SQLException u) {
			throw new RuntimeException("Falha ao atualizar consulta.", u);
		}

		return status;
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
					rs.getString("url_anexo"),
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
}
