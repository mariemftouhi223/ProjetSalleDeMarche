package tn.esprit.projetsalledemarche.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.*;
import tn.esprit.projetsalledemarche.Repository.ModeleActuarielRepository;
import tn.esprit.projetsalledemarche.Repository.ProduitAssuranceRepository;
import tn.esprit.projetsalledemarche.Repository.ProfilRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class ProduitAssuranceService implements IProduitAssuranceService {

    @Autowired
    private ProduitAssuranceRepository produitAssuranceRepository;

    @Autowired
    private ModeleActuarielService modeleActuarielService;

    @Autowired
    private ProfilRepository profilRepository;

    @Override
    public ProduitAssurance getProduitAssurance(long idProduit) {
        return produitAssuranceRepository.findById(idProduit).orElse(null);
    }

    @Override
    public void deleteProduitAssurance(Long idProduit) {
        produitAssuranceRepository.deleteById(idProduit);
    }

    @Override
    public ProduitAssurance updateProduitAssurance(ProduitAssurance updatedProduit) {
        // Fetch the existing product from the database
        ProduitAssurance existingProduit = produitAssuranceRepository.findById(updatedProduit.getIdProduit())
                .orElseThrow(() -> new EntityNotFoundException("ProduitAssurance not found with ID: " + updatedProduit.getIdProduit()));

        // Update only the fields that are not null in the updated entity
        if (updatedProduit.getNomProduit() != null) {
            existingProduit.setNomProduit(updatedProduit.getNomProduit());
        }
        if (updatedProduit.getPrime() != null) {
            existingProduit.setPrime(updatedProduit.getPrime());
        }
        if (updatedProduit.getCouverture() != null) {
            existingProduit.setCouverture(updatedProduit.getCouverture());
        }
        if (updatedProduit.getAtype() != null) {
            existingProduit.setAtype(updatedProduit.getAtype());
        }

        // Save and return the updated product
        return produitAssuranceRepository.save(existingProduit);
    }


    @Override
    public List<ProduitAssurance> getAllProduitAssurance() {
        return produitAssuranceRepository.findAll();
    }

    @Override
    public ProduitAssurance addProduitAssurance(ProduitAssurance produitAssurance) {
        // Base name of the product

        // Count existing products with the base name


        // Save the product and return it
        return produitAssuranceRepository.save(produitAssurance);
    }



    public BigDecimal calculatePrime(String nomActif, Date dateCalcul) {
        List<Double> predictions = modeleActuarielService.getPredictionsAroundDate(nomActif, dateCalcul);

        if (predictions.isEmpty()) {
            throw new RuntimeException("Aucune prédiction trouvée pour l'actif : " + nomActif);
        }

        double moyenne = predictions.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double prime = (moyenne * 0.01) + 50 + (moyenne * 0.1); // Exemple de calcul
        return BigDecimal.valueOf(prime);
    }

    public BigDecimal calculateCoverage(String nomActif, Date dateCalcul) {
        List<Double> predictions = modeleActuarielService.getPredictionsAroundDate(nomActif, dateCalcul);

        if (predictions.isEmpty()) {
            throw new RuntimeException("Aucune prédiction trouvée pour l'actif : " + nomActif);
        }

        double maxPrediction = predictions.stream().mapToDouble(Double::doubleValue).max().orElse(0);
        double couverture = maxPrediction * 3; // Exemple de calcul
        return BigDecimal.valueOf(couverture);
    }



    public ProduitAssurance generateProduitAssurance(String nomActif, Date dateCalcul, String typeAssurance, Long idProfil) {
        // Validation des paramètres
        if (nomActif == null || nomActif.isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'actif ne peut pas être null ou vide.");
        }
        if (dateCalcul == null) {
            throw new IllegalArgumentException("La date de calcul ne peut pas être null.");
        }
        if (typeAssurance == null || typeAssurance.isEmpty()) {
            throw new IllegalArgumentException("Le type d'assurance ne peut pas être null ou vide.");
        }
        if (idProfil == null) {
            throw new IllegalArgumentException("L'ID du profil ne peut pas être null.");
        }

        // Récupération du profil
        Profil profil = profilRepository.findById(idProfil)
                .orElseThrow(() -> new RuntimeException("Profil utilisateur introuvable pour l'ID : " + idProfil));

        // Calcul de la prime et de la couverture
        BigDecimal prime = calculatePrime(nomActif, dateCalcul);
        BigDecimal couverture = calculateCoverage(nomActif, dateCalcul);
        // Count existing products with the base name
        long count = produitAssuranceRepository.countByNomProduitStartingWith("Assurance " + nomActif);

        // If count is greater than zero, append it as a suffix
        if (count > 0) {
            nomActif=nomActif+count;
        }
        // Création de l'objet ProduitAssurance
        ProduitAssurance produitAssurance = new ProduitAssurance();
        produitAssurance.setNomProduit("Assurance " + nomActif); // Nom du produit
        produitAssurance.setPrime(prime); // Prime calculée
        produitAssurance.setCouverture(couverture); // Couverture calculée
        produitAssurance.setAtype(TypeAssurance.valueOf(typeAssurance.toUpperCase())); // Conversion en majuscules pour correspondre à l'énumération
        produitAssurance.setProfil(profil); // Association avec le profil

        // Enregistrement en base de données
        return produitAssuranceRepository.save(produitAssurance);
    }


    public Map<String, Object> calculateSinistrePrimeRatio(Long idProduit) {
        ProduitAssurance produitAssurance = produitAssuranceRepository.findById(idProduit)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé pour l'ID : " + idProduit));

        BigDecimal prime = produitAssurance.getPrime();
        if (prime == null || prime.compareTo(BigDecimal.ZERO) == 0) {
            throw new RuntimeException("Prime non définie pour le produit ID : " + idProduit);
        }

        BigDecimal totalSinistres = produitAssurance.getSinistres().stream()
                .map(Sinistre::getMontantSinistre)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal ratio = totalSinistres.divide(prime, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        Map<String, Object> response = new HashMap<>();
        response.put("idProduitAssurance", idProduit);
        response.put("ratioSinistrePrime", ratio);
        response.put("commentaire", ratio.compareTo(BigDecimal.valueOf(100)) < 0 ? "Produit rentable" : "Produit déficitaire");

        return response;
    }

    @Override
    public BigDecimal calculateSinistrePrimeRatioForClosSinistres(String nomProduit) {
        // Récupérer tous les produits avec le même nom
        List<ProduitAssurance> produits = produitAssuranceRepository.findByNomProduit(nomProduit);

        if (produits.isEmpty()) {
            throw new RuntimeException("Aucun produit trouvé pour le nom : " + nomProduit);
        }

        // Variables globales pour le calcul
        BigDecimal totalMontantSinistres = BigDecimal.ZERO;
        BigDecimal totalPrime = BigDecimal.ZERO;

        for (ProduitAssurance produitAssurance : produits) {
            Set<Sinistre> sinistres = produitAssurance.getSinistres();

            // Filtrer les sinistres clos
            List<Sinistre> sinistresClos = sinistres.stream()
                    .filter(sinistre -> "clos".equalsIgnoreCase(sinistre.getEtatSinistre()))
                    .toList();

            if (!sinistresClos.isEmpty()) {
                // Calculer le montant total des sinistres clos pour ce produit
                BigDecimal montantSinistres = sinistresClos.stream()
                        .map(Sinistre::getMontantSinistre)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                totalMontantSinistres = totalMontantSinistres.add(montantSinistres);
            }

            // Ajouter la prime de ce produit
            BigDecimal prime = produitAssurance.getPrime();
            if (prime == null || prime.compareTo(BigDecimal.ZERO) == 0) {
                throw new RuntimeException("La prime est inexistante ou égale à zéro pour le produit : " + produitAssurance.getNomProduit());
            }

            totalPrime = totalPrime.add(prime);
        }

        // Vérifier si la prime totale est valide
        if (totalPrime.compareTo(BigDecimal.ZERO) == 0) {
            throw new RuntimeException("La prime totale est inexistante ou égale à zéro.");
        }

        // Calculer et retourner le ratio global
        return totalMontantSinistres.divide(totalPrime, 2, RoundingMode.HALF_UP);
    }


    public List<ProduitAssurance> getProduitsByDateRange(Date startDate, Date endDate) {
        return produitAssuranceRepository.findByModelesActuariels_DateCalculBetween(startDate, endDate);
    }
}
