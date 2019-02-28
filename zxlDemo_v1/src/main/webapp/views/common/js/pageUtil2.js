var pageUtil = {
	showPage : function(cPage,tPage,total,_pagesize,changeMethod){
		var currentPage = parseInt(cPage);
		var countPage = parseInt(tPage);
		var str = "";
		document.getElementById("pagination").innerHTML = "";
		if(total < 10) {
			return;
		};
		if(currentPage == countPage) {
			str += "&nbsp;&nbsp;" + total + "/" + total;
		}else {
			str += "&nbsp;&nbsp;" + _pagesize * currentPage + "/" + total;
		}
		str += "&nbsp;&nbsp;<span>每页显示：<select style=\"height:24px;line-height:24px;\" id='pagesize'>";
		str += "<option value='10'" ;
		if(_pagesize == 10){
			str += " selected  ";
		}
		str += ">10</option><option value='20'" ;
		if(_pagesize == 20){
			str += " selected  ";
		}				
		str += ">20</option><option value='50'" ;
		if(_pagesize == 50){
			str += " selected  ";
		}				
		str += 	">50</option>";
		
		str += "<option value='100'" ;
		if(_pagesize == 100){
			str += " selected  ";
		}				
		str += 	">100</option>";
		
		str += "<option value='200'" ;
		if(_pagesize == 200){
			str += " selected  ";
		}
		str += 	">200</option>";
		
		str += "<option value='500'" ;
		if(_pagesize == 500){
			str += " selected  ";
		}
		str += 	">500</option>";
		
		str += "</select>&nbsp;&nbsp;&nbsp;&nbsp;<span>";
		var startNum = 0;
		//head
        if (currentPage == 1) {
            str += " <a href=\"javascript:void(0)\"> 上一页  </a>   ";
        }
        else {
            str += "<a href=\"javascript:void(0)\" onclick=\""+changeMethod+"("+(currentPage-1) + "," + _pagesize + ")\"> 上一页   </a>";
        }
        
		//startPage
		if(currentPage < 4||countPage < 6){
			startNum = 1;
		}else if(currentPage + 2 > countPage){
			startNum = countPage - 4;
		}else{
			startNum = currentPage - 2;
		}
		
		//Body
		if(currentPage > 3 && countPage > 5) {
			str += "<a href=\"javascript:void(0)\"  onclick=\""+changeMethod+"(" + 1 + "," + _pagesize + ")\">"+1+"</a>  ...  ";
		}
		for(var i = startNum; i < countPage + 1; i++){
			if(i==currentPage){	
				str += "<i class=\"cur\">"+i+"</i>";
			}else{
				str += "<a href=\"javascript:void(0)\"  onclick=\""+changeMethod+"(" + i + "," + _pagesize + ")\">"+i+"</a>";
			}
			
			if(i == startNum + 4){
				break;
			}
		}
		if(countPage > 5 && currentPage < countPage - 2) {
			str += "  ...  <a href=\"javascript:void(0)\"  onclick=\""+changeMethod+"(" + countPage + "," + _pagesize + ")\">"+countPage+"</a>";
		}
		//End
        if (currentPage == countPage) {
        	str += " <a href=\"javascript:void(0)\">  下一页     </a> ";
        }
        else {
            str += "<a href=\"javascript:void(0)\"  onclick=\""+changeMethod+"(" +(currentPage+1) + "," + _pagesize + ")\">  下一页     </a>";
        }
        str += "</span> ";
        if(countPage > 5) {
        	str += " <span>转到：<input id=\"topage\" type=\"text\" value=\""+currentPage+"\" onkeyup=\"if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\\D/g,'')}\" onafterpaste=\"if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}\"><input type=\"button\" value=\"GO\" class=\"go\" onclick=\"goPages()\";></span>";
        }
		document.getElementById("pagination").innerHTML = str;
	}
};


