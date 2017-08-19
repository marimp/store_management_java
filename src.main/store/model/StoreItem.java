package store.model;

/**
 * @author marco
 *
 */
public class StoreItem {
	private String code;
	private String name;
	private float price;
	private int quantity = 0;
	private float subtotal = 0;

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

	public boolean equals(StoreItem o) {
		return this.getCode() == o.getCode();
	}

	public void print(){
		System.out.println("Code: " + code + ", Name: " + name + " Price: " + price);
	}
}
