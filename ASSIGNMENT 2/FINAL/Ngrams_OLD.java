import java.io.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class Ngrams {

	static public String getContents(File aFile, File bFile, File cFile) throws FileNotFoundException, IOException{
    //////////////////////////////////// PART 1 //////////////////////////////////////////
		int count_alpha = 0;
		int count_blank = 0;
		StringBuilder sb1 = new StringBuilder();
		StringBuilder punc = new StringBuilder();
		punc.append("[");
		List<String> plist = new ArrayList<String>();
		List<String> words = new ArrayList<String>();
		Set<String> uwords;
		Set<String> uplist;	
	//////////////////////////////////// PART 2 ///////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	
	if (cFile == null) {
      throw new IllegalArgumentException("File should not be null.");
    }
    if (!cFile.exists()) {
      throw new FileNotFoundException ("File does not exist: " + cFile);
    }
    if (!cFile.isFile()) {
      throw new IllegalArgumentException("Should not be a directory: " + cFile);
    }
    if (!cFile.canWrite()) {
      throw new IllegalArgumentException("File cannot be written: " + cFile);
    }
	
	Writer output = new BufferedWriter(new FileWriter(cFile));
	/////////////////////////////////////////////////////////////////////
    StringBuilder contents = new StringBuilder();
	Pattern pattern,pattern1;
	Matcher matcher,matcher1;
	String USERNAME_PATTERN = "[a-zA-Z]+";
	String USERNAME_BLANKLINES = "\\s*";
	pattern = Pattern.compile(USERNAME_PATTERN);
	pattern1 = Pattern.compile(USERNAME_BLANKLINES);
	
    
    try {
      
      BufferedReader input =  new BufferedReader(new FileReader(aFile));
	  
	  
      try {
///////////////////////////////////////// PART 1 //////////////////////////////////////////////////////////////////////////////////////////			
		
		String line = null; //not declared within while loop

			
			
//////////////////////////////////////// PART 2 ///////////////////////////////////////////////////////////////////////////////////////////			
		
		Map<String, Integer> occurrences = new HashMap<String, Integer>();
		List<String> store_ngrams = new ArrayList<String>();
		String line_1 =null;
		String stop_word = null;
		StringBuilder buffer = new StringBuilder();
		System.out.println(" ------------------------------------- Part 2 --------------------------------- " );
		BufferedReader stop_words = new BufferedReader(new FileReader(bFile));
		List<String> csv_list = new ArrayList<String>();
		stop_word = stop_words.readLine();
			//System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			String[] csv_words = stop_word.split(",");
		for(String csv : csv_words) 
			csv_list.add(csv);
				
		for (int n = 1; n < 5; n++) {
			BufferedReader input_1 =  new BufferedReader(new FileReader(aFile));

			while (( line_1 = input_1.readLine()) != null) {
				matcher1 = pattern1.matcher(line_1);
				if(matcher1.matches()) {
						continue;
				}
				
				StringTokenizer stringTokenizer_1 = new StringTokenizer(line_1);
				StringBuilder buffer1 = new StringBuilder();
				while (stringTokenizer_1.hasMoreElements()) {
				
					StringBuilder sb = new StringBuilder();	
					String temp = stringTokenizer_1.nextElement().toString();
					int count = 0;
						
					matcher = pattern.matcher(temp);
						if(matcher.matches()) {
							buffer.append(temp+" ");
						}
						else {
							String chwrd = "";
							for(int i=0; i<temp.length(); i++ ) {
								char ch = temp.charAt(i);
								int count_alp = 0;
								
								if(Character.isDigit(ch)) {
									continue;
								}
								if(Character.isLetter(ch)) {
									count_alp++;
									chwrd = chwrd + ch ;
								}
								else {
									
								}								
							}
							chwrd = chwrd.toLowerCase();
							buffer.append(chwrd + " ");
						}
				}
				
				String final_string = buffer.toString();
				///////////////////////////////////////////////////////////////////////////////////////////////////////////
				String temp_string = final_string.trim().replaceAll("\\s+", " ");
				temp_string = temp_string.toLowerCase();
				String[] all_words = temp_string.split(" ");
				/*System.out.println("---------------------------");
				for(String word : all_words) {
					System.out.println(">>>>>>>>>>>>>> " + word);
				}*/
				//System.out.println(">>>>>>>>>>>>>> " + final_string);
				//System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				int count_n = 0;
				int count_y = 0;
				
				//System.out.println("Length is " + all_words.length);
				
						
				for (int i = 0; i<all_words.length; i++) {
					//System.out.println(" [ " +all_words[i]+"]");
					 
								//System.out.println("_"+csv);
							if(csv_list.contains(all_words[i])) {
									//System.out.println("the list is " + all_words[i]);
									count_y = 1;
									count_n = 0;
							}else {
								//System.out.println(" not in list is" + all_words[i]);
								count_n = 1;
							}
							
							/*for(String splitt : csv_list) {
								//System.out.println(" [ " +all_words[i]+"]");
								//System.out.println("->"+splitt+"<-");
								System.out.println(" I is "+all_words[i]);
								if(all_words[i].equals(splitt)) {
									count_y = 1;
									System.out.println(" IF !!!!!!!!!!!!!!!!!");
									break;
								}
								else{
									count_n = 1;
									System.out.println(" ELSE !!!!!!!!!! "+all_words[i]); //buffer1.append(all_words[i]+" ");
									//continue;
								}
								//System.out.println(" why comes" );
							}*/
								//System.out.println(" why comes" );
					
						if(count_n == 1 && count_y == 0 ) {
							//System.out.println(" Appended");
							buffer1.append(all_words[i]+" ");
						}
						count_y=0;
						count_n=0;
				}
				String new_final_string = buffer1.toString();
				//System.out.println("????" + new_final_string);
				for (String ngram : ngrams(n, new_final_string)) {
					store_ngrams.add(ngram);
					Integer oldCount = occurrences.get(ngram);
					   if ( oldCount == null ) {
						  oldCount = 0;
					   }
					occurrences.put(ngram, oldCount + 1);
				}
				buffer.delete(0,buffer.length());
				buffer1.delete(0,buffer1.length());
			}
			System.out.println(" ---------------- For " + n + "-grams ----------- ");
			Set<String> unique_ngrams = new HashSet<String>(store_ngrams);
			System.out.println(" There are UNIQUE : " + unique_ngrams.size() + " grams ");
			System.out.println();
			
			Iterator<String> iterator = occurrences.keySet().iterator();  
			List<Integer> topmost = new ArrayList<Integer>();
				/*
				while (iterator.hasNext()) {  
				   String key = iterator.next().toString();  
				   String value = occurrences.get(key).toString();  
						
				   System.out.println(key + " - [" + value + "] ");  
				} */
			Map<String, Integer> sortedMap = sortByComparator(occurrences);
			
			Iterator<String> iterator_sort = sortedMap.keySet().iterator();  
			int topten = 0;
			if( n == 2 ) {
				String init = "digraph words {";
				output.write(init);
				output.write("\n");
				while (iterator_sort.hasNext()) {  
				   String key = iterator_sort.next().toString();  
				   String value = sortedMap.get(key).toString();  
				   String[] two_keys = key.split(" ");
				   
				   output.write(" \" "+two_keys[0] + " \" "+ " -> "+" \" "+two_keys[1]+ " \" "+ "[weight="+value+"];" );
				   output.write("\n");
				   //++topten;
				   //if(topten>9) break;
				} 
				output.write("}");
			}	
			occurrences.clear();
			store_ngrams.clear();
		}
		
	  }
	  
      finally {
        input.close();
		output.close();
      }
    } catch (IOException ex){
		ex.printStackTrace();
    }
		contents.append(System.getProperty("line.separator"));
    return contents.toString();
  }
// Methods Declarations and its definitions called from above method.  
  
				static public List<String> ngrams(int n, String str) {
				
						List<String> ngrams = new ArrayList<String>();
						str = str.replaceAll("\\s+", " ");
						String[] words = str.split(" ");
						for (int i = 0; i < words.length - n + 1; i++)
							ngrams.add(concat(words, i, i+n));
						
					return ngrams;
				}

				static public String concat(String[] words, int start, int end) {
						StringBuilder sb = new StringBuilder();
						for (int i = start; i < end; i++)
							sb.append((i > start ? " " : "") + words[i]);
						return sb.toString();
				}
							
				static public Map<String,Integer> sortByComparator(Map unsortMap) {
			 
					List<String> list = new LinkedList<String>(unsortMap.entrySet());
						Collections.sort(list, new Comparator() {
							public int compare(Object o1, Object o2) {
								return ((Comparable) ((Map.Entry) (o2)).getValue())
													   .compareTo(((Map.Entry) (o1)).getValue());
							}
						});
			 
					// put sorted list into map again
					//LinkedHashMap make sure order in which keys were inserted
					Map<String,Integer> sortedMap = new LinkedHashMap<String,Integer>();
					for (Iterator it = list.iterator(); it.hasNext();) {
						Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>) it.next();
						sortedMap.put(entry.getKey(), entry.getValue());
					}
					return sortedMap;
				}
  

  public static void main (String... aArguments) throws IOException {
    File testFile = new File("D:\\SPRING 2013_DP\\ALGORITHM\\ASSIGNMENT 2\\shakespeare.txt");
	File stopword = new File("D:\\SPRING 2013_DP\\ALGORITHM\\ASSIGNMENT 2\\stop_words.txt");
	File output   = new File("D:\\SPRING 2013_DP\\ALGORITHM\\ASSIGNMENT 2\\Output.txt");
    System.out.println("" + getContents(testFile,stopword,output));   
  }
} 
