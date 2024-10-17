package be.kdg.be.programming3.sortify_webapp.repository;

import be.kdg.be.programming3.sortify_webapp.domain.Trash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrashRepository extends JpaRepository<Trash, Integer> {
    // No need for custom methods here; JPA provides built-in methods.
}

