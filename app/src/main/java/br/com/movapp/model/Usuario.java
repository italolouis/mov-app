package br.com.movapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario implements Serializable {
    private Long codusu;
    private String nome;
    private LocalDate dtnascimento;
    private byte[] foto;
    //private int idade;
    private String genero;
    private BigDecimal altura;
    private String telefone;
    private String email;
    private String senha;
    private String bloqueio;
    private Endereco endereco;

    public Long getCodusu() {
        return codusu;
    }

    public void setCodusu(Long codusu) {
        this.codusu = codusu;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDtnascimento() {
        return dtnascimento;
    }

    public void setDtnascimento(LocalDate dtnascimento) {
        this.dtnascimento = dtnascimento;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    /*public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }*/

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public BigDecimal getAltura() {
        return altura;
    }

    public void setAltura(BigDecimal altura) {
        this.altura = altura;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getBloqueio() {
        return bloqueio;
    }

    public void setBloqueio(String bloqueio) {
        this.bloqueio = bloqueio;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return getCodusu() + " - " + getNome();
    }
}
