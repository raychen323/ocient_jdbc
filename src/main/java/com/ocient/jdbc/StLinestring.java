package com.ocient.jdbc;

import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class StLinestring
{
	private final List<StPoint> points;

	public StLinestring(final List<StPoint> points)
	{
		this.points = points;
	}

    public List<StPoint> getPoints(){
        return points; 
    }
    
	@Override
	public String toString()
	{
		if (points == null || points.size() == 0) {
			return "LINESTRING EMPTY";
		}
		final StringBuilder str = new StringBuilder();
		str.append("LINESTRING(");
		for (int i = 0; i < points.size(); i++) {
			StPoint point = points.get(i);
			str.append(point.getX());
			str.append(" ");
			str.append(point.getY());
			if(i < points.size() - 1) {
				str.append(", ");
			}
		}
		str.append(")");
		return str.toString();
	}

	public void writeXML(Document doc, Element docElement, String name, String description, int id)
	{
		Element placemark = doc.createElement("Placemark");
		docElement.appendChild(placemark);

		Element lineName = doc.createElement("name");
		lineName.appendChild(doc.createTextNode(name));
		placemark.appendChild(lineName); 

		Element lineDescription = doc.createElement("description");
		lineDescription.appendChild(doc.createTextNode(description));
		placemark.appendChild(lineDescription);       

		Element lineStyle = doc.createElement("styleUrl");
		lineStyle.appendChild(doc.createTextNode("#" + Integer.toString(id % 10)));
		placemark.appendChild(lineStyle);

		Element line = doc.createElement("LineString");
		placemark.appendChild(line);

		Attr lineID = doc.createAttribute("id");
		lineID.setValue(name);
		line.setAttributeNode(lineID);

		Element coords = doc.createElement("coordinates");
		StringBuffer coordinatesBuilder = new StringBuffer();
		for(StPoint point : points) {
			coordinatesBuilder.append(point.getX());
			coordinatesBuilder.append(",");
			coordinatesBuilder.append(point.getY());
			coordinatesBuilder.append(" ");
		}
		String coordinates = coordinatesBuilder.toString();
		coords.appendChild(doc.createTextNode(coordinates));
		line.appendChild(coords);
	}
}
