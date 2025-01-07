package tn.esprit.projetsalledemarche.Service.ser.marche;

import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.projetsalledemarche.Entity.Souha.GraphiquePerformance;
import tn.esprit.projetsalledemarche.Repository.souharepo.GraphiquePerformanceRepository;
import tn.esprit.projetsalledemarche.Service.IMP.IGraphiquePerformanceService;

import java.util.List;

public class GraphiquePerformanceService implements IGraphiquePerformanceService {
    @Autowired
    private GraphiquePerformanceRepository repository;

    @Override
    public List<GraphiquePerformance> getAllGraphiquesPerformance() {
        return repository.findAll();
    }

    @Override
    public GraphiquePerformance getGraphiquePerformanceById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Graphique de performance introuvable avec ID : " + id));
    }

    @Override
    public GraphiquePerformance createGraphiquePerformance(GraphiquePerformance graphiquePerformance) {
        return repository.save(graphiquePerformance);
    }

    @Override
    public GraphiquePerformance updateGraphiquePerformance(Long id, GraphiquePerformance graphiquePerformance) {
        GraphiquePerformance existing = getGraphiquePerformanceById(id);

        existing.setActifFinancier(graphiquePerformance.getActifFinancier());
        return repository.save(existing);
    }

    @Override
    public void deleteGraphiquePerformance(Long id) {
        repository.deleteById(id);
    }

}
