package store.model;

/**
 * @author marco
 *
 */
public class DiscountItem {
	private String code;
	private String type;
	private int quantity;
	private float discount;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public void print(){
		System.out.println("Code: " + code + ", Type: " + type + " Quantity: " + quantity + " Discount: " + discount);
	}

}
