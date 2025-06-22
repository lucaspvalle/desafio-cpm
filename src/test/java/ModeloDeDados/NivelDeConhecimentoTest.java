package ModeloDeDados;

import org.desafio.ModeloDeDados.NivelDeConhecimento;
import org.junit.jupiter.api.Test;

public class NivelDeConhecimentoTest {
    @Test
    public void verificaOrdenacaoDeNiveis() {
        NivelDeConhecimento nivelA = new NivelDeConhecimento("A.1");
        NivelDeConhecimento nivelB = new NivelDeConhecimento("B.1");

        assert nivelB.getValor() >= nivelA.getValor();
    }

    @Test
    public void verificaOrdenacaoDeNiveisComEquivalencia() {
        NivelDeConhecimento nivelA = new NivelDeConhecimento("A.1");
        NivelDeConhecimento nivelTeens = new NivelDeConhecimento("Teens 1.2", "B");

        assert nivelTeens.getValor() >= nivelA.getValor();
    }
}
