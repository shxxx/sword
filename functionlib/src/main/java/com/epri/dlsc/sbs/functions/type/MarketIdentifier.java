package com.epri.dlsc.sbs.functions.type;
/**
 *场景
 */
public enum MarketIdentifier {
	/**所有*/
	ALL{
		@Override
		public String id() {
			return "ALL";
		}
	},
	/**不可用*/
	DISABLE{
		@Override
		public String id() {
			return "DISABLE";
		}
	},
	/**国网总部*/
	SGZB{
		@Override
		public String id() {
			return "91812";
		}
	},
	/**华北分部**/
	HBFB{
		@Override
		public String id() {
			return "92618";
		}
	},
	/**华中分部*/
	HZFB{
		@Override
		public String id() {
			return "93112";
		}
	},
	/**东北分部*/
	DBFB{
		@Override
		public String id() {
			return "94002";
		}
	},
	/**西北分部*/
	XBFB{
		@Override
		public String id() {
			return "98312";
		}
	},
	/**华东分部*/
	HDFB{
		@Override
		public String id() {
			return "95312";
		}
	},
	/**北京*/
	BJ{
		@Override
		public String id() {
			return "92652";
		}
	},
	/**天津*/
	TJ{
		@Override
		public String id() {
			return "92212";
		}
	},
	/**河北*/
	HeB{
		@Override
		public String id() {
			return "92812";
		}
	},
	/**冀北*/
	JB{
		@Override
		public String id() {
			return "962181";
		}
	},
	/**山西*/
	TY{
		@Override
		public String id() {
			return "92416";
		}
	},
	/**山东*/
	SD{
		@Override
		public String id() {
			return "95812";
		}
	},
	/**上海*/
	SH{
		@Override
		public String id() {
			return "95321";
		}
	},
	/**江苏*/
	JS{
		@Override
		public String id() {
			return "95412";
		}
	},
	/**浙江*/
	ZJ{
		@Override
		public String id() {
			return "95518";
		}
	},
	/**安徽*/
	AH{
		@Override
		public String id() {
			return "95612";
		}
	},
	/**福建*/
	FJ{
		@Override
		public String id() {
			return "95712";
		}
	},
	/**湖北*/
	HuB{
		@Override
		public String id() {
			return "93512";
		}
	},
	/**湖南*/
	HuN{
		@Override
		public String id() {
			return "93312";
		}
	},
	/**江西*/
	JX{
		@Override
		public String id() {
			return "93412";
		}
	},
	/**四川*/
	SC{
		@Override
		public String id() {
			return "96813";
		}
	},
	/**重庆*/
	CQ{
		@Override
		public String id() {
			return "96218";
		}
	},
	/**辽宁*/
	LN{
		@Override
		public String id() {
			return "94004";
		}
	},
	/**吉林*/
	JL{
		@Override
		public String id() {
			return "94212";
		}
	},
	/**黑龙江*/
	HLJ{
		@Override
		public String id() {
			return "94312";
		}
	},
	/**蒙东*/
	MD{
		@Override
		public String id() {
			return "11001";
		}
	},
	/**陕西*/
	XA{
		@Override
		public String id() {
			return "98412";
		}
	},
	/**甘肃*/
	GS{
		@Override
		public String id() {
			return "98512";
		}
	},
	/**宁夏*/
	NX{
		@Override
		public String id() {
			return "98611";
		}
	},
	/**青海*/
	QH{
		@Override
		public String id() {
			return "98712";
		}
	},
	/**新疆*/
	XJ{
		@Override
		public String id() {
			return "98812";
		}
	},
	/**河南*/
	HeN{
		@Override
		public String id() {
			return "93212";
		}
	},
	/**西藏*/
	XZ{
		@Override
		public String id() {
			return "96712";
		}
	};
	public abstract String id();
}
