package br.com.helpdev.jornada.unittests.business;

import br.com.helpdev.jornada.unittests.business.model.SimulationData;
import br.com.helpdev.jornada.unittests.business.model.SimulationResult;
import br.com.helpdev.jornada.unittests.service.CalculatorService;
import br.com.helpdev.jornada.unittests.service.SerasaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FinanceSimulateBusiness {

  private final SerasaService serasaService;
  private final CalculatorService calculator;
  private final Long personCalcValue;
  private final Long companyCalcValue;

  FinanceSimulateBusiness(final SerasaService serasaService,
                          final CalculatorService calculator,
                          @Value("${finance.person.calc.value}") final Long personCalcValue,
                          @Value("${finance.company.calc.value}") final Long companyCalcValue) {
    this.serasaService = serasaService;
    this.calculator = calculator;
    this.personCalcValue = personCalcValue;
    this.companyCalcValue = companyCalcValue;
  }

  public SimulationResult simulate(final SimulationData simulationData) {

    final var debits = serasaService.getDebits(simulationData.getDocument());

    if (!debits.isEmpty()) {
      throw new SecurityException("Your document has debits!");
    }

    final Long valueToCalc;

    switch (simulationData.getDocumentType()) {
      case CPF:
        valueToCalc = personCalcValue;
        break;
      case CNPJ:
        valueToCalc = companyCalcValue;
        break;
      default:
        throw new IllegalArgumentException("The document is not valid");
    }

    if (calculator.isAvailableToFinance(simulationData, valueToCalc)) {
      throw new IllegalArgumentException("You can't finance this value");
    }

    return calculator.calculateParcelValue(simulationData);
  }


}
