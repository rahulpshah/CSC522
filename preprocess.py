import sys
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
				sys.stdout.write('(' + line_split[1]+';'+line_split[2]+')')
				flag = False
			else:  #first line of next doc
				sys.stdout.write('}\n')
				sys.stdout.write(line_split[0]+':{')
				sys.stdout.write('(' + line_split[1]+';'+line_split[2]+')')
				doc = line_split[0]
	sys.stdout.write('}')
				
fin.close()
