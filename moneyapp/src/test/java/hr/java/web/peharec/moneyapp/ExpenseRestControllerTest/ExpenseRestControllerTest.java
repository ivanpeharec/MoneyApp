package hr.java.web.peharec.moneyapp.ExpenseRestControllerTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import hr.java.web.peharec.moneyapp.Trosak;
import hr.java.web.peharec.moneyapp.Trosak.VrstaTroska;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseRestControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test 
	public void findAllTest() throws Exception {
		this.mockMvc
			.perform(get("/api/expense").with(user("admin").password("admin")
				.roles("USER", "ADMIN")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			//.andExpect(content()
				//.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", Matchers.hasSize(4)));
	}
	
	@Test 
	public void findOneTest() throws Exception {
		this.mockMvc
			.perform(get("/api/expense/1").with(user("admin").password("admin")
				.roles("USER", "ADMIN")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			//.andExpect(content()
				//.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.naziv", is("firstExpense")));
	}
	
	@Test 
	public void postTest() throws Exception {
		
		Trosak trosak = new Trosak();
		trosak.setNaziv("noviTrosak");
		trosak.setVrstaTroska(VrstaTroska.valueOf("HRANA"));
		trosak.setIznos(55.50);
		
		this.mockMvc
			.perform(post("/api/expense?walletId=1").with(user("admin").password("admin")
				.roles("USER", "ADMIN")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(trosak)))
			.andExpect(status().isCreated());
			//.andExpect(content()
				//.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		
		this.mockMvc
			.perform(get("/api/expense/5").with(user("admin").password("admin")
				.roles("USER", "ADMIN")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			//.andExpect(content()
				//.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.naziv", is("noviTrosak")));
	}
	
	@Test 
	public void putTest() throws Exception {
		Trosak trosak = new Trosak();
		trosak.setNaziv("firstEdited");
		trosak.setVrstaTroska(VrstaTroska.valueOf("HRANA"));
		trosak.setIznos(35.50);
		
		this.mockMvc
			.perform(put("/api/expense/1").with(user("admin").password("admin")
				.roles("USER", "ADMIN")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(trosak)))
			.andExpect(status().isOk());
			//.andExpect(content()
				//.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		
		this.mockMvc
			.perform(get("/api/expense/1").with(user("admin").password("admin")
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
			.perform(delete("/api/expense/4").with(user("admin").password("admin")
				.roles("USER", "ADMIN")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
			//.andExpect(content()
				//.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		
		this.mockMvc
			.perform(get("/api/expense").with(user("admin").password("admin")
					.roles("USER", "ADMIN")).with(csrf())
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			//.andExpect(content()
				//.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", Matchers.hasSize(4)));
	}
}
