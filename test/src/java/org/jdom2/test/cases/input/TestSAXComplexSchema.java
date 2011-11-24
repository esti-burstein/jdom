/**
 * 
 */
package org.jdom2.test.cases.input;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJAXPSingletons;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


/**
 * @author Rolf Lear
 *
 */
@SuppressWarnings("javadoc")
public class TestSAXComplexSchema {

	
	/**
	 * Test method for {@link org.jdom.input.SAXBuilder#build(java.io.File)}.
	 */
	@Test
	public void testBuildFile() throws IOException {
		SAXBuilder builder = new SAXBuilder(true);
		builder.setFeature("http://xml.org/sax/features/namespaces", true);
		builder.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
		builder.setFeature("http://apache.org/xml/features/validation/schema", true);
		
		File inputdir = new File(".");
		URL furl = inputdir.toURI().toURL();
		URL rurl = new URL(furl, "test/resources/xsdcomplex/input.xml");
		
		
		try {
			Document doc = builder.build(rurl);
			XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
			StringWriter sw = new StringWriter();
			out.output(doc, sw);
			assertTrue(sw.toString().length() > 0);
			//System.out.println("Document parsed. Content:\n" + xml + "\n");
			
			Namespace defns = Namespace.getNamespace("http://www.jdom.org/tests/default");
			Namespace impns = Namespace.getNamespace("http://www.jdom.org/tests/imp");
			
			Element root = doc.getRootElement();
			assertTrue(root != null);
			assertTrue("test".equals(root.getName()));
			List<Element> kids = root.getChildren("data", defns);
			for (Iterator<Element> it = kids.iterator(); it.hasNext(); ) {
				Element data = it.next();
				assertTrue(defns.equals(data.getNamespace()));
				Attribute att = data.getAttribute("type", Namespace.NO_NAMESPACE);
				assertTrue("Could not find type attribute in default ns.", att != null);
				att = data.getAttribute("type", impns);
				assertTrue("Could not find type attribute in impns.", att != null);
			}
		} catch (JDOMException e) {
			e.printStackTrace();
			fail("Parsing failed. See stack trace.");
		}
		
	}

	/**
	 * Test method for {@link org.jdom.input.SAXBuilder#build(java.io.File)}.
	 */
	@Test
	public void testBuildFileNewSAX() throws IOException {
		SAXBuilder builder = new SAXBuilder(XMLReaderJAXPSingletons.XSDVALIDATING);
		
		File inputdir = new File(".");
		URL furl = inputdir.toURI().toURL();
		URL rurl = new URL(furl, "test/resources/xsdcomplex/input.xml");
		
		
		try {
			Document doc = builder.build(rurl);
			XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
			StringWriter sw = new StringWriter();
			out.output(doc, sw);
			assertTrue(sw.toString().length() > 0);
			//System.out.println("Document parsed. Content:\n" + xml + "\n");
			
			Namespace defns = Namespace.getNamespace("http://www.jdom.org/tests/default");
			Namespace impns = Namespace.getNamespace("http://www.jdom.org/tests/imp");
			
			Element root = doc.getRootElement();
			assertTrue(root != null);
			assertTrue("test".equals(root.getName()));
			List<Element> kids = root.getChildren("data", defns);
			for (Iterator<Element> it = kids.iterator(); it.hasNext(); ) {
				Element data = it.next();
				assertTrue(defns.equals(data.getNamespace()));
				Attribute att = data.getAttribute("type", Namespace.NO_NAMESPACE);
				assertTrue("Could not find type attribute in default ns.", att != null);
				att = data.getAttribute("type", impns);
				assertTrue("Could not find type attribute in impns.", att != null);
			}
		} catch (JDOMException e) {
			e.printStackTrace();
			fail("Parsing failed. See stack trace.");
		}
		
	}

}
