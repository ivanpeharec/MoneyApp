package hr.java.web.peharec.moneyapp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ExpenseRepository extends CrudRepository<Trosak, Long>{
	List<Trosak> findByNovcanik(Novcanik novcanik);
	List<Trosak> findByNazivContainingIgnoreCase(String naziv);
	List<Trosak> findByVrstaTroska(Trosak.VrstaTroska vrstaTroska);
	@Transactional
	void deleteByNovcanik(Novcanik novcanik);
	
}
