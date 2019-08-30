package cn.itcast.dw.realtime.bean;

import java.io.Serializable;

/**
 * Bean DTO
 * Created by: mengyao
 * 2019年7月2日
 */
public abstract class Bean implements Serializable {
    
	private static final long serialVersionUID = -3438840157416046708L;
	private long ts = System.currentTimeMillis();		//时间戳
	
	public long getTs() {
		return ts;
	}

	public void setTs(long ts) {
		this.ts = ts;
	}

	@Override
    public String toString() {
        return super.toString();
    }
}
