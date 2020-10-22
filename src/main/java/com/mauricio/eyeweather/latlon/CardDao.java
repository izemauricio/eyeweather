package com.mauricio.eyeweather.latlon;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.stereotype.Repository;

@Repository
public class CardDao {

	HashMap<String, ArrayList<Card>> database = new HashMap<String, ArrayList<Card>>();

	public ArrayList<Card> getListOfUser(String userid) {

		if (database.get(userid) == null) {
			database.put(userid, new ArrayList<Card>());
		}

		return database.get(userid);

	}

}
