package hr.java.web.peharec.moneyapp;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(path="/api/wallet", produces="application/json")
@CrossOrigin
public class WalletRestController {
	
	private final ExpenseRepository repository;
	private final WalletRepository walletRepository;
	public WalletRestController(ExpenseRepository repository, WalletRepository walletRepository) {
		this.repository = repository;
		this.walletRepository = walletRepository;
	}
	@GetMapping
	public Iterable<Novcanik> findAll() {
		return walletRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Novcanik> findOne(@PathVariable Long id) {
		Novcanik novcanik = walletRepository.findById(id).get();
		if(novcanik != null) {
			return new ResponseEntity<>(novcanik, HttpStatus.OK);
		} 
		else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes="application/json")
	public Novcanik save(@RequestBody Novcanik novcanik) {
		novcanik.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		novcanik.setCreateDate(LocalDateTime.now());
		return walletRepository.save(novcanik);
	}
	
	@PutMapping("/{id}") 
	public Novcanik update(@PathVariable Long id, @RequestBody Novcanik novcanik) {
		Novcanik stariNovcanik = walletRepository.findById(id).get();
		
		stariNovcanik.setNaziv(novcanik.getNaziv());
		stariNovcanik.setListaTroskova(novcanik.getListaTroskova());
		stariNovcanik.setTipNovcanika(novcanik.getTipNovcanika());
		
		return walletRepository.save(stariNovcanik);
	} 
	
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	@DeleteMapping("/{id}") 
	public void delete(@PathVariable Long id) { 
		Novcanik novcanik = walletRepository.findById(id).get();
		
		for (Trosak t : novcanik.getListaTroskova()) {
			repository.deleteById(t.getId());
		}
		
		walletRepository.deleteById(id);
	}
}