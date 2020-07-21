package hr.java.web.peharec.moneyapp.TrosakControllerTest;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrosakControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test 
	public void AAA_searchAllExpensesTest() throws Exception {
		this.mockMvc
			.perform(get("/expenses/search").with(user("admin").password("admin")
			.roles("USER", "ADMIN")).with(csrf()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("listaTroskova"))
			.andExpect(model().attribute("listaTroskova", Matchers.hasSize(3)))
			.andExpect(view().name("pretraziTrosak"));
	}
	
	@Test 
	public void BBB_searchExpenseByNameTest() throws Exception {
		this.mockMvc
			.perform(post("/expenses/search").with(user("admin").password("admin")
			.roles("USER", "ADMIN")).with(csrf()).param("naziv", "firstExpense"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("listaTroskova"))
			.andExpect(model().attribute("listaTroskova", Matchers.hasSize(1)))
			.andExpect(view().name("pretraziTrosak"));
	}
	
	@Test 
	public void CCC_newExpenseWindowTest() throws Exception {
		this.mockMvc
			.perform(get("/expenses/new").with(user("admin").password("admin")
			.roles("USER", "ADMIN")).with(csrf()))
			.andExpect(status().isOk())
			.andExpect(view().name("trosak"));
	}
	
	
	@Test 
	public void DDD_newExpenseTest() throws Exception {
		this.mockMvc
			.perform(post("/expenses/new").with(user("admin").password("admin")
			.roles("USER", "ADMIN")).with(csrf())
			.param("naziv", "newExpense").param("vrstaTroska", "HRANA")
			.param("iznos", "35"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("trosak"))
			.andExpect(model().attributeExists("currentDate"))
			.andExpect(model().attributeExists("novcanik"))
			.andExpect(model().attributeExists("iznos"))
			.andExpect(view().name("unesenTrosak"));
	}
	
	
	/*@Test 
	public void EEE_resetWalletTest() throws Exception {
		this.mockMvc
			.perform(get("/expenses/resetWallet").with(user("admin").password("admin")
			.roles("USER", "ADMIN"))
			.with(csrf()))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/expenses/new"));
	}*/
}
