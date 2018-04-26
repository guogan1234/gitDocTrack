/**
 * Created by boris feng on 2017/6/9.
 */

$(function () {
	initDataTable();
	addBtn();
})
/*DataTable 添加 添加按钮*/
function addBtn() {
    var dataPlugin =
        '<div id="div_button" class="pull-left dateRange" style="margin-left: 10px"> '+
        '</div> ';
    $('#mytoolbox').append(dataPlugin);
}

/* DataTable 加载 */
function initDataTable() {
    dataTable = $('#user-list').dataTable({
        "columnDefs":[{
            orderable:false,//禁用排序
            targets:[0, 6]   //指定的列
        }],
        "processing": true,
        "serverSide": false,
        "autoWidth": true,
        "oLanguage": {
            "sSearch": '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>',
            "sLengthMenu": "每页 _MENU_ 条记录",
            'sZeroRecords': '没有数据',
            'sInfo': '第 _PAGE_ 页 / 总 _PAGES_ 页',
            'sInfoEmpty': '没有数据',
            'sInfoFiltered': '(过滤总件数 _MAX_ 条)' ,
            "oPaginate": {
                "sPrevious": "上一页",
                "sNext": "下一页"
            }
        },
        "iDisplayLength": 5,
        "aaSorting": [[1, 'desc']],
        "lengthMenu": [[5, 10, 20], [5, 10, 20]], // change per
        "dom":
        "<'dt-toolbar'<'col-sm-7'l<'#mytoolbox'>><'col-sm-5'f>r>"+
        "t"+
        "<'dt-toolbar-footer'<'col-sm-6'i><'col-sm-6'p>>"  ,
        "ajax" : {
            "url" :CONSTANTS.BASE.PATH+ "/users",
            "dataSrc" : "results"
        },
        "columns" : [  {
            "data" : "id"
        },{
            "data" : "userAccount"
        }, {
            "data" : "userName"
        }, {
            "data" : "userEmail"
        }, {
            "data" : "userPhone"
        }, {
            "data" : "roleNames"
        }],
        "columnDefs" : [
            {
                'targets' :0,
                'searchable' : false,
                'orderable' : false,
                'className' : 'dt-body-center',
                'bSortable' : false,
                'render' : function(data, type, row, meta) {
                    return '<input type="checkbox" onclick="chkClick()" name="ids" value='+row.id+' id="chk'+row.id+'"  class="ace"/>';
                }
            },
            {
                'targets' :5,
                'searchable' : false,
                'orderable' : false,
                'className' : 'dt-body-center',
                'bSortable' : false,
                'render' : function(data, type, row, meta) {
                	for (var i = 0; i < row.roles.length; i++) {
						var tt=row.roles[i];
						return tt.roleName;
					}
                }
            },
            {
                'targets' :6,
                'searchable' : false,
                'orderable' : false,
                'className' : 'dt-body-center',
                'bSortable' : false,
                'render' : function(data, type, row, meta) {
                    return '<button id="btnEdit_' + row.id + '" json="' + JSON.stringify(row).replace(/"/g, '\\\'')
                        + '" data-toggle="modal" onclick="openDialogDetail('+row.id+',' + meta.row + ')" class="btn btn-warning" title="编辑" data-toggle="modal"><i class="glyphicon glyphicon-pencil"></i> </button> ';
                }
            }]
    });

    //复选框全选控制
    var active_class = 'active';
    $('#user-list > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
        var th_checked = this.checked;//checkbox inside "TH" table header
        $(this).closest('table').find('tbody > tr').each(function(){
            var row = this;
            if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
            else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
        });
    });
}


function openXJ()
{
	
	isBtn = 'save';
	//角色
	$.ajax({
    	type : 'GET',
		url : CONSTANTS.BASE.PATH +'/roles',
		dataType : 'JSON',
		contentType : 'application/json',
		success : function(retData) {
//			var sel_js = "";
//	        for (var i = 0; i < retData.resultList.length; i++) {
//	        	sel_js += "<option id='sel_js" + retData.resultList[i].id + "' value=" + retData.resultList[i].id + ">" + retData.resultList[i].title + "</option>";
//	        }
//	        $("#sel_js").html(sel_js);
//	        $("#sel_js").select2();
			console.log(retData);
	        
	        $.fn.modal.Constructor.prototype.enforceFocus = function () {
	        };
		},
		failure : function() {
		}
    });
	$("#userAccount").attr("disabled", false);
	$("#userAccount").val("");
	$("#userName").val("");
	$("#userPassword").val("");
	$("#userPassword2").val("");
	$("#userPhone").val("");
	$("#whitelist").val("");
	$("#userEmail").val("");
	$("#userQq").val("");
	$("#userWechart").val("");
	$("#sxtime").val("");
	$("#jsok").hide();
	$("#jsng").hide();
	$("#mygdlxModalLabel").html("新建用户");
	$("#gdlxForm").modal('show');		
}
function quxiao()
{
	$("#gdlxForm").modal('hide');
}

function save()
{
	$("#dangerMsg").html('');
	
	if('save' == isBtn)
	{
		$.ajax({
			type : 'POST',
			url : CONSTANTS.BASE.PATH +'/business-resource/users?roleIds='+$("#sel_js").val()+'&projectIds='+qxList,
			dataType : 'JSON',
			data : JSON.stringify(param),
			contentType : 'application/json',
			success : function(data) {
				if (data.resultCode==20000) {
					reloadDataTable();
					$('.alert-success').show();
					setTimeout("$('.alert-success').hide()",ALERTMESSAGE);
				}
				else {
					$("#dangerMsg").html(data.resultMassage);
					$('.alert-danger').show();
					setTimeout("$('.alert-danger').hide()",ALERTMESSAGE);
				}
			}
		});
	}
	else
	{
		rowJson.userAccount = param.userAccount;
		rowJson.userName = param.userName;
		rowJson.userPassword = param.userPassword;
		rowJson.userPhone = param.userPhone;
		rowJson.whitelist = param.whitelist;
		rowJson.userEmail = param.userEmail;
		rowJson.userQq = param.userQq;
		rowJson.userWechart = param.userWechart;
		rowJson.roleName = param.roleName;
		rowJson.expiryTime = param.expiryTime;
		$.ajax({
			type : 'PUT',
			url : CONSTANTS.BASE.PATH +'/business-resource/users/'+logicalId+'?roleIds='+$("#sel_js").val()+'&projectIds='+qxList,
			dataType : 'JSON',
			contentType : 'application/json',
			data : JSON.stringify(rowJson),
			success : function(data) {
				console.log(data.resultMassage);
				if (data.resultCode == 30000){
					rowJson.roleIds = data.resultEntity.roleIds;
					//数据修改
					var myTable = $('#user-list').DataTable();
					
					myTable.cell( rowNo,1 ).data( rowJson.userAccount );//更新行

					myTable.cell( rowNo,2 ).data( rowJson.userName );//更新行
					
					myTable.cell( rowNo,3 ).data( rowJson.userEmail );//更新行
					
					myTable.cell( rowNo,4 ).data( rowJson.userPhone );//更新行
					
					myTable.cell( rowNo,5 ).data( rowJson.roleName );//更新行
									
					$("#btnEdit_" + logicalId).attr('json',JSON.stringify(rowJson).replace(/"/g, '\\\''))
					$('.alert-success').show();
					setTimeout("$('.alert-success').hide()",ALERTMESSAGE);
				}
				else
				{
					$("#dangerMsg").html(data.resultMassage);
					$('.alert-danger').show();
					setTimeout("$('.alert-danger').hide()",ALERTMESSAGE);
				}
			},
			error : function() {
				$('.alert-danger').show();
				setTimeout("$('.alert-danger').hide()",ALERTMESSAGE);
			}
		});
	}
	$("#gdlxForm").modal('hide');
}


