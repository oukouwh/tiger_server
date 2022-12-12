package co.jp.tiger.web.rest;

import co.jp.tiger.domain.Quotationitem;
import co.jp.tiger.repository.QuotationitemRepository;
import co.jp.tiger.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.jp.tiger.domain.Quotationitem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class QuotationitemResource {

    private final Logger log = LoggerFactory.getLogger(QuotationitemResource.class);

    private static final String ENTITY_NAME = "quotationitem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuotationitemRepository quotationitemRepository;

    public QuotationitemResource(QuotationitemRepository quotationitemRepository) {
        this.quotationitemRepository = quotationitemRepository;
    }

    /**
     * {@code POST  /quotationitems} : Create a new quotationitem.
     *
     * @param quotationitem the quotationitem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quotationitem, or with status {@code 400 (Bad Request)} if the quotationitem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quotationitems")
    public ResponseEntity<Quotationitem> createQuotationitem(@Valid @RequestBody Quotationitem quotationitem) throws URISyntaxException {
        log.debug("REST request to save Quotationitem : {}", quotationitem);
        if (quotationitem.getId() != null) {
            throw new BadRequestAlertException("A new quotationitem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Quotationitem result = quotationitemRepository.save(quotationitem);
        return ResponseEntity.created(new URI("/api/quotationitems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quotationitems} : Updates an existing quotationitem.
     *
     * @param quotationitem the quotationitem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotationitem,
     * or with status {@code 400 (Bad Request)} if the quotationitem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quotationitem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quotationitems")
    public ResponseEntity<Quotationitem> updateQuotationitem(@Valid @RequestBody Quotationitem quotationitem) throws URISyntaxException {
        log.debug("REST request to update Quotationitem : {}", quotationitem);
        if (quotationitem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Quotationitem result = quotationitemRepository.save(quotationitem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quotationitem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /quotationitems} : get all the quotationitems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quotationitems in body.
     */
    @GetMapping("/quotationitems")
    public List<Quotationitem> getAllQuotationitems() {
        log.debug("REST request to get all Quotationitems");
        return quotationitemRepository.findAll();
    }

    /**
     * {@code GET  /quotationitems/:id} : get the "id" quotationitem.
     *
     * @param id the id of the quotationitem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quotationitem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quotationitems/{id}")
    public ResponseEntity<Quotationitem> getQuotationitem(@PathVariable Long id) {
        log.debug("REST request to get Quotationitem : {}", id);
        Optional<Quotationitem> quotationitem = quotationitemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(quotationitem);
    }

    /**
     * {@code DELETE  /quotationitems/:id} : delete the "id" quotationitem.
     *
     * @param id the id of the quotationitem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quotationitems/{id}")
    public ResponseEntity<Void> deleteQuotationitem(@PathVariable Long id) {
        log.debug("REST request to delete Quotationitem : {}", id);
        quotationitemRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
