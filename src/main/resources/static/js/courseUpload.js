/**
 * Created by Administrator on 2016/12/28.
 */
/**
 * 利用ssi_uploader上传图片
 * 后台接收到图片后将图片存储路径返回
 */
// jQuery('#ssi-upload3').ssi_uploader({
//     data: {
//   // 这两个参数写成固定字符串后台可接收，变量接收不到
//     },
//     url: '/fileUpload',
//     dropZone: true,
//     maxNumberOfFiles: 10,    // 每次允许上传多少个文件
//     maxFileSize: 6,  // 允许上传的最大文件尺寸
//     allowed: ['jpg', 'gif', 'txt', 'png', 'pdf'],    // 允许上传的文件类型
//     beforeUpload: function () {
//         //上传前执行的回调函数
//         console.log("文件上传准备就绪!")
//     },
//     beforeEachUpload: function () {
//         console.log("该文件准备上传！");
//         // 每一个单独的文件上传前执行的回调函数
//     },
//     onUpload: function (data) {
//        var path= data.result;
//         alert(path);
//         // 文件上传后执行的回调函数
//     }
// });

/**
 * ajax上传课程会顾内容
 *
 */
//将输入数据转换为json格式
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
function courseUpload() {
    var mode = $("select[name=mode]").val();
    var title = $("input[name=title]").val();
    var description = $("input[name=description]").val();
    var startTime = $("input[name=startTime]").val();
    var content = $('.textarea_editor').val();
    var price = $("input[name=price]").val();
    var path = $("input[name=path]").val();
    var cover = $("input[name=cover]").val();
    var nextId = $("select[name=nextId]").val();

    if(title.length <= 0 ){
        alert("主题不可为空!")
        return false;
    } else if(description.length <= 0){
        alert("摘要不可为空!")
        return false;
    }

    if(mode==3){
        if(path.length <= 0 ){
            alert("请上传图片!")
            return false;
        } else if(cover.length <= 0){
            alert("请上传封面!")
            return false;
        }
    } else if(mode==2){
        if(content.length <= 0 ){
            alert("内容不可为空!")
            return false;
        }
    }else{
        if(content.length <= 0 ){
            alert("内容不可为空!")
            return false;
        } else if(price.length <= 0){
            alert("请填写价格!")
            return false;
        }
    }

    if(nextId == null ){
        nextId=1;
    }
    if(price == null ){
        price=0;
    }
    var data = {
        mode:mode,
        title:title,
        description:description,
        startTime:startTime,
        content:content,
        price:price,
        path:path,
        cover:cover,
        nextId:nextId
    };
    var url = "course/saveCourse";
    loadPage(url,data);

}
