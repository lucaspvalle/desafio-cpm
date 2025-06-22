//package org.desafio.ModeloMatematico.restricoes;
//
//import com.google.ortools.linearsolver.MPConstraint;
//import com.google.ortools.linearsolver.MPSolver;
//import com.google.ortools.linearsolver.MPVariable;
//import org.desafio.ModeloDeDados.Voluntario;
//import org.desafio.ModeloDeDados.enums.PreferenciaDePeriodo;
//import org.desafio.ModeloMatematico.Variaveis;
//import org.desafio.ModeloMatematico.variaveis.AlocacaoDeVoluntarioNaTurma;
//
//import java.util.ArrayList;
//
///**
// * Se o período for permitido, ambos os voluntários podem ser alocados nas turmas deste período.
// * Caso contrário, a alocação deve ser proibida se a turma for em outro período.</p>
// *
// * <p>Para isso, há uma restrição de big M. Como estamos tratando sempre de voluntários na mesma escala em duplas
// * e é permitido apenas uma alocação para cada voluntário, o nosso M tem valor igual a 2.
// * Então, para cada VOLUNTARIO, VOLUNTARIO_NA_ESCALA e PERIODO, temos:</p>
// *
// * <p>
// *     sum(TURMA_DO_PERIODO,
// *          v_Alocacao(VOLUNTARIO, TURMA_DO_PERIODO)
// *          + v_Alocacao(VOLUNTARIO_NA_ESCALA, TURMA_DO_PERIODO))
// * </p>
// * <p><= 2 * v_LiberaPeriodoParaMesmaEscala(VOLUNTARIO, VOLUNTARIO_NA_ESCALA, PERIODO)</p>
// * */
//public class AlocacaoDeVoluntariosEmMesmaEscala {
//    private final String chaveDeVoluntarios;
//    private final String nomeDaRestricaoDeControle = "c_ControlaAlocacaoParaPeriodoNaMesmaEscala";
//
//    private String getChaveDeVoluntariosComPeriodo(PreferenciaDePeriodo periodo) {
//        return "{" + this.chaveDeVoluntarios + ", " + periodo + "}";
//    }
//
//    private void adicionaVariavelDeAlocacaoNaRestricaoDeControle(MPSolver solver, AlocacaoDeVoluntarioNaTurma alocacao) {
//        String nomeDaRestricao = this.nomeDaRestricaoDeControle + getChaveDeVoluntariosComPeriodo(alocacao.turma.getPeriodo());
//        solver.lookupConstraintOrNull(nomeDaRestricao).setCoefficient(alocacao.variavel, 1);
//    }
//
//    public AlocacaoDeVoluntariosEmMesmaEscala(
//            MPSolver solver,
//            Voluntario voluntario,
//            Voluntario voluntarioNaMesmaEscala,
//            ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesDoVoluntario,
//            ArrayList<AlocacaoDeVoluntarioNaTurma> alocacoesDoVoluntarioNaMesmaEscala,
//
//    ) {
//        this.chaveDeVoluntarios = voluntario + ", " + voluntarioNaMesmaEscala;
//
//        double inf = Double.POSITIVE_INFINITY;
//        for (PreferenciaDePeriodo periodo : PreferenciaDePeriodo.getPeriodosValidos()) {
//            String chaveComPeriodo = getChaveDeVoluntariosComPeriodo(periodo);
//
//            MPVariable vLiberaPeriodoParaMesmaEscala = variaveis.makeBoolVar("v_LiberaPeriodoParaMesmaEscala" + chaveComPeriodo, 0.0);
//
//
//            MPConstraint cControlaAlocacaoParaPeriodoNaMesmaEscala = solver.makeConstraint(
//                    -inf, 0, this.nomeDaRestricaoDeControle + chaveComPeriodo);
//            cControlaAlocacaoParaPeriodoNaMesmaEscala.setCoefficient(vLiberaPeriodoParaMesmaEscala, -2);
//        }
//
//        alocacoesDoVoluntario.forEach(alocacao -> adicionaVariavelDeAlocacaoNaRestricaoDeControle(solver, alocacao));
//        alocacoesDoVoluntarioNaMesmaEscala.forEach(alocacao -> adicionaVariavelDeAlocacaoNaRestricaoDeControle(solver, alocacao));
//    }
//}
