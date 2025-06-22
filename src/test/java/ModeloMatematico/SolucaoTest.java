package ModeloMatematico;

import org.desafio.Integracao.ImportacaoDeDados;
import org.desafio.ModeloDeDados.dao.TurmaDao;
import org.desafio.ModeloDeDados.dao.VoluntarioDao;
import org.desafio.ModeloMatematico.ModeloMatematico;
import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;

import java.util.ArrayList;

public class SolucaoTest {
    ModeloMatematico modelo;
    ArrayList< AlocacaoDeVoluntarioNaTurma > alocacoesSugeridas;

    public SolucaoTest() {
        ImportacaoDeDados importador = new ImportacaoDeDados();
        TurmaDao turmas = importador.importarTurmas();
        VoluntarioDao voluntarios = importador.importarVoluntarios(turmas);

        this.modelo = new ModeloMatematico(voluntarios, turmas, false);
        this.alocacoesSugeridas = this.modelo.alocacoes.filtrarAlocacoes(alocacao -> alocacao.getSolution() >= 1);
    }

    // TODO
}
