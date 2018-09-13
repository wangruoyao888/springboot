
function initTableData(){
    $.ajax({
        Type:"get",
        url:"/user/getAllUser",
        cache: false,
        success:function (data_user) {
            console.log(data_user);
            $("#search").val("");
            tableinit(data_user);
        },
        error:function (msg) {
            console.log(msg);
        }
    });
}

function tableinit(data_user){
    //展示已知数据
    layui.use('table', function(){
        var table = layui.table;
        table.render({
            elem: '#demo',
            center:true
            ,cols: [
                [
                     {field: 'user_id', title: 'ID',align:'center',  sort: true,width:'16.6%'}
                    ,{field: 'user_name', title: '用户名',align:'center',width:'16.6%'}
                    ,{field: 'user_info', title: '用户信息',align:'center',width:'16.6%' }
                    ,{field: 'user_img', title: '用户头像',align:'center',width:'16.6%'}
                    ,{field: 'user_pwd', title: '密码',align:'center' ,width:'16.6%'}
                    ,{field:'right', title: '操作',align:'center' ,width:'16.6%',toolbar:"#barDemo"}
                ]
            ]
            ,data:data_user
            ,id: 'user_table'
            //,skin: 'line' //表格风格
            ,even: true
            ,page:{
                layout:['refresh','prev','page','next','skip','limit','count']
                ,curr:1
                ,groups:10
                ,fist:'首页'
                ,last:'尾页'
                ,theme:'#009688'
            }

        });
        //监听表格复选框选择
        table.on('checkbox(user_table)', function(obj){
            console.log(obj)
        });
        //监听工具条
        table.on('tool(user_table)', function(obj){
            var data = obj.data;
            console.log(JSON.stringify(data));
            var data_user = JSON.parse(JSON.stringify(data));
            //查看监听事件
            if(obj.event === 'detail'){
                var id = {};
                id ["id"] =data_user.user_id;
                $.post("/user/getUserById",id,function (data_one) {
                    var data_detail = JSON.parse(JSON.stringify(data_one));
                    var data_detail_name = data_detail.user_name;
                    var data_detail_pwd =data_detail.user_pwd;
                    var data_detail_info = data_detail.user_info;
                    $('#user_name').val(data_detail_name);
                    $('#user_pwd').val(data_detail_pwd);
                    $('#user_info').val(data_detail_info);
                    layer.open({
                        type: 1,
                        Title:"查看",
                        area: ['600px', '360px'],
                        shadeClose: true,//点击遮罩关闭
                        shade: false,
                        btn:["确定","取消"],
                        content: $("#detail")
                        ,yes:function () {
                            layer.closeAll();
                            $("#detail").hide();
                        },btn2: function () {
                            layer.closeAll();
                            $("#detail").hide();
                        },
                        cancel : function () {
                            layer.closeAll();
                            $("#detail").hide();
                        }
                    });
                })
            }
            //删除监听事件
            else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    layer.close(index);
                    obj.del();//删除对应行（tr）的DOM结构，并更新缓存
                    //向服务端发送删除指令
                    console.log(data_user.user_id);
                    var data_user_del = {};
                    data_user_del["id"]=data_user.user_id;
                    $.post("/user/delUser",data_user_del,function (msg) {
                        if (msg == "200"){
                            layer.msg(' 删除成功');
                            initTableData();
                        }else {
                            alert("删除失败") ;
                        }
                    })
                });
            }
            //编辑监听事件
            else if(obj.event === 'edit'){
                var id_edit = {};
                id_edit ["id"] =data_user.user_id;
                $.post("/user/getUserById",id_edit,function (data_one) {
                    var data_edit = JSON.parse(JSON.stringify(data_one));
                    var data_edit_name = data_edit.user_name;
                    var data_edit_pwd =data_edit.user_pwd;
                    var data_edit_info = data_edit.user_info;
                    $('#user_name_edit').val(data_edit_name);
                    $('#user_pwd_edit').val(data_edit_pwd);
                    $('#user_info_edit').val(data_edit_info);
                    layer.open({
                        type: 1,
                        Title:"编辑",
                        area: ['600px', '360px'],
                        shadeClose: true,//点击遮罩关闭
                        shade: false,
                        btn:["确定","取消"],
                        content: $("#edit")
                        ,yes:function () {
                            var user_name = document.getElementById("user_name_edit").value;
                            var user_pwd = document.getElementById("user_pwd_edit").value;
                            var user_info = document.getElementById("user_info_edit").value;
                            var user_edit ={};
                            user_edit["user_id"]=data_user.user_id;
                            user_edit["user_name"]=user_name;
                            user_edit["user_pwd"]=user_pwd;
                            user_edit["user_info"]=user_info;
                            $.post("/user/updataUser",user_edit ,function (msg) {
                                if(msg=="200"){
                                    layer.closeAll();
                                    $("#edit").hide();
                                    initTableData();
                                }else {
                                    layer.msg(' 更新失败');
                                }
                            });

                        },btn2: function () {
                            $("#edit").hide();
                            layer.closeAll();
                        },
                        cancel : function () {
                            $("#edit").hide();
                            layer.closeAll();
                        }
                    });
                })
            }
        });
    });

}




// self.setInterval(addZD,3);
function addZD() {
    var data_add = {};
    data_add ["user_name"] ="adafas";
    data_add ["user_pwd"] ="4654681";
    console.log(data_add);
    $.post("/doregister",data_add,function (msg) {
        console.log(msg);
    })
}

function add() {
    layer.open({
        type: 1,
        Title:"添加",
        area: ['600px', '360px'],
        shadeClose: true,//点击遮罩关闭
        shade: false,
        btn:["确定","取消"],
        content: $("#add")
        ,yes:function () {
            var data_add = {};
            data_add ["user_name"] =document.getElementById("userName").value;
            data_add ["user_pwd"] =document.getElementById("password").value;
            console.log(data_add);
            $.post("/doregister",data_add,function (msg) {
                console.log(msg);
                if (msg == 200){
                    layer.closeAll();
                    $("#add").hide();
                    initTableData();

                } else {
                    alert("添加失败")
                }
            })

        },btn2: function () {
            layer.closeAll();
            $("#add").hide();
        },
        cancel : function () {
            layer.closeAll();
            $("#add").hide();
        }
    });

}
function serach() {
    var user_name = {};
    user_name ["user_name"] = document.getElementById("search").value;
    $.post("/user/search",user_name,function (data_search) {
        console.log(JSON.stringify(data_search));
        tableinit(data_search);
    })
}
