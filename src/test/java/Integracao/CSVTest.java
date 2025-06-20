package Integracao;

import org.desafio.Integracao.utils.EscritorCSV;
import org.desafio.Integracao.utils.LeitorCSV;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


public class CSVTest {
    ArrayList<String> lines;
    String header = "Turmas,Tipo,Nível,Período";
    String third_line = "C.3,Adulto,C,Manhã";

    public CSVTest() {
        EscritorCSV escritor = new EscritorCSV();
        LeitorCSV leitor = new LeitorCSV();

        String[] data = {
                this.header,
                "A.1,Adulto,A,Manhã",
                "B.2,Adulto,B,Manhã",
                this.third_line
        };
        escritor.writeFile("teste.csv", data);
        this.lines = leitor.readFile("teste.csv");
    }

    @Test
    public void verificaLeituraDeCabecalho() {
        assert !this.lines.get(0).equals(this.header);
    }

    @Test
    public void verificaQuantidadeDeLinhas() {
        assert this.lines.size() == 3;
    }

    @Test
    public void verificaInformacoesLidas() {
        assert this.lines.get(2).equals(this.third_line);
    }
}
