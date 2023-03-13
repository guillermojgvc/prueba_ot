package com.ontop.wallet.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ontop.wallet.dto.ResponsePaginatedDTO;
import com.ontop.wallet.dto.provider.AccountDTO;
import com.ontop.wallet.dto.provider.DestinationDTO;
import com.ontop.wallet.dto.provider.PaymentRequestDTO;
import com.ontop.wallet.dto.provider.ResponsePaymentDTO;
import com.ontop.wallet.dto.provider.SourceDTO;
import com.ontop.wallet.dto.provider.SourceInformationDTO;
import com.ontop.wallet.dto.wallet.ResponseBalanceDTO;
import com.ontop.wallet.dto.wallet.ResponseTransactionDTO;
import com.ontop.wallet.dto.wallet.WalletRequestDTO;
import com.ontop.wallet.entities.RecipientBank;
import com.ontop.wallet.entities.Status;
import com.ontop.wallet.entities.Transaction;
import com.ontop.wallet.entities.User;
import com.ontop.wallet.entities.Wallet;
import com.ontop.wallet.exception.NotEnoughFundException;
import com.ontop.wallet.exception.PreferedAccountNotFoundException;
import com.ontop.wallet.exception.UserNotFoundException;
import com.ontop.wallet.feign.PaymentFeignClient;
import com.ontop.wallet.repository.RecipientBankRepository;
import com.ontop.wallet.repository.StatusRepository;
import com.ontop.wallet.repository.TransactionRepository;
import com.ontop.wallet.repository.UserRepository;
import com.ontop.wallet.repository.WalletRepository;
import com.ontop.wallet.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WalletServiceImpl.class);
	
	@Value("${ontop.fee}")
	private double feePercentage;
	
	@Value("${ontop.processing.status}")
	private int idProcessingStatus;
	
	@Value("${ontop.completed.status}")
	private int idCompletedStatus;

	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private RecipientBankRepository recipientBankRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private PaymentFeignClient paymentFeignClient;
	
	@Transactional
	@Override
	public ResponseTransactionDTO modifyBalance(WalletRequestDTO dto) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Transaction transaction = new Transaction();
		ResponseTransactionDTO result = new ResponseTransactionDTO();
		User user = this.queryUser(dto.getUserId());
		Wallet wallet = this.queryWallet(user);
		 
		double fee = 0.0;
		double newBalance = wallet.getBalance() + dto.getAmount();
		
		transaction.setSrcCurrency(wallet.getTblCurrency().getId());
		transaction.setDesCurrency(wallet.getTblCurrency().getId());
		transaction.setSrcCurrencyAbreviate(wallet.getTblCurrency().getCurrencyAbbreviate());
		transaction.setDesCurrencyAbreviate(wallet.getTblCurrency().getCurrencyAbbreviate());
		
		transaction.setCurrentBalance(wallet.getBalance());
		transaction.setAmount(dto.getAmount());
		transaction.setCreatedDate(timestamp);
		transaction.setUpdatedDate(timestamp);
		transaction.setFee(fee);
		transaction.setTblWallet(wallet);
		
		if(dto.getAmount() < 0) {
			fee = dto.getAmount() * (feePercentage/100);
			newBalance += fee;
			transaction.setFee(fee);
			
			if(newBalance < 0) {
				// rollback transaction	not enough funds		
				throw new NotEnoughFundException();
			}
			
			transaction = this.withdraw(wallet,transaction);
		}else {
			transaction = this.topUp(wallet, transaction);
		}
		
		
		wallet.setBalance(newBalance);
		
		transactionRepository.save(transaction);
		walletRepository.save(wallet);
		
		result.setHttpStatus(HttpStatus.OK);
		result.setAmount(dto.getAmount());
		result.setUserId(dto.getUserId());
		result.setWalletTransactionId(transaction.getId());
		
		return result;
	}

	@Override
	public ResponseBalanceDTO currentBalance(Integer userId) {
		ResponseBalanceDTO result = new ResponseBalanceDTO();
		User user = this.queryUser(userId);
		Wallet wallet = this.queryWallet(user);
		
		result.setHttpStatus(HttpStatus.OK);
		result.setBalance(wallet.getBalance());
		result.setUserId(userId);
		
		return result;
	}
	
	@Override
	public ResponsePaginatedDTO listTransactions(int userId, int pageNumber, int pageSize, double val1, double val2, Timestamp inicio, Timestamp fin) {
		ResponsePaginatedDTO result = new ResponsePaginatedDTO();
		User user = this.queryUser(userId);
		Sort sort = Sort.by( Sort.Order.desc("createdDate") );
		Wallet wallet = this.queryWallet(user);
		Pageable pagination = PageRequest.of(pageNumber, pageSize, sort);
		Page<Transaction> pageTransaction = transactionRepository.findBytblWalletAndAmountBetweenAndCreatedDateBetween(wallet, val1, val2, inicio, fin, pagination);
		
		result.setElements(pageTransaction.getContent().stream().map(x -> {
			return new ResponseTransactionDTO(x.getId(), x.getAmount(), null, x.getCreatedDate(),x.getTransactionId());
			
		}).collect(Collectors.toList()) );
		
		result.setCurrentPage(pageTransaction.getNumber());
		result.setTotalElements(pageTransaction.getTotalElements());
		result.setTotalPages(pageTransaction.getTotalPages());
		result.setHttpStatus(HttpStatus.OK);
		
		return result;
	}
	
	private User queryUser(int userId) {
		Optional<User> optUser = userRepository.findById(userId);
		User user = optUser.orElseThrow(()-> new UserNotFoundException());
		return user;
	}
	
	private Wallet queryWallet(User user) {
		Optional<Wallet> optWallet = walletRepository.findByTblUser(user);
		Wallet wallet = optWallet.orElseThrow(()->new RuntimeException());
		return wallet;
	}
	
	
	private Transaction withdraw(Wallet wallet, Transaction transaction) {
		List<RecipientBank> listRecipientBanks = recipientBankRepository.findByTblUserAndPrefered(transaction.getTblWallet().getTblUser(), true);
		
		if(listRecipientBanks.isEmpty()) {
			throw new PreferedAccountNotFoundException();
		}
		
		Optional<Status> optStatus = statusRepository.findById(idProcessingStatus);
		Status status = optStatus.orElseThrow(()-> new RuntimeException());
		RecipientBank recipientBank = listRecipientBanks.get(0);
		
		transaction.setDesCurrency(wallet.getTblCurrency().getId());
		transaction.setPayment(true);
		transaction.setTblStatus(status);
		
		transaction.setSrcAccount(wallet.getAccountNumber());
		transaction.setDesAccount(recipientBank.getAccountNumber());
		transaction.setSrcRoutingNumber(wallet.getRoutingNumber());
		transaction.setDesRoutingNumber(recipientBank.getRoutingNumber());
		
		ResponsePaymentDTO responsePaymentDTO = this.sendPaymentoToProvider(transaction,recipientBank);
		transaction.setTransactionId(responsePaymentDTO.getPaymentInfo().getId());
		
		return transaction;
	}
	
	private Transaction topUp(Wallet wallet, Transaction transaction) {
		Optional<Status> optStatus = statusRepository.findById(idCompletedStatus);
		Status status = optStatus.orElseThrow(()-> new RuntimeException());
		transaction.setDesCurrency(wallet.getTblCurrency().getId());
		transaction.setPayment(false);
		transaction.setTblStatus(status);
		
		transaction.setDesAccount(wallet.getAccountNumber());
		transaction.setDesRoutingNumber(wallet.getRoutingNumber());
		
		return transaction;
	}
	
	private ResponsePaymentDTO sendPaymentoToProvider(Transaction transaction, RecipientBank recipientBank) {
		AccountDTO srcAccount = new AccountDTO(transaction.getSrcAccount(),transaction.getSrcCurrencyAbreviate(),transaction.getSrcRoutingNumber());
		AccountDTO desAccount = new AccountDTO(transaction.getDesAccount(),transaction.getDesCurrencyAbreviate(),transaction.getDesRoutingNumber());
		SourceInformationDTO sourceInformationDTO = new SourceInformationDTO("ONTOP INC");
		SourceDTO sourceDTO = new SourceDTO("COMPANY",sourceInformationDTO,srcAccount);
		DestinationDTO destinationDTO = new DestinationDTO(recipientBank.getFirstName() + " " + recipientBank.getLastName(), desAccount);
		PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO((-1) * transaction.getAmount(),sourceDTO,destinationDTO);
		
		return paymentFeignClient.createPayment(paymentRequestDTO);
	}
	

}
