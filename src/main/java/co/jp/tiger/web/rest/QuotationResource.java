package co.jp.tiger.web.rest;

import co.jp.tiger.domain.Quotation;
import co.jp.tiger.domain.Quotationitem;
import co.jp.tiger.repository.QuotationRepository;
import co.jp.tiger.service.QuotationService;
import co.jp.tiger.web.rest.errors.BadRequestAlertException;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

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

        // Quotation result = quotationService.update(quotation);
        // ADD
        Quotation result = quotationService.save(quotation);
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
//    @PatchMapping(value = "/quotations/{id}", consumes = { "application/json", "application/merge-patch+json" })
//    public ResponseEntity<Quotation> partialUpdateQuotation(
//        @PathVariable(value = "id", required = false) final Long id,
//        @NotNull @RequestBody Quotation quotation
//    ) throws URISyntaxException {
//        log.debug("REST request to partial update Quotation partially : {}, {}", id, quotation);
//        if (quotation.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        if (!Objects.equals(id, quotation.getId())) {
//            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
//        }
//
//        if (!quotationRepository.existsById(id)) {
//            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
//        }
//
//        Optional<Quotation> result = quotationService.partialUpdate(quotation);
//
//        return ResponseUtil.wrapOrNotFound(
//            result,
//            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quotation.getId().toString())
//        );
//    }

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

    /**
     * 自定义查询，获取全部信息
     * @param id
     * @return
     */
    @GetMapping("/quotations/info/{id}")
    public ResponseEntity<Quotation> getQuotationInfo(@PathVariable Long id) {
        log.debug("REST request to get Quotation : {}", id);
        Optional<Quotation> quotation = quotationService.findAllById(id);
        return ResponseUtil.wrapOrNotFound(quotation);
    }

    /**
     *
     * @param quotation
     * @return
     * @throws Exception
     */
    @PostMapping("excel")
    public ResponseEntity<Object> createTestData(@RequestBody Quotation quotation) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = new ClassPathResource("templates/quotationTemplate2.xlsx");
        try (FileInputStream inp = new FileInputStream(resource.getFile())) {
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(2);
            Cell cell = row.getCell(1);
            if (cell == null) {
                cell = row.createCell(1);
            }
            cell.setCellValue(quotation.getCustomerCharge());
            Cell cell1 = row.getCell(10);
            if (cell1 == null) {
                cell1 = row.createCell(10);
            }
            cell1.setCellValue(quotation.getQuotationNo());

            Row row3 = sheet.getRow(3);
            Cell cell2 = row3.getCell(10);
            if (cell2 == null) {
                cell2 = row3.createCell(10);
            }
            cell2.setCellValue(quotation.getQuotationDate());

            Row row8 = sheet.getRow(8);
            Cell cell3 = row8.getCell(3);
            if (cell3 == null) {
                cell3 = row8.createCell(3);
            }
            cell3.setCellValue(quotation.getQuotationName());

            Row row9 = sheet.getRow(9);
            Cell cell4 = row9.getCell(3);
            if (cell4 == null) {
                cell4 = row9.createCell(3);
            }
            String wordStart = quotation.getWorkStart().toString();
            String year = wordStart.substring(0, 4);
            String month = wordStart.substring(5, 7);
            String day = wordStart.substring(8, 10);
            String start = year + "年" + month + "月" + day + "日";

            String wordEnd = quotation.getWorkEnd().toString();
            String year1 = wordEnd.substring(0, 4);
            String month1 = wordEnd.substring(5, 7);
            String day1 = wordEnd.substring(8, 10);
            String end = year1 + "年" + month1 + "月" + day1 + "日";
            cell4.setCellValue(start + "～" + end);

            Row row10 = sheet.getRow(10);
            Cell cell5 = row10.getCell(3);
            if (cell5 == null) {
                cell5 = row10.createCell(3);
            }
            cell5.setCellValue(quotation.getDeliveryItems());

            Row row11 = sheet.getRow(11);
            Cell cell6 = row11.getCell(3);
            if (cell6 == null) {
                cell6 = row11.createCell(3);
            }
            cell6.setCellValue(quotation.getDeliveryDate());

            Row row12 = sheet.getRow(12);
            Cell cell7 = row12.getCell(3);
            if (cell7 == null) {
                cell7 = row12.createCell(3);
            }
            cell7.setCellValue(quotation.getDeliveryDate());

            Row row13 = sheet.getRow(13);
            Cell cell8 = row13.getCell(3);
            if (cell8 == null) {
                cell8 = row13.createCell(3);
            }
            String payments = "";
            if ("A".equals(quotation.getPaymentsTerms())) {
                payments = "翌月末払い";
            }
            if ("B".equals(quotation.getPaymentsTerms())) {
                payments = "翌々月10日払い";
            }
            if ("C".equals(quotation.getPaymentsTerms())) {
                payments = "翌々月15日払い";
            }
            if ("D".equals(quotation.getPaymentsTerms())) {
                payments = "翌々月20日払い";
            }
            if ("E".equals(quotation.getPaymentsTerms())) {
                payments = "翌々月末払い";
            }
            cell8.setCellValue(payments);

            Row row14 = sheet.getRow(14);
            Cell cell9 = row14.getCell(3);
            if (cell9 == null) {
                cell9 = row14.createCell(3);
            }
            cell9.setCellValue(quotation.getQuotationExpirationDate());

            Row row16 = sheet.getRow(16);
            Cell cell10 = row16.getCell(3);
            if (cell10 == null) {
                cell10 = row16.createCell(3);
            }
            cell10.setCellValue(quotation.getTotalAmount().toString());

            int index = 0;

            Set<Quotationitem> items = quotation.getQuotationItems();
            System.out.print(items.size() + "***************************************************");
            Long quotationItemNo = null;
            String workerName = null;
            BigDecimal standardPrice = null;
            BigDecimal count = null;
            BigDecimal additionalPrice = null;
            BigDecimal deductionPrice = null;
            BigDecimal price = null;
            String memo = null;
            for (Quotationitem obj : items) {
                Row row19 = sheet.getRow(19 + index);
                Cell cell11 = row19.getCell(1);
                Cell cell12 = row19.getCell(2);
                Cell cell13 = row19.getCell(4);

                Cell cell18 = row19.getCell(5);
                Cell cell14 = row19.getCell(6);
                Cell cell19 = row19.getCell(7);
                Cell cell15 = row19.getCell(8);

                Cell cell16 = row19.getCell(9);
                Cell cell17 = row19.getCell(10);
                if (cell11 == null) {
                    cell11 = row19.createCell(1);
                }
                if (cell12 == null) {
                    cell12 = row19.createCell(2);
                }
                if (cell13 == null) {
                    cell13 = row19.createCell(4);
                }
                if (cell18 == null) {
                    cell18 = row19.createCell(5);
                }
                if (cell14 == null) {
                    cell14 = row19.createCell(6);
                }
                if (cell15 == null) {
                    cell15 = row19.createCell(8);
                }
                if (cell16 == null) {
                    cell16 = row19.createCell(9);
                }
                if (cell17 == null) {
                    cell17 = row19.createCell(10);
                }
                if (cell19 == null) {
                    cell19 = row19.createCell(7);
                }
                quotationItemNo = obj.getQuotationItemNo();
                workerName = obj.getWorkerName();
                standardPrice = obj.getStandardPrice();
                 count = obj.getCount();
                // ADD
//                count = BigDecimal.valueOf(obj.getCount());
                additionalPrice = obj.getAdditionalPrice();
                deductionPrice = obj.getDeductionPrice();
                memo = obj.getMemo();
                price = count.multiply(standardPrice);
                // cell11.setCellValue(quotationItemNo);
                cell12.setCellValue(workerName);
                cell13.setCellValue(count.toString());
                cell14.setCellValue(standardPrice.toString());
                cell15.setCellValue(additionalPrice.toString());
                cell16.setCellValue(deductionPrice.toString());
                cell17.setCellValue(memo);
                cell18.setCellValue("月");
                cell19.setCellValue(price.toString());
                index++;
            }

            try (FileOutputStream fileOut = new FileOutputStream("workbook.xlsx")) {
                wb.write(fileOut);
            }
        }

        return new ResponseEntity<>(objectMapper.readValue("{\"result\":\"Success\"}", Object.class), HttpStatus.OK);
    }

    @PostMapping("download-excel")
    public ResponseEntity<?> createTestData2(@RequestBody Quotation quotation) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = new ClassPathResource("templates/quotationTemplate2.xlsx");
        try (FileInputStream inp = new FileInputStream(resource.getFile())) {
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(2);
            Cell cell = row.getCell(1);
            if (cell == null) {
                cell = row.createCell(1);
            }
            cell.setCellValue(quotation.getCustomerCharge());
            Cell cell1 = row.getCell(10);
            if (cell1 == null) {
                cell1 = row.createCell(10);
            }
            cell1.setCellValue(quotation.getQuotationNo());

            Row row3 = sheet.getRow(3);
            Cell cell2 = row3.getCell(10);
            if (cell2 == null) {
                cell2 = row3.createCell(10);
            }
            cell2.setCellValue(quotation.getQuotationDate());

            Row row8 = sheet.getRow(8);
            Cell cell3 = row8.getCell(3);
            if (cell3 == null) {
                cell3 = row8.createCell(3);
            }
            cell3.setCellValue(quotation.getQuotationName());

            Row row9 = sheet.getRow(9);
            Cell cell4 = row9.getCell(3);
            if (cell4 == null) {
                cell4 = row9.createCell(3);
            }
            String wordStart = quotation.getWorkStart().toString();
            String year = wordStart.substring(0, 4);
            String month = wordStart.substring(5, 7);
            String day = wordStart.substring(8, 10);
            String start = year + "年" + month + "月" + day + "日";

            String wordEnd = quotation.getWorkEnd().toString();
            String year1 = wordEnd.substring(0, 4);
            String month1 = wordEnd.substring(5, 7);
            String day1 = wordEnd.substring(8, 10);
            String end = year1 + "年" + month1 + "月" + day1 + "日";
            cell4.setCellValue(start + "～" + end);

            Row row10 = sheet.getRow(10);
            Cell cell5 = row10.getCell(3);
            if (cell5 == null) {
                cell5 = row10.createCell(3);
            }
            cell5.setCellValue(quotation.getDeliveryItems());

            Row row11 = sheet.getRow(11);
            Cell cell6 = row11.getCell(3);
            if (cell6 == null) {
                cell6 = row11.createCell(3);
            }
            cell6.setCellValue(quotation.getDeliveryDate());

            Row row12 = sheet.getRow(12);
            Cell cell7 = row12.getCell(3);
            if (cell7 == null) {
                cell7 = row12.createCell(3);
            }
            cell7.setCellValue(quotation.getDeliveryDate());

            Row row13 = sheet.getRow(13);
            Cell cell8 = row13.getCell(3);
            if (cell8 == null) {
                cell8 = row13.createCell(3);
            }
            String payments = "";
            if ("A".equals(quotation.getPaymentsTerms())) {
                payments = "翌月末払い";
            }
            if ("B".equals(quotation.getPaymentsTerms())) {
                payments = "翌々月10日払い";
            }
            if ("C".equals(quotation.getPaymentsTerms())) {
                payments = "翌々月15日払い";
            }
            if ("D".equals(quotation.getPaymentsTerms())) {
                payments = "翌々月20日払い";
            }
            if ("E".equals(quotation.getPaymentsTerms())) {
                payments = "翌々月末払い";
            }
            cell8.setCellValue(payments);

            Row row14 = sheet.getRow(14);
            Cell cell9 = row14.getCell(3);
            if (cell9 == null) {
                cell9 = row14.createCell(3);
            }
            cell9.setCellValue(quotation.getQuotationExpirationDate());

            Row row16 = sheet.getRow(16);
            Cell cell10 = row16.getCell(3);
            if (cell10 == null) {
                cell10 = row16.createCell(3);
            }
            cell10.setCellValue(quotation.getTotalAmount().toString());

            int index = 0;

            Set<Quotationitem> items = quotation.getQuotationItems();
            System.out.print(items.size() + "***************************************************");
            Long quotationItemNo = null;
            String workerName = null;
            BigDecimal standardPrice = null;
            BigDecimal count = null;
            BigDecimal additionalPrice = null;
            BigDecimal deductionPrice = null;
            BigDecimal price = null;
            String memo = null;
            for (Quotationitem obj : items) {
                Row row19 = sheet.getRow(19 + index);
                Cell cell11 = row19.getCell(1);
                Cell cell12 = row19.getCell(2);
                Cell cell13 = row19.getCell(4);

                Cell cell18 = row19.getCell(5);
                Cell cell14 = row19.getCell(6);
                Cell cell19 = row19.getCell(7);
                Cell cell15 = row19.getCell(8);

                Cell cell16 = row19.getCell(9);
                Cell cell17 = row19.getCell(10);
                if (cell11 == null) {
                    cell11 = row19.createCell(1);
                }
                if (cell12 == null) {
                    cell12 = row19.createCell(2);
                }
                if (cell13 == null) {
                    cell13 = row19.createCell(4);
                }
                if (cell18 == null) {
                    cell18 = row19.createCell(5);
                }
                if (cell14 == null) {
                    cell14 = row19.createCell(6);
                }
                if (cell15 == null) {
                    cell15 = row19.createCell(8);
                }
                if (cell16 == null) {
                    cell16 = row19.createCell(9);
                }
                if (cell17 == null) {
                    cell17 = row19.createCell(10);
                }
                if (cell19 == null) {
                    cell19 = row19.createCell(7);
                }
                quotationItemNo = obj.getQuotationItemNo();
                workerName = obj.getWorkerName();
                standardPrice = obj.getStandardPrice();
                 count = obj.getCount();
                // ADD
//                count = BigDecimal.valueOf(obj.getCount());
                additionalPrice = obj.getAdditionalPrice();
                deductionPrice = obj.getDeductionPrice();
                memo = obj.getMemo();
                price = count.multiply(standardPrice);
                // cell11.setCellValue(quotationItemNo);
                cell12.setCellValue(workerName);
                cell13.setCellValue(count.toString());
                cell14.setCellValue(standardPrice.toString());
                cell15.setCellValue(additionalPrice.toString());
                cell16.setCellValue(deductionPrice.toString());
                cell17.setCellValue(memo);
                cell18.setCellValue("月");
                cell19.setCellValue(price.toString());
                index++;
            }
            wb.setForceFormulaRecalculation(true);

            InputStreamResource resource1;
            HttpHeaders headers;
            try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {

                wb.write(os);
                // InputStream is = new ByteArrayInputStream(os.toByteArray());
                // resource1 = new InputStreamResource(is);
                headers = new HttpHeaders();
                headers.setContentDispositionFormData("attachment", "1workbook.xls");
                headers.setContentType(
                    MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
                return new ResponseEntity<byte[]>(os.toByteArray(), headers, HttpStatus.OK);
            }
        }

        // return new
        // ResponseEntity<>(objectMapper.readValue("{\"result\":\"Success\"}",
        // Object.class), HttpStatus.OK);

    }

}
