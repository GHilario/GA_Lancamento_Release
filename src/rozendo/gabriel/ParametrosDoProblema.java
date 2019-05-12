package rozendo.gabriel;

public class ParametrosDoProblema {
	
	//parametros estaticos
	public static int numeroDeRelease = 3;
	public static int tamanhoCromossomo = 10;
	public static int orcamentoRelease = 120;
	
	
		//Prioridades de cada cliente
		public	int[] W = {3,4,2};
			//risco de cada de funcionalidade
		public  int[] risco = {3, 6, 2, 6, 4, 8, 9, 7, 6, 6};
			//custos de cada funcionalidade
		public  int[] custos = {60,40,40,30,20,20,25,70,50,20};
			//matriz com a importancia de cada funcionalidade dada por cada cliente
		public int[][] V = {
							{10, 10, 5},
							{8, 10, 6},
					         {6, 4, 8},
					         {5, 9, 1},
					         {7, 7, 5},
					         {8, 6, 2},
					         {6, 6, 4},
					         {9, 8, 3},
					         {6, 7, 5},
					         {10, 10, 7}
						};
		
	
	
	
	public int[] CorrigirIndividuo(int[] individuo) {
		int[] orcamentos = new int[this.numeroDeRelease];
		
		for(int i=0; i< this.tamanhoCromossomo ; i++) {
			if(individuo[i] != 0) {
				if( (orcamentos[individuo[i]-1] + this.custos[i]) <= this.orcamentoRelease ) {
					orcamentos[individuo[i]-1] += this.custos[i];
				}else {
					individuo[i] = 0;
				}
			}
		}
			
		return individuo;
	}
	
	//checando se o individuo é valido
	public boolean IndividuoValido(int[] individuo) {
		int[] orcamentos = new int[this.numeroDeRelease];
		for(int i=0; i< this.tamanhoCromossomo ; i++) {
			if(individuo[i] != 0) {
				if( (orcamentos[individuo[i]-1] + this.custos[i]) <= this.orcamentoRelease ) {
					orcamentos[individuo[i]-1] += this.custos[i];
				}else {
					return false;
				}
			}
		}
		return true;
	}
	
	//calculando o score
		public double CalcularScore(int[] S) {
			
			int[] importancia = new int[10];
			double score = 0;
			
			for(int i=0;i<this.tamanhoCromossomo;i++) {
					importancia[i] = 0;
				for(int o=0;o<this.numeroDeRelease;o++) {
					importancia[i] += this.W[o] * this.V[i][o];
				}
				
				if(S[i] != 0) {
					score += importancia[i] * (this.numeroDeRelease - S[i]+1) 
							- (this.risco[i] * S[i]);
				}
			}
			return score;
		}	

}
