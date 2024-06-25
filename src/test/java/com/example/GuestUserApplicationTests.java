package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GuestUserApplicationTests {

	@Test
    void applicationStarts() {
        GuestUserApplication.main(new String[] {});
        Assertions.assertDoesNotThrow(()->{});
    }

}
