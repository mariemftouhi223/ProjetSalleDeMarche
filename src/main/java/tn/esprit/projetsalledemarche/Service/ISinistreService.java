package tn.esprit.projetsalledemarche.Service;

import tn.esprit.projetsalledemarche.Entity.Sinistre;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ISinistreService  {


    Sinistre addSinistre(Sinistre sinistre);

    Sinistre getSinistre(long idSinistre);

    List<Sinistre> getAllSinistres();

    void deleteSinistre(Long idSinistre);

    Sinistre updateSinistre(Sinistre sinistre);

    Sinistre createSinistre(String nomProduit, Date dateSinistre, BigDecimal montantSinistre, String etatSinistre);

    List<Sinistre> getSinistresByDateRange(Date startDate, Date endDate);

    List<Sinistre> getClosedSinistresByDateRange(Date startDate, Date endDate);
}