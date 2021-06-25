package br.com.helpdev.jornada.unittests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.helpdev.jornada.unittests.business.model.SimulationData;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorServiceTest {

  @Test
  @DisplayName("Quando simulo um financiamento de R$1000,00 feito em 10 vezes entao a parcale eh de R$ 110,00")
  void whenHas1000OfFinancialValueAnd10ParcelsThanReturn110OfParcelValue() {
    final var service = new CalculatorService();
    final var simulateResult = service.calculateParcelValue(new SimulationData(null, null, 1000L, 10L));
    assertThat(simulateResult).isNotNull();
    assertThat(simulateResult.getParcelValue()).isEqualTo(110F);
  }

  @Test
  @DisplayName("Quando simulo um financiamento de R$1000,00 feito em 1 vezes entao a parcale eh de R$ 1100,00")
  void whenHas1000OfFinancialValueAnd1ParcelsThanReturn110O0fParcelValue() {
    final var service = new CalculatorService();
    final var simulateResult = service.calculateParcelValue(new SimulationData(null, null, 1000L, 1L));
    assertThat(simulateResult).isNotNull();
    assertThat(simulateResult.getParcelValue()).isEqualTo(1100F);
  }

  @Test
  @DisplayName("Quando renda do cliente eh de R$ 3000,00 e tenntar financiar R$ 5000,00 com fator de 2L aceita simulacao")
  void whenHas3000OfIncomeAndTryNoFinance5000ThenDoAccept() {
    final var service = new CalculatorService();
    final var result = service.isAvailableToFinance(new SimulationData(null, 3000L, 5000L, null), 2L);
    assertThat(result).isTrue();
  }

  @Test
  @DisplayName("Quando renda do cliente eh de R$ 1000,00 e tenntar financiar R$ 2000,00 com fator de 2L nega simulacao")
  void whenHas1000OfIncomeAndTryNoFinance2000ThenDoNotAccept() {
    final var service = new CalculatorService();
    final var result = service.isAvailableToFinance(new SimulationData(null, 1000L, 2000L, null), 2L);
    assertThat(result).isFalse();
  }

  @Test
  @DisplayName("Quando renda do cliente eh de R$ 1000,00 e tenntar financiar R$ 1999,00 com fator de 2L aceita simulacao")
  void whenHas1000OfIncomeAndTryNoFinance1999ThenAccept() {
    final var service = new CalculatorService();
    final var result = service.isAvailableToFinance(new SimulationData(null, 1000L, 1999L, null), 2L);
    assertThat(result).isTrue();
  }

  @Test
  @Disabled("Esta retornando R$ 0 quando deveria retornar R$ 1,00")
  @DisplayName("BUG: Quando simulo um financiamento de R$10,00 feito em 11 vezes entao a parcale eh de R$ 1,00")
  void whenHas10OfFinancialValueAnd11ParcelsThanReturn110O0fParcelValue() {
    final var service = new CalculatorService();
    final var simulateResult = service.calculateParcelValue(new SimulationData(null, null, 10L, 11L));
    assertThat(simulateResult).isNotNull();
    assertThat(simulateResult.getParcelValue()).isEqualTo(1F);
  }

  @Test
  @Disabled("Deveria retornar erro quando recebe um valor negativo de financiamento")
  @DisplayName("BUG: Quando simulo um financiamento negativo feito em 11 vezes entao deve me retornar erro")
  void whenHasNegativeFinancialValueAnd11ParcelsThanReturn110O0fParcelValue() {
    final var service = new CalculatorService();
    assertThatThrownBy(() -> service.calculateParcelValue(new SimulationData(null, null, -1000L, 11L)))
        .isExactlyInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @Disabled("Deveria retornar erro quando recebe um valor negativo de parcela")
  @DisplayName("BUG: Quando simulo um financiamento de R$1000,00 feito em -11 vezes entao retorna erro")
  void whenHas100OfFinancialValueAndNegativeParcelsThanReturn110O0fParcelValue() {
    final var service = new CalculatorService();
    assertThatThrownBy(() -> service.calculateParcelValue(new SimulationData(null, null, 1000L, -11L)))
        .isExactlyInstanceOf(IllegalArgumentException.class);
  }

}