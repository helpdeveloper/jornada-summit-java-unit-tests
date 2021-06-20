package br.com.helpdev.jornada.unittests.controller;

import br.com.helpdev.jornada.unittests.business.FinanceSimulateBusiness;
import br.com.helpdev.jornada.unittests.dto.FinanceSimulateRequestDTO;
import br.com.helpdev.jornada.unittests.dto.FinanceSimulateResponseDTO;
import br.com.helpdev.jornada.unittests.mapper.FinanceSimulateMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FinanceController {

  private final FinanceSimulateBusiness business;

  FinanceController(final FinanceSimulateBusiness business) {
    this.business = business;
  }

  @GetMapping(consumes = "application/json", produces = "application/json")
  public FinanceSimulateResponseDTO simulate(@RequestBody final FinanceSimulateRequestDTO request) {
    final var model = FinanceSimulateMapper.toModel(request);
    final var simulated = business.simulate(model);
    return FinanceSimulateMapper.toDto(simulated);
  }

}
