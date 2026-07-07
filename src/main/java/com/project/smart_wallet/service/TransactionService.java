package com.project.smart_wallet.service;

import com.project.smart_wallet.domain.Asset;
import com.project.smart_wallet.domain.Transaction;
import com.project.smart_wallet.domain.TransactionType;
import com.project.smart_wallet.domain.User;
import com.project.smart_wallet.domain.WalletHolding;
import com.project.smart_wallet.dto.request.CreateTransactionRequest;
import com.project.smart_wallet.dto.response.CreateTransactionResponse;
import com.project.smart_wallet.dto.response.PaginatedResponse;
import com.project.smart_wallet.dto.response.TransactionListResponse;
import com.project.smart_wallet.exception.BusinessException;
import com.project.smart_wallet.exception.NotFoundException;
import com.project.smart_wallet.mapper.TransactionMapper;
import com.project.smart_wallet.repository.AssetRepository;
import com.project.smart_wallet.repository.TransactionRepository;
import com.project.smart_wallet.repository.WalletHoldingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static com.project.smart_wallet.domain.TransactionType.SELL;
import static com.project.smart_wallet.mapper.CreateTransactionMapper.toEntity;
import static com.project.smart_wallet.mapper.CreateTransactionMapper.toResponse;
import static com.project.smart_wallet.mapper.PageMapper.toResponse;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final AssetRepository assetRepository;

    private final WalletHoldingRepository walletHoldingRepository;

    private final UserService userService;

    @Transactional
    public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {
        User user = userService.getAuthenticatedUser();
        Asset asset = assetRepository.findById(request.assetId())
                .orElseThrow(() -> new NotFoundException("Asset não encontrado"));

        Transaction transaction = toEntity(request, user, asset);
        WalletHolding walletHolding = applyTransactionToWalletHolding(transaction);

        transactionRepository.save(transaction);
        walletHoldingRepository.save(walletHolding);

        return toResponse(transaction);
    }

    private WalletHolding applyTransactionToWalletHolding(Transaction transaction) {
        Optional<WalletHolding> walletHolding = walletHoldingRepository.findByUserAndAsset(
                transaction.getUser(), transaction.getAsset()
        );

        if (transaction.getType() == SELL) {

            if (walletHolding.isEmpty() || walletHolding.get().getQuantity().compareTo(transaction.getQuantity()) < 0) {
                throw new BusinessException("Saldo de ativos insuficientes");
            }
        }

        return walletHolding.map(holding -> holding.applyTransaction(transaction))
                .orElseGet(() -> new WalletHolding(
                        transaction.getUser(),
                        transaction.getAsset(),
                        transaction.getQuantity()
                ));
    }

    public PaginatedResponse<TransactionListResponse> listTransactions(Pageable pageable) {
        User user = userService.getAuthenticatedUser();
        Page<TransactionListResponse> paginatedTransactions = transactionRepository.findAllByUser(user, pageable)
                .map(TransactionMapper::toResponse);

        return toResponse(paginatedTransactions);
    }
}
