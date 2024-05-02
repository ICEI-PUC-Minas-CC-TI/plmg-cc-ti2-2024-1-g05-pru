package model;

public class Especialidade {
    private int medicoId;
    private String especialidade;

    public Especialidade() {
    }

    public Especialidade(int medicoId, String especialidade) {
        setMedicoId(medicoId);
        setEspecialidade(especialidade);
    }

    public int getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return "Especialidade{" +
                "idMedico=" + medicoId +
                ",Especialidade=" + especialidade +
                '}';
    }
}
