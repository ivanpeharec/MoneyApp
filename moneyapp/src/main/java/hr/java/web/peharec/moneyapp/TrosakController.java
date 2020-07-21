package hr.java.web.peharec.moneyapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

//import lombok.extern.slf4j.Slf4j;
//@Slf4j
@Controller
@RequestMapping("/expenses")
@SessionAttributes({"vrste"})
public class TrosakController {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	// Load new expense template.
	@GetMapping("/new")
	public String trosakForm(Model model) {
		//log.info("Punim podatke za prikaz forme.");
		model.addAttribute("trosak", new Trosak());
		model.addAttribute("vrste", Trosak.VrstaTroska.values());
		
		return "trosak";
	}
	
	// Save an expense if it's valid.
	@PostMapping("/new")
	public String processForm(@Validated Trosak trosak, Errors errors, Model model) {

		// log.info("Procesiram trošak: " + trosak);
		
		if(errors.hasErrors()) {
			// log.info("Trošak ima grešaka. Prekida se slanje.");
			return "trosak";
		}
		else {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String currentUsername = authentication.getName();
			Novcanik novcanik = walletRepository.findByUsername(currentUsername);
			if (novcanik == null) {
				novcanik = new Novcanik();
				novcanik.setIznos(0.0);
				novcanik.setCreateDate(LocalDateTime.now());
				novcanik.setUsername(currentUsername);
				novcanik = walletRepository.save(novcanik);
			}

			LocalDate date = LocalDate.now();
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			String currentDate = date.format(pattern);
			
			trosak.setCreateDate(LocalDateTime.now());
			trosak.setNovcanik(novcanik);
			trosak = expenseRepository.save(trosak);
			
			
			novcanik.dodajTrosak(trosak);
			novcanik.calculateAmount();

			model.addAttribute("novcanik", novcanik);
			model.addAttribute("trosak", trosak);
			model.addAttribute("currentDate", currentDate);
			model.addAttribute("iznos", novcanik.getIznos());
			
			// log.info("Trošak uspješno unesen.");
			return "unesenTrosak";
		}
	}
	
	// Reset the wallet by deleting all expenses in it.
	@GetMapping("/resetWallet")
	public String resetWallet(SessionStatus status){
		status.setComplete();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
		Novcanik novcanik = walletRepository.findByUsername(currentUsername);
		
		if (novcanik == null) {
			novcanik = new Novcanik();
			novcanik = walletRepository.save(novcanik);
		}
		
		expenseRepository.deleteByNovcanik(novcanik);
		
		// log.info("Resetiranje novčanika.");
		
		return "redirect:/expenses/new";
	}
	
	@GetMapping("/search")
	public String displaySearchForm(Model model) {

		Iterable<Trosak> listaTroskova = expenseRepository.findAll();
		model.addAttribute("listaTroskova", listaTroskova);

		model.addAttribute("trosak", new Trosak());
			
		return "pretraziTrosak";
	}
	
	@PostMapping("/search")
	public String searchForm(Trosak trosak, Model model) {

			Iterable<Trosak> listaTroskova = expenseRepository.findByNazivContainingIgnoreCase(trosak.getNaziv());

			model.addAttribute("listaTroskova", listaTroskova);
			
			// log.info("Trošak uspješno unesen.");
			return "pretraziTrosak";
	}
	
	@GetMapping("/edit/{id}")
	public String editItem(Model model, @PathVariable("id") Long trosakId) {

		Trosak trosak = expenseRepository.findById(trosakId).get();
		model.addAttribute("trosak", trosak);
		model.addAttribute("trosakId", trosakId);
			
		return "urediTrosak";
	}
	
	@PostMapping("/edit")
	public String submitEditForm(@ModelAttribute("trosak") Trosak trosak, Model model) {
		
		Trosak editedTrosak = trosak;
		editedTrosak.setCreateDate(LocalDateTime.now());
		expenseRepository.save(editedTrosak);
		
		Iterable<Trosak> listaTroskova = expenseRepository.findAll();
		model.addAttribute("listaTroskova", listaTroskova);
		model.addAttribute("trosak", new Trosak());
			
			// log.info("Trošak uspješno unesen.");
			return "pretraziTrosak";
	}


}
