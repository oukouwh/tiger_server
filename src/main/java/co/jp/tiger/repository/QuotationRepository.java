package co.jp.tiger.repository;

import co.jp.tiger.domain.Quotation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Quotation entity.
 */
@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {

    /**
     * 自定义查询
     * @param id
     * @return
     */
    @Query(value = "SELECT q1.id,q1.quotationNo,q1.quotationName, q1.quotationDate,q1.workStart,q1.workEnd,\n" +
        "q1.deliveryItems, q1.deliveryDate, q1.paymentsTerms,q1.payFlag,q1.quotationExpirationDate,\n" +
        "q1.totalAmount, q1.customerCharge, q1.accuracy, q1.mailSendDate, q1.postSendDate, q1.sendFlag,\n" +
        "q1.salesStaff, q1.salesOffice, q1.updateCount, q2.quotationNo, q2.quotationItemNo,\n" +
        "q2.workerName, q2.standardPrice, q2.count, q2.upperLimitHour, q2.lowerLimitHour,\n" +
        "q2.additionalPrice, q2.deductionPrice, q2.memo, q2.updateCount " +
        "FROM Quotation q1, Quotationitem q2 WHERE q1.id = q2.id AND q1.id = ?1")
    Optional<Quotation> findAllById(Long id);
}
