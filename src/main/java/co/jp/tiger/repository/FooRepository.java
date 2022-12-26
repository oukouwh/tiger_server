package co.jp.tiger.repository;

import co.jp.tiger.domain.Foo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Foo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FooRepository extends JpaRepository<Foo, Long> {}
