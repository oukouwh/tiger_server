package co.jp.tiger.web.rest;

import static co.jp.tiger.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.jp.tiger.IntegrationTest;
import co.jp.tiger.domain.Quotation;
import co.jp.tiger.domain.enumeration.OrderAccuracy;
import co.jp.tiger.domain.enumeration.PayFlag;
import co.jp.tiger.domain.enumeration.PayMaster;
import co.jp.tiger.domain.enumeration.SendFlag;
import co.jp.tiger.repository.QuotationRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link QuotationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuotationResourceIT {

    private static final String DEFAULT_QUOTATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_QUOTATION_NO = "BBBBBBBBBB";

    private static final String DEFAULT_QUOTATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_QUOTATION_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_QUOTATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_QUOTATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_WORK_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WORK_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_WORK_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WORK_END = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DELIVERY_ITEMS = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_ITEMS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ACCEPTANCE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACCEPTANCE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final PayMaster DEFAULT_PAYMENTS_TERMS = PayMaster.A;
    private static final PayMaster UPDATED_PAYMENTS_TERMS = PayMaster.B;

    private static final PayFlag DEFAULT_PAY_FLAG = PayFlag.Y;
    private static final PayFlag UPDATED_PAY_FLAG = PayFlag.N;

    private static final LocalDate DEFAULT_QUOTATION_EXPIRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_QUOTATION_EXPIRATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(999999999);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(999999998);

    private static final String DEFAULT_CUSTOMER_CHARGE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_CHARGE = "BBBBBBBBBB";

    private static final OrderAccuracy DEFAULT_ACCURACY = OrderAccuracy.A;
    private static final OrderAccuracy UPDATED_ACCURACY = OrderAccuracy.B;

    private static final LocalDate DEFAULT_MAIL_SEND_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MAIL_SEND_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_POST_SEND_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POST_SEND_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final SendFlag DEFAULT_SEND_FLAG = SendFlag.A;
    private static final SendFlag UPDATED_SEND_FLAG = SendFlag.B;

    private static final String DEFAULT_SALES_STAFF = "AAAAAAAAAA";
    private static final String UPDATED_SALES_STAFF = "BBBBBBBBBB";

    private static final String DEFAULT_SALES_OFFICE = "AAAAAAAAAA";
    private static final String UPDATED_SALES_OFFICE = "BBBBBBBBBB";

    private static final Long DEFAULT_UPDATE_COUNT = 1L;
    private static final Long UPDATED_UPDATE_COUNT = 2L;

    private static final String ENTITY_API_URL = "/api/quotations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuotationMockMvc;

    private Quotation quotation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quotation createEntity(EntityManager em) {
        Quotation quotation = new Quotation()
            .quotationNo(DEFAULT_QUOTATION_NO)
            .quotationName(DEFAULT_QUOTATION_NAME)
            .quotationDate(DEFAULT_QUOTATION_DATE)
            .workStart(DEFAULT_WORK_START)
            .workEnd(DEFAULT_WORK_END)
            .deliveryItems(DEFAULT_DELIVERY_ITEMS)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .acceptanceDate(DEFAULT_ACCEPTANCE_DATE)
            .paymentsTerms(DEFAULT_PAYMENTS_TERMS)
            .payFlag(DEFAULT_PAY_FLAG)
            .quotationExpirationDate(DEFAULT_QUOTATION_EXPIRATION_DATE)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .customerCharge(DEFAULT_CUSTOMER_CHARGE)
            .accuracy(DEFAULT_ACCURACY)
            .mailSendDate(DEFAULT_MAIL_SEND_DATE)
            .postSendDate(DEFAULT_POST_SEND_DATE)
            .sendFlag(DEFAULT_SEND_FLAG)
            .salesStaff(DEFAULT_SALES_STAFF)
            .salesOffice(DEFAULT_SALES_OFFICE)
            .updateCount(DEFAULT_UPDATE_COUNT);
        return quotation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quotation createUpdatedEntity(EntityManager em) {
        Quotation quotation = new Quotation()
            .quotationNo(UPDATED_QUOTATION_NO)
            .quotationName(UPDATED_QUOTATION_NAME)
            .quotationDate(UPDATED_QUOTATION_DATE)
            .workStart(UPDATED_WORK_START)
            .workEnd(UPDATED_WORK_END)
            .deliveryItems(UPDATED_DELIVERY_ITEMS)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .acceptanceDate(UPDATED_ACCEPTANCE_DATE)
            .paymentsTerms(UPDATED_PAYMENTS_TERMS)
            .payFlag(UPDATED_PAY_FLAG)
            .quotationExpirationDate(UPDATED_QUOTATION_EXPIRATION_DATE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .customerCharge(UPDATED_CUSTOMER_CHARGE)
            .accuracy(UPDATED_ACCURACY)
            .mailSendDate(UPDATED_MAIL_SEND_DATE)
            .postSendDate(UPDATED_POST_SEND_DATE)
            .sendFlag(UPDATED_SEND_FLAG)
            .salesStaff(UPDATED_SALES_STAFF)
            .salesOffice(UPDATED_SALES_OFFICE)
            .updateCount(UPDATED_UPDATE_COUNT);
        return quotation;
    }

    @BeforeEach
    public void initTest() {
        quotation = createEntity(em);
    }

    @Test
    @Transactional
    void createQuotation() throws Exception {
        int databaseSizeBeforeCreate = quotationRepository.findAll().size();
        // Create the Quotation
        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isCreated());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeCreate + 1);
        Quotation testQuotation = quotationList.get(quotationList.size() - 1);
        assertThat(testQuotation.getQuotationNo()).isEqualTo(DEFAULT_QUOTATION_NO);
        assertThat(testQuotation.getQuotationName()).isEqualTo(DEFAULT_QUOTATION_NAME);
        assertThat(testQuotation.getQuotationDate()).isEqualTo(DEFAULT_QUOTATION_DATE);
        assertThat(testQuotation.getWorkStart()).isEqualTo(DEFAULT_WORK_START);
        assertThat(testQuotation.getWorkEnd()).isEqualTo(DEFAULT_WORK_END);
        assertThat(testQuotation.getDeliveryItems()).isEqualTo(DEFAULT_DELIVERY_ITEMS);
        assertThat(testQuotation.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testQuotation.getAcceptanceDate()).isEqualTo(DEFAULT_ACCEPTANCE_DATE);
        assertThat(testQuotation.getPaymentsTerms()).isEqualTo(DEFAULT_PAYMENTS_TERMS);
        assertThat(testQuotation.getPayFlag()).isEqualTo(DEFAULT_PAY_FLAG);
        assertThat(testQuotation.getQuotationExpirationDate()).isEqualTo(DEFAULT_QUOTATION_EXPIRATION_DATE);
        assertThat(testQuotation.getTotalAmount()).isEqualByComparingTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testQuotation.getCustomerCharge()).isEqualTo(DEFAULT_CUSTOMER_CHARGE);
        assertThat(testQuotation.getAccuracy()).isEqualTo(DEFAULT_ACCURACY);
        assertThat(testQuotation.getMailSendDate()).isEqualTo(DEFAULT_MAIL_SEND_DATE);
        assertThat(testQuotation.getPostSendDate()).isEqualTo(DEFAULT_POST_SEND_DATE);
        assertThat(testQuotation.getSendFlag()).isEqualTo(DEFAULT_SEND_FLAG);
        assertThat(testQuotation.getSalesStaff()).isEqualTo(DEFAULT_SALES_STAFF);
        assertThat(testQuotation.getSalesOffice()).isEqualTo(DEFAULT_SALES_OFFICE);
        assertThat(testQuotation.getUpdateCount()).isEqualTo(DEFAULT_UPDATE_COUNT);
    }

    @Test
    @Transactional
    void createQuotationWithExistingId() throws Exception {
        // Create the Quotation with an existing ID
        quotation.setId(1L);

        int databaseSizeBeforeCreate = quotationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuotationNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationRepository.findAll().size();
        // set the field null
        quotation.setQuotationNo(null);

        // Create the Quotation, which fails.

        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuotationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationRepository.findAll().size();
        // set the field null
        quotation.setQuotationName(null);

        // Create the Quotation, which fails.

        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuotationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationRepository.findAll().size();
        // set the field null
        quotation.setQuotationDate(null);

        // Create the Quotation, which fails.

        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWorkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationRepository.findAll().size();
        // set the field null
        quotation.setWorkStart(null);

        // Create the Quotation, which fails.

        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWorkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationRepository.findAll().size();
        // set the field null
        quotation.setWorkEnd(null);

        // Create the Quotation, which fails.

        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeliveryItemsIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationRepository.findAll().size();
        // set the field null
        quotation.setDeliveryItems(null);

        // Create the Quotation, which fails.

        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeliveryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationRepository.findAll().size();
        // set the field null
        quotation.setDeliveryDate(null);

        // Create the Quotation, which fails.

        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAcceptanceDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationRepository.findAll().size();
        // set the field null
        quotation.setAcceptanceDate(null);

        // Create the Quotation, which fails.

        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentsTermsIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationRepository.findAll().size();
        // set the field null
        quotation.setPaymentsTerms(null);

        // Create the Quotation, which fails.

        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPayFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationRepository.findAll().size();
        // set the field null
        quotation.setPayFlag(null);

        // Create the Quotation, which fails.

        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuotationExpirationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationRepository.findAll().size();
        // set the field null
        quotation.setQuotationExpirationDate(null);

        // Create the Quotation, which fails.

        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTotalAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = quotationRepository.findAll().size();
        // set the field null
        quotation.setTotalAmount(null);

        // Create the Quotation, which fails.

        restQuotationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllQuotations() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get all the quotationList
        restQuotationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotation.getId().intValue())))
            .andExpect(jsonPath("$.[*].quotationNo").value(hasItem(DEFAULT_QUOTATION_NO)))
            .andExpect(jsonPath("$.[*].quotationName").value(hasItem(DEFAULT_QUOTATION_NAME)))
            .andExpect(jsonPath("$.[*].quotationDate").value(hasItem(DEFAULT_QUOTATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].workStart").value(hasItem(DEFAULT_WORK_START.toString())))
            .andExpect(jsonPath("$.[*].workEnd").value(hasItem(DEFAULT_WORK_END.toString())))
            .andExpect(jsonPath("$.[*].deliveryItems").value(hasItem(DEFAULT_DELIVERY_ITEMS)))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].acceptanceDate").value(hasItem(DEFAULT_ACCEPTANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentsTerms").value(hasItem(DEFAULT_PAYMENTS_TERMS.toString())))
            .andExpect(jsonPath("$.[*].payFlag").value(hasItem(DEFAULT_PAY_FLAG.toString())))
            .andExpect(jsonPath("$.[*].quotationExpirationDate").value(hasItem(DEFAULT_QUOTATION_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(sameNumber(DEFAULT_TOTAL_AMOUNT))))
            .andExpect(jsonPath("$.[*].customerCharge").value(hasItem(DEFAULT_CUSTOMER_CHARGE)))
            .andExpect(jsonPath("$.[*].accuracy").value(hasItem(DEFAULT_ACCURACY.toString())))
            .andExpect(jsonPath("$.[*].mailSendDate").value(hasItem(DEFAULT_MAIL_SEND_DATE.toString())))
            .andExpect(jsonPath("$.[*].postSendDate").value(hasItem(DEFAULT_POST_SEND_DATE.toString())))
            .andExpect(jsonPath("$.[*].sendFlag").value(hasItem(DEFAULT_SEND_FLAG.toString())))
            .andExpect(jsonPath("$.[*].salesStaff").value(hasItem(DEFAULT_SALES_STAFF)))
            .andExpect(jsonPath("$.[*].salesOffice").value(hasItem(DEFAULT_SALES_OFFICE)))
            .andExpect(jsonPath("$.[*].updateCount").value(hasItem(DEFAULT_UPDATE_COUNT.intValue())));
    }

    @Test
    @Transactional
    void getQuotation() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        // Get the quotation
        restQuotationMockMvc
            .perform(get(ENTITY_API_URL_ID, quotation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quotation.getId().intValue()))
            .andExpect(jsonPath("$.quotationNo").value(DEFAULT_QUOTATION_NO))
            .andExpect(jsonPath("$.quotationName").value(DEFAULT_QUOTATION_NAME))
            .andExpect(jsonPath("$.quotationDate").value(DEFAULT_QUOTATION_DATE.toString()))
            .andExpect(jsonPath("$.workStart").value(DEFAULT_WORK_START.toString()))
            .andExpect(jsonPath("$.workEnd").value(DEFAULT_WORK_END.toString()))
            .andExpect(jsonPath("$.deliveryItems").value(DEFAULT_DELIVERY_ITEMS))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.acceptanceDate").value(DEFAULT_ACCEPTANCE_DATE.toString()))
            .andExpect(jsonPath("$.paymentsTerms").value(DEFAULT_PAYMENTS_TERMS.toString()))
            .andExpect(jsonPath("$.payFlag").value(DEFAULT_PAY_FLAG.toString()))
            .andExpect(jsonPath("$.quotationExpirationDate").value(DEFAULT_QUOTATION_EXPIRATION_DATE.toString()))
            .andExpect(jsonPath("$.totalAmount").value(sameNumber(DEFAULT_TOTAL_AMOUNT)))
            .andExpect(jsonPath("$.customerCharge").value(DEFAULT_CUSTOMER_CHARGE))
            .andExpect(jsonPath("$.accuracy").value(DEFAULT_ACCURACY.toString()))
            .andExpect(jsonPath("$.mailSendDate").value(DEFAULT_MAIL_SEND_DATE.toString()))
            .andExpect(jsonPath("$.postSendDate").value(DEFAULT_POST_SEND_DATE.toString()))
            .andExpect(jsonPath("$.sendFlag").value(DEFAULT_SEND_FLAG.toString()))
            .andExpect(jsonPath("$.salesStaff").value(DEFAULT_SALES_STAFF))
            .andExpect(jsonPath("$.salesOffice").value(DEFAULT_SALES_OFFICE))
            .andExpect(jsonPath("$.updateCount").value(DEFAULT_UPDATE_COUNT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingQuotation() throws Exception {
        // Get the quotation
        restQuotationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingQuotation() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();

        // Update the quotation
        Quotation updatedQuotation = quotationRepository.findById(quotation.getId()).get();
        // Disconnect from session so that the updates on updatedQuotation are not directly saved in db
        em.detach(updatedQuotation);
        updatedQuotation
            .quotationNo(UPDATED_QUOTATION_NO)
            .quotationName(UPDATED_QUOTATION_NAME)
            .quotationDate(UPDATED_QUOTATION_DATE)
            .workStart(UPDATED_WORK_START)
            .workEnd(UPDATED_WORK_END)
            .deliveryItems(UPDATED_DELIVERY_ITEMS)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .acceptanceDate(UPDATED_ACCEPTANCE_DATE)
            .paymentsTerms(UPDATED_PAYMENTS_TERMS)
            .payFlag(UPDATED_PAY_FLAG)
            .quotationExpirationDate(UPDATED_QUOTATION_EXPIRATION_DATE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .customerCharge(UPDATED_CUSTOMER_CHARGE)
            .accuracy(UPDATED_ACCURACY)
            .mailSendDate(UPDATED_MAIL_SEND_DATE)
            .postSendDate(UPDATED_POST_SEND_DATE)
            .sendFlag(UPDATED_SEND_FLAG)
            .salesStaff(UPDATED_SALES_STAFF)
            .salesOffice(UPDATED_SALES_OFFICE)
            .updateCount(UPDATED_UPDATE_COUNT);

        restQuotationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuotation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQuotation))
            )
            .andExpect(status().isOk());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
        Quotation testQuotation = quotationList.get(quotationList.size() - 1);
        assertThat(testQuotation.getQuotationNo()).isEqualTo(UPDATED_QUOTATION_NO);
        assertThat(testQuotation.getQuotationName()).isEqualTo(UPDATED_QUOTATION_NAME);
        assertThat(testQuotation.getQuotationDate()).isEqualTo(UPDATED_QUOTATION_DATE);
        assertThat(testQuotation.getWorkStart()).isEqualTo(UPDATED_WORK_START);
        assertThat(testQuotation.getWorkEnd()).isEqualTo(UPDATED_WORK_END);
        assertThat(testQuotation.getDeliveryItems()).isEqualTo(UPDATED_DELIVERY_ITEMS);
        assertThat(testQuotation.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testQuotation.getAcceptanceDate()).isEqualTo(UPDATED_ACCEPTANCE_DATE);
        assertThat(testQuotation.getPaymentsTerms()).isEqualTo(UPDATED_PAYMENTS_TERMS);
        assertThat(testQuotation.getPayFlag()).isEqualTo(UPDATED_PAY_FLAG);
        assertThat(testQuotation.getQuotationExpirationDate()).isEqualTo(UPDATED_QUOTATION_EXPIRATION_DATE);
        assertThat(testQuotation.getTotalAmount()).isEqualByComparingTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testQuotation.getCustomerCharge()).isEqualTo(UPDATED_CUSTOMER_CHARGE);
        assertThat(testQuotation.getAccuracy()).isEqualTo(UPDATED_ACCURACY);
        assertThat(testQuotation.getMailSendDate()).isEqualTo(UPDATED_MAIL_SEND_DATE);
        assertThat(testQuotation.getPostSendDate()).isEqualTo(UPDATED_POST_SEND_DATE);
        assertThat(testQuotation.getSendFlag()).isEqualTo(UPDATED_SEND_FLAG);
        assertThat(testQuotation.getSalesStaff()).isEqualTo(UPDATED_SALES_STAFF);
        assertThat(testQuotation.getSalesOffice()).isEqualTo(UPDATED_SALES_OFFICE);
        assertThat(testQuotation.getUpdateCount()).isEqualTo(UPDATED_UPDATE_COUNT);
    }

    @Test
    @Transactional
    void putNonExistingQuotation() throws Exception {
        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();
        quotation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quotation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuotation() throws Exception {
        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();
        quotation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quotation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuotation() throws Exception {
        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();
        quotation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuotationWithPatch() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();

        // Update the quotation using partial update
        Quotation partialUpdatedQuotation = new Quotation();
        partialUpdatedQuotation.setId(quotation.getId());

        partialUpdatedQuotation
            .workStart(UPDATED_WORK_START)
            .workEnd(UPDATED_WORK_END)
            .deliveryItems(UPDATED_DELIVERY_ITEMS)
            .quotationExpirationDate(UPDATED_QUOTATION_EXPIRATION_DATE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .customerCharge(UPDATED_CUSTOMER_CHARGE)
            .accuracy(UPDATED_ACCURACY)
            .mailSendDate(UPDATED_MAIL_SEND_DATE)
            .postSendDate(UPDATED_POST_SEND_DATE)
            .sendFlag(UPDATED_SEND_FLAG)
            .salesStaff(UPDATED_SALES_STAFF)
            .updateCount(UPDATED_UPDATE_COUNT);

        restQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuotation))
            )
            .andExpect(status().isOk());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
        Quotation testQuotation = quotationList.get(quotationList.size() - 1);
        assertThat(testQuotation.getQuotationNo()).isEqualTo(DEFAULT_QUOTATION_NO);
        assertThat(testQuotation.getQuotationName()).isEqualTo(DEFAULT_QUOTATION_NAME);
        assertThat(testQuotation.getQuotationDate()).isEqualTo(DEFAULT_QUOTATION_DATE);
        assertThat(testQuotation.getWorkStart()).isEqualTo(UPDATED_WORK_START);
        assertThat(testQuotation.getWorkEnd()).isEqualTo(UPDATED_WORK_END);
        assertThat(testQuotation.getDeliveryItems()).isEqualTo(UPDATED_DELIVERY_ITEMS);
        assertThat(testQuotation.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testQuotation.getAcceptanceDate()).isEqualTo(DEFAULT_ACCEPTANCE_DATE);
        assertThat(testQuotation.getPaymentsTerms()).isEqualTo(DEFAULT_PAYMENTS_TERMS);
        assertThat(testQuotation.getPayFlag()).isEqualTo(DEFAULT_PAY_FLAG);
        assertThat(testQuotation.getQuotationExpirationDate()).isEqualTo(UPDATED_QUOTATION_EXPIRATION_DATE);
        assertThat(testQuotation.getTotalAmount()).isEqualByComparingTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testQuotation.getCustomerCharge()).isEqualTo(UPDATED_CUSTOMER_CHARGE);
        assertThat(testQuotation.getAccuracy()).isEqualTo(UPDATED_ACCURACY);
        assertThat(testQuotation.getMailSendDate()).isEqualTo(UPDATED_MAIL_SEND_DATE);
        assertThat(testQuotation.getPostSendDate()).isEqualTo(UPDATED_POST_SEND_DATE);
        assertThat(testQuotation.getSendFlag()).isEqualTo(UPDATED_SEND_FLAG);
        assertThat(testQuotation.getSalesStaff()).isEqualTo(UPDATED_SALES_STAFF);
        assertThat(testQuotation.getSalesOffice()).isEqualTo(DEFAULT_SALES_OFFICE);
        assertThat(testQuotation.getUpdateCount()).isEqualTo(UPDATED_UPDATE_COUNT);
    }

    @Test
    @Transactional
    void fullUpdateQuotationWithPatch() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();

        // Update the quotation using partial update
        Quotation partialUpdatedQuotation = new Quotation();
        partialUpdatedQuotation.setId(quotation.getId());

        partialUpdatedQuotation
            .quotationNo(UPDATED_QUOTATION_NO)
            .quotationName(UPDATED_QUOTATION_NAME)
            .quotationDate(UPDATED_QUOTATION_DATE)
            .workStart(UPDATED_WORK_START)
            .workEnd(UPDATED_WORK_END)
            .deliveryItems(UPDATED_DELIVERY_ITEMS)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .acceptanceDate(UPDATED_ACCEPTANCE_DATE)
            .paymentsTerms(UPDATED_PAYMENTS_TERMS)
            .payFlag(UPDATED_PAY_FLAG)
            .quotationExpirationDate(UPDATED_QUOTATION_EXPIRATION_DATE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .customerCharge(UPDATED_CUSTOMER_CHARGE)
            .accuracy(UPDATED_ACCURACY)
            .mailSendDate(UPDATED_MAIL_SEND_DATE)
            .postSendDate(UPDATED_POST_SEND_DATE)
            .sendFlag(UPDATED_SEND_FLAG)
            .salesStaff(UPDATED_SALES_STAFF)
            .salesOffice(UPDATED_SALES_OFFICE)
            .updateCount(UPDATED_UPDATE_COUNT);

        restQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuotation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuotation))
            )
            .andExpect(status().isOk());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
        Quotation testQuotation = quotationList.get(quotationList.size() - 1);
        assertThat(testQuotation.getQuotationNo()).isEqualTo(UPDATED_QUOTATION_NO);
        assertThat(testQuotation.getQuotationName()).isEqualTo(UPDATED_QUOTATION_NAME);
        assertThat(testQuotation.getQuotationDate()).isEqualTo(UPDATED_QUOTATION_DATE);
        assertThat(testQuotation.getWorkStart()).isEqualTo(UPDATED_WORK_START);
        assertThat(testQuotation.getWorkEnd()).isEqualTo(UPDATED_WORK_END);
        assertThat(testQuotation.getDeliveryItems()).isEqualTo(UPDATED_DELIVERY_ITEMS);
        assertThat(testQuotation.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testQuotation.getAcceptanceDate()).isEqualTo(UPDATED_ACCEPTANCE_DATE);
        assertThat(testQuotation.getPaymentsTerms()).isEqualTo(UPDATED_PAYMENTS_TERMS);
        assertThat(testQuotation.getPayFlag()).isEqualTo(UPDATED_PAY_FLAG);
        assertThat(testQuotation.getQuotationExpirationDate()).isEqualTo(UPDATED_QUOTATION_EXPIRATION_DATE);
        assertThat(testQuotation.getTotalAmount()).isEqualByComparingTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testQuotation.getCustomerCharge()).isEqualTo(UPDATED_CUSTOMER_CHARGE);
        assertThat(testQuotation.getAccuracy()).isEqualTo(UPDATED_ACCURACY);
        assertThat(testQuotation.getMailSendDate()).isEqualTo(UPDATED_MAIL_SEND_DATE);
        assertThat(testQuotation.getPostSendDate()).isEqualTo(UPDATED_POST_SEND_DATE);
        assertThat(testQuotation.getSendFlag()).isEqualTo(UPDATED_SEND_FLAG);
        assertThat(testQuotation.getSalesStaff()).isEqualTo(UPDATED_SALES_STAFF);
        assertThat(testQuotation.getSalesOffice()).isEqualTo(UPDATED_SALES_OFFICE);
        assertThat(testQuotation.getUpdateCount()).isEqualTo(UPDATED_UPDATE_COUNT);
    }

    @Test
    @Transactional
    void patchNonExistingQuotation() throws Exception {
        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();
        quotation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quotation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quotation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuotation() throws Exception {
        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();
        quotation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quotation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuotation() throws Exception {
        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();
        quotation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuotationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(quotation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuotation() throws Exception {
        // Initialize the database
        quotationRepository.saveAndFlush(quotation);

        int databaseSizeBeforeDelete = quotationRepository.findAll().size();

        // Delete the quotation
        restQuotationMockMvc
            .perform(delete(ENTITY_API_URL_ID, quotation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
