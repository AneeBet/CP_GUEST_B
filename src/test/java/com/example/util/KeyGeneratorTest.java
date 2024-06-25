package com.example.util;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.SecureRandom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.repository.GuestProfileRepository;

@ExtendWith(MockitoExtension.class)
public class KeyGeneratorTest {

    @Mock
    private GuestProfileRepository guestProfileRepository;

    @InjectMocks
    private KeyGenerator keyGenerator;

    @Test
    void testGenerateUniqueApplicationId() {
        // Arrange
        SecureRandom random = new SecureRandom();
        long expectedId = 1000000000L + (long) (random.nextDouble() * 9000000000L);
        when(guestProfileRepository.existsByApplicationId(anyLong()))
            .thenReturn(true)  // Assume the first generated ID exists
            .thenReturn(false);  // Second generated ID does not exist

        // Act
        Long resultId = keyGenerator.generateUniqueApplicationId();

        // Assert
        assertNotNull(resultId);
        verify(guestProfileRepository, times(2)).existsByApplicationId(anyLong());  // Check that method was retried
        assertNotEquals(expectedId, resultId);  // Ensure the second ID was generated
    }
}
