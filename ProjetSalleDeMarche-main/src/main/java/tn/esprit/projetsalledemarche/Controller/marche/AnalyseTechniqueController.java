package tn.esprit.projetsalledemarche.Controller.marche;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Entity.Souha.AnalyseTechnique;
import tn.esprit.projetsalledemarche.Service.IMP.IAnalyseTechniqueService;

import java.util.List;
@RestController
@RequestMapping("/api/analyses")
public class AnalyseTechniqueController{
    @Autowired
    private IAnalyseTechniqueService service; // Injection de l'interface

    @GetMapping
    public List<AnalyseTechnique> getAllAnalyses() {
        return service.getAllAnalyses();
    }

    @GetMapping("/{id}")
    public AnalyseTechnique getAnalyseById(@PathVariable Long id) {
        return service.getAnalyseById(id);
    }

    @PostMapping
    public AnalyseTechnique createAnalyse(@RequestBody AnalyseTechnique analyse) {
        return service.createAnalyse(analyse);
    }

    @PutMapping("/{id}")
    public AnalyseTechnique updateAnalyse(@PathVariable Long id, @RequestBody AnalyseTechnique analyse) {
        return service.updateAnalyse(id, analyse);
    }

    @DeleteMapping("/{id}")
    public void deleteAnalyse(@PathVariable Long id) {
        service.deleteAnalyse(id);
    }
}
