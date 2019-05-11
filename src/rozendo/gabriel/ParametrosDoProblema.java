package rozendo.gabriel;

public class ParametrosDoProblema {
	
	//parametros estaticos
	public static int numeroDeRelease = 3;
	public static int tamanhoCromossomo = 10;
	public static int orcamentoRelease = 120;
	
	//numero de releases
		private int P = 3;
			//Prioridades de cada cliente
		private	int[] W = {3,4,2};
			//risco de cada de funcionalidade
		private	int[] risco = {3, 6, 2, 6, 4, 8, 9, 7, 6, 6};
			//custos de cada funcionalidade
		private	int[] custos = {60,40,40,30,20,20,25,70,50,20};
			//TEMP vetor resposta
		private	int[] S = {2, 0, 1, 1, 3, 0, 3, 1, 2, 1};
			 //matriz com a importancia de cada funcionalidade dada por cada cliente
		private int[][] V = {
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

}
