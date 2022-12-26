package co.jp.tiger.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Foo.
 */
@Entity
@Table(name = "foo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Foo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "foo_name", nullable = false)
    private String fooName;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "mobile", nullable = false)
    private String mobile;

    @NotNull
    @Column(name = "foo_no", nullable = false)
    private String fooNo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Foo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFooName() {
        return this.fooName;
    }

    public Foo fooName(String fooName) {
        this.setFooName(fooName);
        return this;
    }

    public void setFooName(String fooName) {
        this.fooName = fooName;
    }

    public String getAddress() {
        return this.address;
    }

    public Foo address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return this.mobile;
    }

    public Foo mobile(String mobile) {
        this.setMobile(mobile);
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFooNo() {
        return this.fooNo;
    }

    public Foo fooNo(String fooNo) {
        this.setFooNo(fooNo);
        return this;
    }

    public void setFooNo(String fooNo) {
        this.fooNo = fooNo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Foo)) {
            return false;
        }
        return id != null && id.equals(((Foo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Foo{" +
            "id=" + getId() +
            ", fooName='" + getFooName() + "'" +
            ", address='" + getAddress() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", fooNo='" + getFooNo() + "'" +
            "}";
    }
}
