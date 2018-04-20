package com.kamal.khidma.repository;

import com.kamal.khidma.domain.Enseignant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Enseignant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    @Query("select distinct enseignant from Enseignant enseignant left join fetch enseignant.eleves")
    List<Enseignant> findAllWithEagerRelationships();

    @Query("select enseignant from Enseignant enseignant left join fetch enseignant.eleves where enseignant.id =:id")
    Enseignant findOneWithEagerRelationships(@Param("id") Long id);

}
