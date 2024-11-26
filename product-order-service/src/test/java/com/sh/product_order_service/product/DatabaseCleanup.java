package com.sh.product_order_service.product;

import com.google.common.base.CaseFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * 설명 :
 * - REST-assured의 테스트 격리 이슈로 인한 DB 초기화 클래스
 *
 * 동작 :
 * - 모든 Entity의 Table Name을 추출하여 데이터 초기화.
 * - ID 컬럼의 Sequence를 1부터 시작하도록 설정.
 *
 * 필요 라이브러리 :
 * Google Guava - CaseFormat 포함
 * https://mvnrepository.com/artifact/com.google.guava/guava/31.1-jre
 *
 * 예시 의존성 (Gradle) :
 * implementation 'com.google.guava:guava:31.1-jre'
 */

@Component
public class DatabaseCleanup implements InitializingBean {
    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    /*
     * 동작 순서 :
     * 1. JPA 매니저가 Entity Stream을 가져옴.
     *
     * 2. tableNames :
     *  - @Table이 붙은 @Entity들의 @Table name 속성 추출.
     *  - name 속성이 비어 있는 경우, Entity Name을 [카멜 케이스 -> 스네이크 케이스]로 변환하여 추가.
     *
     * 3. entityNames :
     *  - @Table이 없는 Entity Name도 [카멜 케이스 -> 스네이크 케이스]로 변환하여 추가.
     *
     * 4. entityNames를 tableNames에 추가하여 최종 tableNames 완성.
     */
    @Override
    public void afterPropertiesSet() {
        final Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        tableNames = entities.stream()
                .filter(e -> isEntity(e) && hasTableAnnotation(e))
                .map(e -> {
                    String tableName = e.getJavaType().getAnnotation(Table.class).name();
                    return tableName.isBlank() ? CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()) : tableName;
                })
                .collect(Collectors.toList());

        final List<String> entityNames = entities.stream()
                .filter(e -> isEntity(e) && !hasTableAnnotation(e))
                .map(e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()))
                .toList();

        tableNames.addAll(entityNames);
    }

    private boolean isEntity(final EntityType<?> e) {
        return null != e.getJavaType().getAnnotation(Entity.class);
    }

    private boolean hasTableAnnotation(final EntityType<?> e) {
        return null != e.getJavaType().getAnnotation(Table.class);
    }

    /*
     * 동작 순서 :
     * 1. 참조 무결성(REFERENTIAL_INTEGRITY)을 비활성화하여 Table간 제약 조건 해제.
     *
     * 2. 각 Table의 데이터를 TRUNCATE로 비우고, ID 컬럼의 SEQUENCE를 1로 초기화.
     *
     * 3. 참조 무결성을 다시 활성화.
     */
    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (final String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN ID RESTART WITH 1").executeUpdate();
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
