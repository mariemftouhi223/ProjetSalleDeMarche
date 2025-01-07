package tn.esprit.projetsalledemarche.Service.IMP;

import tn.esprit.projetsalledemarche.Entity.Souha.AnalyseTechnique;

import java.util.List;

public interface IAnalyseTechniqueService {
    List<AnalyseTechnique> getAllAnalyses();
    AnalyseTechnique getAnalyseById(Long id);
    AnalyseTechnique createAnalyse(AnalyseTechnique analyse);
    AnalyseTechnique updateAnalyse(Long id, AnalyseTechnique analyse);
    void deleteAnalyse(Long id);
}
