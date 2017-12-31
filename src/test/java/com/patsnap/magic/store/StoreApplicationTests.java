package com.patsnap.magic.store;

import com.patsnap.magic.store.service.CategoryServiceImplTest;
import com.patsnap.magic.store.service.OrderServiceImplTest;
import com.patsnap.magic.store.service.ProductServiceImplTest;
import com.patsnap.magic.store.service.UserServiceImplTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CategoryServiceImplTest.class,
        OrderServiceImplTest.class,
        ProductServiceImplTest.class,
        UserServiceImplTest.class})

public class StoreApplicationTests {

    @Test
    public void contextLoads() {
    }

}
