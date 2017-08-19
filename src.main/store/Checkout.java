package store;

import java.util.HashMap;

import store.dao.SqliteStoreDaoImpl;
import store.dao.StoreDao;
import store.model.DiscountItem;
import store.model.StoreItem;
import store.util.ItemNotFoundException;
import store.util.Rule;

/**
 * @author marco
 *
 */
public class Checkout {
	final static public String currency = "€";

	private HashMap<String,StoreItem> storeCheckout = new HashMap<String,StoreItem>(); 
	private HashMap<String,DiscountItem> discountCheckout = new HashMap<String,DiscountItem>(); 

	/**
	 * @param pricingRules The rules to be applied. Current valid values: tshirtdiscountforthree, cardigantwoforone
	 */
	public Checkout(String[] pricingRules) {
		StoreDao st = new SqliteStoreDaoImpl();
		storeCheckout = (HashMap<String, StoreItem>) st.getAllStoreItems();
		discountCheckout = (HashMap<String, DiscountItem>) st.getSelectedDiscountItems(pricingRules);
	}

	public static void main(String[] args) throws ItemNotFoundException {
		Checkout co = new Checkout(new String[]{"tshirtdiscountforthree","cardigantwoforone"});
		try {
			co.scan("CARDIGAN");
			co.scan("TSHIRT");
			co.scan("TROUSERS");
			co.total();		
		} catch (ItemNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

		}
	}

	/**
	 * It scans the product item and collects the 
	 * Example {
	 * 	"CARDIGAN"=>{"code"=>"CARDIGAN", "name"=>"Cardigan", "price"=>5.0, "quantity"=>3},
	 *  "TSHIRT"=>{"code"=>"TSHIRT", "name"=>"T-Shirt", "price"=>20.0, "quantity"=>3},
	 *	"TROUSERS"=>{"code"=>"TROUSERS", "name"=>"Trousers", "price"=>7.5, "quantity"=>1}
	 * }
	 * 
	 * @param item Item to be added (current valid values: CARDIGAN, TSHIRT, TROUSERS)
	 * @throws ItemNotFoundException
	 */
	public void scan(String item) throws ItemNotFoundException {
		StoreItem storedItem = storeCheckout.get(item);
		if (storedItem == null ) {
			throw new ItemNotFoundException("The item " + item + " is not present in the store"); 		
		}
		int existingQuantity = storedItem.getQuantity();
		storedItem.setQuantity(existingQuantity + 1);
	}	


	/**
	 * It reports the total
	 * 
	 * @return
	 * @throws ItemNotFoundException
	 */
	public float total() throws ItemNotFoundException {
		float total = 0;

		storeCheckout = Rule.applyBaseRules(storeCheckout);
		storeCheckout = Rule.applyDiscountRules(storeCheckout, discountCheckout);

		System.out.println("Product | Quantity | Original Price | Discounted (if applied)");
		for (StoreItem item : storeCheckout.values()) {
			float discounted = (item.getSubtotal() != 0) ? item.getSubtotal() : item.getPrice();
			System.out.println(item.getName() + " | " + item.getQuantity() + " | " + String.format("%.2f", item.getPrice()) + " | " + String.format("%.2f", discounted));
			total += item.getSubtotal();
		}
		System.out.println("   Total:" + String.format("%.2f", total) + currency);
		return total;
	}


}
