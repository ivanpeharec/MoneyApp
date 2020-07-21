package hr.java.web.peharec.moneyapp;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import hr.java.web.peharec.moneyapp.Trosak.VrstaTroska;

public class MyJob extends QuartzJobBean {

@Autowired
private WalletRepository walletRepository;

@Autowired
private ExpenseRepository expenseRepository;

@Override
protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

	Iterable<Trosak> listaTroskova = expenseRepository.findAll();
	System.out.println("__________________________________________________________\n");
	System.out.println("\t\t SUM \t\t MIN \t\t MAX\n");
	for (VrstaTroska vrsta : Trosak.VrstaTroska.values()) {
		
		Double suma = 0.0;
		Double min = 1000000.00;
		Double max = 0.0;
		
		for (Trosak trosak : listaTroskova) {
			if (trosak.getVrstaTroska() == vrsta) {
				suma += trosak.getIznos();
				
				if (trosak.getIznos() < min)
					min = trosak.getIznos();
				
				if (trosak.getIznos() > max)
					max = trosak.getIznos();
			}
		}
		
		System.out.println(vrsta.toString() + "\t\t" + suma + "\t\t" + min + "\t\t" + max + "\n");
	}
	System.out.println("__________________________________________________________\n");
}
}