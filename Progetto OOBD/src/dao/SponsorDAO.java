package dao;

import java.util.List;

import entity.Sponsor;

public interface SponsorDAO {

	List<Sponsor> getAllsponsor(String nomeColonna);
	
	Sponsor getSponsor(String nomeCercato);

	void insertSponsor(Sponsor sponsor);

	void deleteSponsor(Sponsor sponsor);

	void updateSponsor(Sponsor nuovaSponsor, String vecchioNome);

}