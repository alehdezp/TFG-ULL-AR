public class XmlHandler extends DefaultHandler {

    String elementValue = null;
    Boolean elementOn = false;
    Arrival arrival;
    public static XMLGettersSetters data = new XMLGettersSetters();

    public XmlHandler(){
        data= new XMLGettersSetters();
    }

    public static XMLGettersSetters getXMLData() {
        return data;
    }

    public static void setXMLData(XMLGettersSetters data) {
        XmlHandler.data = data;
    }
    /**
     * This will be called when the tags of the XML starts.
     **/
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        elementOn = true;
        if (localName.equals("llegada"))
        {
            Log.i("INFO", "Creando nuevo elemento llegada...");
            arrival = new Arrival();
        }

    }
    /**
     * This will be called when the tags of the XML end.
     **/
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        elementOn = false;
        /**
         * Sets the values after retrieving the values from the XML tags
         * */
        if (localName.equalsIgnoreCase("codigoparada"))
            arrival.setStopCode(elementValue);
        else if (localName.equalsIgnoreCase("denominacion"))
            arrival.setStopName(elementValue);
        /*else if (localName.equalsIgnoreCase("destinolinea"))
            arrival.setDestination(elementValue); Destination not correct, we will get the route from parsing titsa webpage*/
        else if (localName.equalsIgnoreCase("hora"))
            arrival.setHour(elementValue);
        else if (localName.equalsIgnoreCase("idtrayecto"))
            arrival.setTravelId(elementValue);
        else if (localName.equalsIgnoreCase("linea"))
            arrival.setLineNumber(elementValue);
        else if (localName.equalsIgnoreCase("minutosparallegar"))
            arrival.setMinutesForArrival(elementValue);
        else if (localName.equalsIgnoreCase("llegada")) {
            Log.i("Setting arrival:", arrival.toString());
            data.setArrivals(arrival);
        }
    }
}
