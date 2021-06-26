package br.com.helpdev.jornada.unittests.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import br.com.helpdev.jornada.unittests.business.model.SimulationData;
import br.com.helpdev.jornada.unittests.business.model.SimulationResult;
import br.com.helpdev.jornada.unittests.service.CalculatorService;
import br.com.helpdev.jornada.unittests.service.SerasaService;
import br.com.helpdev.jornada.unittests.service.entity.SerasaDebits;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FinanceSimulateBusinessTest {

  @Mock
  private SerasaService serasaService;
  @Mock
  private CalculatorService calculator;

  @Test
  @DisplayName("Quanto client PF com renda de R$ 3000,00 tenta financiar R$ 5000,00 em 15X e tem debitos no serasa, recebe erro")
  void whenCustomerHaveSerasaRestrictionThenDoNotAccept() {
    final var service = new FinanceSimulateBusiness(serasaService, calculator, 100L, 100L);
    String document = "12345678901";
    when(serasaService.getDebits(document)).thenReturn(List.of(new SerasaDebits(1500L)));
    assertThatThrownBy(() -> service.simulate(new SimulationData(document, 3000L, 5000L, 15L)))
        .isExactlyInstanceOf(SecurityException.class)
        .hasMessage("Your document has debits!");
    verify(serasaService).getDebits(document);
    verifyNoMoreInteractions(serasaService);
    verifyNoInteractions(calculator);
  }

  @Test
  @DisplayName("Quanto client com renda de R$ 3000,00 tenta financiar R$ 5000,00 em 15X e o valor acima do liberado, recebe erro")
  void whenCustomerHasFinancialValueGreaterThanTheLimitThenNotAccept() {
    final var service = new FinanceSimulateBusiness(serasaService, calculator, 100L, 100L);
    final var document = "12345678901";
    final var simulationData = new SimulationData(document, 3000L, 5000L, 15L);
    when(calculator.isAvailableToFinance(simulationData, 100L)).thenReturn(false);
    assertThatThrownBy(() -> service.simulate(simulationData))
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasMessage("You can't finance this value");
    verify(serasaService).getDebits(document);
    verify(calculator).isAvailableToFinance(simulationData, 100L);
    verifyNoMoreInteractions(serasaService);
    verifyNoMoreInteractions(calculator);
  }

  @Test
  @DisplayName("Quanto client com renda de R$ 3000,00 tenta financiar R$ 5000,00 em 15X e o documento eh invalido internament, recebe erro")
  void whenCustomerHasInvalidInternalDocuemntThenNotAccept() {
    final var service = new FinanceSimulateBusiness(serasaService, calculator, 100L, 100L);
    String document = "INVALID";
    assertThatThrownBy(() -> service.simulate(new SimulationData(document, 3000L, 5000L, 15L)))
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasMessage("The document is not valid");
    verify(serasaService).getDebits(document);
    verifyNoMoreInteractions(serasaService);
    verifyNoInteractions(calculator);
  }

  @Test
  @DisplayName("Quanto client com renda de R$ 3000,00 tenta financiar R$ 10000,00 em 10X e o documento eh invalido internament, retorna parcela de R$ 1100,00")
  void whenCustomerHasInvalidInternalDocuemntThenNotAccept1() {
    final var service = new FinanceSimulateBusiness(serasaService, calculator, 100L, 100L);
    String cnpj = "12345678901234";
    SimulationData simulationData = new SimulationData(cnpj, 3000L, 1000L, 10L);
    when(calculator.isAvailableToFinance(simulationData, 100L)).thenReturn(true);
    when(calculator.calculateParcelValue(simulationData)).thenReturn(new SimulationResult(1100F));
    final var result = service.simulate(simulationData);
    assertThat(result.getParcelValue()).isEqualTo(1100F);
    verify(serasaService).getDebits(cnpj);
    verify(calculator).isAvailableToFinance(simulationData, 100L);
    verify(calculator).calculateParcelValue(simulationData);
  }

}