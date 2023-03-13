package com.ontop.wallet.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ontop.wallet.dto.ResponsePaginatedDTO;
import com.ontop.wallet.dto.bank.BankAccountRequestDTO;
import com.ontop.wallet.dto.bank.ResponseBankAccountDTO;
import com.ontop.wallet.entities.RecipientBank;
import com.ontop.wallet.entities.User;
import com.ontop.wallet.exception.AccountAlreadyRegisteredException;
import com.ontop.wallet.exception.UserNotFoundException;
import com.ontop.wallet.repository.RecipientBankRepository;
import com.ontop.wallet.repository.UserRepository;
import com.ontop.wallet.service.BankAccountService;

@Service
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	private RecipientBankRepository recipientBankRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Transactional
	@Override
	public ResponseBankAccountDTO addBankAccount(BankAccountRequestDTO dto) {
		User user = this.queryUser(dto.getUserId());
		
		if(recipientBankRepository.existsByAccountNumberAndTblUser(dto.getAccountNumber(), user)) {
			throw new AccountAlreadyRegisteredException();
		}
		
		if(dto.getPrefered()) {
			List<RecipientBank> listRecipientBanks = recipientBankRepository.findByTblUserAndPrefered(user, true);
			listRecipientBanks.stream().map(x -> {
				x.setPrefered(false);
				recipientBankRepository.save(x);
				return x;
			});
		}
		
		RecipientBank recipientBank = new RecipientBank();
		ResponseBankAccountDTO result = new ResponseBankAccountDTO();
		recipientBank.setAccountNumber(dto.getAccountNumber());
		recipientBank.setBankName(dto.getBankName());
		recipientBank.setDniNumber(dto.getIdentification());
		recipientBank.setFirstName(dto.getFirstName());
		recipientBank.setLastName(dto.getLastName());
		recipientBank.setRoutingNumber(dto.getRoutingNumber());
		recipientBank.setTblUser(user);
		recipientBank.setPrefered(dto.getPrefered());
		recipientBankRepository.save(recipientBank);
		
		result.setBankAccountId(recipientBank.getId());
		result.setBankName(recipientBank.getBankName());
		result.setBankAccountNumber(recipientBank.getAccountNumber());
		result.setHttpStatus(HttpStatus.OK);
		
		return result;
	}

	@Override
	public ResponsePaginatedDTO listBankAccount(int userId, int pageNumber, int pageSize) {
		ResponsePaginatedDTO result = new ResponsePaginatedDTO();
		User user = this.queryUser(userId);
		Sort sort = Sort.by( Sort.Order.desc("accountNumber") );
		Pageable pagination = PageRequest.of(pageNumber, pageSize, sort);
		Page<RecipientBank> pageRecipientBank = recipientBankRepository.findByTblUser(user, pagination);
		
		result.setElements(pageRecipientBank.getContent().stream().map(x -> {
			return new ResponseBankAccountDTO(x.getId(), x.getBankName(), x.getAccountNumber(), x.getPrefered());
		}).collect(Collectors.toList()) );
		
		result.setCurrentPage(pageRecipientBank.getNumber());
		result.setTotalElements(pageRecipientBank.getTotalElements());
		result.setTotalPages(pageRecipientBank.getTotalPages());
		result.setHttpStatus(HttpStatus.OK);
		
		return result;
	}

	
	private User queryUser(int userId) {
		Optional<User> optUser = userRepository.findById(userId);
		User user = optUser.orElseThrow(()-> new UserNotFoundException());
		return user;
	}

}
