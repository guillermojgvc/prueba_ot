package com.ontop.wallet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ontop.wallet.dto.wallet.WalletRequestDTO;
import com.ontop.wallet.entities.Currency;
import com.ontop.wallet.entities.Status;
import com.ontop.wallet.entities.Transaction;
import com.ontop.wallet.entities.User;
import com.ontop.wallet.entities.Wallet;
import com.ontop.wallet.repository.StatusRepository;
import com.ontop.wallet.repository.TransactionRepository;
import com.ontop.wallet.repository.UserRepository;
import com.ontop.wallet.repository.WalletRepository;
import com.ontop.wallet.service.BankAccountService;


@SpringBootTest
@AutoConfigureMockMvc
class BankAccountControllerTests {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	BankAccountService bankAccountService;
	
	@MockBean
	WalletRepository walletRepository;

	@MockBean
	UserRepository userRepository;
	
	@MockBean
	TransactionRepository transactionRepository;
	
	@MockBean
	StatusRepository statusRepository;
	
	
	@Test
	public void testTransaction400() throws Exception {
		MvcResult mvcResult = mockMvc.perform(
				post("/wallets/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}")
			).andExpect(status().isBadRequest()).andReturn();
		
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testTransaction404() throws Exception {
		// Modify userRepository response		
		when(userRepository.findById(404)).thenReturn(Optional.empty());
		
		ObjectWriter writer = new ObjectMapper().writer();
		WalletRequestDTO requestDTO = new WalletRequestDTO(2000.0,404);

//		ResponseErrorDTO res = (ResponseErrorDTO)walletService.modifyBalance(requestDTO);
//		assertEquals(HttpStatus.BAD_REQUEST,res.getStatus());
//		assertEquals("INVALID_USER",res.getCode());
		
		MvcResult mvcResult = mockMvc.perform(
				post("/wallets/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(writer.writeValueAsString(requestDTO))
			).andExpect(status().isNotFound()).andReturn();
		
		
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testTransaction500() throws Exception {
		// Modify userRepository response		
		when(userRepository.findById(500)).thenThrow(new RuntimeException());
		
		ObjectWriter writer = new ObjectMapper().writer();
		WalletRequestDTO requestDTO = new WalletRequestDTO(2000.0,500);
		
		MvcResult mvcResult = mockMvc.perform(
				post("/wallets/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(writer.writeValueAsString(requestDTO))
			).andExpect(status().isInternalServerError()).andReturn();
		
		
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testTransaction200() throws Exception {
		// Modify userRepository response		
		Status status = new Status();
		Currency currency = new Currency();
		Wallet wallet = new Wallet();
		User user = new User();
		currency.setId(1);
		user.setId(1000);
		wallet.setBalance(2000.0);
		wallet.setTblCurrency(currency);
		status.setId(1);
		
		when(statusRepository.findById(anyInt())).thenReturn(Optional.of(status));
		when(userRepository.findById(1000)).thenReturn(Optional.of(user));
		when(walletRepository.findByTblUser(any(User.class))).thenReturn(Optional.of(wallet));
		when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);
		when(transactionRepository.save(any(Transaction.class))).then(t -> { 
			Transaction tx = t.getArgument(0);
			tx.setId(66319);
			return tx;
		});
		
		ObjectWriter writer = new ObjectMapper().writer();
		WalletRequestDTO requestDTO = new WalletRequestDTO(2000.0,1000);
		
		MvcResult mvcResult = mockMvc.perform(
				post("/wallets/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(writer.writeValueAsString(requestDTO))
			).andExpect(status().isOk()).andReturn();
		
		
		System.out.println(mvcResult.getResponse().getContentAsString());
	}

}
