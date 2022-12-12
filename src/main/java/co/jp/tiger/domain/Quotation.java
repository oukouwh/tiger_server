package co.jp.tiger.domain;

import co.jp.tiger.domain.enumeration.OrderAccuracy;
import co.jp.tiger.domain.enumeration.SendFlag;
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
    // description = "見積書表\nquotationNo  見積書番号 String\nquotationName 見積書名  String\nquotationDate 見積書日付 LocalDate\nworkStart 作業开始时间 LocalDate\nworkEnd 作業終了期間  LocalDate\ndeliveryItems 納入物件 String\ndeliveryDate 納入日 LocalDate\nacceptanceDate 検収予定日 LocalDate\npaymentsTerms 支払条件 PayMaster\npayFlag 精算フラグ    PayFlag  Yあり、Nなし\nquotationExpirationDate 見積有効期限 LocalDate\ntotalAmount 合計金額  BigDecimal\n合計金額 = SUM(明細.金額=明細.数量×明細.単価)  ： totalAmount = SUM(standardPrice*count)\n明細.追加時間単価=明細.単価÷上限時間  ：  additionalPrice = standardPrice/upperLimitHour\n明細.控除時間単価=明細.単価÷下限時間 ：  deductionPrice = standardPrice/lowerLimitHour\n↓↓↓↓↓↓↓↓非表示字段，数据库字段可以插入空值↓↓↓↓↓↓↓↓\ncustomerCharge 顧客担当 String\naccuracy 受注確度 OrderAccuracy\nmailSendDate メール送付日 LocalDate\npostSendDate 見積郵送日 LocalDate\nsendFlag 送信フラグ SendFlag\nsalesStaff 営業担当 String\nsalesOffice 営業事務 String\nupdateCount 更新回数 Long"
)
@Entity
@Table(name = "quotation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Quotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String paymentsTerms;

    @NotNull
//    @Enumerated(EnumType.STRING)
    @Column(name = "pay_flag", nullable = false)
//    private PayFlag payFlag;
    private String payFlag;

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
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Quotationitem> quotationItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuotationNo() {
        return quotationNo;
    }

    public Quotation quotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
        return this;
    }

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
    }

    public String getQuotationName() {
        return quotationName;
    }

    public Quotation quotationName(String quotationName) {
        this.quotationName = quotationName;
        return this;
    }

    public void setQuotationName(String quotationName) {
        this.quotationName = quotationName;
    }

    public LocalDate getQuotationDate() {
        return quotationDate;
    }

    public Quotation quotationDate(LocalDate quotationDate) {
        this.quotationDate = quotationDate;
        return this;
    }

    public void setQuotationDate(LocalDate quotationDate) {
        this.quotationDate = quotationDate;
    }

    public LocalDate getWorkStart() {
        return workStart;
    }

    public Quotation workStart(LocalDate workStart) {
        this.workStart = workStart;
        return this;
    }

    public void setWorkStart(LocalDate workStart) {
        this.workStart = workStart;
    }

    public LocalDate getWorkEnd() {
        return workEnd;
    }

    public Quotation workEnd(LocalDate workEnd) {
        this.workEnd = workEnd;
        return this;
    }

    public void setWorkEnd(LocalDate workEnd) {
        this.workEnd = workEnd;
    }

    public String getDeliveryItems() {
        return deliveryItems;
    }

    public Quotation deliveryItems(String deliveryItems) {
        this.deliveryItems = deliveryItems;
        return this;
    }

    public void setDeliveryItems(String deliveryItems) {
        this.deliveryItems = deliveryItems;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public Quotation deliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getAcceptanceDate() {
        return acceptanceDate;
    }

    public Quotation acceptanceDate(LocalDate acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
        return this;
    }

    public void setAcceptanceDate(LocalDate acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public String getPaymentsTerms() {
        return paymentsTerms;
    }

    public Quotation paymentsTerms(String paymentsTerms) {
        this.paymentsTerms = paymentsTerms;
        return this;
    }

    public void setPaymentsTerms(String paymentsTerms) {
        this.paymentsTerms = paymentsTerms;
    }

    public String getPayFlag() {
        return payFlag;
    }

    public Quotation payFlag(String payFlag) {
        this.payFlag = payFlag;
        return this;
    }

    public void setPayFlag(String payFlag) {
        this.payFlag = payFlag;
    }

    public LocalDate getQuotationExpirationDate() {
        return quotationExpirationDate;
    }

    public Quotation quotationExpirationDate(LocalDate quotationExpirationDate) {
        this.quotationExpirationDate = quotationExpirationDate;
        return this;
    }

    public void setQuotationExpirationDate(LocalDate quotationExpirationDate) {
        this.quotationExpirationDate = quotationExpirationDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Quotation totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomerCharge() {
        return customerCharge;
    }

    public Quotation customerCharge(String customerCharge) {
        this.customerCharge = customerCharge;
        return this;
    }

    public void setCustomerCharge(String customerCharge) {
        this.customerCharge = customerCharge;
    }

    public OrderAccuracy getAccuracy() {
        return accuracy;
    }

    public Quotation accuracy(OrderAccuracy accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    public void setAccuracy(OrderAccuracy accuracy) {
        this.accuracy = accuracy;
    }

    public LocalDate getMailSendDate() {
        return mailSendDate;
    }

    public Quotation mailSendDate(LocalDate mailSendDate) {
        this.mailSendDate = mailSendDate;
        return this;
    }

    public void setMailSendDate(LocalDate mailSendDate) {
        this.mailSendDate = mailSendDate;
    }

    public LocalDate getPostSendDate() {
        return postSendDate;
    }

    public Quotation postSendDate(LocalDate postSendDate) {
        this.postSendDate = postSendDate;
        return this;
    }

    public void setPostSendDate(LocalDate postSendDate) {
        this.postSendDate = postSendDate;
    }

    public SendFlag getSendFlag() {
        return sendFlag;
    }

    public Quotation sendFlag(SendFlag sendFlag) {
        this.sendFlag = sendFlag;
        return this;
    }

    public void setSendFlag(SendFlag sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getSalesStaff() {
        return salesStaff;
    }

    public Quotation salesStaff(String salesStaff) {
        this.salesStaff = salesStaff;
        return this;
    }

    public void setSalesStaff(String salesStaff) {
        this.salesStaff = salesStaff;
    }

    public String getSalesOffice() {
        return salesOffice;
    }

    public Quotation salesOffice(String salesOffice) {
        this.salesOffice = salesOffice;
        return this;
    }

    public void setSalesOffice(String salesOffice) {
        this.salesOffice = salesOffice;
    }

    public Long getUpdateCount() {
        return updateCount;
    }

    public Quotation updateCount(Long updateCount) {
        this.updateCount = updateCount;
        return this;
    }

    public void setUpdateCount(Long updateCount) {
        this.updateCount = updateCount;
    }

    public Set<Quotationitem> getQuotationItems() {
        return quotationItems;
    }

    public Quotation quotationItems(Set<Quotationitem> quotationitems) {
        this.quotationItems = quotationitems;
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

    public void setQuotationItems(Set<Quotationitem> quotationitems) {
        this.quotationItems = quotationitems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
        return 31;
    }

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
