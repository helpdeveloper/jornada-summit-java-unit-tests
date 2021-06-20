package br.com.helpdev.jornada.unittests.service;

import br.com.helpdev.jornada.unittests.service.entity.SerasaDebits;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class SerasaService {

  public List<SerasaDebits> getDebits(final String document) {

    System.out.println("Calling external call to document " + document + " ...");
    try {
      Thread.sleep(1_000);

      if (new Random().nextInt(2) == 1) {
        return Collections.singletonList(new SerasaDebits(1231L));
      }

    } catch (InterruptedException ignore) {
    } finally {
      System.out.println("...done");
    }

    return Collections.emptyList();
  }

}
