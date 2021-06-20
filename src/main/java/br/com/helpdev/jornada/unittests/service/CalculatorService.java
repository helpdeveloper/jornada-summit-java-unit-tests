package br.com.helpdev.jornada.unittests.service;

import br.com.helpdev.jornada.unittests.business.model.SimulationData;
import br.com.helpdev.jornada.unittests.business.model.SimulationResult;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

  public SimulationResult calculateParcelValue(final SimulationData simulationData) {
    long parcelValue = simulationData.getFinancialValue() / simulationData.getParcels();
    parcelValue += parcelValue * 0.1f;
    return new SimulationResult(parcelValue);
  }

  public boolean isAvailableToFinance(final SimulationData simulationData, final Long calcValue) {
    return simulationData.getFinancialValue() / simulationData.getIncome() > calcValue;
  }

}
