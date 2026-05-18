package dio.budgeting.infrastructure.persistence.repository;

import dio.budgeting.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);
}
