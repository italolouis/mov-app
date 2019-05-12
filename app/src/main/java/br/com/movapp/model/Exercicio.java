package br.com.movapp.model;

public class Exercicio {
    private Long cod;
    private String nome;
    private String descricao;
    private byte[] image;
    private GrupoMuscular grupo;
    private Equipamento equipamento;

    public Long getCod() {
        return cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public GrupoMuscular getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoMuscular grupo) {
        this.grupo = grupo;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }
}
