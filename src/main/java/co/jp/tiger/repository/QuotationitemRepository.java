package co.jp.tiger.repository;

import co.jp.tiger.domain.Quotationitem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Quotationitem entity.
 */
@Repository
public interface QuotationitemRepository extends JpaRepository<Quotationitem, Long> {
}
