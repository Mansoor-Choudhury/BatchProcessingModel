package partners.configurations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LogConfigTest {

    @Autowired
    private Logger logger;

    @Test
    public void testLogger() {
        assertThat(logger).isNotNull();
        logger.info("Verify logger working");
    }
}
