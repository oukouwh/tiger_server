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
    default Optional<Quotationitem> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Quotationitem> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Quotationitem> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct quotationitem from Quotationitem quotationitem left join fetch quotationitem.quotation",
        countQuery = "select count(distinct quotationitem) from Quotationitem quotationitem"
    )
    Page<Quotationitem> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct quotationitem from Quotationitem quotationitem left join fetch quotationitem.quotation")
    List<Quotationitem> findAllWithToOneRelationships();

    @Query("select quotationitem from Quotationitem quotationitem left join fetch quotationitem.quotation where quotationitem.id =:id")
    Optional<Quotationitem> findOneWithToOneRelationships(@Param("id") Long id);
}
