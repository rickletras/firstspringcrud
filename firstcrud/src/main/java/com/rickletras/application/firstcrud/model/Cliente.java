package com.rickletras.application.firstcrud.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

    private long id;
    private String nome;
    private String cpf;
    private Date dataNascimento;
 
    public Cliente() {
    }
 
    public Cliente(String nome, String cpf, Date dataNascimento) {
         this.nome = nome;
         this.cpf = cpf;
         this.dataNascimento = dataNascimento;
    }
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
 
    @Column(name = "nome", nullable = false)
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "cpf", nullable = false)
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(name = "data_nascimento", nullable = false)
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", cpf=" + cpf + ", dataNascimento=" + dataNascimento + "]";
	}
 
}
