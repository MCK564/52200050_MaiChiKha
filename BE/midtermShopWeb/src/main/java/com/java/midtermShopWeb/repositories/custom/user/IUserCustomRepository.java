package com.java.midtermShopWeb.repositories.custom.user;

import com.java.midtermShopWeb.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Map;

public class IUserCustomRepository implements UserCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findByCustomCondition(Map<String, Object> conditions) {
        StringBuilder jpql = new StringBuilder("SELECT u FROM User u WHERE ");

        for (String key : conditions.keySet()) {
            jpql.append("u.").append(key).append(" = :").append(key).append(" AND ");
        }

        jpql.delete(jpql.length() - 5, jpql.length()); // Xóa AND cuối cùng
        Query query = entityManager.createQuery(jpql.toString(), User.class);

        for (Map.Entry<String, Object> entry : conditions.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }
}
