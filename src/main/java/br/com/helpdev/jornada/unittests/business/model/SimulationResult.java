package br.com.helpdev.jornada.unittests.business.model;

public class SimulationResult {

  private final float parcelValue;

  public SimulationResult(final float parcelValue) {
    this.parcelValue = parcelValue;
  }

  public float getParcelValue() {
    return parcelValue;
  }
}
