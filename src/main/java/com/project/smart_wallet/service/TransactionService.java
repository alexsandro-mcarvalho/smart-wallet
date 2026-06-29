package com.project.smart_wallet.service;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.Transaction;
import com.project.smart_wallet.domain.TransactionType;
import com.project.smart_wallet.domain.User;
import com.project.smart_wallet.dto.request.CreateTransactionRequest;
import com.project.smart_wallet.dto.response.CreateTransactionResponse;
import com.project.smart_wallet.dto.response.PaginatedResponse;
import com.project.smart_wallet.dto.response.TransactionListResponse;
import com.project.smart_wallet.exception.BusinessException;
import com.project.smart_wallet.exception.NotFoundException;
import com.project.smart_wallet.mapper.TransactionMapper;
import com.project.smart_wallet.repository.AssetRepository;
import com.project.smart_wallet.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.project.smart_wallet.mapper.CreateTransactionMapper.toEntity;
import static com.project.smart_wallet.mapper.CreateTransactionMapper.toResponse;
import static com.project.smart_wallet.mapper.PageMapper.toResponse;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final AssetRepository assetRepository;

    private final UserService userService;

    public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {
        User user = userService.getAuthenticatedUser();
        Asset asset = assetRepository.findById(request.assetId())
                .orElseThrow(() -> new NotFoundException("Asset não encontrado"));

        if (request.type() == TransactionType.SELL) {
            BigDecimal assetQuantity = transactionRepository.getTotalQuantityByUserIdAndAssetId(
                    user.getId(),
                    asset.getId()
            );

            if (assetQuantity.compareTo(request.quantity()) < 0) {
                throw new BusinessException("Usuário não possui saldo suficente");
            }
        }

        Transaction transaction = toEntity(request, user, asset);

        transactionRepository.save(transaction);

        return toResponse(transaction);
    }

    public PaginatedResponse<TransactionListResponse> listTransactions(Pageable pageable) {
        User user = userService.getAuthenticatedUser();
        Page<TransactionListResponse> paginatedTransactions = transactionRepository.findAllByUser(user, pageable)
                .map(TransactionMapper::toResponse);

        return toResponse(paginatedTransactions);
    }
}
