package br.com.alura.repository;

import br.com.alura.domain.Agencia;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AgenciaRepository implements PanacheRepository<Agencia> {

    public Agencia findByCnpj(String cnpj) {
        return find("cnpj", cnpj).firstResult();
    }

    @Transactional
    public Long deleteByCnpj(String cnpj){
        return delete("cnpj", cnpj);
    }

    @Transactional
    public int update(Agencia agencia){
        return  update("nome = ?1, razaoSocial = ?2, endereco = ?3, cnpj = ?4 where id= ?5",
                agencia.nome,
                agencia.razaoSocial,
                agencia.endereco,
                agencia.cnpj,
                agencia.id
        );
    }

}
