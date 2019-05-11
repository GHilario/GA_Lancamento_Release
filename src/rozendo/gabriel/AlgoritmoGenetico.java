package rozendo.gabriel;

import java.util.ArrayList;
import java.util.Random;

public class AlgoritmoGenetico {
	//variaveis locais
	private int tamanhoPopulacao;
	private int numGeracoes;
	private ArrayList<int[]> populacao = new ArrayList();
	
	//outras variaveis
	ParametrosDoProblema parametrosDoProblema = new ParametrosDoProblema();
	
	//definindo numeros padroes
	public AlgoritmoGenetico() {
		this.numGeracoes = 100;
		this.tamanhoPopulacao = 100;
	}

	//metodos SET

	public void setTamanhoPopulacao(int tamanhoPopulacao) {
		this.tamanhoPopulacao = tamanhoPopulacao;
	}

	public void setNumGeracoes(int numGeracoes) {
		this.numGeracoes = numGeracoes;
	}
	
	//GERAR PRIMEIRA POPULACAO
	public void primeiraGeracao() {
		Random random = new Random();;
		int[] individuo;
		for(int i=0; i< this.tamanhoPopulacao; i++) {
			individuo = new int[this.parametrosDoProblema.tamanhoCromossomo];
			for(int o=0; o< this.parametrosDoProblema.tamanhoCromossomo;o++) {
				individuo[o] = random.nextInt((this.parametrosDoProblema.numeroDeRelease + 1));
			}
			
			individuo = this.parametrosDoProblema.CorrigirIndividuo(individuo);
			this.populacao.add(individuo);
		}
	}
	
	//PRINTANDO INDIVIDUOS DA GERAÇÃO
	public void printPopulacao() {
		for(int i=0; i<this.tamanhoPopulacao;i++) {
			System.out.println("Individuo " + i);
			for(int o=0; o< this.parametrosDoProblema.tamanhoCromossomo;o++) {
				System.out.print("|" + this.populacao.get(i)[o] + "|");
			}
			System.out.print("\n");
		}
	}
	
}

