package br.com.helpdev.jornada.unittests.business;

import br.com.helpdev.jornada.unittests.business.model.SimulationData;

public class SimulationDataSeeder {
  public static SimulationData getSimulationData(final String s) {
    return new SimulationData(s,
        1000L,
        10000L,
        10L);
  }

  public static SimulationData getSimulationData2(final String s) {
    return new SimulationData(s,
        1000L,
        100000L,
        10L);
  }


  public static SimulationData getSimulationData3(final String s) {
    return new SimulationData(s,
        10L,
        10000L,
        10L);
  }

}
