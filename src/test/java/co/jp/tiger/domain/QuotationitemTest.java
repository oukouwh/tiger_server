package co.jp.tiger.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.jp.tiger.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuotationitemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quotationitem.class);
        Quotationitem quotationitem1 = new Quotationitem();
        quotationitem1.setId(1L);
        Quotationitem quotationitem2 = new Quotationitem();
        quotationitem2.setId(quotationitem1.getId());
        assertThat(quotationitem1).isEqualTo(quotationitem2);
        quotationitem2.setId(2L);
        assertThat(quotationitem1).isNotEqualTo(quotationitem2);
        quotationitem1.setId(null);
        assertThat(quotationitem1).isNotEqualTo(quotationitem2);
    }
}
