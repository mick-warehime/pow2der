package items;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class ItemParser {

	private DocumentBuilderFactory buildFactory;
	private DocumentBuilder builder;
	private Document document;

	// store items as hashmaps in a big list
	private List<Map<String, String>> itemMaps = new ArrayList<Map<String, String>>();

	public ItemParser(String fileName) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {

		setItemMaps(fileName);

	}

	public List<Map<String, String>> getItemMaps(){
		return itemMaps;
	}


	private void setItemMaps(String fileName) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException{

		// This function reads items.xml and takes every item entry in the xml and turns it into a hashmap

		
		//		load items/items.xml file into a new xml parsing document
		buildFactory = DocumentBuilderFactory.newInstance();	
		builder = buildFactory.newDocumentBuilder();
		document = builder.parse(new FileInputStream(fileName));



		// root element of the xml
		Element rootElement = document.getDocumentElement();

		// these nodes are the sheet names
		NodeList children = rootElement.getChildNodes();

		// loop over sheet names
		for (int i=1; i<children.getLength(); i++){
			String childName = children.item(i).getNodeName();
			if(!childName.equalsIgnoreCase("#text")){


				NodeList items = children.item(i).getChildNodes();
				// loop over the items in the sheet
				for (int j = 0; j < items.getLength(); j++){

					//for each item add a new armor item to the armor item list 
					if (items.item(j).getNodeName().toString().equalsIgnoreCase("item")){
						Element item = (Element) items.item(j);

						// ignore blank rows
						if(!items.item(j).getNodeName().equalsIgnoreCase("#text")){

							HashMap<String, String> itemMap = new HashMap<String,String>();

							// add the sheetname as the first entry of the map
							itemMap.put("itemType",childName);

							// loop over all the columns of the item and make a map entry
							NamedNodeMap attributes = item.getAttributes();						
							for (int k = 0; k < attributes.getLength(); k++) {

								// add an entry for each item attribute using its name as the key and its value as the value
								String attributeName = attributes.item(k).getNodeName();
								String attributeValue = attributes.getNamedItem(attributeName).getNodeValue();

								itemMap.put(attributeName,attributeValue);						


							}

							itemMaps.add(itemMap);
						}

					}
				}
			}
		}
	}





}
