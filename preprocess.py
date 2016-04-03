import sys

#Data preprocessing script written by Ethan Swartzentruber and Ming Dai 2016
#To use, run the script with first argument as the file to be processed, and redirect output to the output file.
#For example, to preprocess the file in.txt and put result in out.txt:
# python preprocess.py in.txt >| out.txt


filename_input = sys.argv[1]


line_num = 0 # line number

doc = '1'


sys.stdout.write('1:{')
flag = True

with open(filename_input,'r') as fin:
	for line in fin: 
		if(line_num<3):
			line_num = line_num+1
		else:
			line_split = line.split()
			if (line_split[0] == doc):
				if(flag==False):
					sys.stdout.write(',')
				sys.stdout.write('(' + line_split[1]+','+line_split[2]+')')
				flag = False
			else:  #first line of next doc
				sys.stdout.write('}\n')
				sys.stdout.write(line_split[0]+':{')
				sys.stdout.write('(' + line_split[1]+','+line_split[2]+')')
				doc = line_split[0]
	sys.stdout.write('}')
				
fin.close()
