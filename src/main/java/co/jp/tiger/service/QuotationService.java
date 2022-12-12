package co.jp.tiger.service;

import co.jp.tiger.domain.Quotation;
import co.jp.tiger.repository.QuotationRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Quotation}.
 */
@Service
@Transactional
public class QuotationService {

    private final Logger log = LoggerFactory.getLogger(QuotationService.class);

    private final QuotationRepository quotationRepository;

    public QuotationService(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;
    }

    /**
     * Save a quotation.
     *
     * @param quotation the entity to save.
     * @return the persisted entity.
     */
    public Quotation save(Quotation quotation) {
        log.debug("Request to save Quotation : {}", quotation);
        return quotationRepository.save(quotation);
    }

    /**
     * Update a quotation.
     *
     * @param quotation the entity to save.
     * @return the persisted entity.
     */
//    public Quotation update(Quotation quotation) {
//        log.debug("Request to update Quotation : {}", quotation);
//        return quotationRepository.save(quotation);
//    }

    /**
     * Partially update a quotation.
     *
     * @param quotation the entity to update partially.
     * @return the persisted entity.
     */
//    public Optional<Quotation> partialUpdate(Quotation quotation) {
//        log.debug("Request to partially update Quotation : {}", quotation);
//
//        return quotationRepository
//            .findById(quotation.getId())
//            .map(existingQuotation -> {
//                if (quotation.getQuotationNo() != null) {
//                    existingQuotation.setQuotationNo(quotation.getQuotationNo());
//                }
//                if (quotation.getQuotationName() != null) {
//                    existingQuotation.setQuotationName(quotation.getQuotationName());
//                }
//                if (quotation.getQuotationDate() != null) {
//                    existingQuotation.setQuotationDate(quotation.getQuotationDate());
//                }
//                if (quotation.getWorkStart() != null) {
//                    existingQuotation.setWorkStart(quotation.getWorkStart());
//                }
//                if (quotation.getWorkEnd() != null) {
//                    existingQuotation.setWorkEnd(quotation.getWorkEnd());
//                }
//                if (quotation.getDeliveryItems() != null) {
//                    existingQuotation.setDeliveryItems(quotation.getDeliveryItems());
//                }
//                if (quotation.getDeliveryDate() != null) {
//                    existingQuotation.setDeliveryDate(quotation.getDeliveryDate());
//                }
//                if (quotation.getAcceptanceDate() != null) {
//                    existingQuotation.setAcceptanceDate(quotation.getAcceptanceDate());
//                }
//                if (quotation.getPaymentsTerms() != null) {
//                    existingQuotation.setPaymentsTerms(quotation.getPaymentsTerms());
//                }
//                if (quotation.getPayFlag() != null) {
//                    existingQuotation.setPayFlag(quotation.getPayFlag());
//                }
//                if (quotation.getQuotationExpirationDate() != null) {
//                    existingQuotation.setQuotationExpirationDate(quotation.getQuotationExpirationDate());
//                }
//                if (quotation.getTotalAmount() != null) {
//                    existingQuotation.setTotalAmount(quotation.getTotalAmount());
//                }
//                if (quotation.getCustomerCharge() != null) {
//                    existingQuotation.setCustomerCharge(quotation.getCustomerCharge());
//                }
//                if (quotation.getAccuracy() != null) {
//                    existingQuotation.setAccuracy(quotation.getAccuracy());
//                }
//                if (quotation.getMailSendDate() != null) {
//                    existingQuotation.setMailSendDate(quotation.getMailSendDate());
//                }
//                if (quotation.getPostSendDate() != null) {
//                    existingQuotation.setPostSendDate(quotation.getPostSendDate());
//                }
//                if (quotation.getSendFlag() != null) {
//                    existingQuotation.setSendFlag(quotation.getSendFlag());
//                }
//                if (quotation.getSalesStaff() != null) {
//                    existingQuotation.setSalesStaff(quotation.getSalesStaff());
//                }
//                if (quotation.getSalesOffice() != null) {
//                    existingQuotation.setSalesOffice(quotation.getSalesOffice());
//                }
//                if (quotation.getUpdateCount() != null) {
//                    existingQuotation.setUpdateCount(quotation.getUpdateCount());
//                }
//
//                return existingQuotation;
//            })
//            .map(quotationRepository::save);
//    }

    /**
     * Get all the quotations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Quotation> findAll(Pageable pageable) {
        log.debug("Request to get all Quotations");
        return quotationRepository.findAll(pageable);
    }

    /**
     * Get one quotation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Quotation> findOne(Long id) {
        log.debug("Request to get Quotation : {}", id);
        return quotationRepository.findById(id);
    }

    /**
     * Delete the quotation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Quotation : {}", id);
        quotationRepository.deleteById(id);
    }
}
