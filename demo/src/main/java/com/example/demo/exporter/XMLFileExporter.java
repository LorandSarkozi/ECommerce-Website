package com.example.demo.exporter;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.StringWriter;
import java.util.List;

public class XMLFileExporter implements FileExporter {

    @Override
    public String exportData(Object object) {
        String xmlContent = null;
        try {
            JAXBContext jaxbContext;
            if (object instanceof List) {
                // Create a JAXB context for the wrapper class
                jaxbContext = JAXBContext.newInstance(WrappedList.class, object.getClass().getComponentType());
                object = new WrappedList((List<?>) object);
            } else {
                jaxbContext = JAXBContext.newInstance(object.getClass());
            }

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter sw = new StringWriter();
            // Include the reference to the XSLT file
            sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            sw.write("<?xml-stylesheet type=\"text/xsl\" href=\"/order_stylesheet.xslt\"?>\n");

            jaxbMarshaller.marshal(object, sw);

            xmlContent = sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return xmlContent;
    }

    @XmlRootElement(name = "items")
    public static class WrappedList {
        private List<?> items;

        public WrappedList() {
        }

        public WrappedList(List<?> items) {
            this.items = items;
        }

        @XmlElement(name = "item")
        public List<?> getItems() {
            return items;
        }

        public void setItems(List<?> items) {
            this.items = items;
        }
    }
}
