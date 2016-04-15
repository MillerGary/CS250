import java.util.*;
import java.io.*;


public class Lab7Strings {

	public static void main(String[] args) throws IOException {
		String similarity ="PFVGGNWKMNGTKAEAKELVEALKAKLPDDVEVVVAPPAVYLDTAREALKGSKIKVAAQNCYKEAKGAFTGEISPEMLKDLGADYVILGHSERRHYFGETDELVAKKVAHALEHGLKVIACIGETLEEREAGKTEEVVFRQTKALLAGLGDEWKNVVIAYEPVWAIGTGKTATPEQAQEVHAFIRKWLAENVSAEVAESVRILYGGSVKPANAKELAAQPDIDGFLVGGASLKPEFLDII";

		int countLines = countLines();
		Protein[] protein = new Protein[countLines];
		String[] ID = new String[countLines];
		int[] Similar = new int[countLines];
		int initial =0;


		objectAdd(protein);
        analyse(protein,countLines);
		int max =findMax(protein,countLines);
		int min =findMin(protein,countLines);
		Arrays.sort(protein);
        //**** PART 1*****
      //printData(protein,countLines,max,min);

        //**** Part2************
        //analyseSimilar(protein, countLines, similarity);
		//int maxS =findMaxS(protein,countLines);
		//int minS =findMinS(protein,countLines);
		//sort( protein, Similar, ID, minS,maxS,initial);


     //printSimilar(Similar,ID,maxS, minS);

		//Part3
		String alg ="LSD";
		String alg2= "MSD";
		storeArray(alg,alg2,countLines,protein);





    }

	public static int countLines() throws IOException{
		int count=0;
		File file = new File("cTIM_core_align.fa");
		FileReader fr = new FileReader(file);
	    LineNumberReader lnr = new LineNumberReader(fr);
	    while (lnr.readLine() != null){
        	count++;
            }
	    lnr.close();

		return (count/3);
	}


	public static void objectAdd(Protein[] protein) throws IOException{
		int counta =0;
		Scanner s = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader("cTIM_core_align.fa")));
            while (s.hasNext())
            {
              String id = s.next();
              String str = s.next();
              protein[counta] = new Protein(id,str);
              counta++;
         }
        } finally {
            if (s != null) {
                s.close();
            }	}
}

	public static void analyse(Protein[] protein,int countLines){
    	int nmr=0;
		for (int i = 0; i < countLines; i++){
    		for(int j=0; j<protein[i].getSequence().length();j++){
    		char c = protein[i].getSequence().charAt(j);

    		if(c == '-'){
    			nmr++;
    		}
    		}
    		protein[i].setNumDashes(nmr);
    		nmr =0;
    	}

	}


	public static void printData(Protein[] protein, int countLines,int max, int min){

	       for (int k=countLines-1;k>=0;k--){
	        	if(min==protein[k].getDashes()){

	    	   System.out.println("******MIN***** MIN: "+ min );
	        	System.out.println(protein[k].getIdentifier());
	        //	System.out.println(protein[k].getAmino());
	        	//System.out.println(protein[k].getCount());
	        	}
	    			}

	       for (int k=countLines-1;k>=0;k--){
	        	if(max==protein[k].getDashes()){

	        	System.out.println("******MAX***** MAX: "+ max);
	        	System.out.println(protein[k].getIdentifier());
	        //	System.out.println(protein[k].getAmino());
	        	//System.out.println(protein[k].getCount());
	       }
	    			}
	}

	public static int  findMax(Protein[] protein, int countLines){
		int max=protein[0].getDashes();
		for(int i=0; i<countLines;i++){

			if (max<protein[i].getDashes()){
				max =protein[i].getDashes();
			}


			}
		return max;
	}

	public static int findMin(Protein[] protein, int countLines){
		int min=protein[0].getDashes();
		for(int i=0; i<countLines;i++){

			if (min>protein[i].getDashes()){
				min =protein[i].getDashes();
			}


			}
		return min;
	}


	public static void analyseSimilar(Protein[] protein, int countLines, String similar){

		for (int i = 0; i < similar.length(); i++){
			char e = similar.charAt(i);

    		for(int j=0; j<countLines;j++){
    		char c = protein[j].getSequence().charAt(i);

    			if(c == e){
    		protein[j].setSimilar(protein[j].getSimilar()+3);
    		}else if(c== '-'){
    			protein[j].setSimilar(protein[j].getSimilar()-1);
    		}else if (c!=e){

    			protein[j].setSimilar(protein[j].getSimilar()-2);
    		}
    		}

    	}

	}

	public static void printSimilar(int[] Similar, String[] ID, int maxS, int minS){

	       for (int i=0; i<ID.length;i++){
	    	   System.out.println("********************");
	        	System.out.println("ID: "+ID[i]);
	        	System.out.println("Similar: "+Similar[i]);

	        	}

	}


	public static int  findMaxS(Protein[] protein, int countLines){
		int max=protein[0].getSimilar();
		for(int i=0; i<countLines;i++){

			if (max<protein[i].getSimilar()){
				max =protein[i].getSimilar();
			}


			}
		return max;
	}

	public static int findMinS(Protein[] protein, int countLines){
		int min=protein[0].getSimilar();
		for(int i=0; i<countLines;i++){

			if (min>protein[i].getSimilar()){
				min =protein[i].getSimilar();
			}


			}
		return min;
	}


	public static void sort(Protein[] protein, int[] Similar, String[] ID, int min, int max, int initial){

		for(int i=0; i<ID.length;i++){
			if(protein[i].getSimilar()==min){
				Similar[initial]= protein[i].getSimilar();
				ID[initial]= protein[i].getIdentifier();
				initial++;
				}

			}
		if(min<max){
		sort( protein, Similar, ID, min+1,max,initial);
		}



	}

    public static double time(String alg, String[] a, int size) {
        Stopwatch timer = new Stopwatch();
        double time =0;


        if (alg.equals("LSD")) {
           LSD.sort(a,size);
        } else if (alg.equals("MSD")) {
          MSD.sort(a);
        } else if (alg.equals("Shell")) {
  //          Shell.sort(a);
        } else if (alg.equals("Merge")) {
           Merge.sort(a);
        } else if (alg.equals("Quick")) {
          Quick.sort(a);
        } else if (alg.equals("Heap")) {
  //          Heap.sort(a);
        } //if-else
         time =timer.elapsedTime() +time;
        return timer.elapsedTime();



    } //time



    	public static void storeArray(String alg,String alg2,int countLines, Protein[] protein){

    	String[] ID = new String[countLines];
    	String[] String = new String[countLines];




    	for(int i=0; i<countLines;i++){
    		ID[i]=protein[i].getIdentifier();
    		String[i]=protein[i].getSequence();
    		}
    	int maxL =lengthMax(ID,countLines);

    	for(int j=0;j<countLines;j++){

    		if(ID[j].length()<maxL){
    			int temp =ID[j].length();
    			for (int k=0; k<(maxL-temp);k++){
    				ID[j]= ID[j] +" ";
    				}

    		}


    	}

    	for (int t=0; t<6;t++){


    	double v1 = time(alg, String,0);
    	double v2 =time(alg2, String,0);

    	System.out.println(alg2 + " is "+ (v2/v1)+ " times faster than " + alg);
    	System.out.println(v2/v1);



    	}
}

    	public static int lengthMax(String[] ID, int countLines){
    		int max =ID[0].length();
    		for(int i=0; i<countLines;i++){
    			if(ID[i].length()>max){
    				max=ID[i].length();
    			}

    		}

    		return max;

    	}

	}









