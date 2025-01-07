package tn.esprit.projetsalledemarche.Service.IMP;

import tn.esprit.projetsalledemarche.Entity.Souha.GraphiquePerformance;

import java.util.List;

public interface IGraphiquePerformanceService {
    List<GraphiquePerformance> getAllGraphiquesPerformance();
    GraphiquePerformance getGraphiquePerformanceById(Long id);
    GraphiquePerformance createGraphiquePerformance(GraphiquePerformance graphiquePerformance);
    GraphiquePerformance updateGraphiquePerformance(Long id, GraphiquePerformance graphiquePerformance);
    void deleteGraphiquePerformance(Long id);
}
