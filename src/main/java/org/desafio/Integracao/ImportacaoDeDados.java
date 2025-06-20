package org.desafio.Integracao;

import org.desafio.Integracao.utils.LeitorCSV;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;

import java.util.ArrayList;


public class ImportacaoDeDados {
    private final LeitorCSV leitor = new LeitorCSV();

    public TurmaDao turmas;
    public VoluntarioDao voluntarios;

    private void importarTurmas() {
        ArrayList<String> csvTurmas = this.leitor.readFile(ArquivosDeIntegracao.TURMA.getCaminho());
        ArrayList<String> csvProgressaoDeTurmas = this.leitor.readFile(ArquivosDeIntegracao.PROGRESSAO.getCaminho());
        ArrayList<String> csvEquivalencias = this.leitor.readFile(ArquivosDeIntegracao.EQUIVALENCIAS.getCaminho());

        this.turmas = new TurmaDao(csvTurmas, csvProgressaoDeTurmas, csvEquivalencias);
    }

    private void importarVoluntarios() {
        ArrayList<String> csvVoluntarios = this.leitor.readFile(ArquivosDeIntegracao.VOLUNTARIOS.getCaminho());
        ArrayList<String> csvVoluntariosEmEscala = this.leitor.readFile(ArquivosDeIntegracao.MESMA_ESCALA.getCaminho());

        this.voluntarios = new VoluntarioDao(this.turmas, csvVoluntarios, csvVoluntariosEmEscala);
    }

    public ImportacaoDeDados() {
        importarTurmas();
        importarVoluntarios();
    }
}
