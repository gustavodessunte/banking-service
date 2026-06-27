package br.com.alura.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Endereco extends PanacheEntity {

    @Column(nullable = false)
    public String rua;

    @Column(nullable = false)
    public String logradouro;

    @Column(nullable = false)
    public String complemento;

    @Column(nullable = false)
    public Integer numero;
}
