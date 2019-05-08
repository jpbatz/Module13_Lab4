SortComparison.java

Joanne Hayashi / EN.605.202.84.SP19 / May 7, 2019


Compile:
$ java SortComparison.java


Usage:

	java SortComparison <command list filename> <report filename> <# items> [# iterations]

	The <command list file> has the format:
		<input filename> [<output filename for n=50 items>]

		Where 
			<input filename> contains the input items to sort (see Note)
			<output filename> will contain the sorted array and is required for 50 items, only.

	The <report filename> will contain runtime metrics.

	The <# of items> is: 50 | 500 | 1k | 2k | 5k | 10k | 20k

	The [# iterations] is the number of times each sort should run - the average runtime will be calculated. 
	Default is 1 iteration. This qualifier is optional.

	Note: Each command list file contains a list of input files for the same n amount of items for the
	      4 order types listed one per line, in the following order: 
		ascending, random, random duplicate, reverse.


Run (command line prompt):
java SortComparison <command list filename> <report filename> <# items> [# iterations]

java SortComparison files50items.txt report50items.txt 50 10
java SortComparison files500items.txt report500items.txt 500 10
java SortComparison files1Kitems.txt report1Kitems.txt 1k 10
java SortComparison files2Kitems.txt report2Kitems.txt 2k 10
java SortComparison files5Kitems.txt report5Kitems.txt 5k 10
java SortComparison files10Kitems.txt report10Kitems.txt 10k 10
java SortComparison files20Kitems.txt report20Kitems.txt 20k 10

Run Eclipse Arguments:

files50items.txt report50items.txt 50 10
files500items.txt report500items.txt 500 10
files1Kitems.txt report1Kitems.txt 1k 10
files2Kitems.txt report2Kitems.txt 2k 10
files5Kitems.txt report5Kitems.txt 5k 10
files10Kitems.txt report10Kitems.txt 10k 10
files20Kitems.txt report20Kitems.txt 20k 10


Output files for n=50 (sorted):
asc50out.txt
ran50out.txt
dup50out.txt
rev50out.txt  

Each command list file contains the run parameters for the same n items and files for the 4 order types listed in the following order: ascending, random, random duplicate, reverse.

Format for command list file is:
<input order filename> [<output file for n=50>]

files50items.txt
asc50.txt asc50out.txt
ran50.txt ran50out.txt
dup50.txt dup50out.txt
rev50.txt rev50out.txt

files500items.txt
asc500.txt
ran500.txt
dup500.txt
rev500.txt

files1Kitems.txt
asc1K.txt
ran1K.txt
dup1K.txt
rev1K.txt

files2Kitems.txt
asc2K.txt
ran2K.txt
dup2K.txt
rev2K.txt

files5Kitems.txt
asc5K.txt
ran5K.txt
dup5K.txt
rev5K.txt

files10Kitems.txt
asc10K.txt
ran10K.txt
dup10K.txt
rev10K.txt

files20Kitems.txt
asc20K.txt
ran20K.txt
dup20K.txt
rev20K.txt