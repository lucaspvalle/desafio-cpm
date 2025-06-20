package Integracao;

import org.desafio.Integracao.ImportacaoDeDados;
import org.desafio.ModeloDeDados.NivelDeConhecimento;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.enums.PreferenciaDeFaixaEtaria;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;
import org.junit.jupiter.api.Test;

public class TurmasTest {
    TurmaDao turmas;

    public TurmasTest() {
        ImportacaoDeDados importador = new ImportacaoDeDados();
        this.turmas = importador.importarTurmas();
    }

    @Test
    public void verificaFaixaEtariaDaTurma() {
        assert this.turmas.getTurmaPorId("Teens 2.2.1").getFaixaEtaria() == PreferenciaDeFaixaEtaria.ADOLESCENTE;
    }

    @Test
    public void verificaNivelDaTurma() {
        NivelDeConhecimento nivelC = new NivelDeConhecimento("C");
        assert this.turmas.getTurmaPorId("C.3").getNivel().getValor() == nivelC.getValor();
    }

    @Test
    public void verificaNivelDeTurmaComEquivalencia() {
        NivelDeConhecimento nivelA = new NivelDeConhecimento("A");
        assert this.turmas.getTurmaPorId("Teens 1.1.1").getNivel().getValor() == nivelA.getValor();
    }

    @Test
    public void verificaPeriodoDaTurma() {
        assert this.turmas.getTurmaPorId("A.2").getPeriodo() == PreferenciaDePeriodo.MANHA;
    }

    @Test
    public void verificaTurmaSeguinte() {
        assert this.turmas.getTurmaPorId("A.3").getTurmaSeguinte().getNome().equals("B.3");
    }
}
