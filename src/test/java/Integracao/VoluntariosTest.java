package Integracao;

import org.desafio.Integracao.ImportacaoDeDados;
import org.desafio.ModeloDeDados.NivelDeConhecimento;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloDeDados.enums.Genero;
import org.desafio.ModeloDeDados.enums.PreferenciaDeFaixaEtaria;
import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;
import org.desafio.ModeloDeDados.enums.PreferenciaDeProgressaoDeTurma;
import org.junit.jupiter.api.Test;

public class VoluntariosTest {
    VoluntarioDao voluntarios;

    public VoluntariosTest() {
        ImportacaoDeDados importador = new ImportacaoDeDados();
        TurmaDao turmas = importador.importarTurmas();
        this.voluntarios = importador.importarVoluntarios(turmas);
    }

    @Test
    public void verificaGenero() {
        assert this.voluntarios.getVoluntarioPorId(1).getGenero() == Genero.FEMININO;
    }

    @Test
    public void verificaSeEhVeterano() {
        assert this.voluntarios.getVoluntarioPorId(117).isVeterano();
    }

    @Test
    public void verificaSeEhCalouro() {
        assert !this.voluntarios.getVoluntarioPorId(122).isVeterano();
    }

    @Test
    public void verificaTurmaDoVoluntario() {
        assert this.voluntarios.getVoluntarioPorId(36).getTurmaAtual().getNome().equals("Teens 4.1.1");
    }

    @Test
    public void verificaComprometimento() {
        assert this.voluntarios.getVoluntarioPorId(20).getComprometimento() == 7.4;
    }

    @Test
    public void verificaNivel() {
        NivelDeConhecimento nivelD = new NivelDeConhecimento("D");
        assert this.voluntarios.getVoluntarioPorId(12).getNivel().getValor() == nivelD.getValor();
    }

    @Test
    public void verificaPreferenciaDePeriodoManhaDoVoluntario() {
        assert this.voluntarios.getVoluntarioPorId(3).getPeriodo() == PreferenciaDePeriodo.MANHA;
    }

    @Test
    public void verificaPreferenciaDePeriodoIndiferenteDoVoluntario() {
        assert this.voluntarios.getVoluntarioPorId(12).getPeriodo() == PreferenciaDePeriodo.PERIODO_INDIFERENTE;
    }

    @Test
    public void verificaPreferenciaDeProgressaoDePermanecerDoVoluntario() {
        assert this.voluntarios.getVoluntarioPorId(8).getProgressao() == PreferenciaDeProgressaoDeTurma.PERMANECER;
    }

    @Test
    public void verificaPreferenciaDeProgressaoIndiferenteDoVoluntario() {
        assert this.voluntarios.getVoluntarioPorId(4).getProgressao() ==
                PreferenciaDeProgressaoDeTurma.PROGRESSAO_INDIFERENTE;
    }

    @Test
    public void verificaPreferenciaDeFaixaEtariaDoVoluntario() {
        assert this.voluntarios.getVoluntarioPorId(11).getFaixaEtaria() == PreferenciaDeFaixaEtaria.ADULTO;
    }

    @Test
    public void verificaVoluntariosEmMesmaEscala() {
        assert this.voluntarios.getVoluntarioPorId(56).getVoluntariosNaMesmaEscala().get(0).getId().equals(59);
    }
}
