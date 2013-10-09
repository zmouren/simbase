package com.guokr.simbase.recommend;

import gnu.trove.map.hash.TIntFloatHashMap;

import com.guokr.simbase.SimContext;

public class Recommendation {
	private int limit;
	private TIntFloatHashMap items;
	private float waterline;
	private int waterlineId;

	public Recommendation(SimContext simContext) {
		limit = simContext.getInt("limit");
		items = new TIntFloatHashMap();
		waterline = 0;
		waterlineId = 0;
	}

	/*
	 * 更新水位线
	 */
	private void updateWaterline() {
		int[] ids = items.keys();
		waterline = Float.MIN_VALUE;
		for (int id : ids) {
			float v = items.get(id);
			if (v > waterline) {
				waterline = v;
				waterlineId = id;
			}
		}
	}

	void setLimit(int v) {
		limit = v;
	}

	public void add(int vecid, float score) {
		if (items.size() < limit) {
			items.put(vecid, score);
			if (score > waterlineId) {
				waterline = score;
				waterlineId = vecid;
			}
		} else if (score < waterline) {
			items.remove(waterlineId);
			items.put(vecid, score);
			updateWaterline();
		}
	}

	public void remove(int vecid) {
		items.remove(vecid);
		updateWaterline();
	}

	public int[] ids() {
		return items.keys();
	}

	public String jsonize(int vecid) {
		return null;
	}
}
