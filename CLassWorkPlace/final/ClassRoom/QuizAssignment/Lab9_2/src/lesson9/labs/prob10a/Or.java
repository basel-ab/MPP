package lesson9.labs.prob10a;

import java.util.*;

public class Or {

	public static void main(String[] args) {
		List<Simple> list = Arrays.asList(new Simple(false), new Simple(false), new Simple(true));
		Or or = new Or();
		System.out.println(or.someSimpleIsTrueNew(list));
	}
	
	public boolean someSimpleIsTrueNew(List<Simple> list) {
		return list.stream().map(m -> m.flag).reduce(false, (x,y) -> x || y);
	}

	public boolean someSimpleIsTrue(List<Simple> list) {
		boolean accum = false;
		for(Simple s: list) {
			accum = accum || s.flag;
		}
		return accum;
	}

}
