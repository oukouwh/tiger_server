package co.jp.tiger.web.rest;

import static co.jp.tiger.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.jp.tiger.IntegrationTest;
import co.jp.tiger.domain.Quotationitem;
import co.jp.tiger.repository.QuotationitemRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link QuotationitemResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class QuotationitemResourceIT {

    private static final String DEFAULT_QUOTATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_QUOTATION_NO = "BBBBBBBBBB";

    private static final Long DEFAULT_QUOTATION_ITEM_NO = 1L;
    private static final Long UPDATED_QUOTATION_ITEM_NO = 2L;

    private static final String DEFAULT_WORKER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WORKER_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_STANDARD_PRICE = new BigDecimal(999999999);
    private static final BigDecimal UPDATED_STANDARD_PRICE = new BigDecimal(999999998);

    private static final Long DEFAULT_COUNT = 1L;
    private static final Long UPDATED_COUNT = 2L;

    private static final BigDecimal DEFAULT_UPPER_LIMIT_HOUR = new BigDecimal(999);
    private static final BigDecimal UPDATED_UPPER_LIMIT_HOUR = new BigDecimal(998);

    private static final BigDecimal DEFAULT_LOWER_LIMIT_HOUR = new BigDecimal(999);
    private static final BigDecimal UPDATED_LOWER_LIMIT_HOUR = new BigDecimal(998);

    private static final BigDecimal DEFAULT_ADDITIONAL_PRICE = new BigDecimal(999999999);
    private static final BigDecimal UPDATED_ADDITIONAL_PRICE = new BigDecimal(999999998);

    private static final BigDecimal DEFAULT_DEDUCTION_PRICE = new BigDecimal(999999999);
    private static final BigDecimal UPDATED_DEDUCTION_PRICE = new BigDecimal(999999998);

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final Long DEFAULT_UPDATE_COUNT = 1L;
    private static final Long UPDATED_UPDATE_COUNT = 2L;

    private static final String ENTITY_API_URL = "/api/quotationitems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuotationitemRepository quotationitemRepository;

    @Mock
    private QuotationitemRepository quotationitemRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuotationitemMockMvc;

    private Quotationitem quotationitem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quotationitem createEntity(EntityManager em) {
        Quotationitem quotationitem = new Quotationitem()
            .quotationNo(DEFAULT_QUOTATION_NO)
            .quotationItemNo(DEFAULT_QUOTATION_ITEM_NO)
            .workerName(DEFAULT_WORKER_NAME)
            .standardPrice(DEFAULT_STANDARD_PRICE)
            .count(DEFAULT_COUNT)
            .upperLimitHour(DEFAULT_UPPER_LIMIT_HOUR)
            .lowerLimitHour(DEFAULT_LOWER_LIMIT_HOUR)
            .additionalPrice(DEFAULT_ADDITIONAL_PRICE)
            .deductionPrice(DEFAULT_DEDUCTION_PRICE)
            .memo(DEFAULT_MEMO)
            .updateCount(DEFAULT_UPDATE_COUNT);
        return quotationitem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quotationitem createUpdatedEntity(EntityManager em) {
        Quotationitem quotationitem = new Quotationitem()
            .quotationNo(UPDATED_QUOTATION_NO)
            .quotationItemNo(UPDATED_QUOTATION_ITEM_NO)
            .workerName(UPDATED_WORKER_NAME)
            .standardPrice(UPDATED_STANDARD_PRICE)
            .count(UPDATED_COUNT)
            .upperLimitHour(UPDATED_UPPER_LIMIT_HOUR)
            .lowerLimitHour(UPDATED_LOWER_LIMIT_HOUR)
            .additionalPrice(UPDATED_ADDITIONAL_PRICE)
            .deductionPrice(UPDATED_DEDUCTION_PRICE)
            .memo(UPDATED_MEMO)
            .updateCount(UPDATED_UPDATE_COUNT);
        return quotationitem;
    }

    @BeforeEach
    public void initTest() {
        quotationitem = createEntity(em);
    }

    @Test
    @Transactional
    void createQuotationitem() throws Exception {
        int databaseSizeBeforeCreate = quotationitemRepository.findAll().size();
        // Create the Quotationitem
        restQuotationitemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotationitem)))
            .andExpect(status().isCreated());

        // Validate the Quotationitem in the database
        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeCreate + 1);
        Quotationitem testQuotationitem = quotationitemList.get(quotationitemList.size() - 1);
        assertThat(testQuotationitem.getQuotationNo()).isEqualTo(DEFAULT_QUOTATION_NO);
        assertThat(testQuotationitem.getQuotationItemNo()).isEqualTo(DEFAULT_QUOTATION_ITEM_NO);
        assertThat(testQuotationitem.getWorkerName()).isEqualTo(DEFAULT_WORKER_NAME);
        assertThat(testQuotationitem.getStandardPrice()).isEqualByComparingTo(DEFAULT_STANDARD_PRICE);
        assertThat(testQuotationitem.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testQuotationitem.getUpperLimitHour()).isEqualByComparingTo(DEFAULT_UPPER_LIMIT_HOUR);
        assertThat(testQuotationitem.getLowerLimitHour()).isEqualByComparingTo(DEFAULT_LOWER_LIMIT_HOUR);
        assertThat(testQuotationitem.getAdditionalPrice()).isEqualByComparingTo(DEFAULT_ADDITIONAL_PRICE);
        assertThat(testQuotationitem.getDeductionPrice()).isEqualByComparingTo(DEFAULT_DEDUCTION_PRICE);
        assertThat(testQuotationitem.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testQuotationitem.getUpdateCount()).isEqualTo(DEFAULT_UPDATE_COUNT);
    }

    @Test
    @Transactional
    void createQuotationitemWithExistingId() throws Exception {
        // Create the Quotationitem with an existing ID
        quotationitem.setId(1L);

        int databaseSizeBeforeCreate = quotationitemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotationitemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotationitem)))
            .andExpect(status().isBadRequest());

        // Validate the Quotationitem in the database
        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuotationNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationitemRepository.findAll().size();
        // set the field null
        quotationitem.setQuotationNo(null);

        // Create the Quotationitem, which fails.

        restQuotationitemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotationitem)))
            .andExpect(status().isBadRequest());

        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuotationItemNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationitemRepository.findAll().size();
        // set the field null
        quotationitem.setQuotationItemNo(null);

        // Create the Quotationitem, which fails.

        restQuotationitemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotationitem)))
            .andExpect(status().isBadRequest());

        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWorkerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationitemRepository.findAll().size();
        // set the field null
        quotationitem.setWorkerName(null);

        // Create the Quotationitem, which fails.

        restQuotationitemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotationitem)))
            .andExpect(status().isBadRequest());

        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStandardPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationitemRepository.findAll().size();
        // set the field null
        quotationitem.setStandardPrice(null);

        // Create the Quotationitem, which fails.

        restQuotationitemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotationitem)))
            .andExpect(status().isBadRequest());

        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationitemRepository.findAll().size();
        // set the field null
        quotationitem.setCount(null);

        // Create the Quotationitem, which fails.

        restQuotationitemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotationitem)))
            .andExpect(status().isBadRequest());

        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpperLimitHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationitemRepository.findAll().size();
        // set the field null
        quotationitem.setUpperLimitHour(null);

        // Create the Quotationitem, which fails.

        restQuotationitemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotationitem)))
            .andExpect(status().isBadRequest());

        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLowerLimitHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationitemRepository.findAll().size();
        // set the field null
        quotationitem.setLowerLimitHour(null);

        // Create the Quotationitem, which fails.

        restQuotationitemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotationitem)))
            .andExpect(status().isBadRequest());

        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdditionalPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationitemRepository.findAll().size();
        // set the field null
        quotationitem.setAdditionalPrice(null);

        // Create the Quotationitem, which fails.

        restQuotationitemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotationitem)))
            .andExpect(status().isBadRequest());

        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMemoIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationitemRepository.findAll().size();
        // set the field null
        quotationitem.setMemo(null);

        // Create the Quotationitem, which fails.

        restQuotationitemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotationitem)))
            .andExpect(status().isBadRequest());

        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllQuotationitems() throws Exception {
        // Initialize the database
        quotationitemRepository.saveAndFlush(quotationitem);

        // Get all the quotationitemList
        restQuotationitemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotationitem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotationNo").value(hasItem(DEFAULT_QUOTATION_NO)))
            .andExpect(jsonPath("$.[*].quotationItemNo").value(hasItem(DEFAULT_QUOTATION_ITEM_NO.intValue())))
            .andExpect(jsonPath("$.[*].workerName").value(hasItem(DEFAULT_WORKER_NAME)))
            .andExpect(jsonPath("$.[*].standardPrice").value(hasItem(sameNumber(DEFAULT_STANDARD_PRICE))))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].upperLimitHour").value(hasItem(sameNumber(DEFAULT_UPPER_LIMIT_HOUR))))
            .andExpect(jsonPath("$.[*].lowerLimitHour").value(hasItem(sameNumber(DEFAULT_LOWER_LIMIT_HOUR))))
            .andExpect(jsonPath("$.[*].additionalPrice").value(hasItem(sameNumber(DEFAULT_ADDITIONAL_PRICE))))
            .andExpect(jsonPath("$.[*].deductionPrice").value(hasItem(sameNumber(DEFAULT_DEDUCTION_PRICE))))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].updateCount").value(hasItem(DEFAULT_UPDATE_COUNT.intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllQuotationitemsWithEagerRelationshipsIsEnabled() throws Exception {
        when(quotationitemRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restQuotationitemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(quotationitemRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllQuotationitemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(quotationitemRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restQuotationitemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(quotationitemRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getQuotationitem() throws Exception {
        // Initialize the database
        quotationitemRepository.saveAndFlush(quotationitem);

        // Get the quotationitem
        restQuotationitemMockMvc
            .perform(get(ENTITY_API_URL_ID, quotationitem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quotationitem.getId().intValue()))
            .andExpect(jsonPath("$.quotationNo").value(DEFAULT_QUOTATION_NO))
            .andExpect(jsonPath("$.quotationItemNo").value(DEFAULT_QUOTATION_ITEM_NO.intValue()))
            .andExpect(jsonPath("$.workerName").value(DEFAULT_WORKER_NAME))
            .andExpect(jsonPath("$.standardPrice").value(sameNumber(DEFAULT_STANDARD_PRICE)))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT.intValue()))
            .andExpect(jsonPath("$.upperLimitHour").value(sameNumber(DEFAULT_UPPER_LIMIT_HOUR)))
            .andExpect(jsonPath("$.lowerLimitHour").value(sameNumber(DEFAULT_LOWER_LIMIT_HOUR)))
            .andExpect(jsonPath("$.additionalPrice").value(sameNumber(DEFAULT_ADDITIONAL_PRICE)))
            .andExpect(jsonPath("$.deductionPrice").value(sameNumber(DEFAULT_DEDUCTION_PRICE)))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO))
            .andExpect(jsonPath("$.updateCount").value(DEFAULT_UPDATE_COUNT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingQuotationitem() throws Exception {
        // Get the quotationitem
        restQuotationitemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQuotationitem() throws Exception {
        // Initialize the database
        quotationitemRepository.saveAndFlush(quotationitem);

        int databaseSizeBeforeUpdate = quotationitemRepository.findAll().size();

        // Update the quotationitem
        Quotationitem updatedQuotationitem = quotationitemRepository.findById(quotationitem.getId()).get();
        // Disconnect from session so that the updates on updatedQuotationitem are not directly saved in db
        em.detach(updatedQuotationitem);
        updatedQuotationitem
            .quotationNo(UPDATED_QUOTATION_NO)
            .quotationItemNo(UPDATED_QUOTATION_ITEM_NO)
            .workerName(UPDATED_WORKER_NAME)
            .standardPrice(UPDATED_STANDARD_PRICE)
            .count(UPDATED_COUNT)
            .upperLimitHour(UPDATED_UPPER_LIMIT_HOUR)
            .lowerLimitHour(UPDATED_LOWER_LIMIT_HOUR)
            .additionalPrice(UPDATED_ADDITIONAL_PRICE)
            .deductionPrice(UPDATED_DEDUCTION_PRICE)
            .memo(UPDATED_MEMO)
            .updateCount(UPDATED_UPDATE_COUNT);

        restQuotationitemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuotationitem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQuotationitem))
            )
            .andExpect(status().isOk());

        // Validate the Quotationitem in the database
        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeUpdate);
        Quotationitem testQuotationitem = quotationitemList.get(quotationitemList.size() - 1);
        assertThat(testQuotationitem.getQuotationNo()).isEqualTo(UPDATED_QUOTATION_NO);
        assertThat(testQuotationitem.getQuotationItemNo()).isEqualTo(UPDATED_QUOTATION_ITEM_NO);
        assertThat(testQuotationitem.getWorkerName()).isEqualTo(UPDATED_WORKER_NAME);
        assertThat(testQuotationitem.getStandardPrice()).isEqualByComparingTo(UPDATED_STANDARD_PRICE);
        assertThat(testQuotationitem.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testQuotationitem.getUpperLimitHour()).isEqualByComparingTo(UPDATED_UPPER_LIMIT_HOUR);
        assertThat(testQuotationitem.getLowerLimitHour()).isEqualByComparingTo(UPDATED_LOWER_LIMIT_HOUR);
        assertThat(testQuotationitem.getAdditionalPrice()).isEqualByComparingTo(UPDATED_ADDITIONAL_PRICE);
        assertThat(testQuotationitem.getDeductionPrice()).isEqualByComparingTo(UPDATED_DEDUCTION_PRICE);
        assertThat(testQuotationitem.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testQuotationitem.getUpdateCount()).isEqualTo(UPDATED_UPDATE_COUNT);
    }

    @Test
    @Transactional
    void putNonExistingQuotationitem() throws Exception {
        int databaseSizeBeforeUpdate = quotationitemRepository.findAll().size();
        quotationitem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationitemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quotationitem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotationitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotationitem in the database
        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuotationitem() throws Exception {
        int databaseSizeBeforeUpdate = quotationitemRepository.findAll().size();
        quotationitem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationitemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotationitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotationitem in the database
        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuotationitem() throws Exception {
        int databaseSizeBeforeUpdate = quotationitemRepository.findAll().size();
        quotationitem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationitemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotationitem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quotationitem in the database
        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuotationitemWithPatch() throws Exception {
        // Initialize the database
        quotationitemRepository.saveAndFlush(quotationitem);

        int databaseSizeBeforeUpdate = quotationitemRepository.findAll().size();

        // Update the quotationitem using partial update
        Quotationitem partialUpdatedQuotationitem = new Quotationitem();
        partialUpdatedQuotationitem.setId(quotationitem.getId());

        partialUpdatedQuotationitem
            .standardPrice(UPDATED_STANDARD_PRICE)
            .count(UPDATED_COUNT)
            .upperLimitHour(UPDATED_UPPER_LIMIT_HOUR)
            .lowerLimitHour(UPDATED_LOWER_LIMIT_HOUR)
            .additionalPrice(UPDATED_ADDITIONAL_PRICE)
            .deductionPrice(UPDATED_DEDUCTION_PRICE)
            .memo(UPDATED_MEMO);

        restQuotationitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotationitem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuotationitem))
            )
            .andExpect(status().isOk());

        // Validate the Quotationitem in the database
        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeUpdate);
        Quotationitem testQuotationitem = quotationitemList.get(quotationitemList.size() - 1);
        assertThat(testQuotationitem.getQuotationNo()).isEqualTo(DEFAULT_QUOTATION_NO);
        assertThat(testQuotationitem.getQuotationItemNo()).isEqualTo(DEFAULT_QUOTATION_ITEM_NO);
        assertThat(testQuotationitem.getWorkerName()).isEqualTo(DEFAULT_WORKER_NAME);
        assertThat(testQuotationitem.getStandardPrice()).isEqualByComparingTo(UPDATED_STANDARD_PRICE);
        assertThat(testQuotationitem.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testQuotationitem.getUpperLimitHour()).isEqualByComparingTo(UPDATED_UPPER_LIMIT_HOUR);
        assertThat(testQuotationitem.getLowerLimitHour()).isEqualByComparingTo(UPDATED_LOWER_LIMIT_HOUR);
        assertThat(testQuotationitem.getAdditionalPrice()).isEqualByComparingTo(UPDATED_ADDITIONAL_PRICE);
        assertThat(testQuotationitem.getDeductionPrice()).isEqualByComparingTo(UPDATED_DEDUCTION_PRICE);
        assertThat(testQuotationitem.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testQuotationitem.getUpdateCount()).isEqualTo(DEFAULT_UPDATE_COUNT);
    }

    @Test
    @Transactional
    void fullUpdateQuotationitemWithPatch() throws Exception {
        // Initialize the database
        quotationitemRepository.saveAndFlush(quotationitem);

        int databaseSizeBeforeUpdate = quotationitemRepository.findAll().size();

        // Update the quotationitem using partial update
        Quotationitem partialUpdatedQuotationitem = new Quotationitem();
        partialUpdatedQuotationitem.setId(quotationitem.getId());

        partialUpdatedQuotationitem
            .quotationNo(UPDATED_QUOTATION_NO)
            .quotationItemNo(UPDATED_QUOTATION_ITEM_NO)
            .workerName(UPDATED_WORKER_NAME)
            .standardPrice(UPDATED_STANDARD_PRICE)
            .count(UPDATED_COUNT)
            .upperLimitHour(UPDATED_UPPER_LIMIT_HOUR)
            .lowerLimitHour(UPDATED_LOWER_LIMIT_HOUR)
            .additionalPrice(UPDATED_ADDITIONAL_PRICE)
            .deductionPrice(UPDATED_DEDUCTION_PRICE)
            .memo(UPDATED_MEMO)
            .updateCount(UPDATED_UPDATE_COUNT);

        restQuotationitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotationitem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuotationitem))
            )
            .andExpect(status().isOk());

        // Validate the Quotationitem in the database
        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeUpdate);
        Quotationitem testQuotationitem = quotationitemList.get(quotationitemList.size() - 1);
        assertThat(testQuotationitem.getQuotationNo()).isEqualTo(UPDATED_QUOTATION_NO);
        assertThat(testQuotationitem.getQuotationItemNo()).isEqualTo(UPDATED_QUOTATION_ITEM_NO);
        assertThat(testQuotationitem.getWorkerName()).isEqualTo(UPDATED_WORKER_NAME);
        assertThat(testQuotationitem.getStandardPrice()).isEqualByComparingTo(UPDATED_STANDARD_PRICE);
        assertThat(testQuotationitem.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testQuotationitem.getUpperLimitHour()).isEqualByComparingTo(UPDATED_UPPER_LIMIT_HOUR);
        assertThat(testQuotationitem.getLowerLimitHour()).isEqualByComparingTo(UPDATED_LOWER_LIMIT_HOUR);
        assertThat(testQuotationitem.getAdditionalPrice()).isEqualByComparingTo(UPDATED_ADDITIONAL_PRICE);
        assertThat(testQuotationitem.getDeductionPrice()).isEqualByComparingTo(UPDATED_DEDUCTION_PRICE);
        assertThat(testQuotationitem.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testQuotationitem.getUpdateCount()).isEqualTo(UPDATED_UPDATE_COUNT);
    }

    @Test
    @Transactional
    void patchNonExistingQuotationitem() throws Exception {
        int databaseSizeBeforeUpdate = quotationitemRepository.findAll().size();
        quotationitem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quotationitem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quotationitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotationitem in the database
        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuotationitem() throws Exception {
        int databaseSizeBeforeUpdate = quotationitemRepository.findAll().size();
        quotationitem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationitemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quotationitem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotationitem in the database
        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuotationitem() throws Exception {
        int databaseSizeBeforeUpdate = quotationitemRepository.findAll().size();
        quotationitem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationitemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(quotationitem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quotationitem in the database
        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuotationitem() throws Exception {
        // Initialize the database
        quotationitemRepository.saveAndFlush(quotationitem);

        int databaseSizeBeforeDelete = quotationitemRepository.findAll().size();

        // Delete the quotationitem
        restQuotationitemMockMvc
            .perform(delete(ENTITY_API_URL_ID, quotationitem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Quotationitem> quotationitemList = quotationitemRepository.findAll();
        assertThat(quotationitemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
