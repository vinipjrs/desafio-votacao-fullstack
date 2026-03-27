package com.cooperativa.votacao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

class BackJavaApplicationTest {

    @Test
    void main() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
            mockedSpringApplication.when(() -> SpringApplication.run(eq(BackJavaApplication.class), any(String[].class)))
                    .thenReturn(null);

            BackJavaApplication.main(new String[]{});

            mockedSpringApplication.verify(() -> SpringApplication.run(eq(BackJavaApplication.class), any(String[].class)));
        }
    }
}
