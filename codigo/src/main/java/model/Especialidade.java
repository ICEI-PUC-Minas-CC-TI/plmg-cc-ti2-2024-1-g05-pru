package model;

public class Especialidade {
    private int medicoId;
    private int especialidadeId;

    public Especialidade() {
    }

    public Especialidade(int medicoId, int especialidadeId) {
        setMedicoId(medicoId);
        setEspecialidadeId(especialidadeId);
    }

    public int getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
    }

    public int getEspecialidadeId() {
        return especialidadeId;
    }

    public void setEspecialidadeId(int especialidadeId) {
        this.especialidadeId = especialidadeId;
    }

    @Override
    public String toString() {
        return "Especialidade{" +
                "idMedico=" + medicoId +
                ", idEspecialidade=" + especialidadeId +
                '}';
    }
}
