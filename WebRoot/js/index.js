$(function(){
	window.onload = function(){
		document.documentElement.style.fontSize = document.documentElement.clientWidth/10.8 +'px';
		var deviceWidth = document.documentElement.clientWidth;
		if(deviceWidth>1080){
			deviceWidth = 1080;
		}
		//100px = 1rem
		document.documentElement.style.fontSize = deviceWidth/10.8 +'px';
	};



//这里我们给个定时器来实现页面加载完毕再进行字体设置
	var now = { row:1, col:1 }, last = { row:0, col:0};
	const towards = { up:1, right:2, down:3, left:4};
	var isAnimating = false;

	s=window.innerHeight/500;
	ss=250*(1-s);

	$('.wrap').css('-webkit-transform','scale('+s+','+s+') translate(0px,-'+ss+'px)');

	document.addEventListener('touchmove',function(event){event.preventDefault(); },false);

	$(".page.pageCanSwipe").swipeUp(function(){
		upSwipping();
		function upSwipping(){
			if (isAnimating) return;
			last.row = now.row;
			last.col = now.col;
			if (last.row != 16) { now.row = last.row+1; now.col = 1; pageMove(towards.up);}
		}
	});

	function pageMove(tw){
		var lastPage = ".page-"+last.row+"-"+last.col;
		var nowPage = ".page-"+now.row+"-"+now.col;
		switch(tw) {
			case towards.up:
				outClass = 'pt-page-moveToTop';
				inClass = 'pt-page-moveFromBottom';
				break;
			case towards.right:
				outClass = 'pt-page-moveToRight';
				inClass = 'pt-page-moveFromLeft';
				break;
			case towards.down:
				outClass = 'pt-page-moveToBottom';
				inClass = 'pt-page-moveFromTop';
				break;
			case towards.left:
				outClass = 'pt-page-moveToLeft';
				inClass = 'pt-page-moveFromRight';
				break;
		}
		isAnimating = true;
		$(nowPage).removeClass("hide");

		$(lastPage).addClass(outClass);
		$(nowPage).addClass(inClass);

		setTimeout(function(){
			$(lastPage).removeClass('page-current');
			$(lastPage).removeClass(outClass);
			$(lastPage).addClass("hide");
			$(lastPage).find("img").addClass("hide");

			$(nowPage).addClass('page-current');
			$(nowPage).removeClass(inClass);
			$(nowPage).find("img").removeClass("hide");

			isAnimating = false;
		},500);
	}

  	// 全局对象，取到所有字段的值
	var temp = {};
  	$(".headingBtn").on("singleTap",function(){
		if (isAnimating) return;
		last.row = now.row;
		last.col = now.col;
		if (last.row != 16) { now.row = last.row+1; now.col = 1; pageMove(towards.up);}
	});
	
	// 获取职位信息，取得对应的json文件路径
	function getJobName(type){
		var tag = type;
		var path = "";
		switch(tag){
			case "讲师":
				path = "json/projectManager.json";
				break;
			case "班主任":
				path = "json/headmaster.json";
				break;
			case "就业":
				path = "json/employmentManager.json";
				break;
		}
		return path;
	}
	
	// 配置大区字段
	function confBigAreaText(bArea){
		var areaLength = $(".area").length;
		for(var i=0;i<areaLength;i++){
			$($(".area")[i]).html(bArea[i].name);
		}
	}
	// 配置大区字段
	(function confAllJsonData(){
		// 大区的数据
		var p0 = new Promise(function(resolve, reject){
			$.getJSON("json/jsondata/bigarea.json",function(bArea){
				resolve(bArea);
			});
		});
		// 学校数据
		var p1 = new Promise(function(resolve, reject){
			$.getJSON('json/jsondata/schools.json',function(schools){
				resolve(schools);
			})
		});
		//专业数据
		var p2 = new Promise(function(resolve, reject){
			$.getJSON("json/jsondata/subject.json",function(subs){
				resolve(subs);
			});
		});
		var p3 = new Promise(function(resolve, reject){
			$.ajax({
				async:false,
				method:"POST",
				traditional:true,
				url:"recentServlet",
				data: {
					//checkJson: JSON.stringify(postdata)
				},
				async:true,
				success: function(data){
					resolve(data);
				},
				error: function(err){
					//callback(null,err);
				}
			});
		// TODO
		//			$.getJSON("recentServlet",function(tcs){
		//				resolve(tcs);
		//			});
		});
		
		Promise.all([p0,p1,p2,p3]).then(function(results){
			// 大区
			confBigAreaText(results[0]);
			// 学校
			schoolChoice(results[1]);
			// 专业
			confSubjectText(results[2]);
			// 老师班级数据
			updateClassAndTeaName(results[3]);
			
		});
	})();
	
	function updateClassAndTeaName(tcs){
		var classSpans = $(".classListOne span");
		var min = Math.min(tcs.classList.length,classSpans.length);
		for(var i=0;i<min;i++){
			$(classSpans[i]).html(tcs.classList[i]);
		}
		// 隐藏没有内容的span
		if(min<classSpans.length){
			for(var i=min;i<classSpans.length;i++){
				$(classSpans[i]).hide();
			}
		}
		var teaNameSpans = $(".nameList span");
		min = Math.min(tcs.teacherName.length,teaNameSpans.length);
		for(var i=0;i<min;i++){
			$(teaNameSpans[i]).html(tcs.teacherName[i]);
		}
		if(min<teaNameSpans.length){
			for(var i=min;i<teaNameSpans.length;i++){
				$(teaNameSpans[i]).hide();
			}
		}
	}
	
	// 配置专业数据
	var subs = [];
	function confSubjectText(sub){
		subs = sub;
	}
	
	//点击大区选项进入校区选项
	function schoolChoice(d){
		$(".area").on("singleTap",function(){
			// 得到大区的字段
			console.log($(this).html());
			temp.large_Area = $(this).html();
			// 修改选中项的显示颜色
			$(".area").css({"background":"","color":"#14c6d0"});
			$(this).css({"background":"#14c6d0","color":"#fff"});
			$("#confirm").css({"display":"block","background":"#878787"});
			//显示校区弹框
      		$(".kuang").show(5000);
			$("#schools").css({"display":"block"});
			$(this).css("display","block");

			var index=$(this).index();   //大区的下标
			var list="";
			for(var i=0;i<d[index].length;i++){
				list+="<li>"+d[index][i].sch+"</li>"+"<span style='display: none;'>"+d[index][i].subcode+"</span>" ;
				$("#schools").html(list);
				$("li").unbind();
				$("li").on("singleTap",function(){
					$("li").css({"border":"","color":""});
					$(this).css({"border":"1px solid #14c6d0","color":"#14c6d0"});
					$("#confirm").css("background-color"," #14c6d0");
					$("#confirm").unbind();
					$("#confirm").on("singleTap",function(){
						// 根据选择的学校，配置专业数据
						var codes = temp.subcode;
						for(var i=0;i<codes.length;i++){
							var sub = subs[codes[i]];
							$($(".profession")[i]).html(sub);
							$($(".profession")[i]).show();
						}
						// 向上滑动
						if (isAnimating) return;
						last.row = now.row;
						last.col = now.col;
						if (last.row != 12) { now.row = last.row+1; now.col = 1; pageMove(towards.up);}
					});

					//得到每个校区名字
					var liIndex = $(this).index();
					var schoolName=$(this).html();
					temp.sch_Name = schoolName;
					temp.subcode = $(this).next().html().split(',');
				})
			}
		});
	}

	//点击学校名称页面的×号，关闭页面
	$(".modal-close-btn").on("singleTap",function(){
		setTimeout(function(){
			$("#schools").css("display","none");
			$(".kuang").css("display","none");
			$("#confirm").css("display","none");
			$("#confirm").unbind();
		},500);
	});

	//得到专业的value值
	$(".profession").on("singleTap",function(){
		$(".profession").css({"border":"","color":""});
		$(this).css({"border":"1px solid #14c6d0","color":"#14c6d0"});

		$(".professionBtn").css("background","#14c6d0");
		$(".professionBtn").unbind();
		$(".professionBtn").on("singleTap",function(){
			if (isAnimating) return;
			last.row = now.row;
			last.col = now.col;
			if (last.row != 12) { now.row = last.row+1; now.col = 1; pageMove(towards.up);}

		});
		temp.cus_Name = $(this).html();
	});
	/*//得到教师的名字
	$("#uName").blur(function(){
		temp.tea_Name = $(this).val();
	});*/
	//判断输入的字符是否满足要求
	function isMatch(){
		var user = $("#uName").val();
		var patten= new RegExp("^([\u4E00-\u9FA5]{2,4})$");
		return patten.test(user);
	}
	//隐藏弹框
	function hidebox(){
    	$('.box').hide();
    }
	//显示弹框
	function showbox(info){
		$('.info').text(info);
		$('.box').show();
	}
	//点击确定隐藏弹框
	$(".cont .yes").on("click",function(){
    	hidebox();
    });

	$("#uName").bind("input propertychange",function(){
		var classInputVal=$("#classInput").val();
		var taValue = $("#uName").val();
		var len = getCharSize(taValue);
		if (len >= 4 && len <= 8 && isMatch() &&classInputVal!="") {
			$(".btn").css("background","#14c6d0");
			//解绑点击事件
			$(".btn").unbind();
			$(".btn").click(lightBtnAndAjax);
			
		}else if(len < 4 || !isMatch()){
			$(".btn").css("background","#ccc");
			//解绑点击事件
			$(".btn").unbind();
		}
	});
	
	$("#classInput").bind("input propertychange",function(){
		if($("#classInput").val()==""){
			$(".btn").css("background","#ccc");
			//解绑点击事件
			$(".btn").unbind();
		}else{
			var taValue = $("#uName").val();
			var len = getCharSize(taValue);
			if (len >= 4 && len <= 8 && isMatch()) {
				$(".btn").css("background","#14c6d0");
				//解绑点击事件
				$(".btn").unbind();
				$(".btn").click(lightBtnAndAjax);
			}
		}
	});
	
	function lightBtnAndAjax(){
		//获取老师的类型
		var typeValue  = $('input[name="tachertype"]:checked').val(); 
		//console.log(typeValue);
		var jsonpath = getJobName(typeValue);
		$.getJSON(jsonpath,function(titles){
			recycleDiv(titles);
		});
		
		//在这里发送ajax请求给后台，判断是否已经评论过了
		//类型
		if(typeValue == "讲师"){
			temp.role_Level = 0;
		}else if(typeValue == "班主任"){
			temp.role_Level = 1;
		}
		//tmp.role_Level = typeValue;
		//得到班级的名字
		temp.stu_Class=$("#classInput").val();
		//得到教师的名字
		temp.tea_Name = $("#uName").val();
		var postdata = {
			"large_Area": temp.large_Area,
			"sch_Name": temp.sch_Name,
			"cus_Name": temp.cus_Name,
			"tea_Name": temp.tea_Name
		};
		postComment(postdata,function(isComment,err){
			if(err){
				showbox("网络错误");
			}else{
				isComment = Number.parseInt(isComment)
				if(isComment == 0){
					showbox("您本月度对此老师评论过了！");
				}else{
					//向上滑动到下一页
					if (isAnimating) return;
					last.row = now.row;
					last.col = now.col;
					if (last.row != 12) { now.row = last.row+1; now.col = 1; pageMove(towards.up);}
					return true;
				}
			}
		});
	}
	
	//ajax请求，判断是否已经评论过了
	function postComment(postdata,callback){
		$.ajax({
			async:false,
			method:"POST",
			traditional:true,
			url:"CheckServlet",
			data: {
				checkJson: JSON.stringify(postdata)
			},
			async:true,
			success: function(data){
				callback(data,null);
			},
			error: function(err){
				callback(null,err);
			}
		});
	}

	//建议框的提示文字
	$("#suggestions").bind("input propertychang",function(){
		var taValue = $("#suggestions").val();
		temp.stu_Advice= taValue;
	});


	//表单验证
	//当失去焦点的时候，判断不能为空
	/*$("#uName").blur(function(){
		if($("#uName").val() == ""){
			$(".nameRemindWords").html("姓名不能为空");
		}else{
			$(".nameRemindWords").html("");
		}
	});*/

	$("#courses").blur(function(){
		if($("#courses").val() == ""){
			$(".courseRemindWords").html("课程不能为空");
		}else{
			$(".courseRemindWords").html("");
		}

	});

	//获得字符串的字符数
	function getCharSize(str){
	 var realLength = 0, len = str.length, charCode = -1;
	 for (var i = 0; i < len; i++) {
	 charCode = str.charCodeAt(i);
	 if (charCode >= 0 && charCode <= 128) realLength += 1;
	 else realLength += 2;
	 }
	 return realLength;
	 }

	//单选框中name的值的数组
	//单选框中id的值的数组
	var dataObj =[
		["alway","sometimes","nolate","always","everyday","every"],
		["noOrder","canBut","canUnderstan","tuChu","mindClea","bothClear"],
		["noQuestion","strangeQuestion","rareQuestion","goodQuestions","goodQuestion","excellentQuestion"],
		["noResponse","noAblility","avoid","hard","patient","patientAndGood"],
		["tutorAfterClass","noCare","solvedOnClass","farFetched","patientButNoAccurate","patientAndAccurate"],
		["disOrder","noGood","noInfluence","somePersons","good","goodOrder"],
		["noSkills","ownOpinin","arrange","onAccept","rangeFromResponse","active"],
		["noThinkAboutClass","noThinkAboutFact","goodButGood","goodContentButDifficult","burden","goodContent"],
		["noExample","rare","normalInfluence","goodInfluence","rich","oneAndThree"],
		["never","s","everyDayButNever","everydaySome","everydayAndAlways","accurate"]
	];

	// 单选框的name属性取出来循环
	var nameArr= ["attendance","onClass","questions","answers","tutorAfterClass",
		"discipline","skills","progress","explain","works"];
	var scoreNames = {attendance:"tea_Attendance",onClass:"cls_Explain",
		questions:"cls_Quesions", answers:"ques_Answer", tutorAfterClass:"cls_Coach",
		discipline:"cls_Discipline",skills:"cls_Skill",progress:"cls_Progress",
		explain:"exam_Explain", works:"class_Homework"};
	var titles = {};
	function recycleDiv(data){
		titles = data;
		// 根据json文件修改第1、2题的内容
		$('.page-title.one').html(titles.subject[0].title);
		$('.page-title.two').html(titles.subject[1].title);//siblings
		// 根据json文件修改第1、2题内容
		var labelArr = $('.page-title.one').siblings(".pageChoice").find("label");
		labelArr.each(function(index,item){
			$(item).html(titles.subject[0].options[index]);
		});
		labelArr = $('.page-title.two').siblings(".pageChoice").find("label");
		labelArr.each(function(index,item){
			$(item).html(titles.subject[1].options[index]);
		});
		
		for(var h= 0;h<nameArr.length;h++){
			var arrStr=nameArr[h];
			$("input[name ="+arrStr+"]").click(radioClickEvent);
		}
	}
	// 点击单选框触发事件
	function radioClickEvent(){
		//选中的单选框题目是几分
		var key = $(this).attr('name');
		var val = scoreNames[key];
		// 设置全局变量中对应属性的分数值
		temp[val] = parseInt($(this).val());

		//点击单选框选项的时候，下一题的按钮颜色变化&& 页面滑动到下一页
		$(".nextQuestion").css("background","");
		$(this).parent(".pageChoice").siblings('.nextQuestion').
			css("background","#14c6d0");
		//点击下一题
		$(this).parent(".pageChoice").siblings('.nextQuestion')
			.on("singleTap", nextQuestionEvent);
	}
	// 点击下一题触发事件，更新题卡内容
	function nextQuestionEvent(){
		//取到当前页面的value值，来改变页码数
		var value = Number.parseInt($(this).parents(".page").attr('value'));
		//改变当前页面的class的page-5-1的值
		var nowDiv = $(this).parents(".page")
			.removeClass()
			.addClass("page-"+(value+2)+"-1")
			.addClass("hide")
			.addClass("page")
			.attr('value',value+2);
		setTimeout(function(){
			$(this).parents(".page").next().after(nowDiv);
		},1000);

		// 题卡到第十题的时候，停止循环
		if(value>12){
			nowDiv.removeClass("page-"+(value+2)+"-1");
		}
		// 点击下一题页面滑动
		if (isAnimating) return;
		last.row = now.row;
		last.col = now.col;
		if (last.row != 15) { now.row = last.row+1; now.col = 1;
				pageMove(towards.up);}
		//题卡的页数1/10
		var pageNum = nowDiv.find(".pageNum");
		var page = $(pageNum).html(value-2);

		// 修改input的name值
		var inputArr = nowDiv.find("input");
			for(var i=0;i<inputArr.length;i++){
			inputArr[i].name=nameArr[value-3];
		}
		//修改题卡的标题
		if(value<13){
			nowDiv.find('.page-title').html(titles.subject[value-3].title);
			//改变单选框id的值
			var labelArr = nowDiv.find("label");
			labelArr.each(function(index,item){
				$(item).attr("for",dataObj[value-3][index]);
			});

			//改变题卡的选项内容
			labelArr.each(function(index,item){
				$(item).html(titles.subject[value-3].options[index]);
			});

			//清除所有被选中的单选框
			var radios = $("input[type='radio']");
			for(var i=0;i<radios.length;i++){
				if(radios[i].checked){
					radios[i].checked = false;
				}else{
					$(".nextQuestion").unbind();
				}
			}
			//改变单选框id的值
			var inputArr = nowDiv.find("input[type='radio']");
			$(inputArr).each(function(index,item){
				$(item).attr("id",dataObj[value-3][index]);
			});
		}
	}
	//TODO
	//全部提交按钮，传输数据
	$(".lastSubmit").click(function(){
		//如果建议没有填的话，传空字符传上去
		if(!(temp.stu_Advice)){
			temp.stu_Advice = "";
		}
		var jslength=0;
		for(var js2 in temp){
			jslength++;
		}
		// 删除专业代码属性
		delete temp.subcode;
		console.log(temp);
		var url="SaveServlet";
		$.ajax({
			async:false,
			method:"POST",
			traditional:true,
			data:{
				name:JSON.stringify(temp)
			},
			url:url,
			success:function(data){
				data = Number.parseInt(data);
				if(data == 200){
					if (isAnimating) return;
					last.row = now.row;
					last.col = now.col;
					if (last.row != 12) {
						now.row = last.row + 1;
						now.col = 1; pageMove(towards.up);
					}
					showbox("点评成功！");
				}else{
					showbox("点评失败！");
				}
			},
			error: function(err){
				//showbox("网络错误！");
				if (isAnimating) return;
				last.row = now.row;
				last.col = now.col;
				if (last.row != 12) {
					now.row = last.row + 1;
					now.col = 1; pageMove(towards.up);
				}
			}
		});
	});
	
	// 返回第一屏
	$(".returnFirstPage").click(function(){
		window.location="index.html?r="+Math.random();
		
	});
	
	//显示柱形图
	function showColumnChart(data){
		/**柱状图**/
		var bigData = JSON.parse(data);
		//获取标题
		var strtit =  bigData[0].cus_Name;
		var lartit =  bigData[0].large_Area;

		// 基于准备好的dom,初始化echarts实例
		var teacherName = [];
		var teacherScore = [];
		var teaName = [];//动态获取的老师的姓名
		var teaSc = [];//动态获取的老师的分数
		for(var i = 0;i<bigData.length;i++){
			teacherName.push(bigData[i].tea_Name);
			teacherScore.push(bigData[i].average);
		}
		for(var i = 0;i<5;i++){
			teaName.push(teacherName[i]);
			teaSc.push(teacherScore[i]);
		}

		var myChart = echarts.init(document.getElementById('main'));
		// 指定图表的配置项和数据
		
		var option = {
			title: {
				text: 'ECharts 入门示例'
			},
			tooltip: {},
			legend: {
				itemWidth:20,
				backgroundColor:'#52a245'
			},
			color:['#52a245','#000', '#00f', '#f00', '#0f0'],
			xAxis: {
				data: teaName,
//                    name:"老师",
				axisLabel:{
					textStyle:{
						fontSize:14
					},
					interval:0
				}
			},
			yAxis: {
				axisLabel:{
					textStyle:{
						fontSize:15
					}
				},
				name:"平均分数",
				axisLabel:{
					textStyle:{
						fontSize:20
					},
					interval:0
				}
			},
			series: [{
				name: '销量',
				type: 'bar',
				data:teaSc
			}]
		};
		myChart.setOption(option);
		$("#bTit").html(lartit+"大区"+strtit+"排行");
	}
	
	/***第四屏姓名输入框+班级输入框**/	
	$(".classListOne span").click(function(){
		$("#classInput").val($(this).html());
		
		if($("#uName").val()!=""){
			//点亮下方按钮
			$(".btn").css("background","#14c6d0");
			//解绑点击事件
			$(".btn").unbind();
			$(".btn").click(lightBtnAndAjax);
		}
	});
	
	$(".nameList span").click(function(){
		$("#uName").val($(this).html());
		if($("#classInput").val()!=""){
			//点亮下方按钮
			$(".btn").css("background","#14c6d0");
			//解绑点击事件
			$(".btn").unbind();
			$(".btn").click(lightBtnAndAjax);
		}
	});

	function clearInputValue(){
		$(".btn").css("background","#ccc");
		//解绑点击事件
		$(".btn").unbind();
		if($(this).attr("class")=="closeBtn1"){
			$("#classInput").val("");
		}else{
			$("#uName").val("");
		}
	}
	$(".closeBtn1").click(clearInputValue);
	$(".closeBtn2").click(clearInputValue);
});


