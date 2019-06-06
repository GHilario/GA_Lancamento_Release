package rozendo.gabriel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class AlgoritmoGenetico {
	//variaveis locais
	private int tamanhoPopulacao;
	private int numGeracoes;
	private int elitismo;
	private double taxaMutacao;
	private double taxaCruzamento;
	private ArrayList<int[]> populacao = new ArrayList();
	private ArrayList<int[]> novaPopulacao = new ArrayList();
	
	//outras variaveis
	ParametrosDoProblema parametrosDoProblema = new ParametrosDoProblema();
	
	//definindo numeros padroes
	public AlgoritmoGenetico() {
		this.numGeracoes = 2000;
		this.tamanhoPopulacao = 100;
		this.elitismo = 1;
		this.taxaMutacao = 0.3;
		this.taxaCruzamento = 0.8;
	}

	//metodos SET

	public void setTamanhoPopulacao(int tamanhoPopulacao) {
		this.tamanhoPopulacao = tamanhoPopulacao;
	}

	public void setNumGeracoes(int numGeracoes) {
		this.numGeracoes = numGeracoes;
	}
	
	public void setElitismo(int elitismo) {
		this.elitismo = elitismo;
	}
	
	public void setTaxaMutacao(float taxaMutacao) {
		this.taxaMutacao = taxaMutacao;
	}
	
	//GERAR PRIMEIRA POPULACAO
	public void primeiraGeracao() {
		Random random = new Random();
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
	
	//criando nova geração
	public void novaGeracao() {
		Random random = new Random();
		int[] individuo;
		for(int i= this.elitismo; i<this.tamanhoPopulacao;i++) {
			individuo = new int[this.parametrosDoProblema.tamanhoCromossomo];
			int pai = this.Sorteio();
			int mae = this.Sorteio();
			//olhando taxa de cruzamento
			double prob = random.nextDouble();
			if(prob >= this.taxaCruzamento) {
				if( this.parametrosDoProblema.CalcularScore(this.populacao.get(pai)) > this.parametrosDoProblema.CalcularScore(this.populacao.get(mae)) ) {
					individuo = this.populacao.get(pai);
				}
				else {
					individuo = this.populacao.get(mae);
				}
			}
			else {
				for(int o=0; o<this.parametrosDoProblema.tamanhoCromossomo;o++) {
					if(i % 2 == 0)
						individuo[o] = this.populacao.get(pai)[o];
					else
						individuo[o] = this.populacao.get(mae)[o];
				}
				individuo = this.parametrosDoProblema.CorrigirIndividuo(individuo);
				
			}
			
			this.novaPopulacao.add(individuo);
		}
	}
	
	//MUTACAO
	public void Mutacao() {
		Random random = new Random();
		this.novaPopulacao.forEach(p -> {
			for(int i=0; i< this.parametrosDoProblema.tamanhoCromossomo;i++) {
				double prob = random.nextDouble();
				if(prob <= this.taxaMutacao) {
					int releaseSorteada= random.nextInt(3) + 1;
					int releaseAntiga = p[i];
					p[i] = releaseSorteada;
					if(!this.parametrosDoProblema.IndividuoValido(p)) {
						p[i] = releaseAntiga;
					}
				}
			}
		});
	
		
	}
	
	//escolhendo sobreviventes
	public void SelecaoNatural() {
		this.OrdenarFitness();
		for(int i=0; i<this.elitismo;i++) {
			this.novaPopulacao.add(this.populacao.get(i));
		}
		this.populacao = (ArrayList<int[]>) this.novaPopulacao.clone();
		this.novaPopulacao.clear();
	}
	
	//ordenando populacao de acordo com o Fitness
	public void OrdenarFitness() {
		Collections.sort(this.populacao, new Comparator() {
			@Override
			public int compare(Object arg0, Object arg1) {
				ParametrosDoProblema p = new ParametrosDoProblema();
				int[] i1 = (int[]) arg0;
				int[] i2 = (int[]) arg1;
					return (int) (p.CalcularScore(i2) - p.CalcularScore(i1));
				
			}
		});
		
	}
	
	
	//selecionando pais
	public int Sorteio() {
		Random random = new Random();
		int c1 = random.nextInt(this.tamanhoPopulacao);
		int c2 = random.nextInt(this.tamanhoPopulacao);
		if(this.parametrosDoProblema.CalcularScore(this.populacao.get(c1)) > this.parametrosDoProblema.CalcularScore(this.populacao.get(c2)))
			return c1;
		else
			return c2;
	}
	
	//PRINTANDO RESPOSTA
	public void resposta() {
		this.OrdenarFitness();
		System.out.println("________________________________");
		System.out.println("Melhor Individuo:");
		for(int i=0; i< this.parametrosDoProblema.tamanhoCromossomo;i++) {
			System.out.print("|" + this.populacao.get(0)[i] + "|");
		}
		System.out.println("\nScore:" + this.parametrosDoProblema.CalcularScore(this.populacao.get(0)));
		System.out.println("________________________________");
	}
	
	
	
	//PRINTANDO INDIVIDUOS DA GERAÇÃO
	public void printPopulacao() {		
		this.populacao.forEach(p -> {
			for(int i=0; i< this.parametrosDoProblema.tamanhoCromossomo;i++) {
				System.out.print("|" + p[i] + "|");
			}
			System.out.print("\n");
		});
	}
	
	//PRINTANDO INDIVIDUOS DA GERAÇÃO TEMP
	public void printPopulacaoNova() {
		this.novaPopulacao.forEach(p -> {
			for(int i=0; i< this.parametrosDoProblema.tamanhoCromossomo;i++) {
				System.out.print("|" + p[i] + "|");
			}
			System.out.print("\n");
		});
	}
	
	//PRINTANDO O SCORE DE CADA INDIVIDUO DA POPULACAO
	public void PrintScore() {
		System.out.println("Print do score");
		this.populacao.forEach( p ->{
			System.out.println(this.parametrosDoProblema.CalcularScore(p));
		});
	}
	
	//METODO PRINCIPAL
	
	public void start() {
		this.primeiraGeracao();
		System.out.println("Geração 0");
		this.printPopulacao();
		for(int i=1;i<this.numGeracoes;i++) {
			System.out.println("Geração "+i);
			this.novaGeracao();
			this.Mutacao();
			this.SelecaoNatural();
			this.printPopulacao();
		}
		this.resposta();
	}
	
	
}

