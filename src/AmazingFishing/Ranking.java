package AmazingFishing;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Ranking {
	HashMap<String, Double> r;
	HashMap<String, Double> a;
	public Ranking(HashMap<String, Double> a) {
		this.a=a;
		r = a.entrySet().stream().sorted(comparingByValue()).collect(toMap(e -> e.getKey(),
				e -> e.getValue(), (e1, e2) -> e2,LinkedHashMap::new));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getPlayer(int pos) {
		pos=pos-1;
		try {
		String o = "-";
		List keys = new ArrayList(r.keySet());
		for (int i = 0; i < r.size(); ++i) {
			if(i==pos) {
				o= keys.get(i).toString();
				break;
			}
		}
		return o;
		}catch(Exception e) {
			return "-";
		}
	}

	public String getPosition(String player) {
		int i = 0;
		if(a.keySet().isEmpty()==false)
		for(String s: r.keySet()) {
			++i;
			if(s.toString().equals(player)) {
				return i+"";
			}
		}
		return "-";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getDouble(int pos) {
		pos=pos-1;
		try {
		return a.get(new ArrayList(r.keySet()).get(pos)).toString();
		}catch(Exception e) {
			return "-";
		}
	}
}
