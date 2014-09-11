import java.io.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class Ngrams_3 {

	static public String getContents(File aFile) {
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
    StringBuilder contents = new StringBuilder();
	Pattern pattern,pattern1;
	Matcher matcher,matcher1;
	String USERNAME_PATTERN = "[\u0900 - \u097F]+";
	String USERNAME_BLANKLINES = "\\s*";
	pattern = Pattern.compile(USERNAME_PATTERN);
	pattern1 = Pattern.compile(USERNAME_BLANKLINES);
	
    
    try {
      
      BufferedReader input =  new BufferedReader(new FileReader(aFile));
	  
      try {
///////////////////////////////////////// PART 1 //////////////////////////////////////////////////////////////////////////////////////////			
		
		String line = null; //not declared within while loop
        while (( line = input.readLine()) != null){
			matcher1 = pattern1.matcher(line);
				if(matcher1.matches()) {
						++count_blank;
				}
			StringTokenizer stringTokenizer = new StringTokenizer(line);
			
		    while (stringTokenizer.hasMoreElements()) {
				StringBuilder sb = new StringBuilder();
				String temp = stringTokenizer.nextElement().toString();
				sb.append(" [ " + temp + "]");
				int count = 0;
				matcher = pattern.matcher(temp);
					if(matcher.matches()) {
						count_alpha ++;
						sb1.append("[" + temp +"]");
						words.add(temp);
					}
					else {
						/*for(int i=0; i<temp.length(); i++ ) {
							char ch = temp.charAt(i);
							/*if(Character.isDigit(ch)) {
								plist.add(Character.toString(ch));
								punc.append(" "+ ch +",");
								continue;
							}
							if(Character.isLetter(ch)) {
								count_alpha++;
								break;
							}
							else {
								plist.add(Character.toString(ch));
								punc.append(" "+ ch +",");
							}
						}*/
						
					}
			}
		}
		
			uwords = new HashSet<String>(words);
			uplist = new HashSet<String>(plist);
			//System.out.println(" -------------------------------- Part 1 -------------------------------- ");
			//System.out.println(" There were " + count_alpha + " words.");
			//System.out.println(" There were " + ++count_blank + " blank lines.");
			//System.out.println(" There are " + uplist + " Punctuations ");
			punc.append("]");
		
			
			
//////////////////////////////////////// PART 2 ///////////////////////////////////////////////////////////////////////////////////////////			
		
		Map<String, Integer> occurrences = new HashMap<String, Integer>();
		List<String> store_ngrams = new ArrayList<String>();
		String line_1 =null;
		StringBuilder buffer = new StringBuilder();
		System.out.println(" ------------------------------------- Part 2 --------------------------------- " );
		for (int n = 1; n < 5; n++) {
			BufferedReader input_1 =  new BufferedReader(new FileReader(aFile));
			
			while (( line_1 = input_1.readLine()) != null) {
				matcher1 = pattern1.matcher(line_1);
				if(matcher1.matches()) {
						continue;
				}
				
				StringTokenizer stringTokenizer_1 = new StringTokenizer(line_1);
			
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
								
								/*if(Character.isDigit(ch)) {
									continue;
								}*/
								/*if(Character.isLetter(ch)) {
									count_alp++;
									chwrd = chwrd + ch ;
								}*/
								else {
									
								}								
							}
							//chwrd = chwrd.toLowerCase();
							//buffer.append(chwrd + " ");
						}
				}
				
				String final_string = buffer.toString();
				System.out.println(" ---- >String is " + final_string );
				for (String ngram : ngrams(n, final_string)) {
				
					store_ngrams.add(ngram);
					Integer oldCount = occurrences.get(ngram);
					   if ( oldCount == null ) {
						  oldCount = 0;
					   }
					 
					occurrences.put(ngram, oldCount + 1);
				}
				buffer.delete(0,buffer.length());
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
				while (iterator_sort.hasNext()) {  
				   String key = iterator_sort.next().toString();  
				   String value = sortedMap.get(key).toString();  
						
				   System.out.println(key + " - [ " + value + " ] ");  
				   ++topten;
				   if(topten>9) break;
				} 
				
			occurrences.clear();
			store_ngrams.clear();
		}
		
	  }
	  
      finally {
        input.close();
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
    File testFile = new File("D:\\SPRING 2013_DP\\ALGORITHM\\ASSIGNMENT 3\\input.txt");
    System.out.println("" + getContents(testFile));   
  }
} 
