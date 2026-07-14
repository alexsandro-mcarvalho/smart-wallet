package com.project.smart_wallet.service;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.Holding;
import com.project.smart_wallet.domain.Transaction;
import com.project.smart_wallet.domain.User;
import com.project.smart_wallet.dto.request.CreateTransactionRequest;
import com.project.smart_wallet.dto.response.CreateTransactionResponse;
import com.project.smart_wallet.exception.NotFoundException;
import com.project.smart_wallet.repository.AssetRepository;
import com.project.smart_wallet.repository.HoldingRepository;
import com.project.smart_wallet.repository.TransactionRepository;
import com.project.smart_wallet.testDataBuilder.domain.AssetBuilder;
import com.project.smart_wallet.testDataBuilder.domain.UserBuilder;
import com.project.smart_wallet.testDataBuilder.dto.CreateTransactionRequestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


import static com.project.smart_wallet.testDataBuilder.domain.AssetBuilder.anAsset;
import static com.project.smart_wallet.testDataBuilder.domain.UserBuilder.aUser;
import static com.project.smart_wallet.testDataBuilder.dto.CreateTransactionRequestBuilder.aBuyCreateTransactionRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private HoldingRepository holdingRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TransactionService transactionService;

    @DisplayName("Should throw a NotFoundException when the asset does not exists")
    @Test
    void ShouldThrowExceptionWhenAssetDoesNotExists() {
        CreateTransactionRequest request = aBuyCreateTransactionRequest().build();

        when(assetRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> transactionService.createTransaction(request));

        verify(transactionRepository, never()).save(any(Transaction.class));
        verify(holdingRepository, never()).save(any(Holding.class));
    }

    @DisplayName("Should create a buy transaction")
    @Test
    void shouldCreateBuyTransaction() {

        CreateTransactionRequest request = aBuyCreateTransactionRequest().build();
        User user = aUser().build();
        Asset asset = anAsset().build();

        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(assetRepository.findById(anyLong())).thenReturn(Optional.of(asset));
        when(holdingRepository.findByUserAndAsset(any(User.class), any(Asset.class))).thenReturn(Optional.empty());

        CreateTransactionResponse response = transactionService.createTransaction(request);

        verify(transactionRepository).save(any(Transaction.class));
        verify(holdingRepository).save(any(Holding.class));

        assertEquals(request.type(), response.type());
        assertEquals(request.quantity(), response.quantity());
        assertEquals(request.price(), response.price());
        assertEquals(request.transactionAt(), response.transactionAt());
        assertEquals(asset.getId(), response.asset().id());
    }

}