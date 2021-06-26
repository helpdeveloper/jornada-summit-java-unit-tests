package br.com.helpdev.jornada.unittests.controller.mapper;

import br.com.helpdev.jornada.unittests.business.model.SimulationData;
import br.com.helpdev.jornada.unittests.business.model.SimulationResult;
import br.com.helpdev.jornada.unittests.controller.dto.FinanceSimulateRequestDTO;
import br.com.helpdev.jornada.unittests.controller.dto.FinanceSimulateResponseDTO;


public class FinanceSimulateMapper {

  public static SimulationData toModel(final FinanceSimulateRequestDTO request) {
    return new SimulationData(
        request.document,
        request.income,
        request.financialValue,
        request.parcels
    );
  }

  public static FinanceSimulateResponseDTO toDto(final SimulationResult simulated) {
    final var response = new FinanceSimulateResponseDTO();
    response.parcelValue = simulated.getParcelValue();
    return response;
  }

}
