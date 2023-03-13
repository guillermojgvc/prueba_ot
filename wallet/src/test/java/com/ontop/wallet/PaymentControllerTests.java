package com.ontop.wallet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.ontop.wallet.entities.provider.ProviderTransaction;
import com.ontop.wallet.repository.provider.ProviderTransactionRepository;


@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTests {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	ProviderTransactionRepository providerTransactionRepository;

	
	@Test
	public void testPayment400() throws Exception {
		MvcResult mvcResult = mockMvc.perform(
				post("/api/v1/payments")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}")
			).andExpect(status().isBadRequest()).andReturn();
		
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testPayment200() throws Exception {
		ProviderTransaction providerTransaction = new ProviderTransaction();
		providerTransaction.setId(UUID.randomUUID());
		providerTransaction.setStatus("processing");
		when(providerTransactionRepository.save(any())).thenReturn(providerTransaction);
		
		MvcResult mvcResult = mockMvc.perform(
				post("/api/v1/payments")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n"
						+ "    \"source\": {\n"
						+ "        \"type\": \"COMPANY\",\n"
						+ "        \"sourceInformation\": {\n"
						+ "            \"name\": \"ONTOP INC\"\n"
						+ "        },\n"
						+ "        \"account\": {\n"
						+ "            \"accountNumber\": \"0245253419\",\n"
						+ "            \"currency\": \"USD\",\n"
						+ "            \"routingNumber\": \"028444018\"\n"
						+ "        }\n"
						+ "    },\n"
						+ "    \"destination\": {\n"
						+ "        \"name\": \"TONY STARK\",\n"
						+ "        \"account\": {\n"
						+ "            \"accountNumber\": \"1885226711\",\n"
						+ "            \"currency\": \"USD\",\n"
						+ "            \"routingNumber\": \"211927207\"\n"
						+ "        }\n"
						+ "    },\n"
						+ "    \"amount\": 1000\n"
						+ "}")
			).andExpect(status().isOk()).andReturn();
		
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
}
