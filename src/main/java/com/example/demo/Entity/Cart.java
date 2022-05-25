package com.example.demo.Entity;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	private Map<Integer, Items> items = new HashMap<>();

	private int total;

	public Cart() {

	}

	public void addCart(Items item, int quantity) {
		Items existedItem = items.get(item.getCode());
		

//	アイテムが存在しない場合は追加
		if (existedItem == null) {
//		数量を設定
			quantity = (item.getStock() - quantity < 0) ? item.getStock() : quantity;
			item.setQuantity(quantity);
//		リストに追加
			items.put(item.getCode(), item);

		} else {
//	アイテムが存在する場合は数量のみ追加
			quantity += existedItem.getQuantity();
			quantity = (existedItem.getStock() - quantity < 0) ? existedItem.getStock() : quantity;
			existedItem.setQuantity(quantity);
		}

		recalcTotal();
	}

	/**
	 * カートの削除の処理
	 * 
	 * @param itemCode
	 */
	public void deleteCart(int itemCode) {
//	itemCodeを利用してがーと配列から削除
		items.remove(itemCode);
		recalcTotal();
	}

	/**
	 * カートの中身の合計を算出
	 */
	public void recalcTotal() {
		total = 0;
		for (Items item : items.values()) {

			total += item.getPrice() * item.getQuantity();
		}

	}

	public Map<Integer, Items> getItems() {
		return items;
	}

	public int getTotal() {
		return total;
	}
}
