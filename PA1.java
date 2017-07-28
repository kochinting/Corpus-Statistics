import java.io.*;
import java.util.*;
import java.lang.String;

/**
 * PA1. java
 * @version 2.0,  2016-02-12
 * @author Chin-Ting Ko
 *
 */

public class PA1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

/* load input file "input.txt"*/
		Scanner input= new Scanner (new FileReader("bible-asv.txt")); 
/* generate output file "output.txt"*/	
		PrintWriter output = new PrintWriter (new FileWriter("output.txt"));	
		
	    int number_paragraphs = 0;   
		int total_words = 0;
		int single_occurs=0;
        String input_string;
		HashMap<String, Integer> collectionFrequency= new HashMap<String, Integer>() ;
		HashMap<String, Integer> documentFrequency= new HashMap<String, Integer>() ;
		HashMap<String, Integer> paragraphFrequency= new HashMap<String, Integer>() ;
		
	        while (input.hasNextLine()) {
	            input_string = input.nextLine();
	            
	        	if (input_string.startsWith("<P ID=")){
	        		number_paragraphs++;
	        		String paragraph = input.nextLine();
		        	String words[] = paragraph.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
		        
		        	for(int i=0; i<words.length; i++){
		        		total_words++;
		        		
		        		if(collectionFrequency.containsKey(words[i])==true){
		        			int cf= collectionFrequency.get(words[i]);
		        			collectionFrequency.put(words[i],++cf);
		                } 
		        		else if (collectionFrequency.containsKey(words[i])==false){
		        			collectionFrequency.put(words[i],1); 
			           } 
		        		
		        		if (documentFrequency.containsKey(words[i])==false){
		        			documentFrequency.put(words[i],1);
			           } 
		        		else if (documentFrequency.containsKey(words[i])==true && paragraphFrequency.containsKey(words[i])==false){
		        			int df= documentFrequency.get(words[i]);
		        			documentFrequency.put(words[i],++df);
		                }
		        		
		        		if (paragraphFrequency.containsKey(words[i])==false){
		        			paragraphFrequency.put(words[i],1); 
		        		}
		        		else if(paragraphFrequency.containsKey(words[i])==true){
		        			int pf= paragraphFrequency.get(words[i]);
		        			paragraphFrequency.put(words[i],++pf);
		                } 
		        		if (i==words.length-1){
		        			paragraphFrequency.clear();
		        		}
			        }
	        	} 
	        }

	        Set<Map.Entry<String, Integer>> entriesCf = collectionFrequency.entrySet();
	        Comparator<Map.Entry<String, Integer>> valueComparatorCf = new Comparator<Map.Entry<String,Integer>>() { 
	        	public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) { 
	        		Integer v1 = e1.getValue(); 
	        		Integer v2 = e2.getValue(); 
	        		return v2.compareTo(v1); 
	        	} 
	        };	      
	        //convert Set to List in Java 
	        List<Map.Entry<String, Integer>> listOfEntriesCf = new ArrayList<Map.Entry<String, Integer>>(entriesCf); 
	        // sorting HashMap by values using comparator 
	        Collections.sort(listOfEntriesCf, valueComparatorCf);         
	        LinkedHashMap<String, Integer> sortedCfByValue = new LinkedHashMap<String, Integer>(listOfEntriesCf.size()); 
	        // copying entries from List to Map 
	        for(Map.Entry<String, Integer> entryCf : listOfEntriesCf){ 
	        	sortedCfByValue.put(entryCf.getKey(), entryCf.getValue()); 
	        } 

	        Set<Map.Entry<String, Integer>> entriesDf = documentFrequency.entrySet();
	        Comparator<Map.Entry<String, Integer>> valueComparatorDf = new Comparator<Map.Entry<String,Integer>>() { 
	        	public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) { 
	        		Integer v1 = e1.getValue(); 
	        		Integer v2 = e2.getValue(); 
	        		return v2.compareTo(v1); 
	        	} 
	        };	      
	        // convert Set to List in Java 
	        List<Map.Entry<String, Integer>> listOfEntriesDf = new ArrayList<Map.Entry<String, Integer>>(entriesDf); 
	        // sorting HashMap by values using comparator 
	        Collections.sort(listOfEntriesDf, valueComparatorDf);   
	        LinkedHashMap<String, Integer> sortedDfByValue = new LinkedHashMap<String, Integer>(listOfEntriesDf.size());         
	        // copying entries from List to Map 
	        for(Map.Entry<String, Integer> entryDf : listOfEntriesDf){ 
	        	sortedDfByValue.put(entryDf.getKey(), entryDf.getValue());
	        	if (entryDf.getValue()==1){
	        		single_occurs++;
	        	}
	        } 

	        output.println("The total number of paragraphs processed: "+number_paragraphs);
	        output.println("The total number of unique words observed: "+collectionFrequency.size());
	        output.println("The total number of words is: "+total_words);
	        output.println();
	        Iterator<String> itCf= sortedCfByValue.keySet().iterator();
	        Iterator<String> itDf= sortedDfByValue.keySet().iterator();
	        for (int i=1; i<1001; i++){	
	        	String keyCf=(String)itCf.next();
	        	if (i<31|| i==100 || i==500 || i==1000){
	        	output.println(i+"  "+keyCf+ "- cf: " + sortedCfByValue.get(keyCf)+ " and numOccurs: " + sortedDfByValue.get(keyCf));
	        	}
	        }
	        output.println();
	        output.println("Number of words that occur in exactly one document: "+single_occurs);
	        output.println();
	        
	        //below is for tag cloud
	        Iterator<String> itCf_TagCloud= sortedCfByValue.keySet().iterator();
	        for (int i=1; i<51; i++){	
	        	String keyCf=(String)itCf_TagCloud.next();
	        	output.println(keyCf+":"+ sortedCfByValue.get(keyCf));
	        }
		output.close();
	}
}
