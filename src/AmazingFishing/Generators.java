package AmazingFishing;

import java.util.Arrays;

import me.DevTec.AmazingFishing.Loader;
import me.devtec.theapi.TheAPI;
import me.devtec.theapi.utils.StringUtils;

public class Generators {
	public static double length(String path, String fish) {
		String awd = "0.0";
		if(Loader.c.exists("Types."+path+"."+fish+".MaxCm"))
			awd=Loader.c.getString("Types."+path+"."+fish+".MaxCm");
		double limit = StringUtils.getDouble(awd);
		if(limit==0.0)limit=1000;
		double random = TheAPI.generateRandomDouble(limit);
		if(random<Loader.c.getDouble("Options.Manual.MinimalFishLength"))random= Loader.c.getDouble("Options.Manual.MinimalFishLength");
		String c =String.format("%2.02f",random);
		return StringUtils.getDouble(c);
	}

	public static double weight(double length) {
		length=(length/100)*Arrays.asList(1,2,3,4,5,6,7).get(TheAPI.generateRandomInt(7));
		String c =String.format("%2.02f",length);
		length=StringUtils.getDouble(c);
		if(length<0.1)length=0.1;
		return length;
	}
}
