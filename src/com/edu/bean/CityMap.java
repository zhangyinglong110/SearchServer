package com.edu.bean;

import java.util.LinkedHashMap;
import java.util.Map;

public class CityMap {
	/**
	 * 全国（省，直辖市，自治区，特别行政区）映射集合
	 */
	public static Map<String, String[]> model = new LinkedHashMap();

	static {
		model.put("请选择", new String[] { "请选择" });
		model.put("京津冀",
				new String[] { "请选择", "北京中关村", "北京国贸", "北京通州", "北京回龙观", "北京艾特", "翡翠昌平", "天津南开", "天津新安", "石家庄民族路" });
		model.put("上海区", new String[] { "请选择", "完美上海虹口区东江湾", "卓新上海虹口区东江湾校区", "上海徐汇", "上海珠洲口", "上海浦东" });
		model.put("西北区", new String[] { "请选择", "完美先雁塔区小寨校区", "卓新智趣西安第二分公司", "西安华融", "西安太白", "西安钟楼", "太原小店", "太原长风街",
				"郑州金水", "郑州国泰" });
		model.put("华中区", new String[] { "请选择", "长沙书院", "长沙雨花", "长沙中扬", "武汉洪山", "武汉江汉", "常婵红谷滩", "南昌二分" });
		model.put("华北区", new String[] { "请选择", "济南历下", "济南新", "青岛南区", "沈阳沈河", "沈阳和平", "大连中山" });
		model.put("华南区",
				new String[] { "请选择", "深圳华强北", "深圳罗湖", "深圳福田", "广州黄埔", "广州元岗", "广州长兴", "柳州广场路", "南宁西乡塘", "福州台江" });
		model.put("华东区", new String[] { "请选择", "合肥新都会", "合肥长江路", "南京玄武", "南京雨花台", "苏州姑苏", "常州钟楼", "无锡滨湖", "杭州下城",
				"卓新智趣杭州第二分公司", "宁波***州" });
		model.put("西南区", new String[] { "请选择", "重庆沙坪坝", "重庆天陈", "重庆渝中", "成都武侯", "成都青羊", "成都牛王庙", "贵阳南明" });

	}

}
