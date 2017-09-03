var page = {
	showPage : function(currentPage,countPage,url){
		var str = "";
		var countNum;
		//head
        if (currentPage == 1) {
        	str += "<a>首页&nbsp;</a>";
            /*str += "<a>上一页&nbsp;</a>";*/
        }
        else {
        	str += "<a href="+url+"1"+">首页&nbsp;</a>";
            str += "<a href="+url+(currentPage-1)+">上一页&nbsp;</a>";
        }
		
        //pageNum
        //countNum是指以哪页为开头，这里默认页码只显示5个
		if(currentPage < 4||countPage < 6){
			countNum = 1;
		}else if(currentPage + 2 > countPage){
			countNum = countPage - 4;
		}else{
			countNum = currentPage - 2;
		}
		
		var countPage1 = parseInt(countPage)+1;
		var countNum1 = parseInt(countNum);
		//body
		for(var i = countNum1; i < countPage1; i++){
			
			if(i==currentPage){	
				str += "<a class='cur' style='color:red;'>["+i+"]&nbsp;</a>";
			}else{
				str += "<a href="+url+i+">["+i+"]&nbsp;</a>";
			}
			
			if(i == countNum + 4){
				break;
			}
			
		}
        //end
        if (currentPage == countPage) {
        	/*str += "<a>下一页&nbsp;</a>";*/
            str += "<a>尾页</a>";
        }
        else {
            str += "<a href="+url+(currentPage+1)+">下一页&nbsp;</a>";
            str += "<a href="+url+(countPage1-1)+">尾页</a>";
        }
		if(countPage1>2){
			$(".paging").empty().append(str);
		}
	}

}
