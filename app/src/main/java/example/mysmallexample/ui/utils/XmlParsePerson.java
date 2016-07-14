package example.mysmallexample.ui.utils;


import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import example.mysmallexample.model.ClassItem;

/**
 * 
 * @description： 解析xml
 *
 * @author： hades
 *
 * @update： 2013-7-2
 *
 * @version： 1.0
 */
public class XmlParsePerson {

	public List<ClassItem> getPersons(XmlPullParser xmlPullParser)
			throws Exception {
		List<ClassItem> persons = null;
		ClassItem classItem = null;
		XmlPullParser parser = xmlPullParser;
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				persons = new ArrayList<ClassItem>();
				break;
			case XmlPullParser.START_TAG:
				String name = parser.getName();
				if (name.equals("class")) {
					classItem = new ClassItem();
					classItem.setClassId(new Integer(parser
							.getAttributeValue(0)));
				}
				if (classItem != null) {
					if (name.equals("name")) {
						classItem.setClassName(parser.nextText());
					}
					if (name.equals("icon")) {
						classItem.setClassIcon(parser.nextText());

					}
					if (name.equals("partid")) {
						classItem.setPartId(new Integer(parser.nextText()));
					}
					if (name.equals("partname")) {
						classItem.setPartName(parser.nextText());
					}
				}
				break;
			case XmlPullParser.END_TAG:
				if (parser.getName().equals("class")) {
					persons.add(classItem);
					classItem = null;
				}
				break;
			}
			eventType = parser.next();
		}
		return persons;
	}

}
