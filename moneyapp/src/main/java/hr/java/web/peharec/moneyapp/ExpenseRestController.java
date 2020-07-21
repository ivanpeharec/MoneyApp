package hr.java.web.peharec.moneyapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(path="/api/expense", produces="application/json")
@CrossOrigin
public class ExpenseRestController {
	@Autowired
	private ExpenseRepository repository;
	@Autowired
	private WalletRepository walletRepository;
	
	public ExpenseRestController(ExpenseRepository repository, WalletRepository walletRepository) {
		this.repository = repository;
		this.walletRepository = walletRepository;
	}
	
	@GetMapping
	 public Iterable<Trosak> findAll() {
		return repository.findAll();
	 }
	
	@GetMapping("/{id}")
	public ResponseEntity<Trosak> findOne(@PathVariable Long id) {
		Trosak trosak = repository.findById(id).get();
		if(trosak != null) {
			return new ResponseEntity<>(trosak, HttpStatus.OK);
		} 
		else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	 
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes="application/json")
	public Trosak save(@RequestBody Trosak trosak, @RequestParam Long walletId) {
		Novcanik novcanik = walletRepository.findById(walletId).get();
		trosak.setCreateDate(LocalDateTime.now());
		trosak.setNovcanik(novcanik);
		
		return repository.save(trosak);
	}
	 
	@PutMapping("/{id}")
	public Trosak update(@RequestBody Trosak trosak, @PathVariable Long id) {
		Trosak stariTrosak = repository.findById(id).get();
			
		stariTrosak.setNaziv(trosak.getNaziv());
		stariTrosak.setIznos(trosak.getIznos());
		stariTrosak.setVrstaTroska(trosak.getVrstaTroska());
			
		return repository.save(stariTrosak);
	}
	 
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}

}