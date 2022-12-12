package co.jp.tiger.web.rest;

import co.jp.tiger.domain.Quotation;
import co.jp.tiger.repository.QuotationRepository;
import co.jp.tiger.service.QuotationService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.jp.tiger.domain.Quotation}.
 */
@RestController
@RequestMapping("/api")
public class QuotationResource {

    private final Logger log = LoggerFactory.getLogger(QuotationResource.class);

    private static final String ENTITY_NAME = "quotation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuotationService quotationService;

    private final QuotationRepository quotationRepository;

    public QuotationResource(QuotationService quotationService, QuotationRepository quotationRepository) {
        this.quotationService = quotationService;
        this.quotationRepository = quotationRepository;
    }

    /**
     * {@code POST  /quotations} : Create a new quotation.
     *
     * @param quotation the quotation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quotation, or with status {@code 400 (Bad Request)} if the quotation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quotations")
    public ResponseEntity<Quotation> createQuotation(@Valid @RequestBody Quotation quotation) throws URISyntaxException {
        log.debug("REST request to save Quotation : {}", quotation);
        if (quotation.getId() != null) {
            throw new BadRequestAlertException("A new quotation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Quotation result = quotationService.save(quotation);
        return ResponseEntity
            .created(new URI("/api/quotations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quotations/:id} : Updates an existing quotation.
     *
     * @param id the id of the quotation to save.
     * @param quotation the quotation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotation,
     * or with status {@code 400 (Bad Request)} if the quotation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quotation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quotations/{id}")
    public ResponseEntity<Quotation> updateQuotation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Quotation quotation
    ) throws URISyntaxException {
        log.debug("REST request to update Quotation : {}, {}", id, quotation);
        if (quotation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quotation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quotationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Quotation result = quotationService.update(quotation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quotation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /quotations/:id} : Partial updates given fields of an existing quotation, field will ignore if it is null
     *
     * @param id the id of the quotation to save.
     * @param quotation the quotation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotation,
     * or with status {@code 400 (Bad Request)} if the quotation is not valid,
     * or with status {@code 404 (Not Found)} if the quotation is not found,
     * or with status {@code 500 (Internal Server Error)} if the quotation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/quotations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Quotation> partialUpdateQuotation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Quotation quotation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Quotation partially : {}, {}", id, quotation);
        if (quotation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quotation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quotationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Quotation> result = quotationService.partialUpdate(quotation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quotation.getId().toString())
        );
    }

    /**
     * {@code GET  /quotations} : get all the quotations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quotations in body.
     */
    @GetMapping("/quotations")
    public ResponseEntity<List<Quotation>> getAllQuotations(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Quotations");
        Page<Quotation> page = quotationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quotations/:id} : get the "id" quotation.
     *
     * @param id the id of the quotation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quotation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quotations/{id}")
    public ResponseEntity<Quotation> getQuotation(@PathVariable Long id) {
        log.debug("REST request to get Quotation : {}", id);
        Optional<Quotation> quotation = quotationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quotation);
    }

    /**
     * {@code DELETE  /quotations/:id} : delete the "id" quotation.
     *
     * @param id the id of the quotation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quotations/{id}")
    public ResponseEntity<Void> deleteQuotation(@PathVariable Long id) {
        log.debug("REST request to delete Quotation : {}", id);
        quotationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
