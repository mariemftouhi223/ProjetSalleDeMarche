package tn.esprit.projetsalledemarche.Service.IMP;

import tn.esprit.projetsalledemarche.Entity.Souha.DonneesHistoriques;

import java.util.List;

public interface IDonneesHistoriqueService {
    List<DonneesHistoriques> getAllDonneesHistorique();
    DonneesHistoriques getDonneesHistoriqueById(Long id);
    DonneesHistoriques createDonneesHistorique(DonneesHistoriques donneesHistorique);
    void deleteDonneesHistorique(Long id);
}
