package com.prover.dao;

import com.prover.model.FraseAnalisada;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class FraseAnalisadaDAO {
    
    @PersistenceContext(unitName = "proverPU")
    private EntityManager entityManager;
    
    public void salvar(FraseAnalisada fraseAnalisada) {
        entityManager.persist(fraseAnalisada);
    }
    
    public FraseAnalisada buscarPorId(Long id) {
        return entityManager.find(FraseAnalisada.class, id);
    }
    
    public List<FraseAnalisada> buscarTodasOrdenadasPorData() {
        TypedQuery<FraseAnalisada> query = entityManager.createQuery(
            "SELECT f FROM FraseAnalisada f ORDER BY f.dataAnalise DESC", 
            FraseAnalisada.class
        );
        return query.getResultList();
    }
    
    public List<FraseAnalisada> buscarPorFraseOriginal(String fraseOriginal) {
        TypedQuery<FraseAnalisada> query = entityManager.createQuery(
            "SELECT f FROM FraseAnalisada f WHERE f.fraseOriginal = :fraseOriginal ORDER BY f.dataAnalise DESC", 
            FraseAnalisada.class
        );
        query.setParameter("fraseOriginal", fraseOriginal);
        return query.getResultList();
    }
    
    public List<FraseAnalisada> buscarUltimas(int quantidade) {
        TypedQuery<FraseAnalisada> query = entityManager.createQuery(
            "SELECT f FROM FraseAnalisada f ORDER BY f.dataAnalise DESC", 
            FraseAnalisada.class
        );
        query.setMaxResults(quantidade);
        return query.getResultList();
    }
    
    public void remover(FraseAnalisada fraseAnalisada) {
        entityManager.remove(entityManager.merge(fraseAnalisada));
    }
    
    public void removerPorId(Long id) {
        FraseAnalisada fraseAnalisada = buscarPorId(id);
        if (fraseAnalisada != null) {
            entityManager.remove(fraseAnalisada);
        }
    }
    
    public long contarTotal() {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(f) FROM FraseAnalisada f", 
            Long.class
        );
        return query.getSingleResult();
    }
} 