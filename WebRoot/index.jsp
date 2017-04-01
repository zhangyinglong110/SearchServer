﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>翡翠点评之终结者</title>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<meta name="format-detection" content="email=no">
<link rel="stylesheet" type="text/css" href="css/reset.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" type="text/css" href="css/animations.css">

</head>
<body>

	<%
		String unionid = (String) request.getSession().getAttribute("unionid");
		//if (unionid == null) {
	%>
	<script type="text/javascript">
		//window.location.href = "LoginServlet";
	</script>
	<%
		//} else {
	%>
	<div class="page page-1-1 page-current">
		<div class="wrap headingContainer">
			<!--<img class="bgPic" src="images/headingPage.jpg" alt=""/>-->
			<button type="button" class="headingBtn pt-page-moveCircle">进入</button>
		</div>
	</div>
	<div class="page page-2-1 hide">
		<div class="kuang">
			<!--×号-->
			<img class="modal-close-btn" src="images/close.png" alt="" />
		</div>
		<div class="wrap flex-container">
			<h2 class="page-title">请选择您所在的区域：</h2>
			<div class="bigContainer">
				<div>
					<ul id="schools" class="pt-page-moveCircle"></ul>
					<div id="confirm">确定</div>
				</div>
				<div>
					<div id="first"
						class="flex-item left area pt-page-rotateUnfoldRight">京津冀</div>
					<div id="second" class="flex-item area pt-page-rotateUnfoldRight">上海区</div>
					<div id="third"
						class="flex-item left area pt-page-rotateUnfoldRight">西北区</div>
					<div id="forth" class="flex-item area pt-page-rotateUnfoldRight">华中区</div>
					<div id="fifth"
						class="flex-item left area pt-page-rotateUnfoldLeft">华北区</div>
					<div id="sixth" class="flex-item area pt-page-rotateUnfoldLeft">华南区</div>
					<div id="seventh"
						class="flex-item left area pt-page-rotateUnfoldLeft">华东区</div>
					<div id="eight" class="flex-item area pt-page-rotateUnfoldLeft">西南区</div>
				</div>
			</div>
		</div>
	</div>

	<div class="page  page-3-1 hide">
		<div class="wrap flex-container">
			<h2 class="page-title">请选择您的专业：</h2>
			<div class="flex-item left profession pt-page-rotateUnfoldRight">UI设计</div>
			<div class="flex-item profession pt-page-rotateUnfoldRight">web前端</div>
			<div class="flex-item left profession pt-page-rotateUnfoldRight">安卓</div>
			<div class="flex-item profession pt-page-rotateUnfoldRight">网络营销</div>
			<div class="flex-item left profession pt-page-rotateUnfoldLeft">java</div>
			<div class="flex-item profession pt-page-rotateUnfoldLeft">php</div>

			<!--<img  class="triangleIcon profession pt-page-moveIconUp" src="images/icon_up1.png">-->
			<div class="professionBtn">确定</div>
		</div>
	</div>
	<!--<div class="page  page-4-1 hide">
		<div class="wrap">
			<img class="inputPic pt-page-moveCircle " src="images/page23-3.png" alt=""/>
			<h2 class="page-title">请选择您要评价的老师：</h2>
			<div  class="flex-item post pt-page-rotateUnfoldRight">项目经理</div>
			<div  class="flex-item post pt-page-rotateUnfoldRight">班主任</div>
			<div  class="flex-item post pt-page-rotateUnfoldRight">就业经理</div>
			&lt;!&ndash;<img  class="triangleIcon profession pt-page-moveIconUp" src="images/icon_up1.png">&ndash;&gt;
			<div class="professioform-containernBtn">确定</div>
		</div>
	</div>-->
	<div class="page  page-4-1 hide">
		<div class="wrap flex-container">
			<h2 class="page-title">请输入教师姓名：</h2>
			<!--	<img class="inputPic pt-page-moveCircle " src="images/error1.png" alt=""/>-->
			<div class="form-container">
				<input class="inp" id="uName" maxlength="5" name='uname' type="text"
					placeholder="教师姓名" />
				<div class="nameRemindWords"></div>
				<div class="btn">确定</div>
			</div>
			<!--<img class="triangleIcon pt-page-moveIconUp" src="images/icon_up1.png">-->
		</div>
	</div>
	<div class="page page-5-1 hide" value="5">
		<div class="wrap flex-container">
			<h2 class="page-title">项目经理出勤情况：</h2>
			<div class="pageChoice">
				<input type="radio" name="attendance" id="alway" value="0" /> <label
					for="alway">经常迟到早退 </label> <br /> <input type="radio"
					name="attendance" id="sometimes" value="1" /> <label
					for="sometimes">偶尔迟到早退 </label> <br /> <input type="radio"
					name="attendance" id="nolate" value="2" /> <label for="nolate">从不迟到早退</label>
				<br /> <input type="radio" name="attendance" id="always" value="3" />
				<label for="always">不迟到，偶尔提前到项目组</label><br /> <input type="radio"
					name="attendance" id="everyday" value="4" /> <label for="everyday">不迟到，而且经常提前到项目组</label>
				<br /> <input type="radio" name="attendance" id="every" value="5" />
				<label for="every">每天都提前到项目组</label>
			</div>
			<div class="nextQuestion" id="firstQuestion">下一题</div>
			<div class="count">
				<span class="pageNum">1</span>/10
			</div>
		</div>
	</div>
	<div class="page  page-6-1 hide" value="6">
		<div class="wrap flex-container">
			<h2 class="page-title">项目讲解：</h2>
			<div class="pageChoice">
				<input type="radio" name="onClass" id="noOrder" value="0" /> <label
					for="noOrder">杂乱无章，听不懂</label> <br /> <input type="radio"
					name="onClass" id="canBut" value="1" /> <label for="canBut">能听懂，但费力</label>
				<br /> <input type="radio" name="onClass" id="canUnderstand"
					value="2" /> <label for="canUnderstand">思路清晰但不深入</label> <br /> <input
					type="radio" name="onClass" id="tuChu" value="3" /> <label
					for="tuChu">重点难点突出</label> <br /> <input type="radio"
					name="onClass" id="mindClear" value="4" /> <label for="mindClear">条理分明讲解清晰</label>
				<br /> <input type="radio" name="onClass" id="bothClear" value="5" />
				<label for="bothClear">讲解清晰有启发性</label><br />
			</div>
			<div class="nextQuestion" id="secondQuestion">下一题</div>
			<div class="count">
				<span class="pageNum">2</span>/10
			</div>
		</div>
	</div>
	<div class="page page-15-1 hide">
		<div class="wrap flex-container">
			<h2 class="page-title">您的宝贵建议和意见</h2>
			<textarea name="" id="suggestions"></textarea>
			<div class="lastSubmit pt-page-rotateInNewspaper">全部提交</div>
		</div>
	</div>
	<div class="page page-16-1 hide">
		<div class="wrap">
			<h1 id="bTit"
				style="text-align: center;width: 100%;height: 5vh;line-height:5vh;margin-top: 2vh;">web前端排名</h1>
			<div id="main" style="width:100%;height:88vh;"></div>
		</div>
	</div>
	<script src="js/zepto.min.js"></script>
	<script src="js/touch.js"></script>
	<script src="js/echarts.simple.min.js"></script>
	<script src="js/index2.js" type="text/javascript" charset="UTF-8"></script>

	<%
		//}
	%>
</body>
</html>