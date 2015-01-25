import sys 
import os
import xlrd
from lxml import etree

# major group of xml
root = etree.Element('items')

# load workbooks
book = xlrd.open_workbook('items.xlsx')

# load first sheet of first book (armor)
sheetnames = book.sheet_names()
num_sheets = len(sheetnames)-1
curr_sheet = -1
while curr_sheet<num_sheets:
	curr_sheet+=1

	sname = sheetnames[curr_sheet]
	sheet = book.sheet_by_name(sname)
	sheet_tree = etree.Element(str(sname))

	num_rows = sheet.nrows - 1
	num_cells = sheet.ncols - 1

	# props is a list of strings of column headers
	props = []
	curr_row = -1
	while curr_row < num_rows:
		curr_row += 1

		# load the next row
		row = sheet.row(curr_row)
		

		# loop over all columns of row
		curr_cell = -1
		while curr_cell < num_cells:
			curr_cell += 1

			# Cell Types: 0=Empty, 1=Text, 2=Number, 3=Date, 4=Boolean, 5=Error, 6=Blank
			cell_type = sheet.cell_type(curr_row, curr_cell)
			cell_value = str(sheet.cell_value(curr_row, curr_cell))

			# skip empty rows
			if curr_cell == 0 and cell_type == 0:
				break

			# grab names of properties of first row
			if curr_row == 0:			
				# store all the column rows
				props.append(cell_value)


			# add current cell to the appropriate columns child
			if curr_row > 0:
				if curr_cell == 0:

					# create a new item node
					item_tree = etree.Element('item')
					item_tree.attrib['id'] = cell_value
					item_tree.attrib['type'] = sname
				else:
					# add properties to the item nodes
					# prop_value = etree.Element(props[curr_cell])
					# prop_value.text = cell_value

					# item_tree.append(prop_value)
					item_tree.attrib[props[curr_cell]]=cell_value

		if curr_row>0:
			# add item tree to main item list
			sheet_tree.append(item_tree)

	root.append(sheet_tree)

xml_string = etree.tostring(root,pretty_print=True)

f = open('items.xml','w')
f.write(xml_string) # python will convert \n to os.linesep
f.close()
	
