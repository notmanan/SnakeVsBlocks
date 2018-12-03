package SnakeVsBlocks;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RankingPosition implements Serializable, Comparable<RankingPosition> {
	private static final long serialVersionUID = 1L;
	String name;
	int score;
	Date date;
	String dateString;

	public RankingPosition(String nameS, int scoreS) {
		name = (nameS);
		score = (scoreS);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		date = new Date();
		dateString = ((dateFormat.format(date))); // 2016/11/16 12:08:43
	}

	@Override
	public int compareTo(RankingPosition arg0) {
		// TODO Auto-generated method stub
		if(this.score != arg0.score) {
			return arg0.score - this.score;
		}else {
			if(this.date.before(arg0.date)) {
				return -1;
			}else {
				return 1;
			}
		}
	}
}
