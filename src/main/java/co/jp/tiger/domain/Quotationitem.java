package co.jp.tiger.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 見積書明細\nquotationNo  見積書番号 绑定\nquotationItemNo 見積書明細No\nworkerName 作業者\nstandardPrice 月額基準単価\ncount 数量\nupperLimitHour 上限時間\nlowerLimitHour 下限時間\nadditionalPrice 追加単価\ndeductionPrice 控除単価\nmemo 備考\nupdateCount 更新回数  非表示
 */
@Schema(
    description = "見積書明細\nquotationNo  見積書番号 绑定\nquotationItemNo 見積書明細No\nworkerName 作業者\nstandardPrice 月額基準単価\ncount 数量\nupperLimitHour 上限時間\nlowerLimitHour 下限時間\nadditionalPrice 追加単価\ndeductionPrice 控除単価\nmemo 備考\nupdateCount 更新回数  非表示"
)
@Entity
@Table(name = "quotationitem")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Quotationitem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "quotation_no", nullable = false)
    private String quotationNo;

    @NotNull
    @Column(name = "quotation_item_no", nullable = false)
    private Long quotationItemNo;

    @NotNull
    @Column(name = "worker_name", nullable = false)
    private String workerName;

    @NotNull
    @DecimalMax(value = "999999999")
    @Column(name = "standard_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal standardPrice;

    @NotNull
    @Column(name = "count", nullable = false)
    private Long count;

    @NotNull
    @DecimalMax(value = "999")
    @Column(name = "upper_limit_hour", precision = 21, scale = 2, nullable = false)
    private BigDecimal upperLimitHour;

    @NotNull
    @DecimalMax(value = "999")
    @Column(name = "lower_limit_hour", precision = 21, scale = 2, nullable = false)
    private BigDecimal lowerLimitHour;

    @NotNull
    @DecimalMax(value = "999999999")
    @Column(name = "additional_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal additionalPrice;

    @DecimalMax(value = "999999999")
    @Column(name = "deduction_price", precision = 21, scale = 2)
    private BigDecimal deductionPrice;

    @NotNull
    @Size(max = 255)
    @Column(name = "memo", length = 255, nullable = false)
    private String memo;

    @Column(name = "update_count")
    private Long updateCount;

    @ManyToOne
    @JsonIgnoreProperties(value = { "quotationItems" }, allowSetters = true)
    private Quotation quotation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Quotationitem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuotationNo() {
        return this.quotationNo;
    }

    public Quotationitem quotationNo(String quotationNo) {
        this.setQuotationNo(quotationNo);
        return this;
    }

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
    }

    public Long getQuotationItemNo() {
        return this.quotationItemNo;
    }

    public Quotationitem quotationItemNo(Long quotationItemNo) {
        this.setQuotationItemNo(quotationItemNo);
        return this;
    }

    public void setQuotationItemNo(Long quotationItemNo) {
        this.quotationItemNo = quotationItemNo;
    }

    public String getWorkerName() {
        return this.workerName;
    }

    public Quotationitem workerName(String workerName) {
        this.setWorkerName(workerName);
        return this;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public BigDecimal getStandardPrice() {
        return this.standardPrice;
    }

    public Quotationitem standardPrice(BigDecimal standardPrice) {
        this.setStandardPrice(standardPrice);
        return this;
    }

    public void setStandardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
    }

    public Long getCount() {
        return this.count;
    }

    public Quotationitem count(Long count) {
        this.setCount(count);
        return this;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public BigDecimal getUpperLimitHour() {
        return this.upperLimitHour;
    }

    public Quotationitem upperLimitHour(BigDecimal upperLimitHour) {
        this.setUpperLimitHour(upperLimitHour);
        return this;
    }

    public void setUpperLimitHour(BigDecimal upperLimitHour) {
        this.upperLimitHour = upperLimitHour;
    }

    public BigDecimal getLowerLimitHour() {
        return this.lowerLimitHour;
    }

    public Quotationitem lowerLimitHour(BigDecimal lowerLimitHour) {
        this.setLowerLimitHour(lowerLimitHour);
        return this;
    }

    public void setLowerLimitHour(BigDecimal lowerLimitHour) {
        this.lowerLimitHour = lowerLimitHour;
    }

    public BigDecimal getAdditionalPrice() {
        return this.additionalPrice;
    }

    public Quotationitem additionalPrice(BigDecimal additionalPrice) {
        this.setAdditionalPrice(additionalPrice);
        return this;
    }

    public void setAdditionalPrice(BigDecimal additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    public BigDecimal getDeductionPrice() {
        return this.deductionPrice;
    }

    public Quotationitem deductionPrice(BigDecimal deductionPrice) {
        this.setDeductionPrice(deductionPrice);
        return this;
    }

    public void setDeductionPrice(BigDecimal deductionPrice) {
        this.deductionPrice = deductionPrice;
    }

    public String getMemo() {
        return this.memo;
    }

    public Quotationitem memo(String memo) {
        this.setMemo(memo);
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getUpdateCount() {
        return this.updateCount;
    }

    public Quotationitem updateCount(Long updateCount) {
        this.setUpdateCount(updateCount);
        return this;
    }

    public void setUpdateCount(Long updateCount) {
        this.updateCount = updateCount;
    }

    public Quotation getQuotation() {
        return this.quotation;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }

    public Quotationitem quotation(Quotation quotation) {
        this.setQuotation(quotation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quotationitem)) {
            return false;
        }
        return id != null && id.equals(((Quotationitem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quotationitem{" +
            "id=" + getId() +
            ", quotationNo='" + getQuotationNo() + "'" +
            ", quotationItemNo=" + getQuotationItemNo() +
            ", workerName='" + getWorkerName() + "'" +
            ", standardPrice=" + getStandardPrice() +
            ", count=" + getCount() +
            ", upperLimitHour=" + getUpperLimitHour() +
            ", lowerLimitHour=" + getLowerLimitHour() +
            ", additionalPrice=" + getAdditionalPrice() +
            ", deductionPrice=" + getDeductionPrice() +
            ", memo='" + getMemo() + "'" +
            ", updateCount=" + getUpdateCount() +
            "}";
    }
}
