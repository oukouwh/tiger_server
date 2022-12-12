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
    private BigDecimal count;

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
    @JsonIgnoreProperties("quotationItems")
    private Quotation quotation;

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

    public Quotationitem quotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
        return this;
    }

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
    }

    public Long getQuotationItemNo() {
        return quotationItemNo;
    }

    public Quotationitem quotationItemNo(Long quotationItemNo) {
        this.quotationItemNo = quotationItemNo;
        return this;
    }

    public void setQuotationItemNo(Long quotationItemNo) {
        this.quotationItemNo = quotationItemNo;
    }

    public String getWorkerName() {
        return workerName;
    }

    public Quotationitem workerName(String workerName) {
        this.workerName = workerName;
        return this;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public BigDecimal getStandardPrice() {
        return standardPrice;
    }

    public Quotationitem standardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
        return this;
    }

    public void setStandardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
    }

    public BigDecimal getCount() {
        return count;
    }

    public Quotationitem count(BigDecimal count) {
        this.count = count;
        return this;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getUpperLimitHour() {
        return upperLimitHour;
    }

    public Quotationitem upperLimitHour(BigDecimal upperLimitHour) {
        this.upperLimitHour = upperLimitHour;
        return this;
    }

    public void setUpperLimitHour(BigDecimal upperLimitHour) {
        this.upperLimitHour = upperLimitHour;
    }

    public BigDecimal getLowerLimitHour() {
        return lowerLimitHour;
    }

    public Quotationitem lowerLimitHour(BigDecimal lowerLimitHour) {
        this.lowerLimitHour = lowerLimitHour;
        return this;
    }

    public void setLowerLimitHour(BigDecimal lowerLimitHour) {
        this.lowerLimitHour = lowerLimitHour;
    }

    public BigDecimal getAdditionalPrice() {
        return additionalPrice;
    }

    public Quotationitem additionalPrice(BigDecimal additionalPrice) {
        this.additionalPrice = additionalPrice;
        return this;
    }

    public void setAdditionalPrice(BigDecimal additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    public BigDecimal getDeductionPrice() {
        return deductionPrice;
    }

    public Quotationitem deductionPrice(BigDecimal deductionPrice) {
        this.deductionPrice = deductionPrice;
        return this;
    }

    public void setDeductionPrice(BigDecimal deductionPrice) {
        this.deductionPrice = deductionPrice;
    }

    public String getMemo() {
        return memo;
    }

    public Quotationitem memo(String memo) {
        this.memo = memo;
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getUpdateCount() {
        return updateCount;
    }

    public Quotationitem updateCount(Long updateCount) {
        this.updateCount = updateCount;
        return this;
    }

    public void setUpdateCount(Long updateCount) {
        this.updateCount = updateCount;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public Quotationitem quotation(Quotation quotation) {
        this.quotation = quotation;
        return this;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
        return 31;
    }

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
