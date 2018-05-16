layui.config({
	base : "../../static/sysback/js/"
}).use(['form','layer','jquery','layedit','laydate'],function(){
	var form = layui.form(),
	layer = parent.layer === undefined ? layui.layer : parent.layer,
	laypage = layui.laypage,
	layedit = layui.layedit,
	laydate = layui.laydate,
	$ = layui.jquery;
 	var addNewsArray = [],addTeacher;
 	form.on("submit(addTeacher)",function(data){
 		addTeacher = '{"loginName":"'+$(".loginName").val()+'",'; 
 		addTeacher += '"schoolName":"'+$(".schoolName").val()+'",'; 
 		addTeacher += '"privEmail":"'+$(".privEmail").val()+'",'; 
 		addTeacher += '"email":"'+$(".email").val()+'",'; 
 		addTeacher += '"emailPwd":"'+$(".emailPwd").val()+'",';
 		addTeacher += '"loginPwd":"'+$(".loginPwd").val()+'",';
 		addNewsArray.unshift(JSON.parse(addTeacher));
 		//params = data.field;
 		//alert(JSON.stringify(params))
 		//submit($,params);
 		//弹出loading
 		var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            top.layer.close(index);
			top.layer.msg("文章添加成功！");
 			layer.closeAll("iframe");
	 		//刷新父页面
	 		parent.location.reload();
        },2000);
 		return false;
 	})
 	
 	/*function submit($,params){
        $.post('../../sysback/user/addTeacher', params, function (res) {
            if (res.status==1) {
                 layer.msg(res.message,{icon:1},function(index){
                 CloseWin();
                }) 
            }else if(res.status==2){
            layer.msg(res.message,{icon:0},function(){
            parent.location.href='${path}/sys/toLogin';
            CloseWin();
            })
            }else{
            layer.msg(res.message,{icon:0},function(){
            location.reload(); // 页面刷新
                 return false
            })
            } 
        }, 'json');

    }*/
 	
	
})
