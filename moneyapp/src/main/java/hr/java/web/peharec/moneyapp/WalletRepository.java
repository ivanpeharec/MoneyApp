package hr.java.web.peharec.moneyapp;

import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Novcanik, Long>{
	Novcanik findByUsername(String username);
}
