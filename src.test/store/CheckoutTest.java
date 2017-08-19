package store;

import store.Checkout;
import store.util.ItemNotFoundException;
import junit.framework.*;

public class CheckoutTest extends TestCase {
	static String[] pricingRules = null;

	// assigning the values
	protected void setUp(){
		pricingRules =  new String[]{"tshirtdiscountforthree","cardigantwoforone"};
	}


	// test method to add two values
	public void test1(){
		Checkout co = new Checkout(pricingRules);
		try {
			co.scan("CARDIGAN");
			co.scan("TSHIRT");
			co.scan("TROUSERS");
			co.total();		
			assertTrue(co.total() == 45.50);
		} catch (ItemNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void test2(){
		Checkout co = new Checkout(pricingRules);
		try {
			co.scan("CARDIGAN");
			co.scan("TSHIRT");
			co.scan("CARDIGAN");
			co.total();		
			assertTrue(co.total() == 30.00);
		} catch (ItemNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void test3(){
		Checkout co = new Checkout(pricingRules);
		try {
			co.scan("TSHIRT");
			co.scan("TSHIRT");
			co.scan("TSHIRT");
			co.scan("CARDIGAN");
			co.scan("TSHIRT");
			co.total();		
			assertTrue(co.total() == 57.00);
		} catch (ItemNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void test4(){
		Checkout co = new Checkout(pricingRules);
		try {
			co.scan("CARDIGAN");
			co.scan("TSHIRT");
			co.scan("CARDIGAN");
			co.scan("CARDIGAN");
			co.scan("TROUSERS");
			co.scan("TSHIRT");
			co.scan("TSHIRT");
			co.total();		
			assertTrue(co.total() == 82.50);
		} catch (ItemNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
