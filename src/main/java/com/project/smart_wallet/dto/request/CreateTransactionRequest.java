package com.project.smart_wallet.dto.request;

import com.project.smart_wallet.domain.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateTransactionRequest(

        @NotNull(message = "Campo não pode ser vazio")
        Long assetId,

        @NotNull(message = "Campo não pode ser vazio")
        @Positive(message = "Quantidade deve ser valor positivo")
        BigDecimal quantity,

        @NotNull(message = "Campo não pode ser vazio")
        @PositiveOrZero(message = "Valor deve ser maior ou igual a 0")
        BigDecimal price,

        @PastOrPresent(message = "Transação não pode ser feito no futuro")
        @NotNull(message = "Campo não pode ser vazio")
        Instant transactionAt,

        @NotNull(message = "Campo não pode ser vazio")
        TransactionType type
) {
}
