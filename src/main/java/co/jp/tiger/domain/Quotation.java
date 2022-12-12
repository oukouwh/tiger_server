package co.jp.tiger.domain;

import co.jp.tiger.domain.enumeration.OrderAccuracy;
import co.jp.tiger.domain.enumeration.PayFlag;
import co.jp.tiger.domain.enumeration.PayMaster;
import co.jp.tiger.domain.enumeration.SendFlag;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 見積書表\nquotationNo  見積書番号 String\nquotationName 見積書名  String\nquotationDate 見積書日付 LocalDate\nworkStart 作業开始时间 LocalDate\nworkEnd 作業終了期間  LocalDate\ndeliveryItems 納入物件 String\ndeliveryDate 納入日 LocalDate\nacceptanceDate 検収予定日 LocalDate\npaymentsTerms 支払条件 PayMaster\npayFlag 精算フラグ    PayFlag  Yあり、Nなし\nquotationExpirationDate 見積有効期限 LocalDate\ntotalAmount 合計金額  BigDecimal\n合計金額 = SUM(明細.金額=明細.数量×明細.単価)  ： totalAmount = SUM(standardPrice*count)\n明細.追加時間単価=明細.単価÷上限時間  ：  additionalPrice = standardPrice/upperLimitHour\n明細.控除時間単価=明細.単価÷下限時間 ：  deductionPrice = standardPrice/lowerLimitHour\n↓↓↓↓↓↓↓↓非表示字段，数据库字段可以插入空值↓↓↓↓↓↓↓↓\ncustomerCharge 顧客担当 String\naccuracy 受注確度 OrderAccuracy\nmailSendDate メール送付日 LocalDate\npostSendDate 見積郵送日 LocalDate\nsendFlag 送信フラグ SendFlag\nsalesStaff 営業担当 String\nsalesOffice 営業事務 String\nupdateCount 更新回数 Long
 */
@Schema(
    description = "見積書表\nquotationNo  見積書番号 String\nquotationName 見積書名  String\nquotationDate 見積書日付 LocalDate\nworkStart 作業开始时间 LocalDate\nworkEnd 作業終了期間  LocalDate\ndeliveryItems 納入物件 String\ndeliveryDate 納入日 LocalDate\nacceptanceDate 検収予定日 LocalDate\npaymentsTerms 支払条件 PayMaster\npayFlag 精算フラグ    PayFlag  Yあり、Nなし\nquotationExpirationDate 見積有効期限 LocalDate\ntotalAmount 合計金額  BigDecimal\n合計金額 = SUM(明細.金額=明細.数量×明細.単価)  ： totalAmount = SUM(standardPrice*count)\n明細.追加時間単価=明細.単価÷上限時間  ：  additionalPrice = standardPrice/upperLimitHour\n明細.控除時間単価=明細.単価÷下限時間 ：  deductionPrice = standardPrice/lowerLimitHour\n↓↓↓↓↓↓↓↓非表示字段，数据库字段可以插入空值↓↓↓↓↓↓↓↓\ncustomerCharge 顧客担当 String\naccuracy 受注確度 OrderAccuracy\nmailSendDate メール送付日 LocalDate\npostSendDate 見積郵送日 LocalDate\nsendFlag 送信フラグ SendFlag\nsalesStaff 営業担当 String\nsalesOffice 営業事務 String\nupdateCount 更新回数 Long"
)
@Entity
@Table(name = "quotation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Quotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "quotation_no", nullable = false)
    private String quotationNo;

    @NotNull
    @Size(max = 100)
    @Column(name = "quotation_name", length = 100, nullable = false)
    private String quotationName;

    @NotNull
    @Column(name = "quotation_date", nullable = false)
    private LocalDate quotationDate;

    @NotNull
    @Column(name = "work_start", nullable = false)
    private LocalDate workStart;

    @NotNull
    @Column(name = "work_end", nullable = false)
    private LocalDate workEnd;

    @NotNull
    @Column(name = "delivery_items", nullable = false)
    private String deliveryItems;

    @NotNull
    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @NotNull
    @Column(name = "acceptance_date", nullable = false)
    private LocalDate acceptanceDate;

    @NotNull
//    @Enumerated(EnumType.STRING)
    @Column(name = "payments_terms", nullable = false)
    private PayMaster paymentsTerms;

    @NotNull
//    @Enumerated(EnumType.STRING)
    @Column(name = "pay_flag", nullable = false)
    private PayFlag payFlag;

    @NotNull
    @Column(name = "quotation_expiration_date", nullable = false)
    private LocalDate quotationExpirationDate;

    @NotNull
    @DecimalMax(value = "999999999")
    @Column(name = "total_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "customer_charge")
    private String customerCharge;

    @Enumerated(EnumType.STRING)
    @Column(name = "accuracy")
    private OrderAccuracy accuracy;

    @Column(name = "mail_send_date")
    private LocalDate mailSendDate;

    @Column(name = "post_send_date")
    private LocalDate postSendDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "send_flag")
    private SendFlag sendFlag;

    @Column(name = "sales_staff")
    private String salesStaff;

    @Column(name = "sales_office")
    private String salesOffice;

    @Column(name = "update_count")
    private Long updateCount;

    @OneToMany(mappedBy = "quotation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    @JsonIgnoreProperties(value = { "quotation" }, allowSetters = true)
    private Set<Quotationitem> quotationItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Quotation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuotationNo() {
        return this.quotationNo;
    }

    public Quotation quotationNo(String quotationNo) {
        this.setQuotationNo(quotationNo);
        return this;
    }

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
    }

    public String getQuotationName() {
        return this.quotationName;
    }

    public Quotation quotationName(String quotationName) {
        this.setQuotationName(quotationName);
        return this;
    }

    public void setQuotationName(String quotationName) {
        this.quotationName = quotationName;
    }

    public LocalDate getQuotationDate() {
        return this.quotationDate;
    }

    public Quotation quotationDate(LocalDate quotationDate) {
        this.setQuotationDate(quotationDate);
        return this;
    }

    public void setQuotationDate(LocalDate quotationDate) {
        this.quotationDate = quotationDate;
    }

    public LocalDate getWorkStart() {
        return this.workStart;
    }

    public Quotation workStart(LocalDate workStart) {
        this.setWorkStart(workStart);
        return this;
    }

    public void setWorkStart(LocalDate workStart) {
        this.workStart = workStart;
    }

    public LocalDate getWorkEnd() {
        return this.workEnd;
    }

    public Quotation workEnd(LocalDate workEnd) {
        this.setWorkEnd(workEnd);
        return this;
    }

    public void setWorkEnd(LocalDate workEnd) {
        this.workEnd = workEnd;
    }

    public String getDeliveryItems() {
        return this.deliveryItems;
    }

    public Quotation deliveryItems(String deliveryItems) {
        this.setDeliveryItems(deliveryItems);
        return this;
    }

    public void setDeliveryItems(String deliveryItems) {
        this.deliveryItems = deliveryItems;
    }

    public LocalDate getDeliveryDate() {
        return this.deliveryDate;
    }

    public Quotation deliveryDate(LocalDate deliveryDate) {
        this.setDeliveryDate(deliveryDate);
        return this;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getAcceptanceDate() {
        return this.acceptanceDate;
    }

    public Quotation acceptanceDate(LocalDate acceptanceDate) {
        this.setAcceptanceDate(acceptanceDate);
        return this;
    }

    public void setAcceptanceDate(LocalDate acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public PayMaster getPaymentsTerms() {
        return this.paymentsTerms;
    }

    public Quotation paymentsTerms(PayMaster paymentsTerms) {
        this.setPaymentsTerms(paymentsTerms);
        return this;
    }

    public void setPaymentsTerms(PayMaster paymentsTerms) {
        this.paymentsTerms = paymentsTerms;
    }

    public PayFlag getPayFlag() {
        return this.payFlag;
    }

    public Quotation payFlag(PayFlag payFlag) {
        this.setPayFlag(payFlag);
        return this;
    }

    public void setPayFlag(PayFlag payFlag) {
        this.payFlag = payFlag;
    }

    public LocalDate getQuotationExpirationDate() {
        return this.quotationExpirationDate;
    }

    public Quotation quotationExpirationDate(LocalDate quotationExpirationDate) {
        this.setQuotationExpirationDate(quotationExpirationDate);
        return this;
    }

    public void setQuotationExpirationDate(LocalDate quotationExpirationDate) {
        this.quotationExpirationDate = quotationExpirationDate;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public Quotation totalAmount(BigDecimal totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomerCharge() {
        return this.customerCharge;
    }

    public Quotation customerCharge(String customerCharge) {
        this.setCustomerCharge(customerCharge);
        return this;
    }

    public void setCustomerCharge(String customerCharge) {
        this.customerCharge = customerCharge;
    }

    public OrderAccuracy getAccuracy() {
        return this.accuracy;
    }

    public Quotation accuracy(OrderAccuracy accuracy) {
        this.setAccuracy(accuracy);
        return this;
    }

    public void setAccuracy(OrderAccuracy accuracy) {
        this.accuracy = accuracy;
    }

    public LocalDate getMailSendDate() {
        return this.mailSendDate;
    }

    public Quotation mailSendDate(LocalDate mailSendDate) {
        this.setMailSendDate(mailSendDate);
        return this;
    }

    public void setMailSendDate(LocalDate mailSendDate) {
        this.mailSendDate = mailSendDate;
    }

    public LocalDate getPostSendDate() {
        return this.postSendDate;
    }

    public Quotation postSendDate(LocalDate postSendDate) {
        this.setPostSendDate(postSendDate);
        return this;
    }

    public void setPostSendDate(LocalDate postSendDate) {
        this.postSendDate = postSendDate;
    }

    public SendFlag getSendFlag() {
        return this.sendFlag;
    }

    public Quotation sendFlag(SendFlag sendFlag) {
        this.setSendFlag(sendFlag);
        return this;
    }

    public void setSendFlag(SendFlag sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getSalesStaff() {
        return this.salesStaff;
    }

    public Quotation salesStaff(String salesStaff) {
        this.setSalesStaff(salesStaff);
        return this;
    }

    public void setSalesStaff(String salesStaff) {
        this.salesStaff = salesStaff;
    }

    public String getSalesOffice() {
        return this.salesOffice;
    }

    public Quotation salesOffice(String salesOffice) {
        this.setSalesOffice(salesOffice);
        return this;
    }

    public void setSalesOffice(String salesOffice) {
        this.salesOffice = salesOffice;
    }

    public Long getUpdateCount() {
        return this.updateCount;
    }

    public Quotation updateCount(Long updateCount) {
        this.setUpdateCount(updateCount);
        return this;
    }

    public void setUpdateCount(Long updateCount) {
        this.updateCount = updateCount;
    }

    public Set<Quotationitem> getQuotationItems() {
        return this.quotationItems;
    }

    public void setQuotationItems(Set<Quotationitem> quotationitems) {
        if (this.quotationItems != null) {
            this.quotationItems.forEach(i -> i.setQuotation(null));
        }
        if (quotationitems != null) {
            quotationitems.forEach(i -> i.setQuotation(this));
        }
        this.quotationItems = quotationitems;
    }

    public Quotation quotationItems(Set<Quotationitem> quotationitems) {
        this.setQuotationItems(quotationitems);
        return this;
    }

    public Quotation addQuotationItem(Quotationitem quotationitem) {
        this.quotationItems.add(quotationitem);
        quotationitem.setQuotation(this);
        return this;
    }

    public Quotation removeQuotationItem(Quotationitem quotationitem) {
        this.quotationItems.remove(quotationitem);
        quotationitem.setQuotation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quotation)) {
            return false;
        }
        return id != null && id.equals(((Quotation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quotation{" +
            "id=" + getId() +
            ", quotationNo='" + getQuotationNo() + "'" +
            ", quotationName='" + getQuotationName() + "'" +
            ", quotationDate='" + getQuotationDate() + "'" +
            ", workStart='" + getWorkStart() + "'" +
            ", workEnd='" + getWorkEnd() + "'" +
            ", deliveryItems='" + getDeliveryItems() + "'" +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", acceptanceDate='" + getAcceptanceDate() + "'" +
            ", paymentsTerms='" + getPaymentsTerms() + "'" +
            ", payFlag='" + getPayFlag() + "'" +
            ", quotationExpirationDate='" + getQuotationExpirationDate() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", customerCharge='" + getCustomerCharge() + "'" +
            ", accuracy='" + getAccuracy() + "'" +
            ", mailSendDate='" + getMailSendDate() + "'" +
            ", postSendDate='" + getPostSendDate() + "'" +
            ", sendFlag='" + getSendFlag() + "'" +
            ", salesStaff='" + getSalesStaff() + "'" +
            ", salesOffice='" + getSalesOffice() + "'" +
            ", updateCount=" + getUpdateCount() +
            "}";
    }
}
