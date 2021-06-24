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
  void simulate_DontThrowException_WithCPF() {
    SimulationData simulationData = SimulationDataSeeder.getSimulationData3("11111111111");

    assertDoesNotThrow(() -> {
      financeSimulateBusiness.simulate(simulationData);
    });
  }

  @Test
  void simulate_ThrowsSecutiyException_withDebits() {
    SimulationData simulationData = SimulationDataSeeder.getSimulationData(SerasaService.CPF_WITH_DEBIT);

    assertThrows(SecurityException.class, () -> financeSimulateBusiness.simulate(simulationData));
  }

  @Test
  void simulate_ThrowsException_withInvalidValue() {
    SimulationData simulationData = SimulationDataSeeder.getSimulationData("444");

    assertThrows(IllegalArgumentException.class, () -> financeSimulateBusiness.simulate(simulationData));
  }

  @Test
  void simulate_dontAcceptFinance() {
    SimulationData simulationData = new SimulationData("11111111111",
        10L,
        10L,
        1L);

    assertThrows(IllegalArgumentException.class, () -> financeSimulateBusiness.simulate(simulationData));
  }


  @Test
  void simulate_needReturnAValue_withCnpj() {
    SimulationData simulationData = SimulationDataSeeder.getSimulationData2("11111111111114");

    final var response = financeSimulateBusiness.simulate(simulationData);
    assertTrue(response != null);
    assertTrue(response.getParcelValue() > 0);
  }

  @Test
  void test2() {
    SimulationData simulationData = SimulationDataSeeder.getSimulationData("11111");

    assertThrows(RuntimeException.class, () -> financeSimulateBusiness.simulate(simulationData));
  }


}