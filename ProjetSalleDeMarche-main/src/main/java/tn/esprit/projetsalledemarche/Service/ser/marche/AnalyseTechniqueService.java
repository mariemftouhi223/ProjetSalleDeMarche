package tn.esprit.projetsalledemarche.Service.ser.marche;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Souha.AnalyseTechnique;
import tn.esprit.projetsalledemarche.Repository.souharepo.AnalyseTechniqueRepository;
import tn.esprit.projetsalledemarche.Service.IMP.IAnalyseTechniqueService;

import java.util.List;
@Service
public class AnalyseTechniqueService implements IAnalyseTechniqueService {
    @Autowired
    private AnalyseTechniqueRepository repository;

    @Override
    public List<AnalyseTechnique> getAllAnalyses() {
        return repository.findAll();
    }

    @Override
    public AnalyseTechnique getAnalyseById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Analyse introuvable avec ID : " + id));
    }

    @Override
    public AnalyseTechnique createAnalyse(AnalyseTechnique analyse) {
        return repository.save(analyse);
    }

    @Override
    public AnalyseTechnique updateAnalyse(Long id, AnalyseTechnique analyse) {
        AnalyseTechnique existing = getAnalyseById(id);
        existing.setValeurIndicateur(analyse.getValeurIndicateur());
        existing.setTypeIndicateur(analyse.getTypeIndicateur());
        existing.setDateAnalyse(analyse.getDateAnalyse());
        existing.setActifFinancier(analyse.getActifFinancier());
        return repository.save(existing);
    }

    @Override
    public void deleteAnalyse(Long id) {
        repository.deleteById(id);
    }
}
