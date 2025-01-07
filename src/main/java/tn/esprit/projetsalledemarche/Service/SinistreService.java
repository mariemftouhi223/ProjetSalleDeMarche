package tn.esprit.projetsalledemarche.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;
import tn.esprit.projetsalledemarche.Entity.SinistreDTO;
import tn.esprit.projetsalledemarche.Repository.ProduitAssuranceRepository;
import tn.esprit.projetsalledemarche.Repository.SinistreRepository;
import tn.esprit.projetsalledemarche.Entity.Sinistre;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class SinistreService implements ISinistreService {

    @Autowired
   private final SinistreRepository sinistreRepository;

    @Autowired
   private ProduitAssuranceRepository produitAssuranceRepository;
    @Autowired
    public SinistreService(SinistreRepository sinistreRepository) {
        this.sinistreRepository = sinistreRepository;
    }
    @Override
    public Sinistre addSinistre(Sinistre sinistre) {
        return sinistreRepository.save(sinistre);
    }

    @Override
    public Sinistre getSinistre(long idSinistre) {
        return sinistreRepository.findById(idSinistre).orElse(null);
    }

    @Override
    public SinistreDTO getSinistreDTO(long idSinistre) {
        return  sinistreRepository.findSinistreByIdWithProduitNom(idSinistre);
    }


    @Override
    public List<Sinistre> getAllSinistres() {
        return sinistreRepository.findAll();
    }

    @Override
    public void deleteSinistre(Long idSinistre) {
        sinistreRepository.deleteById(idSinistre);
    }

    @Override
    public Sinistre updateSinistre(Sinistre sinistre) {
        if (!sinistreRepository.existsById(sinistre.getIdSinistre())) {
            throw new RuntimeException("Sinistre introuvable pour l'ID : " + sinistre.getIdSinistre());
        }
        System.out.println("Sauvegarde du sinistre : " + sinistre);
        return sinistreRepository.save(sinistre);
    }



    @Override
    public Sinistre createSinistre(String nomProduit, Date dateSinistre, BigDecimal montantSinistre, String etatSinistre) {
        // Récupérer le produit d'assurance par son nom
        List<ProduitAssurance> produits = produitAssuranceRepository.findByNomProduit(nomProduit);

        if (produits.isEmpty()) {
            throw new RuntimeException("Produit Assurance introuvable pour le nom : " + nomProduit);
        } else if (produits.size() > 1) {
            throw new RuntimeException("Plusieurs produits trouvés pour le nom : " + nomProduit);
        }

        ProduitAssurance produitAssurance = produits.get(0);

        // Vérifier que le montant du sinistre ne dépasse pas la couverture
        if (montantSinistre.compareTo(produitAssurance.getCouverture()) > 0) {
            throw new RuntimeException("Montant du sinistre dépasse la couverture du produit.");
        }

        // Créer et sauvegarder le sinistre
        Sinistre sinistre = new Sinistre();
        sinistre.setDateSinistre(dateSinistre);
        sinistre.setMontantSinistre(montantSinistre);
        sinistre.setEtatSinistre(etatSinistre);
        sinistre.setProduitAssurance(produitAssurance);

        return sinistreRepository.save(sinistre);
    }


    @Override
    public List<Sinistre> getSinistresByDateRange(Date startDate, Date endDate) {
        return sinistreRepository.findByDateSinistreBetween(startDate, endDate);
    }

    @Override
    public List<Sinistre> getClosedSinistresByDateRange(Date startDate, Date endDate) {
        return sinistreRepository.findByDateSinistreBetweenAndEtatSinistre(startDate, endDate, "clos");
    }
}
