REPORT - PROGRAMMING ASSIGNMENT 3

DOCUMENTATION

Map: For every keyword in the fileName list, it generates the pass pairs of keyword and fileName _count. I have used two arrays – first to store the keyword (arrayKeywords) and the second to store the count (arrayValue). The StringTokenizer, tokenizes every word. If that word is a keyword, then the value in the second array at the ith location corresponding to the keyword’s [i] is incremented by 1. Only those keywords whose count is not equal to 0 are collected in the output in the format: keyword, fileName_count.

Combine: A partial reduction process at each node. Used to reduce the number of intermediate outputs, starts aggregating all occurrences of fileNames. This method uses a Hashtable (docListTable) to store all the fileName and count. The fileName _count are split at the ‘_’. The fileName’s count is appended every time the same fileName is encountered and the new count is added back into map against the fileName. The output collects in the format: keyword, fileName _count

Reduce: Summarizes all the information about each keyword and the sum of occurrences of all fileNames and orders the values. I have used Hashtable (docListTable) to store the fileName and count. It checks whether fileName is already stored and if yes, the count with the same fileNames are aggregated; if no, the current count for the fileName is inserted into the table.

Additional Feature: SORTING the filenames in descending order with respect to count is implemented in reduce function.

I have used ArrayList (arrayList) to sort by comparing the counts in descending order. The arrayList is iterated to get the format fileName1 count1 fileName2 count2 and so on with the string docList. The output is collected in the format: keyword, fileName1 count1.

Elapsed Time: To check the elapsed time, I used the ‘time’ in the command line. The real time shows the time taken to finish the MapReduce. Below is the command line:
time ../bin/hadoop jar invertedindexing.jar InvertedIndexing rfc output TCP UDP LAN PPP HDLC
The first real is the elapsed time.

Results: On comparing the performance between single-node and four-node, this code achieves a performance improvement of about 1.17 times.

The elapsed with Single-Node is 8.315 seconds
The elapsed with Four-Node is 7.095 seconds
Performance Improvement 8.315/7.095 = 1.172

YARN, a resource management platform which is responsible for managing the resources in clusters is needed to monitor the processing operations of individual cluster nodes. It makes the environment more suitable for applications that can't wait for batch jobs to finish. But implementation of this is very tedious.

DISCUSSION

What if you wrote the same program using MPI? Using MPI, the number of documents has to be manually divided and each node is responsible for different set of documents and computes the key, value for that set and results has to be gathered at the end. It is difficult to distribute the rpc file to different system with MPI and there is no much inter process communication (IPC) to make use of MPI kernel interaction overhead. The map corresponds to MPI_Scatter, MPI_BCast and combiner and reducer corresponds to MPI_Gather, MPI_Reduce (synchronization has to be done manually). Computation and communication overhead in the mapper can be handled using pipeline. MPI gives more control of how the data can be distributed and computed but it is difficult to debug and identify the faults. With MapReduce I was able to debug and understand how map and reduce works.

MapReduce:

Easy to code and develop but optimization and Hadoop installation is difficult. File system is used for message passing which is much slower. All the parallelism, data partitioning and synchronization of threads are all done automatically. Can debug easily. Good for data parallelism, large data with little IPC. No loading of whole data into memory and executed in parallel over cluster. It is fault-tolerant. Restricted to Map/reduce operations with k,v pairs.

MPI:

Difficult to code but easy installation. It is fast. User has to code the partition and synchronization. Gives explicit control over how nodes send messages to each other so good for complicated coordination. Can specify where to parallelize. Cannot debug easily. Good for task parallelism, moderate data size and with huge IPC. Has shared memory between tasks. Supports iterative computation. Nodes are independent.

MapReduce Pros:

Simple to develop. Stack trace while debugging. Hides messy internals from user. Do not need to consider data partition, process creation and synchronization. Good built in fault-tolerance by maintain multiple copies and scalable. Automatic parallelism, task division and data movement. Better for large data with less communication.

MapReduce Cons:

Single namenode needs care. Not suitable for real-time processing and streaming data. No shared memory and limits communication (IPC) between nodes. Does not have its own file management system and installing and running with Hadoop is difficult. Not suitable for iterative heavy computation and simulations.
Applications written better with MapReduce: Any application which uses the key: value pair that has to simplify to a total list (document clustering),less intermediate talking and shuffling of data, batch process and those which can have a ‘map’ parallel step followed by gathering ‘reduce’. Google uses for generating the index for the web search (information retrieval), stitching imagery data, analyze large data, distributed pattern based searching, distributed grep, distributed sort, web analytics -counting URL access frequency, page rank computation, large scale machine learning, plagiarism detection.
Performance Consideration: Sorting the fileNames in descending order with respect to count is slowing down the performance slightly. There are lot of string comparisons, splitting and formatting. They can be optimized by using algorithms which help in pattern matching. If there are millions of files, the Hashtable will become really big and have to read all files from it. We can segment the space into smaller ones so the reducer does not need to keep all files in memory. Reducer retrieves data from many servers remotely. We can have few thousands at a time in memory and then repeat. Re-execution with machine failure will result in more time.

EXECUTION OUTPUT

Executuion output : typescriptNode1.txt (Single node – typescriptNode1) and typescriptNode4.txt (Four nodes – typescriptNode4)

Below are the screen shots of some outputs and starting of Hadoop with single and four nodes.
