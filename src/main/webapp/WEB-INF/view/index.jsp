<%@page import="java.util.Random"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext['request'].contextPath}" />
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
	<title>淘宝客</title>
	<style type="text/css">
	* {
		margin:0px;
		padding:0px;
		font-family: "微软雅黑";
	}
	body{
	
	<%
		String[] bgs = {
			"http://l.bst.126.net/pub/design/5774740622197861002.jpg",
			"http://l.bst.126.net/pub/design/6597151130517974256.jpg_1680x10000x0x90.jpg",
			"http://l.bst.126.net/pub/design/5789940270939751422.jpg",
			"http://l.bst.126.net/pub/design/5776992422011655245.jpg",
			"http://l.bst.126.net/pub/design/5774740622197861005.jpg",
			"http://l.bst.126.net/pub/design/2683582427977277320.jpg_1680x10000x0x90.jpg"
		};
		int idx = (int)(System.currentTimeMillis() % bgs.length);
		out.print("background-image:url(\""+bgs[idx] +"\");");
	%>
	
	}
	div {
		//opacity:0.7;
	}
	a{
		text-decoration: none;
		color:yellow;
	}
	h1{
		font-size:72px;
		color:#1C87D8;
	}
	div.container{
		width:70%;
		margin:50px auto;
		//background-color:#CBE2F4;
		//border:10px solid #81C0F2;
		padding:10px;
	}
	div.item {
		padding:20px;
		background-color:green;
		color:yellow;
		font-weight:900;
		font-size:24px;
		display: inline-block;
		cursor: pointer;
		border:5px solid #5B9BCC;
	}
	div.item:hover{
		border:5px solid green;
		color:green;
		background-color:yellow;
	}
	div.todo{
		cursor: not-allowed;
		padding:20px;
		background-color:gray;
		color:#eee;
		font-weight:900;
		font-size:24px;
		display: inline-block;
		border:5px solid #ddd;
	}
	div.input{
		padding:20px;
		min-height:20px;
		margin:30px auto 30px auto;
		color:#1C87D8;
		background-color:#B3D9F7;
		border:5px solid #5B9BCC;
	}
	div.tips{
		padding:25px;
		background-color:#A3BE89;
		color:teal;
		font-size:24px;
		display: inline-block;
		float: right;
	}
	#helper{
		position:absolute;
		top:20px;
		left:20px;
		cursor: move;
		color:#000;
		border:4px double blue;
	}
	</style>
	<script src="http://a.tbcdn.cn/apps/top/x/sdk.js?appkey=12668380"></script>
	<script type="text/javascript">
	
	function urlConvert() {
		var tip = document.getElementById("tips");
		tip.innerHTML = "操作中···";
		var url = document.getElementById("url").textContent;
		if( url == null || url == "" || url.length == 0) {
			tip.innerHTML = "<span style='color:red;'>↑↑↑↑将地址复制到上面的输入框↑↑↑↑</span>";
			return false;
		}
		var request =
	    {
	        	method: 'taobao.taobaoke.widget.url.convert',
	        	fields: '',
	        	url: url,
	    };
	    TOP.api(request, function(response) {
	        if(response.error_response) {
	        	tip.innerHTML = response.error_response.sub_msg;
	            return;
	        }
        	tip.innerHTML = "<a href='"+response.taobaoke_item.click_url+"' onmouseover='showQr(this)'>点击进入</a>";
	    });
		
	}
	
	function showQr(o) {
		return;
		document.getElementById("qr").src = "http://www.liantu.com/api.php?text="+ o.href;
		document.getElementById("qr_box").style.display="block";
	}
	
	function tryConvert()
	{
		var tip = document.getElementById("tips");
		tip.innerHTML = "操作中···";
		var url = document.getElementById("url").textContent;
		if( url == null || url == "" || url.length == 0) {
			tip.innerHTML = "<span style='color:red;'>↑↑↑↑将地址复制到上面的输入框↑↑↑↑</span>";
			return false;
		}
		var i = 0, j = 0;
		for(i = 2; i < url.length - 1; i++) {
			if ( (url[i-2] == '?' || url[i-2] == '&') && url[i-1] == 'i' && url[i] == 'd' && url[i+1] == '=' ) {
				j = i;
				i = 9999;
			}
		}
		var iid = 0;
		j+=2;
		for(; j < url.length && url[j] >= '0' && url[j] <= '9'; j++) {
			iid = (url[j] - '0') + iid * 10;
		}
	    var request =
	    {
	        	method: 'taobao.taobaoke.widget.items.convert',
	        	fields: 'title, pic_url,price, nick, num_iid,click_url,shop_click_url,iid,commission,commission_rate,commission_num,volume',
	        	num_iids: [iid],
	    };
	    TOP.api(request, function(response) {
	        if(response.error_response) {
	        	tip.innerHTML = response.error_response.sub_msg;
	            return;
	        }
	        var item = response.total_results == 1 ? response.taobaoke_items.taobaoke_item[0] : null;
	        if(item == null) {
	        	tip.innerHTML = "<span style='color:red;'>该商品没有返利</span>";
	        } else {
	        	tip.innerHTML = "按"+item.price+"元成交可返利"+item.commission+"元　　<a href='"+ item.shop_click_url +"' >店铺返利</a> | <a href='"+ item.click_url +"'>单件返利</a>";
				//item.shop_click_url;
	        }
	    });
	}
	</script>
	</head>
	<body>
	
		<a title="按住此按钮拖拽到书签/收藏栏" id="helper" href="javascript:window.location.href='http://easy-taobaoke.cloudfoundry.com/?url='+encodeURIComponent(window.location.href);">
			返利
		</a>
		<div class="container">
			<div style="text-align: center;"><h1>淘宝返利</h1><br/></div>
			<div class="input" id="url" contenteditable="true">
				<c:if test="${empty param.url}">
					复制链接到此处
				</c:if>
				<c:if test="${empty param.url == false }">
					${param.url }
				</c:if>
			</div>
			<div class="item" onclick="tryConvert()" title="单个商品返利">单品</div>
			<div class="item" onclick="urlConvert()" title="聚划算、天猫超市、天猫、车险等返利">聚划算等</div>
			<div class="todo" title="未完善">更多</div>
			<div class="tips" id="tips">
				&nbsp;
			</div>
			<div id="qr_box" style="margin:0px auto; padding:0px; width:200px; display: none;"
				onmouseover="javascript:document.getElementById('qr').style.display='none';document.getElementById('msg').style.display='block';"
				onmouseout="javascript:document.getElementById('qr').style.display='block';document.getElementById('msg').style.display='none';"
				>
				<img id="qr" width="200" height="200" src=""/>
				<span id="msg" style="width:200px; height:200px; text-align:center; display: none; font-weight:900; font-size:36px;"><br/>手机扫描</span>
			</div>
		</div>
	</body>
</html>