/**
 * 初始化
 */
$(function () {
    console.log('加载了流程引擎js..');
    $('#submitTaskModal').modal('hide');

    // 显示流程审批人
    var $userShowEl = $('#userShow');
    var $nextUserIdEl = $('input[name="nextUserId"]');

    var execType = $('input[name="execType"]').val();
    var testUser = $('input[name="userId"]').val();
    if (execType === 'test') {
        $userShowEl.text(yuheUtils.getUserName(testUser));
        $nextUserIdEl.val(testUser);
    } else {
        var $checkedEl = $('input[name="targetTaskDefKey"]:checked');
        var assignee = $checkedEl.attr('data-assignee');
        var candidateUserIds = $checkedEl.attr('data-candidateUserIds');

        if (assignee) {
            $userShowEl.text(yuheUtils.getUserName(assignee));
            $nextUserIdEl.val(assignee);
        } else if (candidateUserIds) {
            $.each(candidateUserIds.split(','), function (i, item) {
                if (i > 0) {
                    $userShowEl.append(',');
                }
                $userShowEl.append(yuheUtils.getUserName(item));
            });
            $nextUserIdEl.val(candidateUserIds);
        }
    }

    initUserCommOpinion();

});


/**
 * 提交任务
 * @returns {boolean}
 */
function submitTask() {
    var taskData;
    var fcEl = $('#businessForm').find('select.form-control');
    var businessFormData = yuheUtils.getFormJson('businessForm');
    var taskFormData = yuheUtils.getFormJson('taskForm');

    var nextUserId = taskFormData.nextUserId;
    var nextNode = taskFormData.targetTaskDefKey;
    var nextNodeNum = $('input[name="nextNodeNum"]').val();
    if (Number(nextNodeNum) > 0 && !nextUserId) {
        alert('请选择下一环节审批人');
        return false;
    }
    if (Number(nextNodeNum) > 0 && !nextNode) {
        alert('请选择下一环节流向');
        return false;
    }

    // 保存选择的select的text值
    for (var i = 0; i < fcEl.length; i++) {
        var name = $(fcEl[i]).attr('name');
        businessFormData[name + '_show'] = $(fcEl[i]).find('option:selected').text();
    }
    console.log("businessFormData= ", businessFormData);
    taskData = businessFormData;
    taskData.flowVariable = JSON.stringify(taskFormData);
    $.ajax({
        type: 'POST',
        url: '../../submit/task',
        dataType:'json',
        // contentType : 'application/json;charset=utf-8',
        data: taskData,
        success: function (r) {
            if (r.Status === 0) {
                if (Number(nextNodeNum) === 0) {
                    alert('任务已归档');
                    window.opener.location.reload();    //刷新父页面
                } else {
                    alert('任务已提交');
                    window.opener.location.reload();    //刷新父页面
                }

            }
            else {
                alert('任务提交失败：' + r.msg);
            }
        }
    });


    return false;
}

/**
 * 提交表单前执行事件
 * 说明：
 * 1.在需要提交任务前执行一些业务逻辑的情况下在当前表单重写此方法即可。
 * 2.如果在执行此方法条件不成立不想要提交任务，可以return false;即可。
 */
function preSubmit() {
    console.log('表单提交前事件.....');
}

/**
 * 提交表单
 * @param func
 * @returns {*}
 */
function submit(func) {
    var b = func;
    if (b === false) {
        return b;
    }
    $('#busBtn').click();
}

/**
 * 业务表单提交事件
 */
$('#businessForm').submit(function () {
    submitTask();
    return false;
});

/**
 * 审批用户选择
 */
function selUser(that) {
    // $(".glyphicon-user").html("");
    // $("input[name='nextUserId']").val("");
    $(that).html('');
    $(that).next().next().val('');
    layer.open({
        type: 2,
        title: '选择用户',
        resize: true,
        fixed: true,
        shadeClose: true,
        btn:['确定', '取消'],
        shade: 0.1,
        scrollbar: true,
        area:['380px', '380px'],
        content:'/activiti/deal/task',
        yes:function(layero,index){
           findUser(that,index);
            layer.close(layero)
        }
    });
}

function findUser(that,index) {
    var info="";
    var infoId="";
    var info1="";
    var infoId1="";
    var id;
    var id1;
    var name = window[index.find('iframe')[0]['name']];
    var value=name.zTreeOnCheck();
console.log(value)
    for(var i=0;i<value.length;i++){
         id=value[i];
        for(var j=0;j<id.length;j++){
                if(id[j].checked) {
                    console.log(id[j])
                    if(id[j].isParent){

                        id1=id[j].children
                   for(var h=0;h<id1.length;h++){
                       if(id1[h].checked) {
                           info += id1[h].userDepartmentName + ","
                           infoId += id1[h].uid + ","
                       }
                      }

                    }else if(id[j].parentId==0){
                            info1+=id[j].userDepartmentName+","
                            infoId1+=id[j].deptId+","

                    }
                }
        }
    }
    if(info1==""&&infoId1==""){
        info=info.substring(0, info.lastIndexOf(','));
        infoId=infoId.substring(0, infoId.lastIndexOf(','));
    }
    else if(info==""&&infoId==""){
        info1=info1.substring(0, info1.lastIndexOf(','));
        infoId1=infoId1.substring(0, infoId1.lastIndexOf(','));
    }
    else {
        info1 = info1.substring(0, info1.lastIndexOf(','));
        infoId1 = infoId1.substring(0, infoId1.lastIndexOf(','));
    }

    $(that).next().text(info).append(info1);
    $(that).next().next().val(infoId+ infoId1);
    // $(".glyphicon-user").next().text(info).append(info1);
    // $("input[name='nextUserId']").val(infoId+ infoId1);
}


/**
 * 回退到首节点
 */
function back2FirstNode() {
    layer.confirm('确认回退到首环节吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index){
        layer.close();
        var taskFormData = yuheUtils.getFormJson('taskForm');
        $.ajax({
            type: 'POST',
            url: '../../rollBack/firstTask',
            dataType:'json',
            // contentType : 'application/json;charset=utf-8',
            data: taskFormData,
            success: function (r) {
                if (r.Status === 0) {
                    alert('任务已回退到首环节');
                } else {
                    alert('任务回退失败');
                }
            }
        });
        layer.close(index);
    }, function(){
    });

}

/**
 * 驳回上环节
 */
function back2PreNode() {
    layer.confirm('确认驳回上个环节吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index){
        layer.close();
        var taskFormData = yuheUtils.getFormJson('taskForm');
        $.ajax({
            type: 'POST',
            url: '../../rollBack/preTask',
            dataType:'json',
            // contentType : 'application/json;charset=utf-8',
            data: taskFormData,
            success: function (r) {
                if (r.Status == 0) {
                    alert('任务已驳回');
                } else {
                    alert('任务驳回失败');
                }
            }
        });
        layer.close(index);
    }, function(){
    });

}

/**
 * 驳回任意环节
 */
function back2AnyNode() {

    var setting = {
        data: {
            simpleData: {
                enable: true,
                chkStyle: "radio",
                rootPId: -1
            },
            key: {
                url: "nourl"
            }
        }
    };

    var taskId = $('input[name="taskId"]').val();
    $.ajax({
        url:"/activiti/canBackNodes",
        type:"GET",
        data: {taskId: taskId},
        dataType: "json",
        success: function(r){
            console.log(r);
            console.log(r.rNodes);
            var zTree = $.fn.zTree.init($("#backNodeTree"), setting, r.rNodes);
            // 让第一个父节点展开
            var rootNode_0 = zTree.getNodeByParam('pid', 0, null);
            zTree.expandNode(rootNode_0, true, false, false, false);

            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择驳回节点",
                maxmin: true,
                area: ['380px', '380px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#canBackNodeLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = zTree.getSelectedNodes();
                    console.log('选择的nodeID：', node[0].id);
                    console.log('选择的nodeName：', node[0].name);

                    $.ajax({
                        type: 'POST',
                        url: '../../rollBack/anyTask',
                        dataType:'json',
                        data: {taskId: taskId, targetNode: node[0].id},
                        success: function (r) {
                            if (r.Status === 0) {
                                alert('任务已驳回[' + node[0].name + ']节点');
                            } else {
                                alert('任务驳回失败');
                            }
                        }
                    });
                    layer.close(index);
                }
            });
        },
        error: function(){

        }
    });

}

/**
 * 查看流程图
 */
function showFlowChart() {
    var processInstanceId = $('input[name="processInstanceId"]').val();
    var taskId = $('input[name="taskId"]').val();
    layer.open({
        type: 2,
        title: '流程状态图',
        resize: true,
        fixed: true,
        shadeClose: true,
        shade: 0.1,
        scrollbar: true,
        area: ['70%', '80%'],
        content: '/activiti/current/flowChart?processInstanceId=' + processInstanceId+'&taskId='+taskId,
        // end: callback
    });
}

/**
 * 查看流转意见
 */
function showComments() {
    var processInstanceId = $('input[name="processInstanceId"]').val();
    layer.open({
        type: 2,
        title: '查看流转意见',
        resize: true,
        fixed: true,
        shadeClose: true,
        shade: 0.1,
        scrollbar: true,
        area: ['90%', '60%'],
        content: '/activiti/find/advice?processInstanceId='+processInstanceId,
        // end: callback
    });
}

/**
 * 删除附件
 */
function delAttachment(fileId) {
    alert('删除附件:' + fileId);
}

/**
 * 初始化用户个人常用意见
 */
function initUserCommOpinion() {
    $.get("/opinion/list", function (r) {
        console.info(r)
        var opinionList=r;

    // var list = [{opinion: '同意'}, {opinion: '不同意'}]; // todo 把这个换成你数据查询出来的list列表数据就行
    var options = '<option value="">常用意见</option>';
    $.each(opinionList, function (i, item) {
        options += '<option value="' + item.opinionName + '">' + item.opinionName + '</option>';
    });
    $('#commOpinion').append(options);
    });
}

/**
 * 选择常用意见事件
 */
$('#commOpinion').on('change', function () {
    $('textarea[name="comment"]').text($(this).val());
});