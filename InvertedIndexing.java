// Program to write and execute an inverted-indexing
// program with MapReduce. 
// It creates a list of documents for each keyword with 
// the count of the keyword in each document.
// The list of documents are sorted in descending order 
// with respect to count.
// Name : Anusha Prabakaran
// Stdent ID : 1470730


import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class InvertedIndexing {
	// Mapper
    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
	    // configuring and memorizing this job object
		JobConf conf;
		public void configure( JobConf job ) {
			this.conf = job;
		}

		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			// retrieve # keywords from JobConf
			int argc = Integer.parseInt( conf.get( "argc" ) );
			// get the current file name
			FileSplit fileSplit = ( FileSplit )reporter.getInputSplit( );
			String fileName = "" + fileSplit.getPath( ).getName( );

			// Array to store the keywords
			String[] arrayKeywords = new String[argc];
			// Array to store the count
			int[] arrayValue = new int[argc];

			for ( int i = 0; i < argc; i++ ) {
				arrayKeywords[i] = conf.get( "keyword" + i );  
			}

			// Reading each line and tokenizing with " "
		    String line = value.toString();
		    StringTokenizer tokenizer = new StringTokenizer(line, " ");
		    // Increment the count if it is a key word
		    while (tokenizer.hasMoreTokens()) {	
		    	String word = tokenizer.nextToken();
				for ( int i = 0; i < argc; i++ ) {
					if ( arrayKeywords[i].equals(word) ) {
						arrayValue[i]++;
						break;
					}
				}
		    }

		    // Collect the output in the format : keyword, filename_count
		    for ( int i = 0; i < argc; i++ ) {
				if (arrayValue[i] != 0) {
		    		output.collect(new Text(arrayKeywords[i]), new Text(fileName + "_" + arrayValue[i]));  
		    	}
		    } 
		}
    }
///////////////////////////////////////////////////////////////////////
    Int a[N][N];
Int max = ...;
             for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        x = i;
                        y = j;
                        x2 = x * x;
                        y2 = y * y;
                        iteration = 0;
                        for (; iteration < max && (x2 + y2) < 4.0;
                        iteration++) {
                            y = 2 * x * y + j;
                            x = x2 - y2 + i;
                            x2 = x * x;
                            y2 = y * y;
                        }
                        a[i][j] = iteration;
                    }
                }

  String line = value.toString();
		    StringTokenizer tokenizer = new StringTokenizer(line, " ");
		    // Increment the count if it is a key word
		    while (tokenizer.hasMoreTokens()) {	
		    	String iString = tokenizer.nextToken();
		    	String jSTring = tokenizer.nextToken();
		    	atoi
		    }

		    	calculation
		    	output.collect(new Text(istring + " " + jstring), new Text(iteration));  
 
		    }

///////////////////////////////////////////////////////////////////////
    // Combiner
    public static class Combine extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			// Hashtable to store the fileName and count
			Hashtable<String, Integer> docListTable = new Hashtable<String, Integer>();

		    while (values.hasNext()) {
		    	String word = values.next().toString();
		    	if (word.indexOf('_') >= 0 ) {
		    		// Splitting the filename_count at _ as fileName and count
			    	String fileName = word.substring(0, word.lastIndexOf('_'));
			    	Integer count = Integer.parseInt(word.substring(word.lastIndexOf('_') + 1));
			    	// Current count of the fileName
			    	Integer curCount = docListTable.get(fileName);
			    	// Appending the count
			    	if ( curCount != null ) {
			    		count += curCount;
			    	}
			    	// Inserting the appended count to the fileName
			    	docListTable.put(fileName, count);
		    	}
		    }

		    // Collect the output in the format : keyword, filename_count
		    for (String doc : docListTable.keySet()) {
		    	output.collect(key, new Text(doc + "_" + docListTable.get(doc)));
			} 
		}
    }
	 
	// Reducer
    public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			// Hashtable to store the fileName and count
			Hashtable<String, Integer> docListTable = new Hashtable<String, Integer>();
			// Formatting in the form : fileName1 count1 fileName2 count2..
			String docList= new String();

		    while ( values.hasNext() ) {
		    	String word = values.next().toString();
		    	if ( word.indexOf('_') >= 0 ) {
		    		// Splitting the filename_count at _ as fileName and count
			    	String fileName = word.substring(0, word.lastIndexOf('_'));
			    	Integer count = Integer.parseInt(word.substring(word.lastIndexOf('_') + 1));
			    	// Current count of the fileName
			    	Integer curCount = docListTable.get(fileName);
			    	// Appending the count 
			    	if ( curCount != null ) {
			    		count += curCount;
			    	}
			    	// Inserting the appended count to the fileName
			    	docListTable.put(fileName, count);
		    	}
		    }

		    // SORTING IN DESCENDING ORDER WITH RESPECT TO COUNT
		    // Using ArrayList for sorting the Hashtable
			ArrayList<Entry<String, Integer>> arrayList = new ArrayList(docListTable.entrySet());
			Collections.sort( arrayList, new Comparator<Entry<String, Integer>>() {
         		    public int compare( Entry<String, Integer> o1, Entry<String, Integer> o2 ) {
            		        return o2.getValue().compareTo(o1.getValue());
        		    }
 			});

		    // Iterating arrayList to the display format : fileName1 count1 fileName2 count2
		    for ( Entry<String, Integer> e : arrayList ) {
		    	docList += (e.getKey() + " " + e.getValue()+ " ");
			}  
             
            Text docListText = new Text( docList );
            // Collecting the output : key, fileName count
            output.collect( key, docListText );
		}
    }
    

    // Recieves keywords or terms in args
    // Goal is to create a list of documents for each keyword. 
    public static void main(String[] args) throws Exception {
	    // input format:
        // hadoop jar invertedindexes.jar InvertedIndexes input output keyword1 keyword2 ..

        // Pass the  keyword to the map function
		JobConf conf = new JobConf(WordCount.class);            // Program’s file name
		conf.setJobName("invertindex");                         // Job name
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
		
		conf.setMapperClass(Map.class);
		conf.setCombinerClass(Combine.class);
		conf.setReducerClass(Reduce.class);
		
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		
		FileInputFormat.setInputPaths(conf, new Path(args[0]));  // input directory name
		FileOutputFormat.setOutputPath(conf, new Path(args[1])); // output directory name

		conf.set( "argc", String.valueOf( args.length - 2 ) );   // argc maintains keywords
		for ( int i = 0; i < args.length - 2; i++ ) {
			conf.set( "keyword" + i, args[i + 2]);           // keyword1, keyword2, ...
		}
		
		JobClient.runJob(conf);
    }
}
