package it.unicam.cs.mpgc.rpg123279.app;

import it.unicam.cs.mpgc.rpg123279.factory.*;
import it.unicam.cs.mpgc.rpg123279.model.mappe.*;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.nemici.*;
import it.unicam.cs.mpgc.rpg123279.repository.*;
import it.unicam.cs.mpgc.rpg123279.service.*;

public class AppConfig {

    private final IFactoryNemico factoryNemico = new FactoryNemico();
    private final IFactoryOggetto factoryOggetto = new FactoryOggetto();
    private final IRepoGiocatore repoGiocatore = new GiocatoreRepoHibernate();
    private final IRepoSalvataggio repoSalvataggio = new SalvataggioRepoHibernate();
    private final IServiceInventario serviceInventario = new ServiceInventario();
    private final IServiceLivello serviceLivello = new ServiceLivello();
    private final IServiceEvento serviceEvento = new ServiceEvento(factoryNemico, factoryOggetto);
    private final GestoreTurno gestoreTurno = new GestoreTurno(serviceInventario);
    private final IServiceCombattimento serviceCombattimento = new ServiceCombattimento(gestoreTurno,
            serviceLivello,serviceInventario, factoryOggetto);
    private final IServiceSalvataggio serviceSalvataggio = new ServiceSalvataggio(repoSalvataggio);
    private final MappaGioco mappa = costruisciMappa();
    private IServiceNavigazione serviceNavigazione;

    public AppConfig() {
        serviceNavigazione = new ServiceNavigazione(mappa, serviceEvento, mappa.getIsole().getFirst());
    }


    public IServiceInventario getServiceInventario() { return serviceInventario; }
    public IServiceCombattimento getServiceCombattimento() { return serviceCombattimento; }
    public IServiceSalvataggio getServiceSalvataggio() { return serviceSalvataggio; }
    public IServiceNavigazione getServiceNavigazione() { return serviceNavigazione; }
    public MappaGioco getMappa() { return mappa; }

    public void reinizializzaNavigazione(Isola isola) {
        this.serviceNavigazione = new ServiceNavigazione(mappa, serviceEvento, isola);
    }

    private MappaGioco costruisciMappa() {
        MappaGioco m = new MappaGioco();

        Isola tortuga = new Isola("Tortuga","Celebre rifugio di pirati, mercenari e contrabbandieri.",
                1, 0, 5);
        Isola islaDeMuerta = new Isola("Isla de Muerta","L'isola nascosta dove e'" +
                " custodito il tesoro azteco maledetto di Barbossa.", 8, 1, 10);
        Isola pelegosto = new Isola("Pelegosto", "L'isola abitata dalla tribu' cannibale" +
                " che cattura Jack Sparrow.", 15, 2, 20);
        Isola islaCruces = new Isola("Isla Cruces","L'isola dove avviene lo scontro" +
                " per il cuore di Davy Jones.",25, 3, 30);

        tortuga.aggiungiNemicoPossibile(MarinaioMaledetto.class);
        tortuga.aggiungiNemicoPossibile(PirataScheletrico.class);

        islaDeMuerta.aggiungiNemicoPossibile(PirataScheletrico.class);
        islaDeMuerta.aggiungiNemicoPossibile(SerpenteMarino.class);

        pelegosto.aggiungiNemicoPossibile(MarinaioMaledetto.class);
        pelegosto.aggiungiNemicoPossibile(PirataScheletrico.class);
        pelegosto.aggiungiNemicoPossibile(SerpenteMarino.class);

        islaCruces.aggiungiNemicoPossibile(PirataScheletrico.class);
        islaCruces.aggiungiNemicoPossibile(SerpenteMarino.class);
        islaCruces.aggiungiNemicoPossibile(KrakenBoss.class);

        tortuga.setSbloccata(true);

        m.aggiungiIsola(tortuga);
        m.aggiungiIsola(islaDeMuerta);
        m.aggiungiIsola(pelegosto);
        m.aggiungiIsola(islaCruces);
        m.aggiungiRotta(new Rotta(tortuga, islaDeMuerta,4,0.50));
        m.aggiungiRotta(new Rotta(islaDeMuerta, pelegosto,7,0.65));
        m.aggiungiRotta(new Rotta(pelegosto, islaCruces,9,0.80));
        m.aggiungiRotta(new Rotta(islaDeMuerta, tortuga,3,0.40));
        m.aggiungiRotta(new Rotta(pelegosto, islaDeMuerta,6,0.55));
        m.aggiungiRotta(new Rotta(islaCruces, pelegosto,8,0.70));
        m.aggiungiRotta(new Rotta(tortuga, pelegosto,8,0.70));
        m.aggiungiRotta(new Rotta(islaDeMuerta, islaCruces,9,0.75));

        return m;
    }
}