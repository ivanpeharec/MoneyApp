package hr.java.web.peharec.moneyapp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "wallets")
public class Novcanik implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String naziv;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private TipNovcanika tipNovcanika;

	@Column(name = "entry_date")
	private LocalDateTime createDate;

	@Column(name = "username")
	private String username;

	@Column(name = "amount")
	private Double iznos;

	@JsonManagedReference
	@OneToMany(mappedBy = "novcanik", fetch = FetchType.EAGER)
	private List<Trosak> listaTroskova;

	public Novcanik() {
		this.listaTroskova = new ArrayList<Trosak>();
		this.tipNovcanika = TipNovcanika.VLASTITI;
		this.naziv = "Moja Gotovina";
		this.createDate = LocalDateTime.now();
		this.iznos = 0.0;
	}

	public List<Trosak> getListaTroskova() {
		return listaTroskova;
	}

	public void setListaTroskova(List<Trosak> listaTroskova) {
		this.listaTroskova = listaTroskova;
	}

	public void dodajTrosak(Trosak trosak) {
		this.listaTroskova.add(trosak);
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public TipNovcanika getTipNovcanika() {
		return tipNovcanika;
	}

	public void setTipNovcanika(TipNovcanika tipNovcanika) {
		this.tipNovcanika = tipNovcanika;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public static enum TipNovcanika {
		VLASTITI, POSLOVNI, MASTERCARD, MAESTRO, VISA, DINERS
	}

	public void calculateAmount() {
		this.iznos = -listaTroskova.stream().mapToDouble(t -> t.getIznos()).sum();
	}

	public Double getIznos() {
		return iznos;
	}

	public void setIznos(Double iznos) {
		this.iznos = iznos;
	}
}
