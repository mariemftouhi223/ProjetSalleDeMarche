package tn.esprit.projetsalledemarche.Service;
import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;
import tn.esprit.projetsalledemarche.Entity.Profil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IProduitAssuranceService {
    ProduitAssurance getProduitAssurance(long idProduit);

    void deleteProduitAssurance(Long idProduit);

    ProduitAssurance updateProduitAssurance(ProduitAssurance produitAssurance);

    List<ProduitAssurance> getAllProduitAssurance();

    ProduitAssurance addProduitAssurance(ProduitAssurance produitAssurance);

    BigDecimal calculatePrime(String nomActif, Date dateCalcul);

    BigDecimal calculateCoverage(String nomActif, Date dateCalcul);

    ProduitAssurance generateProduitAssurance(String nomActif, Date dateCalcul, String typeAssurance, Long IdProfil);

    Map<String, Object> calculateSinistrePrimeRatio(Long idProduit);

    BigDecimal calculateSinistrePrimeRatioForClosSinistres(String nomProduit);

    List<ProduitAssurance> getProduitsByDateRange(Date startDate, Date endDate);
}
