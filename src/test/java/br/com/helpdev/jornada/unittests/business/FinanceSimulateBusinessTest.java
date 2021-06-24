package br.com.helpdev.jornada.unittests.business;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.helpdev.jornada.unittests.business.model.SimulationData;
import br.com.helpdev.jornada.unittests.service.SerasaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FinanceSimulateBusinessTest {

  @Autowired
  private FinanceSimulateBusiness financeSimulateBusiness;

  @Test
  void simulateSuccessCPF() {
    final var simulationData = SimulationDataSeeder
        .getSimulationData3("11111111111");

    assertDoesNotThrow(() -> {
      final var response = financeSimulateBusiness.simulate(simulationData);
      assertTrue(response != null);
    });
  }

  @Test
  void simulateSuccessCNPJ() {
    SimulationData simulationData = SimulationDataSeeder
        .getSimulationData2("11111111111114");

    assertDoesNotThrow(() -> {
      final var response = financeSimulateBusiness.simulate(simulationData);
      assertTrue(response != null);
    });
  }

  @Test
  void simulate_Exceptions_withInvalidValues() {
    SimulationData simulationData = SimulationDataSeeder.getSimulationData("444");

    assertThrows(IllegalArgumentException.class, () -> financeSimulateBusiness.simulate(simulationData));

    final var simulationData2 = SimulationDataSeeder
        .getSimulationData(SerasaService.CPF_WITH_DEBIT);

    assertThrows(SecurityException.class, () -> financeSimulateBusiness.simulate(simulationData2));

    SimulationData simulationData3 = SimulationDataSeeder.getSimulationValueWithInvalidValue();
    assertThrows(IllegalArgumentException.class, () -> financeSimulateBusiness.simulate(simulationData3));
  }

}