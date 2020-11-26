package com.mauricio.eyeweather.latlon;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

	@Autowired
	private CardDao dao;

	private final int MAX_NUM_CARDS = 20;

	public void addCard(String userid, Card card) {
		dao.getListOfUser(userid).add(0, card);
	}

	public void delCard(String userid, String cardid) {
		ArrayList<Card> listOfCards = dao.getListOfUser(userid);
		for (Card card : listOfCards) {
			if (card.getCardid().equals(cardid)) {
				dao.getListOfUser(userid).remove(card);
				break;
			}
		}
	}

	public ArrayList<Card> get20FirstCards(String userid) {
		try {
			ArrayList<Card> result = new ArrayList<Card>();
			ArrayList<Card> cardsOwnedByUser = dao.getListOfUser(userid);
			int count = 0;
			for (Card card : cardsOwnedByUser) {
				result.add(card);
				if (++count == MAX_NUM_CARDS)
					break;
			}
			return result;
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return new ArrayList<Card>();
	}

}
