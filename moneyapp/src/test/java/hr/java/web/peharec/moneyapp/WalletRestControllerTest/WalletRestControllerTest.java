package hr.java.web.peharec.moneyapp.WalletRestControllerTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import hr.java.web.peharec.moneyapp.Novcanik;
import hr.java.web.peharec.moneyapp.Trosak.VrstaTroska;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WalletRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test 
	public void findAllTest() throws Exception {
		this.mockMvc
			.perform(get("/api/wallet").with(user("admin").password("admin")
				.roles("USER", "ADMIN")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			//.andExpect(content()
				//.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", Matchers.hasSize(1)));
	}
	
	@Test 
	public void findOneTest() throws Exception {
		this.mockMvc
			.perform(get("/api/wallet/1").with(user("admin").password("admin")
				.roles("USER", "ADMIN")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			//.andExpect(content()
				//.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.naziv", is("Moja gotovina")));
	}
	
	@Test 
	public void postTest() throws Exception {
		
		Novcanik novcanik = new Novcanik();
		novcanik.setNaziv("drugi");
		
		this.mockMvc
			.perform(post("/api/wallet").with(user("admin").password("admin")
				.roles("USER", "ADMIN")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(novcanik)))
				.andExpect(status().isCreated());
				//.andExpect(content()
				//.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		
		this.mockMvc
			.perform(get("/api/wallet/2").with(user("admin").password("admin")
			.roles("USER", "ADMIN")).with(csrf())
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			//.andExpect(content()
				//.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.naziv", is("drugi")));
	}
	
	@Test 
	public void putTest() throws Exception {
		
		Novcanik novcanik = new Novcanik();
		novcanik.setNaziv("firstEdited");
		
		this.mockMvc
			.perform(put("/api/wallet/1").with(user("admin").password("admin")
				.roles("USER", "ADMIN")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(novcanik)))
			.andExpect(status().isOk());
			//.andExpect(content()
				//.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		
		this.mockMvc
			.perform(get("/api/wallet/1").with(user("admin").password("admin")
				.roles("USER", "ADMIN")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			//.andExpect(content()
				//.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.naziv", is("firstEdited")));
	}
	
	@Test 
	public void deleteTest() throws Exception {
		this.mockMvc
			.perform(delete("/api/wallet/2").with(user("admin").password("admin")
				.roles("USER", "ADMIN")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
			//.andExpect(content()
				//.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		
		this.mockMvc
			.perform(get("/api/wallet").with(user("admin").password("admin")
					.roles("USER", "ADMIN")).with(csrf())
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			//.andExpect(content()
				//.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", Matchers.hasSize(1)));
	}
}
