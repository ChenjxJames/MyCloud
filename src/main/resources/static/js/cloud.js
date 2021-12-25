$(document).ready(function () {
    let path = [];
    let folderId = 0;
    let optFileIdList = []; // 被选中的文件（夹）id列表，用于文件移动
    let fileIdList = [];
    let pageFileCount =  (Math.floor($("#main_right_content").height()/110)+1) * Math.floor($("#main_right_content").width()/110);
    getUserInfo();

    $("#main_right_content").scroll(function(){
        let nDivHight = $("#main_right_content").height();
        let nScrollHight = $(this)[0].scrollHeight;
        let nScrollTop = $(this)[0].scrollTop;
        let paddingBottom = parseInt( $(this).css('padding-bottom') ),paddingTop = parseInt( $(this).css('padding-top') );
        if(nScrollTop + paddingBottom + paddingTop + nDivHight >= nScrollHight) {
            if ($("#main_right_content").children().length < fileIdList.length) {
                if ($("#main_right_content").children().length + pageFileCount < fileIdList.length) {
                    getFileByIdList(fileIdList.slice($("#main_right_content").children().length, pageFileCount), false);
                } else {
                    getFileByIdList(fileIdList.slice($("#main_right_content").children().length), false);
                }
            } else {
                alert("到底了");
            }
        }
    });

    $("#logout").click(function () {
       $.getJSON("/user/logout", function (result) {
           if (result.state === 0) {
               alert("注销成功");
               window.location.pathname = "/user/login.html";
           }
       })
    });

    function showUserInfo(data) {
        $("#username").text(data.username);
        $("#loading").hide();
    }

    // 在搜索框激活时按下键盘按键
    $("#keyword").keydown(function (event) {
        // 按下的按键是回车键
        if (event.which === 13) {
            alert("keyword:" + $("#keyword").val() + "  搜索模块待开发");
        }
    });

    // 键盘时间监听
    $(document).keydown(function (event) {
        //ctrl+A
        if (event.ctrlKey && event.which === 65) {
            let fileItems = $(".file-item");
            if ($(".file-opt").length === fileItems.length) {
               fileItems.each(function () {
                    $(this).removeClass('file-opt');
                });
            } else {
                fileItems.each(function () {
                    $(this).addClass('file-opt');
                });
            }
            showOtherMenu();
        }
    });

    // 鼠标点击左侧导航条
    $(".menu").click(function () {
        $(".menu").each(function () {
            $(this).removeClass("menu-opt");
        });
        $(this).addClass("menu-opt");
        if ($(this).attr("id") === "all_file") {
            $("#main_right_file_nav").css("display", "inline");
            $("#main_right_share_nav").css("display", "none");
            path = [];
            getFlist(0, "全部");
        } else if ($(this).attr("id") === "my_share") {
            $("#main_right_file_nav").css("display", "none");
            $("#main_right_share_nav").css("display", "inline");
            getShareList();
        } else if ($(this).attr("id") === "share_to_me") {
            $("#main_right_file_nav").css("display", "inline");
            $("#main_right_share_nav").css("display", "none");
            getShareToMeList();
        } else if ($(this).attr("id") === "about") {
            $("#main_right_file_nav").css("display", "none");
            $("#main_right_share_nav").css("display", "none");
            $("#file_path").css("display", "none");
            getAbout();
        }
    });

    // 鼠标点击文件项
    $("#main_right_content").click(function () {
        showOtherMenu();
    });

    // 有文件对象被选中，显示其他选项
    function showOtherMenu() {
        if ($(".file-opt").length > 0) {
            $("#optBtns").css("display", "inline");
        } else {
            $("#optBtns").hide();
        }
    }


    // 上传按钮单击____打开模态框，等待用户输入文件名及选择文件
    $("#btnUpload").click(function () {
        $("#filename").val("");
        $("#inputfile").val("");
        $("#uploadModal").modal('show');
    });

    // 新建按钮单击____打开模态框，等待用户输入文件夹名
    $("#btnNewFile").click(function () {
        $("#foldername").val("");
        $("#setFolderInfoModal").modal('show');
    });

    // 下载按钮单击
    $("#btnDownload").click(function () {
        $(".file-opt").each(function (i) {
            if ($(this).children("[name='fIsFolder']").val() === "true") {


                // 文件夹下载待开发


                alert("download folder " + $(this).children("[name='fId']").val() + "不能下载文件夹");
            } else {
                // 多文件下载待开发
                downloadFile(getFolderId(), $(this).children("[name='fId']").val());
            }
        });
    });

    // 粘贴按钮单击
    $("#btnPaste").click(function () {
        let f = path[path.length - 1];
        moveFile(f.id, optFileIdList);
        optFileIdList = [];
        $("#btnPaste").hide();
    });

    // 文件上传模态框提交按钮单击____ajax上传数据
    $("#btnUploadSubmit").click(function () {
        let data = new FormData($('#uploadForm')[0]);
        data.append("folderId", getFolderId());
        uploadFile(data);
        $("#uploadModal").modal('hide');
    });

    // 新建文件夹模态框提交按钮单击____ajax上传数据
    $("#btnNewFolderSubmit").click(function () {
        let data = JSON.stringify({
                folderId: getFolderId(),
                fileName: $("#foldername").val()
        });
        createFolder(data);
        $("#setFolderInfoModal").modal('hide');
    });

    // 更多操作下拉菜单中  重命名按钮  单击
    $("#other_menu_set_name").click(function () {
        if ($(".file-opt").length === 1) {
            let fId = $(".file-opt").children("[name='fId']").val();
            let fName = prompt("请输入文件（夹）名称:");
            setFileName(fId, fName);
        } else if ($(".file-opt").length === 0) {
            alert("请选中需要重命名的文件（夹）。");
        } else {
            alert("无法对多个文件（夹）同时重命名。");
        }
    });

    // 更多操作下拉菜单中  移动按钮  单击
    $("#other_menu_move_file").click(function () {
        $(".file-opt").each(function () {
            optFileIdList.push(Number($(this).children("[name='fId']").val()));
        });
        folderId = getFolderId();
        $("#btnPaste").css("display", "inline");
    });

    // 更多操作下拉菜单中  详情按钮  单击
    $("#other_menu_get_info").click(function () {
        if ($(".file-opt").length === 1) {
            let fId = $(".file-opt").children("[name='fId']").val();
            getInfo(fId);
        } else if ($(".file-opt").length === 0) {
            alert("请选中需要查看详情的文件（夹）。");
        } else {
            alert("无法同时查看多个文件（夹）详情。");
        }
    });

    // 更多操作下拉菜单中  分享按钮  单击
    $("#other_menu_share_file").click(function () {
        if ($(".file-opt").length === 1) {
            $("#showShareModal").modal('show');
        } else {
            alert("一次只能分享一个文件（夹）");
        }
    });

    // 分享弹窗中 确定按钮 单击
    $("#btn_share_file_submit").click(function () {
       const shareInfo = {
           username: $("#share_user").val(),
           shareUserRole: Number($("#share_user_role").val()),
           fileId: Number($(".file-opt").find("[name='fId']").first().val()),
           folderId: getFolderId(),
       };
       if (shareInfo.username.trim().length === 0) {
           $("#share_user").focus();
           alert("输入用户名！");
       } else {
           shareFile(shareInfo);
       }
    });

    // 删除按钮 单击
    $("#other_menu_del_file").click(function () {
        if (confirm("确定要永久删除选中的文件？")) {
            let fileIdList = [];
            $(".file-opt").each(function (i) {
                fileIdList.push(Number($(this).children("[name='fId']").val()));
            });
            delFile(fileIdList);
        }
    });

    // 返回按钮单击
    $("#btnReverse").click(function () {
        if (path.length > 1) {
            path.pop();
            let f = path.pop();
            getFlist(f.id, f.name);
        }
    });

    // 鼠标点击文件路径中的一项
    $("#file_path_inner").on("click", "a", function () {
        let id = $(this).attr("name");
        while (true) {
            let f = path.pop();
            if (f.id === id) {
                break;
            }
            if (path.length < 1) {
                break;
            }
        }
        getFlist(id, $(this).text());
    });

    // 鼠标单击文件项
    $("#main_right_content").on("click", ".file-item", function () {
        $(this).toggleClass("file-opt");
    });

    // 鼠标双击文件项
    $("#main_right_content").on("dblclick", ".file-item", function () {
        if ($(this).children().eq(1).val() === "true") {
            getFlist($(this).children().eq(0).val(), $(this).children().eq(-1).text());
        } else {
            alert("不能在云端打开文件。");
        }
    });

    // 鼠标移至文件项上方
    $("#main_right_content").on("mouseover", ".file-item", function () {
        $(this).addClass("file-focus");
    });

    // 鼠标离开文件项上方
    $("#main_right_content").on("mouseout", ".file-item", function () {
        $(this).removeClass("file-focus");
    });

    // 设置文件路径
    function setFilePath() {
        $("#file_path_inner").empty();
        $.each(path, function (i, item) {
            let htmlStr = "<a class='folder-a' name='" + item.id + "'>" + item.name + "</a><sapn class='fa fa-angle-right'></sapn>";
            $("#file_path_inner").append(htmlStr);
        });
    }

    let compare = function (prop, asc) {
        return function (obj1, obj2) {
            flag = asc?1:-1;
            let val1 = obj1[prop];
            let val2 = obj2[prop];if (val1 < val2) {
                return 1*flag;
            } else if (val1 > val2) {
                return -1*flag;
            } else {
                return 0;
            }
        }
    };

    // 设置文件目录
    function setFileList(data) {
        setFilePath();
        let htmlStr = "";
        data.sort(compare("fileName", false));
        data.sort(compare("fileType", true));
        $.each(data, function (i, item) {
            let fIsFolder = item.fileType === "folder";
            htmlStr += "<div class='file-item text-center' title='" + item.fileName + "'>";
            if (fIsFolder) {
                htmlStr += "<input type='hidden' value='" + item.fileId + "' name='fId'><input type='hidden' value='" + fIsFolder + "' name='fIsFolder'><span class='fa fa-folder folder-icon'></span>";
            } else {
                htmlStr += "<input type='hidden' value='" + item.fileId + "' name='fId'><input type='hidden' value='" + fIsFolder + "' name='fIsFolder'><span class='fa fa-file-o file-icon'></span>";
            }
            htmlStr += "<p>" + item.fileName + "</p></div>";
        });
        $("#main_right_content").append(htmlStr);
    }

    function getFileIdList(folderId) {
        $.ajax({
            url: "/cloud/fileid",
            data: JSON.stringify({
                folderId: folderId
            }),
            type: "post",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            async: true,
            success: function (result) {
                if (result.state === 0) {
                    fileIdList = result.data;
                    if (fileIdList.length > pageFileCount) {
                        getFileByIdList(fileIdList.slice(0, pageFileCount),true);
                    } else {
                        getFileByIdList(fileIdList, true);
                    }
                } else {
                    alert(result.info + '   [' + result.state + ']');
                }
            },
            error: function () {
                alert("ajax error");
            }
        });
    }

    function getFileByIdList(idList, isFristGet) {
        $.ajax({
            url: "/cloud/byfileid",
            data: JSON.stringify({
                folderId: getFolderId(),
                fileIdList: idList
            }),
            type: "post",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            async: true,
            success: function (result) {
                if (result.state === 0) {
                    if (isFristGet) {
                        $("#main_right_content").empty();
                    }
                    setFileList(result.data);
                } else {
                    alert(result.info + '   [' + result.state + ']');
                }
                $("#loading").hide();
            },
            error: function () {
                $("#loading").hide();
                alert("ajax error");
            }
        });


    }

    // 获取文件目录
    function getFlist(folderId, fName) {
        $("#loading").show();
        path.push(newFloder(folderId, fName));
        getFileIdList(folderId);
    }

    // 获取分享列表
    function getShareList() {
        $("#main_right_content").empty();
        $("#main_right_content").append("<div class='text-center'><h4>此功能尚未开放，敬请期待！</h4></div>");
    }

    // 获取分享给我的列表
    function getShareToMeList() {
        $("#main_right_content").empty();
        $("#main_right_content").append("<div class='text-center'><h4>111</h4></div>");
    }

    // 获取关于信息
    function getAbout() {
        $("#main_right_content").empty();
        $("#main_right_content").load("/about.html");
    }

    // 创建文件夹
    function createFolder(data) {
        $("#loading").show();
        $.ajax({
            url: "/cloud/createFolder",
            data: data,
            type: "post",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            async: true,
            success: function (data) {
                if (data.state === 0) {
                    let f = path.pop();
                    getFlist(f.id, f.name);
                } else {
                    alert(data.info + '   [' + data.state + ']');
                    $("#loading").hide();
                }
            },
            error: function () {
                $("#loading").hide();
                alert("ajax error");
            }
        });
    }

    //上传文件
    function uploadFile(data) {
        $("#loading").show();
        $.ajax({
            url: "/cloud/upload",
            data: data,
            cache: false,
            processData: false,
            contentType: false,
            type: "post",
            async: true,
            success: function (data) {
                if (data.state === 0) {
                    let f = path.pop();
                    getFlist(f.id, f.name);
                } else {
                    alert(data.info + '   [' + data.state + ']');
                    $("#loading").hide();
                }
            },
            error: function () {
                $("#loading").hide();
                alert("ajax error");
            }
        });
    }

    //下载文件
    function downloadFile(folderId, fileId) {
        let url = `/cloud/download/${folderId}/${fileId}`;
        let form = $("<form>");
        form.attr("style", "display:none");
        form.attr("id", "download_file_form_" + fileId);
        form.attr("method", "get");
        form.attr("action", url);
        $("body").append(form);
        let input1 = $("<input>");
        input1.attr("type", "hidden");
        input1.attr("name", "fileId");
        input1.attr("value", fileId);
        form.append(input1);
        form.submit();
        // window.location = "/cloud/download?fId=" + fId;
    }

    // 重命名
    function setFileName(fileId, fileName) {
        $("#loading").show();
        $.ajax({
            url: "/cloud/update",
            data: JSON.stringify({
                fileId: fileId,
                fileName: fileName,
                folderId: path[path.length-1].id
            }),
            type: "post",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            async: true,
            success: function (data) {
                if (data.state === 0) {
                    let f = path.pop();
                    getFlist(f.id, f.name);
                } else {
                    alert(data.info + '   [' + data.state + ']');
                    $("#loading").hide();
                }
            },
            error: function () {
                $("#loading").hide();
                alert("ajax error");
            }
        });
    }

    // 删除文件（夹）（fileIdList）
    function delFile(fileIdList) {
        $("#loading").show();
        $.ajax({
            url: "/cloud/delete",
            data: JSON.stringify({
                fileIdList: fileIdList,
                folderId: getFolderId(),
            }),
            type: "post",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            async: true,
            success: function (data) {
                if (data.state === 0) {
                    let f = path.pop();
                    getFlist(f.id, f.name);
                } else {
                    alert(data.info + '   [' + data.state + ']');
                    $("#loading").hide();
                }
            },
            error: function () {
                $("#loading").hide();
                alert("ajax error");
            }
        });
    }

    // 移动文件（newFloderId, fileIdList）目的文件夹Id，及移动文件（夹）序列
    function moveFile(newFloderId, fileIdList) {
        $("#loading").show();
        $.ajax({
            url: "/cloud/move",
            data: JSON.stringify({
                folderId: folderId,
                newFolderId: Number(newFloderId),
                fileIdList: fileIdList
            }),
            type: "post",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            async: true,
            traditional: true,
            success: function (data) {
                if (data.state === 0) {
                    let f = path.pop();
                    getFlist(f.id, f.name);
                } else {
                    alert(data.info + '   [' + data.state + ']');
                    $("#loading").hide();
                }
            },
            error: function () {
                $("#loading").hide();
                alert("ajax error");
            }
        });
    }

    // 获取文件信息（fId）
    function getInfo(fileId) {
        $("#loading").show();
        $.ajax({
            url: "/cloud/byfileid",
            data: JSON.stringify({
                folderId: getFolderId(),
                fileIdList: [Number(fileId)]
            }),
            type: "post",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            async: true,
            success: function (result) {
                if (result.state === 0) {
                    showInfo(result.data[0]);
                } else {
                    alert(result.info + '   [' + result.state + ']');
                }x
                $("#loading").hide();
            },
            error: function () {
                $("#loading").hide();
                alert("ajax error");
            }
        });
    }

    function showInfo(data) {
        let htmlStr = "<tr><td style='width: 80px;'>";
        if (data.fileType === "folder") {
            htmlStr += "<span class='fa fa-folder folder-icon' style='margin-right: 10px;'></span>";
        } else {
            htmlStr += "<span class='fa fa-file-o file-icon' style='margin-right: 10px;'></span>";
        }
        htmlStr += "</td><td><h4>" + data.fileName + "</h4></td></tr>";
        htmlStr += "<tr><td><b>创建时间:</b></td><td>" + new Date(data.createTime).toLocaleString() + "</td></tr>";
        htmlStr += "<tr><td><b>大小:</b></td><td>" + Math.floor(data.size/1024) + "KB</td></tr>";
        htmlStr += "<tr><td><b>md5:</b></td><td>" + data.md5 + "</td></tr>";
        $("#file_info").append(htmlStr);
        $("#showInfoModal").modal('show');
    }

    function getUserInfo() {
        $("#loading").show();
        $.ajax({
            url: "/user/",
            data: {},
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            async: true,
            success: function (result) {
                if (result.state === 0) {
                    showUserInfo(result.data);
                    getFlist(0, "全部");
                } else if (result.state === -20) {
                    alert("请登录！");
                    window.location.pathname = "/user/login.html"
                }
            },
            error: function () {
                $("#loading").hide();
                alert("ajax error");
            }
        });
    }

    function shareFile(fileShare) {
        $.ajax({
            url: "/share/add",
            data: JSON.stringify(fileShare),
            type: "post",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            async: true,
            success: function (result) {
                if (result.state === 0) {
                    alert(result.info);
                    modalHide();
                } else {
                    alert(result.info + '   [' + result.state + ']');
                }
            },
            error: function () {
                alert("ajax error");
            }
        });
    }

    // 构建文件夹对象
    function newFloder(id, name) {
        const folder = {};
        folder.id = id;
        folder.name = name;
        return folder
    }

    function getFolderId() {
        return Number(path[path.length - 1].id);
    }
});

// 关闭模态框
function modalHide() {
    $("#uploadModal").modal('hide');
    $("#setFolderInfoModal").modal('hide');
    $("#showInfoModal").modal('hide');
    $("#showShareModal").modal('hide');
    $("#file_info").empty();
}