##CSC522 - Document Clustering using KMeans on Hadoop


##Dataset 

https://archive.ics.uci.edu/ml/datasets/Bag+of+Words

##Important Files

**preprocess.py** - It preprocesses the data into the required format.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;```docid:{(word_id;count),(word_id;count),...,(wordid;count)}```

**KMeans.java** - Driver program that sets the job configuration

**KMeansMapper.java** -  Mapper program that implements the mapping of vector to their respective clusters

**KMeansReducer.java** - Reducer program for recalcuting the mean

**Vector.java** - Custom vector class to represent the document

**Distance.java** - Auxillary method to implement various distance measure for evaluations


##Dependencies

<ol>
	<li>Hadoop Framework (Apache Hadoop 2.7.2)</li>
	<li>SLF Logging Library (slf4j-1.7.21) </li>
</ol>

##Installation and Running

The program can be run using the uploaded the Runnable JAR file (kmeans.jar).

Preprocess the document using *preprocess.py* and upload it on hadoop

```{r, engine='bash', count_lines}

$> python preprocess.py text_file > input_file
$> hdfs dfs -put input_file /
$> hadoop jar kmeans.jar path/to/data /output/directory K max_iterations
$> hadoop dfs -cat /outputFINAL_N/part-r-00000

```

##Example

```{r, engine='bash', count_lines}

$> python preprocess.py text.txt > input.txt
$> hdfs dfs -put input.txt /
$> hadoop jar kmeans.jar /input.txt /output 4 1000
$> hadoop dfs -cat /outputFINAL_4/part-r-00000

```

##Contributors
<ul>

	<li>Rahul Prashant Shah (rshah5@ncsu.edu)</li>
	<li>Aniket Hiren Patel (apatel10@ncsu.edu)</li>
	<li>Ethan Swartzentruber (epswartz@ncsu.edu)</li>
	<li>Ming Dai (mdai3@ncsu.edu)</li>
	<li>Rongjin Wang (rwang10@ncsu.edu)</li>
	<li>Aditya Mandhare (amandha3@ncsu.edu)</li>

</ul>








