var videoMap = new Map();
var fileUrl = "";
$(function () {
    initVideoUpload();
    filedel();
})
function initVideoUpload() {
    var $form = $('#main_form');
    var file_input = $form.find('input[jstype=video]');
    var fd = new FormData($form.get(0));

    file_input.ace_file_input({
        style: 'well',
        btn_choose: '请上传文件',
        btn_change: null,
        droppable: false,
        thumbnail: 'large',

        maxSize: 52000000,// bytes
        allowExt : ["apk"],
       // allowMime : [ "video/apk" ],
        before_change: function (files, dropped) {
            return true;
        },
        before_remove: function () {
        },
        preview_error: function (filename, code) {
        }
    });

    file_input.on('file.error.ace', function (ev, info) {
        if (info.error_count['ext'] || info.error_count['mime'])
            alert('文件类型不匹配!');
        if (info.error_count['size'])
            alert('请选择小于50M的视频');
    });

    file_input.on('change', function (e) {
        var files = $(this).data('ace_input_files');
        if (files && files.length > 0) {
            e.preventDefault();
            var formData = new FormData();

            formData.append("file", files[0]);


            $.ajax({
                url: '/business-resource/upload_file',
                data: formData,
                cache: false,
                contentType: false,
                processData: false,
                type: 'POST',
                success: function (data) {
                    if (data.code == 10000) {
                        videoMap.put(data.result.id, data.result);
                        fileUrl = data.result.fileUrl;
                        //refreshContainer();
                    }
                    //$.unblockUI();
                },
                error:function (e) {
                    $("#dangerMsg").html(e.responseJSON.message);
                    $('.alert-danger').show();
                    setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
                }
            });
        }
    });
}


function filedel() {
    $('a.remove').each(function (index) {
        $(this).on("click", function () {
            var fileIndex = $(this).siblings('input').attr('name');
            var fid = $('input[name=' + fileIndex + ']').attr("fileId");
            if (fileIndex.indexOf("video") > 0)
                videoMap.remove(fid);
            refreshContainer();
        })
    })
}

function refreshContainer() {
    $('#main_form').find('input[type=file]').ace_file_input('reset_input_ui');
    $('video').remove();
    n = 1;
    array = videoMap.keySet();
    for (var i in array) {
        var key = array[i];
        var v = videoMap.get(key);
        var thumbnailUrl = v.fileUrl;
        $('input[name=video' + n + ']')
            .next()
            .addClass('hide-placeholder selected')
            .removeAttr('data-title')
            .children('span')
            .attr('data-title', v.fileName)
            .html('<video class="middle" style="width:150px;height:117px;" src="'
                + v.thumbnailUrl
                + '" /><i class="ace-icon fa fa-picture-o file-image"></i>');
        $('input[name=video' + n + ']').attr("fileId", v.id);
        $('input[name=video' + n + ']').parent().append('<a title="查看文件" class="preview" href="#" data-toggle="modal" onclick="showVideoModal(\'' + v.fileUrl + '\')"><i class="ace-icon fa fa-search-plus"></i></a>');
        n++;
    }
}
/*保存*/
function saveMessage() {
    $("#dangerMsg").html('');
    var url = "";
    if (videoMap.size() > 0) {
        url = fileUrl;
    } else {
        url = null;
    }
    if(url == null){
        layer.tips('上传失败，请刷新后重新上传', '#file1');
        return;
    }
    var param = {
        versionNo: $("#appVersion").val(),
        downloadUrl: url,
    }

    $.ajax({
        type: 'POST',
        url: '/business-resource/sysVersions',
        dataType: 'JSON',
        data: JSON.stringify(param),
        contentType: 'application/json',
        success: function (data) {
            if (data.code == 10000) {
                $('.alert-success').show();
                setTimeout("$('.alert-success').hide()", ALERTMESSAGE);
            } else {
                $("#dangerMsg").html(data.responseJSON.message);
                $('.alert-danger').show();
                setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
            }
        },
        error: function (e) {
            $("#dangerMsg").html(e.responseJSON.message);
            $('.alert-danger').show();
            setTimeout("$('.alert-danger').hide()", ALERTMESSAGE);
        }
    });

}