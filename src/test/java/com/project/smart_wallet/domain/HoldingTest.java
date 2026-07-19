package com.project.smart_wallet.domain;

import com.project.smart_wallet.exception.BusinessException;
import com.project.smart_wallet.testDataBuilder.domain.TransactionBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.project.smart_wallet.testDataBuilder.domain.TransactionBuilder.aSell;
import static org.junit.jupiter.api.Assertions.*;

class HoldingTest {

    @DisplayName("Should throw a BusinessException when a holding be initialize with a sell transaction")
    @Test
    void ShouldThrowBusinessExceptionWhenHoldingCreatedWithSellTransaction() {

        Transaction transaction = aSell().build();

        Assertions.assertThrows(BusinessException.class, () -> new Holding(transaction));
    }

}