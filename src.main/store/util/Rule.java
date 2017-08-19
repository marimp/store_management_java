package store.util;

import java.util.HashMap;

import store.model.DiscountItem;
import store.model.StoreItem;

public class Rule {

	/**
	 * It applies the base rules to the checkout store
	 * 
	 * @param storeCheckout the store to be checked out
	 * @return
	 * @throws ItemNotFoundException
	 */
	public static HashMap<String,StoreItem> applyBaseRules(HashMap<String,StoreItem> storeCheckout) throws ItemNotFoundException {
		for (StoreItem item : storeCheckout.values()) {
			item.setSubtotal(item.getPrice() * item.getQuantity());
		}
		return storeCheckout;
	}	

	/**
	 * It applies the discount rules to the checkout store
	 * 
	 * @param storeCheckout the store to be checked out
	 * @param discountCheckout the discount rules for the check out
	 * @return
	 * @throws ItemNotFoundException
	 */
	public static HashMap<String,StoreItem> applyDiscountRules(HashMap<String,StoreItem> storeCheckout, HashMap<String,DiscountItem> discountCheckout) throws ItemNotFoundException {
		for (StoreItem stored : storeCheckout.values()) {
			DiscountItem discountItem = discountCheckout.get(stored.getCode());
			if (discountItem != null) { // The discount can be applied
				discountItem.print();
				if (stored.getQuantity() / discountItem.getQuantity() > 0) {
					int quantityToNotDiscount = stored.getQuantity() % discountItem.getQuantity();
					int quantityToDiscount = stored.getQuantity() - quantityToNotDiscount;
					float priceForItemToDiscount = stored.getPrice() - stored.getPrice() * discountItem.getDiscount();
					float newSubtotal = quantityToNotDiscount * stored.getPrice() + quantityToDiscount * priceForItemToDiscount;
					stored.setSubtotal(newSubtotal);
				}
			}
		}
		return storeCheckout;
	}	
}
