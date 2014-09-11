//package simpleGa;

public class GA {

	 static long BEGIN;
     public static void main(String[] args) {
    	 
    	 BEGIN = System.currentTimeMillis(); 
         // Set a candidate solution
         FitnessCalc.setSolution("1111000000000000000000000000000000000000000000000000000000001111");
         String target = "1111000000000000000000000000000000000000000000000000000000001111";
         System.out.println("Our Target is ::" + target);
         System.out.println("--------------------------------------------------------------");
         System.out.println("The crossover rate is : " + Algorithm.uniformRate);
         System.out.println("Tournament size for generating parents is : " + Algorithm.tournamentSize);
         System.out.println("Mutation Rate is : " + Algorithm.mutationRate);
         System.out.println("----------------------------------------------------------------");
         
         
         // Create an initial population
         Population myPop = new Population(50, true);
         
         // Evolve our population until we reach an optimum solution
         int generationCount = 0;
         while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
             generationCount++;
             System.out.println("Generation: " + generationCount + " Fittest: " + ((myPop.getFittest().getFitness() )+1));
             myPop = Algorithm.evolvePopulation(myPop);
         }
         System.out.println("Solution found!");
         System.out.println("Generation: " + generationCount);
         System.out.println("Genes:");
         System.out.println(myPop.getFittest());
         
        
         //GA ga = new GA();   
 		 //ga.run();     
 		 long END = System.currentTimeMillis();     
 		 System.out.println("Time: " + (END - BEGIN) / 1000.0 + " sec.");

     }
     
     
 }