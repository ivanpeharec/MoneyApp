package hr.java.web.peharec.moneyapp;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="expenses")
public class Trosak implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotNull
    @Size(min=6, max=30, message="{validation.expense.name.size}")
	@Column(name = "name")
	private String naziv;
	
	@NotNull(message = "{validation.expense.type.notNull}")
	@DecimalMin(value = "0.01", message = "{validation.expense.amount.decimalMin}")	
	@Column(name = "amount")
	private double iznos;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "entry_date")
	private LocalDateTime createDate;
	
	@ManyToOne
	@JoinColumn(name="id_wallet")
	@JsonBackReference
	private Novcanik novcanik;
	
	public static enum VrstaTroska {
		ODJECA, OBUCA, HRANA
	}
	
	@NotNull(message="{validation.expense.type.notNull}")
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private VrstaTroska vrstaTroska;
	
	public Trosak() {
		
	}
	
	public Trosak(String t_naziv, double t_iznos, VrstaTroska t_vrstaTroska){
		this.naziv = t_naziv;
		this.iznos = t_iznos;
		this.vrstaTroska = t_vrstaTroska;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Double getIznos() {
		return iznos;
	}

	public void setIznos(double iznos) {
		this.iznos = iznos;
	}
	
	public VrstaTroska getVrstaTroska() {
		return vrstaTroska;
	}

	public void setVrstaTroska(VrstaTroska vrstaTroska) {
		this.vrstaTroska = vrstaTroska;
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
	
	public Novcanik getNovcanik() {
		return novcanik;
	}

	public void setNovcanik(Novcanik novcanik) {
		this.novcanik = novcanik;
	}

}