package com.API.getUser.models.commit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitsRepository extends JpaRepository<Commits, Long> {
}
