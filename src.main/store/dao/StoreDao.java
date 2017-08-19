package store.dao;

import java.util.HashMap;

import store.model.DiscountItem;
import store.model.StoreItem;

public interface StoreDao {

	/**
	 * @return
	 */
	HashMap<String, StoreItem> getAllStoreItems();
	/**
	 * @return
	 */
	HashMap<String, DiscountItem> getAllDiscountItems();
	/**
	 * @return
	 */
	HashMap<String, DiscountItem> getSelectedDiscountItems(String[] discounts);

}
