package br.com.alura.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;


@Entity
public class Agencia extends PanacheEntity {

    @Column(nullable = false)
    public String nome;

    @Column(nullable = false, name = "razao_social")
    public String razaoSocial;

    @Column(nullable = false)
    public String cnpj;

    @OneToOne(targetEntity = Endereco.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    public Endereco endereco;
}
