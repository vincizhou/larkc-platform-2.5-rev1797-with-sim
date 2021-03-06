/*
   This file is part of the LarKC platform 
   http://www.larkc.eu/

   Copyright 2010 LarKC project consortium

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package eu.larkc.core.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Test;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

import eu.larkc.core.data.VariableBinding.Binding;
import eu.larkc.core.query.SPARQLQuery;
import eu.larkc.core.query.SPARQLQueryImpl;
import eu.larkc.shared.SampleQueries;

public class SerializationTests extends ORDITestCase {

	@Test
	public void testORDIVariableBinding() throws Exception {
		String file = "testORDIVariableBinding.test";
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream os = new ObjectOutputStream(fos);

		SPARQLQuery query = new SPARQLQueryImpl(
				SampleQueries.SELECT_ALL_TRIPLES);
		RdfStoreConnection con = DataFactory.INSTANCE
				.createRdfStoreConnection();
		URI u1 = new URIImpl("urn:serialization:test:1");
		con.addStatement(u1, u1, u1, u1);

		VariableBinding vb = con.executeSelect(query);
		os.writeObject(vb);
		os.close();

		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream is = new ObjectInputStream(fis);
		vb = (VariableBinding) is.readObject();
		CloseableIterator<Binding> iter = vb.iterator();
		int count = 0;

		while (iter.hasNext()) {
			iter.next();
			count++;
		}
		Assert.assertEquals(1, count);
		fis.close();
	}

	@Test
	public void testORDIGraphResult() throws Exception {
		String file = "testORDIGraphResult.test";
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream os = new ObjectOutputStream(fos);

		SPARQLQuery query = new SPARQLQueryImpl(
				SampleQueries.CONSTRUCT_ALL_TRIPLES);
		SPARQLEndpoint endpoint = DataFactory.INSTANCE
				.createRdfStoreConnection();
		URI u1 = new URIImpl("urn:serialization:test:1");
		DataFactory.INSTANCE.createRdfStoreConnection().addStatement(u1, u1,
				u1, u1);

		SetOfStatements result = endpoint.executeConstruct(query);
		os.writeObject(result);
		os.close();

		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream is = new ObjectInputStream(fis);
		result = (SetOfStatements) is.readObject();
		CloseableIterator<Statement> iter = result.getStatements();
		int count = 0;

		while (iter.hasNext()) {
			iter.next();
			count++;
		}
		Assert.assertEquals(1, count);
		fis.close();
	}

	@Test
	public void testSPARQLQuery() throws Exception {
		String file = "testSPARQLQuery.test";
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream os = new ObjectOutputStream(fos);

		SPARQLQuery query = new SPARQLQueryImpl(
				SampleQueries.CONSTRUCT_ALL_TRIPLES);
		os.writeObject(query);
		os.close();

		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream is = new ObjectInputStream(fis);
		query = (SPARQLQuery) is.readObject();
		fis.close();
	}

	@Test
	public void testVariableBinding() throws Exception {
		df.createRdfStoreConnection().addStatement(new URIImpl("urn:test"),
				new URIImpl("urn:test"), new URIImpl("urn:test"),
				new URIImpl("urn:test"));
		SPARQLQuery query = new SPARQLQueryImpl(
				SampleQueries.SELECT_ALL_TRIPLES);
		SPARQLEndpoint endpoint = df.createRdfStoreConnection();
		VariableBinding r1 = endpoint.executeSelect(query);
		SetOfStatements s = r1.toRDF(new SetOfStatementsImpl());
		VariableBinding r2 = DataFactory.INSTANCE.createVariableBinding(s);
		Assert.assertEquals(r1, r2);
	}

	/**
	 * Removes the files which are generated by the tests in this class.
	 */
	@AfterClass
	public static void removeTestFiles() {
		String[] args = { "testSPARQLQuery.test", "testORDIGraphResult.test",
				"testORDIVariableBinding.test" };
		for (int i = 0; i < args.length; i++) {
			File f = new File(args[i]);
			if (f.exists() && f.isFile()) {
				f.delete();
			}
		}
	}
}
